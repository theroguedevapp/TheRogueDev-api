package br.com.theroguedev.api.user.service;

import br.com.theroguedev.api.config.JWTUserData;
import br.com.theroguedev.api.exceptions.UnauthorizedException;
import br.com.theroguedev.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("E-mail ou senha inválido, tente novamente!"));
    }

    public JWTUserData validateUserAccess(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        if (!Objects.equals(userData.username(), username)) {
            throw new UnauthorizedException("Usuário não tem permissão para realizar essa ação");
        }

        return userData;
    }
}
