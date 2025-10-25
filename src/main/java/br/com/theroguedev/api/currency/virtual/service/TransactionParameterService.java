package br.com.theroguedev.api.currency.virtual.service;

import br.com.theroguedev.api.currency.virtual.entity.TransactionParameter;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import br.com.theroguedev.api.currency.virtual.repository.TransactionParameterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionParameterService {

    private final TransactionParameterRepository repository;
    private final VirtualCurrencyService virtualCurrencyService;

    public List<TransactionParameter> findAll() {
        return repository.findAll();
    }

    public Optional<TransactionParameter> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<TransactionParameter> findByName(String name) {
        return repository.findByName(name);
    }

    @Transactional
    public TransactionParameter save(TransactionParameter transaction) {
        transaction.setVirtualCurrency(findVirtualCurrencyById(transaction.getVirtualCurrency().getId()));

        return repository.save(transaction);
    }

    private VirtualCurrency findVirtualCurrencyById(Long id) {
        return virtualCurrencyService.findById(id).orElse(null);
    }

}
