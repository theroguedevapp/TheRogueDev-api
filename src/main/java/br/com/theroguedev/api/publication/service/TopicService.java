package br.com.theroguedev.api.publication.service;

import br.com.theroguedev.api.exceptions.CustomNotFoundException;
import br.com.theroguedev.api.publication.entity.Topic;
import br.com.theroguedev.api.publication.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository repository;

    public List<Topic> findAll() {
        return repository.findAllByOrderByNameAsc();
    }

    public Optional<Topic> findById(Long id) {
        return repository.findById(id);
    }

    public Topic save(Topic topic) {
        topic.setIsActive(false);
        return repository.save(topic);
    }

    @Transactional
    public Topic changeStatus(Long id, Boolean active) {
        Optional<Topic> optTopic = repository.findById(id);
        if (optTopic.isEmpty()) {
            throw new CustomNotFoundException("Topic não encontrado");
        }
        Topic topic = optTopic.get();
        topic.setIsActive(active);

        repository.save(topic);
        return topic;
    }



}
