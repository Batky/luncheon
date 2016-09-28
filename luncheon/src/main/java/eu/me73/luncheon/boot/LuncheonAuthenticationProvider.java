package eu.me73.luncheon.boot;

import eu.me73.luncheon.user.api.Role;
import eu.me73.luncheon.user.api.User;
import eu.me73.luncheon.user.api.UserService;

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

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user;

        if (password.isEmpty()) {
            user = userService.getUserByCardFromStorage(username);
            if (Objects.isNull(user)) {
                user = userService.getUserByCard(username);
                if (Objects.nonNull(user)) {
                    ArrayList<Role> roles = new ArrayList<>();
                    roles.add(new Role("ROLE_USER"));
                    user.setAuthorities(roles);
                    userService.logInUser(user);
                }
            }
        } else {
            user = userService.getUserByCredentialsFromStorage(username, password);
            if (Objects.nonNull(user)) {
                user.setId(1L);
                userService.logInUser(user);
            }
        }

        if (user == null) {
            return null;
        }

        userService.logInUser(user);
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        return new UsernamePasswordAuthenticationToken(user, password, authorities);

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
