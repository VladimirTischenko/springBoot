package springBoot.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Vladimir on 16.05.2020.
 */
public enum Role implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
