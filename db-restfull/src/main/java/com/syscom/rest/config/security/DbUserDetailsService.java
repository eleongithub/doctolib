package com.syscom.rest.config.security;

import com.syscom.dao.UserRepository;
import com.syscom.domains.models.User;
import com.syscom.domains.models.referentiels.Fonctionnalite;
import com.syscom.domains.utils.Fonctionnalites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserDetailsService personnalisé
 * Created by Eric Legba on 06/08/17.
 */
//@Component
public class DbUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("Unknown %s user", login));
        }

        List<Fonctionnalite> fonctionnalites = user.getRole().getFonctionnalites();
        List<GrantedAuthority> grantedAuthorities = fonctionnalites.stream().map(fonctionnalite -> new SimpleGrantedAuthority(Fonctionnalites.ROLE_PREFIX+fonctionnalite.getCode()))
                                                                            .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                grantedAuthorities);
    }
}
