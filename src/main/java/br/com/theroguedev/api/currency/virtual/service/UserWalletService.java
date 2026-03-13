package br.com.theroguedev.api.currency.virtual.service;

import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import br.com.theroguedev.api.currency.virtual.entity.UserWallet;
import br.com.theroguedev.api.currency.virtual.repository.UserWalletRepository;
import br.com.theroguedev.api.exceptions.CustomNotFoundException;
import br.com.theroguedev.api.exceptions.UnauthorizedException;
import br.com.theroguedev.api.user.entity.User;
import br.com.theroguedev.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<UserWallet> findByUserAndVirtualCurrency(User user, VirtualCurrency virtualCurrency) {
        return repository.findByUserAndVirtualCurrency(user, virtualCurrency);
    }

    public UserWallet save(UserWallet userWallet) {
        userWallet.setBalance(0L);
        userWallet.setUser(findUserById(userWallet.getUser().getId()));
        userWallet.setVirtualCurrency(findVirtualCurrencyById(userWallet.getVirtualCurrency().getId()));

        return repository.save(userWallet);
    }

    @Transactional
    public UserWallet addBalance(User user, VirtualCurrency virtualCurrency, Long amount) {
        UserWallet userWallet = findByUserAndVirtualCurrency(user, virtualCurrency).orElseThrow(
                () -> new CustomNotFoundException("Não foi encontrada nenhuma carteira para este usuário e moeda virtual")
        );

        userWallet.setBalance(userWallet.getBalance() + amount);

        return repository.save(userWallet);
    }

    @Transactional
    public UserWallet subtractBalance(User user, VirtualCurrency virtualCurrency, Long amount) {
        UserWallet userWallet = findByUserAndVirtualCurrency(user, virtualCurrency).orElseThrow(
            () -> new CustomNotFoundException("Não foi encontrada nenhuma carteira para este usuário e moeda virtual")
        );

        if (userWallet.getBalance() < amount) {
            throw new UnauthorizedException("Saldo da carteira do usuário é menor do que o valor necessário para realizar a ação");
        }

        userWallet.setBalance(userWallet.getBalance() - amount);

        return repository.save(userWallet);
    }

    private User findUserById(UUID id) {
        return userService.findById(id).orElse(null);
    }

    private VirtualCurrency findVirtualCurrencyById(Long id) {
        return virtualCurrencyService.findById(id).orElse(null);
    }


}
