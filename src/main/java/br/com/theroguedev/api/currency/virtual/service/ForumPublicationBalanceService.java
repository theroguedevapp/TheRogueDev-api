package br.com.theroguedev.api.currency.virtual.service;

import br.com.theroguedev.api.currency.virtual.dto.request.ForumPublicationBalanceRequest;
import br.com.theroguedev.api.currency.virtual.entity.ForumPublicationBalance;
import br.com.theroguedev.api.currency.virtual.entity.TransactionType;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import br.com.theroguedev.api.currency.virtual.repository.ForumPublicationBalanceRepository;
import br.com.theroguedev.api.currency.virtual.repository.TransactionTypeRepository;
import br.com.theroguedev.api.publication.entity.ForumPublication;
import br.com.theroguedev.api.publication.repository.ForumPublicationRepository;
import br.com.theroguedev.api.publication.service.ForumPublicationService;
import br.com.theroguedev.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForumPublicationBalanceService {

    private final ForumPublicationBalanceRepository repository;
    private final UserService userService;
    private final TransactionTypeService transactionTypeService;
    private final VirtualCurrencyService virtualCurrencyService;
    private final ForumPublicationService forumPublicationService;

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

    private VirtualCurrency findVirtualCurrencyById(Long id) {
        return virtualCurrencyService.findById(id).orElse(null);
    }

    private ForumPublication findForumPublicationById(UUID id) {
        return forumPublicationService.findById(id).orElse(null);
    }

}
