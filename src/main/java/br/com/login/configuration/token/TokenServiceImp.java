package br.com.login.configuration.token;

import br.com.login.configuration.UserDTO;
import br.com.login.controller.TokenForm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class TokenServiceImp implements TokenService{

    private final String secret = "sopadkjoaiofewnmsifvnesionrfioes";
    private final EntityTokenRepository tokenRepository;

    @Override
    public TokenDTO generatedToken(String username) {
        try {
            Algorithm algorithm = getAlgorithm();

            tokenRepository.findByUsername(username).ifPresent(tokenRepository::delete);

            LocalDateTime expiration = getExpiration();
            String token = JWT.create().
                    withIssuer("auth")
                    .withSubject(username)
                    .withExpiresAt(expiration.toInstant(ZoneOffset.of("-03:00")))
                    .sign(algorithm);
            EntityToken entityToken = new EntityToken(token, username, expiration);
            return tokenRepository.save(entityToken).dto();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static LocalDateTime getExpiration() {
        return LocalDateTime.now().plusMinutes(20);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(this.secret);
    }

    @Override
    public String recoveryEmailByToken(String tokenForm) {
        try {
            String tokenWithoutBearer = tokenWithoutBearer(tokenForm);
            Algorithm algorithm = getAlgorithm();
            Optional<EntityToken> entityTokenOpt = tokenRepository.findByToken(tokenWithoutBearer);
            if (entityTokenOpt.isEmpty())
                return "";

            String token = updateRefresh(entityTokenOpt.get());

            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            return "";
        }
    }

    private String updateRefresh(EntityToken entityToken) {
        if (entityToken.isRefreshNotValid()) {
            entityToken.setTokenRefreshExpiration(getExpiration());
            return tokenRepository.save(entityToken).getToken();
        }

        return entityToken.getToken();
    }

    private static String tokenWithoutBearer(String tokenForm) {
        return tokenForm.replace("Bearer ", "").strip();
    }

    @Override
    public void verifyToken(TokenForm form, UserDTO user) {
        Algorithm algorithm = getAlgorithm();
        String tokenWithoutBearer = tokenWithoutBearer(form.token());
        String username = JWT.require(algorithm)
                .withIssuer("auth")
                .build()
                .verify(tokenWithoutBearer)
                .getSubject();

        RuntimeException tokenInvalid = new RuntimeException("Token invalid");
        if (!username.equals(user.getUsername()))
            throw tokenInvalid;

        EntityToken entityToken = tokenRepository.findByToken(form.token())
                .orElseThrow(() -> new RuntimeException("token not found"));

        if (!entityToken.getUsername().equals(user.getUsername()))
            throw tokenInvalid;

        if (entityToken.isTokenNotValid()) {
            tokenRepository.delete(entityToken);
            throw new RuntimeException("expiration Token");
        }

        updateRefresh(entityToken);
    }
}
