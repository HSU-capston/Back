package capstone.SportyUp.SportyUp_Server.service.AuthService;

import capstone.SportyUp.SportyUp_Server.apiPayload.Exception.UserHandler;
import capstone.SportyUp.SportyUp_Server.apiPayload.code.status.ErrorStatus;
import capstone.SportyUp.SportyUp_Server.domain.RefreshToken;
import capstone.SportyUp.SportyUp_Server.domain.User;
import capstone.SportyUp.SportyUp_Server.domain.enums.Role;
import capstone.SportyUp.SportyUp_Server.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${spring.jwt.secretKey}")
    private String secretKey;
    private final long accessTokenExpirationMs = 1000 * 60 * 60 * 10; //AccessToken만료시간 10시간
    private final long refreshTokenExpirationMs = 1000L * 60 * 60 * 24 * 7; //7일
    private final RefreshTokenRepository refreshTokenRepository;

    public String createAccessToken(Long userId, Role role) {

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject("AccessToken")
                .claim("userId", userId)
                .claim("role","ROLE_"+role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return accessToken;
    }

    public String createRefreshToken(Long userId){

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setSubject("RefreshToken")
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+refreshTokenExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return refreshToken;
    }

    public void saveRefreshToken(User user, String token){
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(token)
                .expiredAt(LocalDateTime.now().plus(Duration.ofMillis(refreshTokenExpirationMs)))
                .build();

        refreshTokenRepository.save(refreshToken);

    }

    public void refreshTokenUpdate(User user, String newToken){
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user).orElseThrow(() -> new UserHandler(ErrorStatus.REFRESH_TOKEN_NOT_FOUND));

        refreshToken.setToken(newToken);
        refreshToken.setExpiredAt(LocalDateTime.now().plus(Duration.ofMillis(refreshTokenExpirationMs)));

        refreshTokenRepository.save(refreshToken);
    }

    public Long extractUserId(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    public String extractRole(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public boolean isTokenValid(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token) //예외 발생 시 예외 던짐
                .getBody();

        return true;
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // jjwt.io의 Decoders 사용
        return Keys.hmacShaKeyFor(keyBytes); // HMAC 전용 키 생성
    }


}
