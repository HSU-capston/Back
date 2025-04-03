package capstone.SportyUp.SportyUp_Server.filter;

import capstone.SportyUp.SportyUp_Server.service.AuthService.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        String token = resolveToken(request); //Authorization 헤더에서 토큰 추출

        // 토큰이 없으면 필터 건너뜀 (스웨거나 로그인 X 요청 처리용)
        if (token == null || token.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = jwtService.extractUserId(token);
        String role = jwtService.extractRole(token);

        //토큰이 유효한지 확인
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //jwt유효성 검사
            if(jwtService.isTokenValid(token)){
                //권한 세팅
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

                //userId를 principal로 세팅
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //SecurityContext에 인증 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response); //다음 필터로 요청 전달
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7); //"Bearer " 제거

        return null;
    }
}
