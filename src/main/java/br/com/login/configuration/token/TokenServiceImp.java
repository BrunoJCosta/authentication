package br.com.login.configuration.token;

import br.com.login.controller.TokenForm;
import br.com.login.users.LoginDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class TokenServiceImp implements TokenService{

    @Value("${environment.token-secret}")
    private String secret;
    private final EntityTokenRepository tokenRepository;
    private final static Integer TEMPO_TOKEN_HORAS = 1;

    @Override
    public TokenDTO generatedToken(String username) {
        try {
            Algorithm algorithm = getAlgorithm();

            tokenRepository.findByUsername(username).ifPresent(tokenRepository::delete);

            OffsetTime offsetTime = OffsetTime.now(ZoneOffset.systemDefault()).plusMinutes(1);
            Instant instant = getExpiration().toInstant(offsetTime.getOffset());
            String token = JWT.create().
                    withIssuer("auth")
                    .withSubject(username)
                    .withExpiresAt(instant)
                    .sign(algorithm);
            LocalDateTime expiration = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            EntityToken entityToken = new EntityToken(token, username, expiration);
            return tokenRepository.save(entityToken).dto();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static LocalDateTime getExpirationRefresh() {
        return LocalDateTime.now().plusHours(20);
    }
    private static LocalDateTime getExpiration() {
        return LocalDateTime.now().plusHours(TEMPO_TOKEN_HORAS);
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

            EntityToken entityToken = entityTokenOpt.get();
            if (entityToken.isRefreshNotValid())
                return "";

            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(entityToken.getToken())
                    .getSubject();
        } catch (Exception e) {
            return "";
        }
    }

    private EntityToken updateRefresh(EntityToken entityToken) {
        if (entityToken.isRefreshNotValid()) {
            entityToken.setTokenRefreshExpiration(getExpirationRefresh());
            return tokenRepository.save(entityToken);
        }

        return entityToken;
    }

    private static String tokenWithoutBearer(String tokenForm) {
        return tokenForm.replace("Bearer ", "").strip();
    }

    @Override
    public LoginDTO verifyToken(TokenForm form) {
        Algorithm algorithm = getAlgorithm();
        String tokenWithoutBearer = tokenWithoutBearer(form.token());
        String username = JWT.require(algorithm)
                .withIssuer("auth")
                .build()
                .verify(tokenWithoutBearer)
                .getSubject();

        EntityToken entityToken = tokenRepository.findByToken(form.token())
                .orElseThrow(() -> new RuntimeException("token not found"));

        RuntimeException tokenInvalid = new RuntimeException("Token invalid");
        if (!entityToken.getUsername().equals(username))
            throw tokenInvalid;

        if (entityToken.isTokenNotValid()) {
            tokenRepository.delete(entityToken);
            throw new RuntimeException("expiration Token");
        }
        EntityToken tokenUpdated = updateRefresh(entityToken);
        return tokenUpdated.loginDTO(username);
    }
}
