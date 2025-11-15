package pdevs.CursITU.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pdevs.CursITU.models.UserEntity;
import pdevs.CursITU.repositories.UserRepo;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {

        UserEntity user = userRepo.findByDni(dni)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con DNI " + dni + " no existe."));

        Collection<? extends GrantedAuthority> authorities =
                user.getRoles().stream().map(
                                role -> new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name()))
                        )
                        .collect(Collectors.toSet());

        return new User(
                user.getDni(),
                user.getClave(),
                true,
                true,
                true,
                true,
                authorities
        );
    }
}
