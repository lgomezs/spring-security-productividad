package com.gmd.productividad.service;

import com.gmd.productividad.domain.Colaborador;
import com.gmd.productividad.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * A custom service for retrieving users from a custom datasource, such as a database.
 * <p>
 * This custom service must implement Spring's {@link org.springframework.security.core.userdetails.UserDetailsService}
 */
@Transactional(readOnly = true)
@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private ColaboradorService colaboradorService;

    @Autowired
    private ParametroService parametroService;


	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

        Colaborador colaborador =  colaboradorService.findByUsername(username);
        if(colaborador==null){
            throw new UsernameNotFoundException("Error in retrieving user");
        }
        GrantedAuthority grantedAuthority = new GrantedAuthorityImpl(parametroService.getRolColaborador(Long.parseLong(String.valueOf(colaborador.getRol()))).getDetalle());
		return new CustomUserDetails(colaborador, Collections.singletonList(grantedAuthority));
	}
	

}
