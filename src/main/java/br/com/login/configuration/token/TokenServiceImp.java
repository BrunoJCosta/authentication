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

            LocalDateTime expiration = LocalDateTime.now().plusMinutes(20);
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

    private Algorithm getAlgorithm() {
        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        return algorithm;
    }

    @Override
    public String recoveryEmailByToken(String tokenForm) {
        try {
            String tokenWithoutBearer = tokenWithoutBearer(tokenForm);
            Algorithm algorithm = getAlgorithm();
            Optional<EntityToken> token = tokenRepository.findByToken(tokenWithoutBearer);
            if (token.isEmpty())
                return "";

            if (token.get().isNotValid()) {
                tokenRepository.delete(token.get());
                throw new RuntimeException("expiration Token");
            }

            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token.get().getToken())
                    .getSubject();
        } catch (Exception e) {
            return "";
        }
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
        if (!username.equals(user.getUsername()))
            throw new RuntimeException("Token invalid");

        EntityToken token = tokenRepository.findByToken(form.token())
                .orElseThrow(() -> new RuntimeException("token not found"));

        if (token.isValid()) {
            return;
        }
        tokenRepository.delete(token);
        throw new RuntimeException("expiration Token");
    }
}
