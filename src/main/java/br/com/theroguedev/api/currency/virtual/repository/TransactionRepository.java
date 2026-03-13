package br.com.theroguedev.api.currency.virtual.repository;

import br.com.theroguedev.api.currency.virtual.entity.Transaction;
import br.com.theroguedev.api.publication.entity.ForumPublication;
import br.com.theroguedev.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    boolean existsByUserAndRelatedForumPublication(User user, ForumPublication forumPublication);

}
