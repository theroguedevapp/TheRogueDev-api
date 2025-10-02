package br.com.theroguedev.api.currency.virtual.service;

import br.com.theroguedev.api.currency.virtual.entity.TransactionType;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import br.com.theroguedev.api.currency.virtual.repository.TransactionTypeRepository;
import br.com.theroguedev.api.currency.virtual.repository.VirtualCurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VirtualCurrencyService {

    private final VirtualCurrencyRepository repository;

    public List<VirtualCurrency> findAll() {
        return repository.findAllByOrderBySymbolAsc();
    }

    public Optional<VirtualCurrency> findById(Long id) {
        return repository.findById(id);
    }

    public VirtualCurrency save(VirtualCurrency virtualCurrency) {
        if (virtualCurrency.getExchangeRate() == null) {
            virtualCurrency.setExchangeRate(new BigDecimal("1"));
        }

        return repository.save(virtualCurrency);
    }

}
