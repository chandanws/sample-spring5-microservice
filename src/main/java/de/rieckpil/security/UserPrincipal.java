package de.rieckpil.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

  private static final long serialVersionUID = -4466754300954333833L;

  private User user;

  public UserPrincipal(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return user.isEnabled();
  }

  @Override
  public boolean isAccountNonLocked() {
    return user.isEnabled();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return user.isEnabled();
  }

  @Override
  public boolean isEnabled() {
    return user.isEnabled();
  }

}
