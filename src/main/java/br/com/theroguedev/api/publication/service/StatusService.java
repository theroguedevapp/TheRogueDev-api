package br.com.theroguedev.api.publication.service;

import br.com.theroguedev.api.exceptions.CustomNotFoundException;
import br.com.theroguedev.api.publication.entity.Status;
import br.com.theroguedev.api.publication.repository.StatusRepository;
import br.com.theroguedev.api.user.entity.SystemRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository repository;

    public List<Status> findAll() {
        return repository.findAllByOrderByNameAsc();
    }

    public Optional<Status> findById(Long id) {
        return repository.findById(id);
    }


    public Optional<Status> findByName(String name) {
        return repository.findByName(name);
    }

    public Status save(Status status) {
        status.setIsActive(false);
        return repository.save(status);
    }

    @Transactional
    public Status changeStatus(Long id, Boolean active) {
        Optional<Status> optStatus = repository.findById(id);
        if (optStatus.isEmpty()) {
            throw new CustomNotFoundException("Status não encontrado");
        }
        Status status = optStatus.get();
        status.setIsActive(active);

        repository.save(status);
        return status;
    }


}
