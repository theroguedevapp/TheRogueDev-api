package br.com.theroguedev.api.currency.virtual.service;

import br.com.theroguedev.api.currency.virtual.entity.TransactionType;
import br.com.theroguedev.api.currency.virtual.repository.TransactionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionTypeService {

    private final TransactionTypeRepository repository;

    public List<TransactionType> findAll() {
        return repository.findAllByOrderByNameAsc();
    }

    public Optional<TransactionType> findById(Long id) {
        return repository.findById(id);
    }

    public TransactionType save(TransactionType type) {
        return repository.save(type);
    }

}
