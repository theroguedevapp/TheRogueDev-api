package br.com.theroguedev.api.currency.virtual.repository;

import br.com.theroguedev.api.currency.virtual.entity.TransactionParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransactionParameterRepository extends JpaRepository<TransactionParameter, Long> {

    Optional<TransactionParameter> findByName(String name);

}
