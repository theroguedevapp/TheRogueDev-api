package br.com.theroguedev.api.currency.virtual.repository;

import br.com.theroguedev.api.currency.virtual.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

    List<TransactionType> findAllByOrderByNameAsc();

    Optional<TransactionType> findByName(String name);
}
