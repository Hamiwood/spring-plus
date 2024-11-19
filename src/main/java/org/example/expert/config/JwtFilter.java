package org.example.expert.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.security.UserRoleEnum;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = bearerToken.substring(7);
        try {
            Claims claims = jwtUtil.extractClaims(token);

            Long userId = Long.valueOf(claims.getSubject());
            UserRoleEnum userRoleEnum = UserRoleEnum.valueOf(claims.get("userRoleEnum", String.class));

            List<GrantedAuthority> authorities =
                    Collections.singletonList(new SimpleGrantedAuthority(userRoleEnum.getAuthority()));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 JWT 서명입니다.");
            return;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT 토큰입니다.");
            return;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰입니다.");
            return;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument in JWT claims", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT Claims에 잘못된 값이 포함되어 있습니다.");
            return;
        } catch (Exception e) {
            log.error("Unexpected error during JWT processing", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JWT 처리 중 내부 서버 오류가 발생했습니다.");
            return;
        }
        filterChain.doFilter(request, response);

    }
}
