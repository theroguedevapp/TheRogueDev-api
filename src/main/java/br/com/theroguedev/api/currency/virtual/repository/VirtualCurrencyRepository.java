package br.com.theroguedev.api.currency.virtual.repository;

import br.com.theroguedev.api.currency.virtual.entity.TransactionType;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VirtualCurrencyRepository extends JpaRepository<VirtualCurrency, Long> {

    List<VirtualCurrency> findAllByOrderBySymbolAsc();

}
