package br.com.theroguedev.api.currency.virtual.service;

import br.com.theroguedev.api.currency.virtual.entity.Transaction;
import br.com.theroguedev.api.currency.virtual.entity.TransactionType;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import br.com.theroguedev.api.currency.virtual.repository.TransactionRepository;
import br.com.theroguedev.api.publication.entity.ForumPublication;
import br.com.theroguedev.api.publication.service.ForumPublicationService;
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
public class TransactionService {

    private final TransactionRepository repository;
    private final UserService userService;
    private final TransactionTypeService transactionTypeService;
    private final VirtualCurrencyService virtualCurrencyService;
    private final ForumPublicationService forumPublicationService;


    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Optional<Transaction> findById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public Transaction save(Transaction transaction, UUID userId) {

        if (transaction.getRelatedUser() != null) {
            transaction.setRelatedUser(findUserById(transaction.getRelatedUser().getId()));
        }

        if (transaction.getRelatedForumPublication() != null) {
            transaction.setRelatedForumPublication(findForumPublicationById(transaction.getRelatedForumPublication().getId()));
        }

        transaction.setUser(findUserById(userId));
        transaction.setTransactionType(findTransactionTypeById(transaction.getTransactionType().getId()));
        transaction.setVirtualCurrency(findVirtualCurrencyById(transaction.getVirtualCurrency().getId()));
        transaction.setTransactionType(findTransactionTypeById(transaction.getTransactionType().getId()));

        return repository.save(transaction);
    }

    private User findUserById(UUID id) {
        return userService.findById(id).orElse(null);
    }

    private TransactionType findTransactionTypeById(Long id) {
        return transactionTypeService.findById(id).orElse(null);
    }

    private VirtualCurrency findVirtualCurrencyById(Long id) {
        return virtualCurrencyService.findById(id).orElse(null);
    }

    private ForumPublication findForumPublicationById(UUID id) {
        return forumPublicationService.findById(id).orElse(null);
    }

}
