package FileStorage.service1.util;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${thunderstore.jwt.secret-key}")
    private String SECRET_KEY;

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public int extractUserId(String token)
    {
        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims((DefaultClaims) extractAllClaims(token));
        return  Integer.parseInt(expectedMap.get("user_id").toString());
    }

    public int extractOrganizationId(String token)
    {
        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims((DefaultClaims) extractAllClaims(token));
        return  Integer.parseInt(expectedMap.get("org_id").toString());
    }

    private Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims)
    {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;

    }
}