package eu.me73.luncheon.boot;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.user.api.Role;
import eu.me73.luncheon.user.api.User;
import eu.me73.luncheon.user.api.UserService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class LuncheonAuthenticationProvider implements AuthenticationProvider {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(LuncheonAuthenticationProvider.class);

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Attempt to login: {}", authentication);
        }
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        if (LocalDate.now().getYear() > 2017) {
            return null;
        }

        User user;

        if (password.isEmpty()) {
            user = userService.getUserByCardFromStorage(username);
            if (Objects.isNull(user)) {
                user = userService.getUserByCard(username);
                if (Objects.nonNull(user)) {
                    ArrayList<Role> roles = new ArrayList<>();
                    roles.add(new Role("ROLE_USER"));
                    user.setAuthorities(roles);
                }
            } else {
                if (Objects.isNull(userService.getUserByCard(user.getBarCode()))) {
                    userService.save(user);
                }
                user.setId(userService.getUserByCard(user.getBarCode()).getId());
            }
        } else {
            boolean saved = false;
            user = userService.getUserByCredentialsFromStorage(username, password);
            if (Objects.nonNull(user)) {
                if (Objects.isNull(userService.getUserByCard(user.getBarCode()))) {
                    userService.save(user);
                    saved = true;
                }
                user.setId(userService.getUserByCard(user.getBarCode()).getId());
                if (!saved) {
                    userService.save(user);
                }
            }
        }

        if (user == null) {
            return null;
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        return new UsernamePasswordAuthenticationToken(user, password, authorities);

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
