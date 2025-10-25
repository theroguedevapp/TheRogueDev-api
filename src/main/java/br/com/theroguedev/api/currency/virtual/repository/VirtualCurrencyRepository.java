package br.com.theroguedev.api.currency.virtual.repository;

import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VirtualCurrencyRepository extends JpaRepository<VirtualCurrency, Long> {

    List<VirtualCurrency> findAllByOrderBySymbolAsc();

    Optional<VirtualCurrency> findBySymbol(String symbol);

}
