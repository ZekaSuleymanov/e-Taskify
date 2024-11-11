package az.atlacademy.etaskify.util;
import az.atlacademy.etaskify.dto.response.ExceptionResponse;
import az.atlacademy.etaskify.exception.ApplicationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class TokenUtil {

    private static String SECRET_KEY = "srthealik1234srthealik1234srthealik1234srthealik1234srthealik1234";
    @Value( "${app.time.token.access}")
    private static long jwtTokenTime;


    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public List<SimpleGrantedAuthority> extractAuthorities(String jwt) {
        List<String> authorities = (List<String>) extractAllClaims(jwt).get("authorities");

        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public long extractOrganizationId(String jwt) {
        return (int) extractAllClaims(jwt).get("orgId");
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolve) {
        Claims claims = extractAllClaims(token);

        return claimResolve.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        Claims body;
        try {

            body = Jwts.parser()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (RuntimeException e){
            throw ApplicationException.of(ExceptionResponse.of(e.getMessage(), HttpStatus.UNAUTHORIZED));
        }
        return body;

    }

    public int extractUserId(String jwt) {
        return Integer.parseInt(extractAllClaims(jwt).getId());
    }

    public boolean isTokenValid(String jwt) {
        return isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        return extractExpression(jwt).after(new Date());
    }

    private Date extractExpression(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    public String generateToken(String email, long userId, Map<String, Object> extraClaims) {
        return generateToken(extraClaims, email, userId);
    }

    private String generateToken(Map<String, Object> map, String email, long userId) {

        return Jwts.
                builder()
                .addClaims(map)
                .setId(String.valueOf(userId))
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 18000000))
                .signWith(getSecretKey())
                .compact();
    }

    private Key getSecretKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA512");
    }

}
