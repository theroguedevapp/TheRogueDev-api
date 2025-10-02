package br.com.theroguedev.api.currency.virtual.service;

import br.com.theroguedev.api.currency.virtual.entity.UserWallet;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import br.com.theroguedev.api.currency.virtual.repository.UserWalletRepository;
import br.com.theroguedev.api.user.entity.User;
import br.com.theroguedev.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserWalletService {

    private final UserWalletRepository repository;
    private final UserService userService;
    private final VirtualCurrencyService virtualCurrencyService;


    public List<UserWallet> findAll() {
        return repository.findAll();
    }

    public Optional<UserWallet> findById(UUID id) {
        return repository.findById(id);
    }

    public UserWallet save(UserWallet userWallet) {
        userWallet.setBalance(0L);
        userWallet.setUser(findUserById(userWallet.getUser().getId()));
        userWallet.setVirtualCurrency(findVirtualCurrencyById(userWallet.getVirtualCurrency().getId()));

        return repository.save(userWallet);
    }

    private User findUserById(UUID id) {
        return userService.findById(id).orElse(null);
    }

    private VirtualCurrency findVirtualCurrencyById(Long id) {
        return virtualCurrencyService.findById(id).orElse(null);
    }


}
