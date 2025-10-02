package br.com.theroguedev.api.currency.virtual.repository;

import br.com.theroguedev.api.currency.virtual.entity.ForumPublicationBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ForumPublicationBalanceRepository extends JpaRepository<ForumPublicationBalance, UUID> {

}
