package com.krince.memegle.global.security;

import com.krince.memegle.domain.user.dto.response.TokenDto;
import com.krince.memegle.global.constant.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.krince.memegle.global.response.ExceptionResponseCode.INVALID_TOKEN;

@Component
public class JwtProvider {

    private static final String TOKEN_TYPE = "Bearer ";

    private final Key secretKey;
    private final Long ACCESS_TOKEN_EXPIRED;
    private final Long REFRESH_TOKEN_EXPIRED;
    private final String HEADER_NAME = "Authorization";
    private final String ROLE = "role";

    public JwtProvider(
            @Value("${jwt.secret.key}") final String secretKey,
            @Value("${jwt.access-token-expired}") final Long accessTokenExpired,
            @Value("${jwt.refresh-token-expired}") final Long refreshTokenExpired
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.ACCESS_TOKEN_EXPIRED = accessTokenExpired;
        this.REFRESH_TOKEN_EXPIRED = refreshTokenExpired;
    }

    public String createAccessToken(final Long id, final Role role) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRED);

        return TOKEN_TYPE + Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim(ROLE, role)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(final Long id) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRED);

        return TOKEN_TYPE + Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public TokenDto generateTokenDto(final Long id, final Role role) {
        String accessToken = createAccessToken(id, role);
        String refreshToken = createRefreshToken(id);

        return TokenDto.of(accessToken, refreshToken);
    }

    public Boolean isValidToken(final String token) {
        try {
            return getClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    public Role getRole(final String token) {
        String roleValue = getClaimsJws(token)
                .getBody()
                .get(ROLE, String.class);

        return Role.valueOf(roleValue);
    }

    public Long getId(final String token) {
        String idValue = getClaimsJws(token)
                .getBody()
                .getSubject();

        return Long.parseLong(idValue);
    }

    private Jws<Claims> getClaimsJws(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }

    public String extractToken(final String token) {
        if (isInvalidTokenType(token)) {
            throw new IllegalArgumentException(INVALID_TOKEN.getMessage());
        }

        return token.substring(TOKEN_TYPE.length());
    }

    private Boolean isInvalidTokenType(final String token) {
        return token == null || !token.startsWith(TOKEN_TYPE);
    }

    public Claims getClaims(final String token) {
        return getClaimsJws(token).getBody();
    }

    public String getToken(HttpServletRequest request) {
        return request.getHeader(HEADER_NAME);
    }

}
