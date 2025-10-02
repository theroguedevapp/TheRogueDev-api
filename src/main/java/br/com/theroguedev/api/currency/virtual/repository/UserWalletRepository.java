package br.com.theroguedev.api.currency.virtual.repository;

import br.com.theroguedev.api.currency.virtual.entity.Transaction;
import br.com.theroguedev.api.currency.virtual.entity.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserWalletRepository extends JpaRepository<UserWallet, UUID> {

}
