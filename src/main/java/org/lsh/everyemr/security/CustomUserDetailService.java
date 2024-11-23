package org.lsh.everyemr.security;

import lombok.RequiredArgsConstructor;
import org.lsh.everyemr.entity.User;
import org.lsh.everyemr.repository.UserRepository;
import org.lsh.everyemr.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        Set<GrantedAuthority> authorities = Set.of(SecurityUtils
                .convertToAuthority(user.getRole().name()));
        return UserPrinciple.builder()
                .user(user)
                .username(username)
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
