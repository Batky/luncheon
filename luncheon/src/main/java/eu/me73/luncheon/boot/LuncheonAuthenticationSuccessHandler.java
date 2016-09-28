package eu.me73.luncheon.boot;

import ch.qos.logback.classic.Logger;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LuncheonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(LuncheonAuthenticationSuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response, Authentication authentication) throws IOException {
            handle(request, response, authentication);
            clearAuthenticationAttributes(request);
        }

        protected void handle(HttpServletRequest request,
                              HttpServletResponse response, Authentication authentication) throws IOException {
            String targetUrl = determineTargetUrl(authentication);

            if (response.isCommitted()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Response has already been committed. Unable to redirect to " + targetUrl);
                }
                return;
            }

            redirectStrategy.sendRedirect(request, response, targetUrl);
        }

        /** Builds the target URL according to the logic defined in the main class Javadoc. */
        protected String determineTargetUrl(Authentication authentication) {
            boolean isUser = false;
            boolean isAdmin = false;
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                    isUser = true;
                    break;
                } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                    isAdmin = true;
                    break;
                }
            }

            if (isUser) {
                LOG.info("Ordinal user logged in");
                return "/orders";
            } else if (isAdmin) {
                LOG.info("Admin is logged in");
                return "/lunches";
            } else {
                throw new IllegalStateException();
            }
        }

        protected void clearAuthenticationAttributes(HttpServletRequest request) {
            HttpSession session = request.getSession(false);
            if (session == null) {
                return;
            }
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }

        public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
            this.redirectStrategy = redirectStrategy;
        }
        protected RedirectStrategy getRedirectStrategy() {
            return redirectStrategy;
        }
}
