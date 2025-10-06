package br.com.theroguedev.api.currency.virtual.service;

import br.com.theroguedev.api.currency.virtual.entity.*;
import br.com.theroguedev.api.currency.virtual.repository.ForumPublicationBalanceRepository;
import br.com.theroguedev.api.exceptions.CustomNotFoundException;
import br.com.theroguedev.api.exceptions.UnauthorizedException;
import br.com.theroguedev.api.publication.entity.ForumPublication;
import br.com.theroguedev.api.publication.repository.ForumPublicationRepository;
import br.com.theroguedev.api.shared.record.VoteDirectionEnum;
import br.com.theroguedev.api.user.entity.User;
import br.com.theroguedev.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForumPublicationBalanceService {

    private final ForumPublicationBalanceRepository repository;
    private final ForumPublicationRepository forumPublicationRepository;

    private final VirtualCurrencyService virtualCurrencyService;
    private final TransactionParameterService transactionParameterService;
    private final TransactionTypeService transactionTypeService;
    private final TransactionService transactionService;
    private final UserWalletService userWalletService;
    private final UserService userService;

    public List<ForumPublicationBalance> findAll() {
        return repository.findAll();
    }

    public Optional<ForumPublicationBalance> findById(UUID id) {
        return repository.findById(id);
    }

    public ForumPublicationBalance save(ForumPublicationBalance forumPublicationBalance) {
        forumPublicationBalance.setCredit(0L);
        forumPublicationBalance.setDebit(0L);
        forumPublicationBalance.setVirtualCurrency(findVirtualCurrencyById(forumPublicationBalance.getVirtualCurrency().getId()));
        forumPublicationBalance.setForumPublication(findForumPublicationById(forumPublicationBalance.getForumPublication().getId()));

        return repository.save(forumPublicationBalance);
    }

    @Transactional
    public ForumPublicationBalance voteForumPublication(String forumPublicationSlug, UUID userId, VoteDirectionEnum direction) {

        User user = userService.findById(userId)
                .orElseThrow(() -> new CustomNotFoundException("Usuário não encontrado"));

        ForumPublication forumPublication = forumPublicationRepository.findBySlug(forumPublicationSlug)
                .orElseThrow(() -> new CustomNotFoundException("Publicação não encontrada"));

        if (forumPublication.getAuthors().contains(user)) {
            throw new UnauthorizedException("O autor não pode votar na própria publicação");
        }

        if (transactionService.existsByUserAndRelatedForumPublication(user, forumPublication)) {
            throw new UnauthorizedException("Usuário já votou nesta publicação");
        }

        String parameterName = direction == VoteDirectionEnum.DOWN ? "downvote_forum_article" : "upvote_forum_article";
        TransactionParameter transactionParameter = findTransactionParameterByName(parameterName);

        ForumPublicationBalance forumPublicationBalance = repository.findByForumPublicationAndVirtualCurrency(forumPublication, transactionParameter.getVirtualCurrency()).orElseThrow(
                () -> new CustomNotFoundException("Não foi encontrada nenhuma carteira para esta publicação e moeda virtual")
        );
        userWalletService.subtractBalance(user, transactionParameter.getVirtualCurrency(), transactionParameter.getCost());

        Transaction transaction = Transaction.builder()
                .transactionType(findTransactionTypeByName("SPEND"))
                .amount(transactionParameter.getCost())
                .virtualCurrency(transactionParameter.getVirtualCurrency())
                .relatedForumPublication(forumPublicationBalance.getForumPublication())
                .user(user)
                .build();

        transactionService.save(transaction);

        if (direction == VoteDirectionEnum.DOWN) {
            forumPublicationBalance.incrementDebit();
        } else {
            forumPublicationBalance.incrementCredit();
        }

        return repository.save(forumPublicationBalance);
    }

    private ForumPublication findForumPublicationById(UUID id) {
        return forumPublicationRepository.findById(id).orElse(null);
    }


    private VirtualCurrency findVirtualCurrencyById(Long id) {
        return virtualCurrencyService.findById(id).orElse(null);
    }

    private TransactionType findTransactionTypeByName(String name) {
        return transactionTypeService.findByName(name).orElse(null);
    }

    private TransactionParameter findTransactionParameterByName(String name) {
        return transactionParameterService.findByName(name).orElse(null);
    }

}
