package br.com.theroguedev.api.publication.service;

import br.com.theroguedev.api.exceptions.CustomNotFoundException;
import br.com.theroguedev.api.publication.entity.Status;
import br.com.theroguedev.api.publication.entity.Tool;
import br.com.theroguedev.api.publication.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ToolRepository repository;

    public List<Tool> findAll() {
        return repository.findAllByOrderByNameAsc();
    }

    public Optional<Tool> findById(Long id) {
        return repository.findById(id);
    }

    public Tool save(Tool tool) {
        tool.setIsActive(false);
        return repository.save(tool);
    }

    @Transactional
    public Tool changeStatus(Long id, Boolean active) {
        Optional<Tool> optTool = repository.findById(id);
        if (optTool.isEmpty()) {
            throw new CustomNotFoundException("Tool não encontrada");
        }
        Tool tool = optTool.get();
        tool.setIsActive(active);

        repository.save(tool);
        return tool;
    }
}
