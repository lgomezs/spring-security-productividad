package com.gmd.productividad.util;

import com.gmd.productividad.domain.Colaborador;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alozano
 * Date: 31/01/13
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustomUserDetails implements UserDetails{
    private List<GrantedAuthority> grantedAuthorities;
    private Colaborador colaborador;

    public CustomUserDetails(Colaborador colaborador, List<GrantedAuthority> grantedAuthorities) throws UsernameNotFoundException {
        this.colaborador = colaborador;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }
    public boolean isUserInRole(final String role) {
        if (CommonUtils.isBlank(role)) {
            return false;
        }
        for (final GrantedAuthority o : grantedAuthorities) {
            if(o.getAuthority().equals(role)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getPassword() {
        return colaborador.getPassword();
    }

    @Override
    public String getUsername() {
        return colaborador.getUsuario();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }
}
