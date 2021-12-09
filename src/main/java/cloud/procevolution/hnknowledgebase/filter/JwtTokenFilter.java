package cloud.procevolution.hnknowledgebase.filter;

import cloud.procevolution.hnknowledgebase.entity.User;
import cloud.procevolution.hnknowledgebase.service.JwtTokenService;
import cloud.procevolution.hnknowledgebase.service.UserService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        final String[] schemeToken = authHeader.split(" ");
        if(authHeader.isBlank()
                || authHeader.isEmpty()
                || schemeToken.length != 2
                || !schemeToken[0].equals("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = schemeToken[1].trim();
        UsernamePasswordAuthenticationToken authentication = null;

        try {
            System.out.println("reached validate");
            if (!this.jwtTokenService.validate(token)) {
                System.out.println("not valid");
                filterChain.doFilter(request, response);
                return;
            }
            System.out.println("valid");

            final User user = this.jwtTokenService.fromToken(token).orElseGet(() -> {
                System.out.println("cant decode token + " + token );
                return new User();
            });
            System.out.println("user: " + user);
            authentication =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );
            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().split("/")[1].contains("public");
    }
}
