package getir.bookretailfirm.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class UserAuthentication implements Authentication {

    private static final long serialVersionUID = 1412499474507793355L;

    private final IdentityUser identityUser;
    private boolean authenticated = true;

    public UserAuthentication(IdentityUser identityUser) {
        this.identityUser = identityUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public IdentityUser getDetails() {
        return identityUser;
    }

    @Override
    public Object getPrincipal() {
        return identityUser.getEmail();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return identityUser.getFirstName() + " " + identityUser.getLastName();
    }
}
