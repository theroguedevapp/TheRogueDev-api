package br.com.theroguedev.api.currency.virtual.repository;

import br.com.theroguedev.api.currency.virtual.entity.ForumPublicationBalance;
import br.com.theroguedev.api.currency.virtual.entity.UserWallet;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import br.com.theroguedev.api.publication.entity.ForumPublication;
import br.com.theroguedev.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ForumPublicationBalanceRepository extends JpaRepository<ForumPublicationBalance, UUID> {

    Optional<ForumPublicationBalance> findByForumPublicationAndVirtualCurrency(ForumPublication forumPublication, VirtualCurrency virtualCurrency);

}
