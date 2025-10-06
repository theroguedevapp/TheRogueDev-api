package br.com.theroguedev.api.user.service;

import br.com.theroguedev.api.currency.virtual.entity.UserWallet;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import br.com.theroguedev.api.currency.virtual.service.UserWalletService;
import br.com.theroguedev.api.currency.virtual.service.VirtualCurrencyService;
import br.com.theroguedev.api.user.entity.SystemRole;
import br.com.theroguedev.api.user.entity.User;
import br.com.theroguedev.api.user.entity.UserProfile;
import br.com.theroguedev.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final SystemRoleService systemRoleService;
    private final VirtualCurrencyService virtualCurrencyService;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return  repository.findByUsername(username);
    }

    @Transactional
    public User save(User user, String userProfileName) {
        user.setIsActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSystemRole(findSystemRoleByName());

        UserProfile profile = UserProfile
                .builder()
                .user(user)
                .name(userProfileName)
                .build();
        user.setUserProfile(profile);

        List<UserWallet> userWallets = new ArrayList<>();
        userWallets.add( UserWallet.builder()
                .user(user)
                .balance(0L)
                .virtualCurrency(findVirtualCurrencyBySymbol())
                .build()
        );
        user.setWallets(userWallets);

        return repository.save(user);
    }

    private SystemRole findSystemRoleByName() {
        return systemRoleService.findByName("USER").orElse(null);
    }

    private VirtualCurrency findVirtualCurrencyBySymbol() {
        return virtualCurrencyService.findBySymbol("RD").orElse(null);
    }

}
