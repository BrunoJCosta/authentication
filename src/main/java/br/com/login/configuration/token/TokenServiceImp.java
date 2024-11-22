package br.com.login.configuration.token;

import br.com.login.exception.AuthException;
import br.com.login.exception.TokenExpiration;
import br.com.login.exception.TokenInvalid;
import br.com.login.exception.TokenNotFound;
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
    private final static Integer TEMPO_TOKEN_HORAS = 8;

    @Override
    public TokenDTO generatedToken(String username) {
        try {
            Algorithm algorithm = getAlgorithm();
            tokenRepository.findByUsername(username).ifPresent(tokenRepository::delete);

            Instant instant = getInstantExpiration();
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

    private static Instant getInstantExpiration() {
        OffsetTime offsetTime = OffsetTime.now(ZoneOffset.systemDefault());
        return LocalDateTime.now().plusHours(TEMPO_TOKEN_HORAS).toInstant(offsetTime.getOffset());
    }

    protected static LocalDateTime getExpirationRefresh() {
        return LocalDateTime.now().plusMinutes(20);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(this.secret);
    }

    @Override
    public String recoveryEmailByToken(String tokenForm) {
        try {
            String tokenWithoutBearer = tokenWithoutBearer(tokenForm);
            Optional<EntityToken> entityTokenOpt = tokenRepository.findByToken(tokenWithoutBearer);
            if (entityTokenOpt.isEmpty())
                return "";

            EntityToken entityToken = entityTokenOpt.get();
            if (entityToken.isRefreshNotValid())
                return "";

            return entityToken.getUsername();
        } catch (Exception e) {
            return "";
        }
    }

    private static String tokenWithoutBearer(String tokenForm) {
        return tokenForm.replace("Bearer ", "").strip();
    }

    @Override
    public LoginDTO verifyToken(TokenForm form) throws AuthException {
        Algorithm algorithm = getAlgorithm();
        String tokenWithoutBearer = tokenWithoutBearer(form.token());
        String username = JWT.require(algorithm)
                .withIssuer("auth")
                .build()
                .verify(tokenWithoutBearer)
                .getSubject();

        EntityToken entityToken = tokenRepository.findByToken(tokenWithoutBearer)
                .orElseThrow(TokenNotFound::new);

        if (!entityToken.getUsername().equals(username))
            throw new TokenInvalid();

        if (entityToken.isTokenNotValid()) {
            tokenRepository.delete(entityToken);
            throw new TokenExpiration();
        }

        if (entityToken.isRefreshNotValid()) {
            entityToken.setTokenRefreshExpiration(getExpirationRefresh());
            return tokenRepository.save(entityToken).loginDTO(username);
        }
        return entityToken.loginDTO(username);
    }
}
