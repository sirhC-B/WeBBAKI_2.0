package de.thb.webbaki.security;

import de.thb.webbaki.repository.UserRepository;
import de.thb.webbaki.security.authority.UserAuthority;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByEmail(username)
                .map(user -> MyUserDetails.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .authorities(List.of(new UserAuthority()))
                        .enabled(user.isEnabled())
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .lastLogin(user.getLastLogin())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
