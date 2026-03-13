package br.com.theroguedev.api.publication.service;

import br.com.theroguedev.api.exceptions.CustomNotFoundException;
import br.com.theroguedev.api.publication.entity.Type;
import br.com.theroguedev.api.publication.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository repository;

    public List<Type> findAll() {
        return repository.findAllByOrderByNameAsc();
    }

    public Optional<Type> findById(Long id) {
        return repository.findById(id);
    }

    public Type save(Type type) {
        type.setIsActive(false);
        return repository.save(type);
    }

    @Transactional
    public Type changeStatus(Long id, Boolean active) {
        Optional<Type> optType = repository.findById(id);
        if (optType.isEmpty()) {
            throw new CustomNotFoundException("Type não encontrado");
        }

        Type type = optType.get();
        type.setIsActive(active);

        repository.save(type);
        return type;
    }


}
