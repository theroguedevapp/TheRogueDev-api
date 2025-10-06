package br.com.theroguedev.api.publication.service;

import br.com.theroguedev.api.currency.virtual.entity.*;
import br.com.theroguedev.api.currency.virtual.service.*;
import br.com.theroguedev.api.exceptions.CustomNotFoundException;
import br.com.theroguedev.api.publication.entity.*;
import br.com.theroguedev.api.publication.repository.ForumPublicationRepository;
import br.com.theroguedev.api.user.entity.User;
import br.com.theroguedev.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ForumPublicationService {

    private final ForumPublicationRepository repository;
    private final UserService userService;
    private final StatusService statusService;
    private final TypeService typeService;
    private final TopicService topicService;
    private final ToolService toolService;
    private final VirtualCurrencyService virtualCurrencyService;
    private final TransactionService transactionService;
    private final TransactionTypeService transactionTypeService;
    private final TransactionParameterService transactionParameterService;
    private final UserWalletService userWalletService;


    public List<ForumPublication> findAll() {
        return repository.findAll();
    }

    public Optional<ForumPublication> findById(UUID id) {
        return repository.findById(id);
    }

    public Optional<ForumPublication> findByIdWithChildren(UUID id) {
        return repository.findByIdWithChildren(id);
    }

    @Transactional
    public ForumPublication save(ForumPublication forumPublication, UUID userId) {
        List<User> authors = forumPublication.getAuthors();
        if (authors == null) {
            authors = new ArrayList<>();
        }
        authors.add(findUserById(userId));

        if (forumPublication.getParent() != null) {
            forumPublication.setParent(findParentById(forumPublication.getParent().getId()));
        }

        forumPublication.setAuthors(findUsersByUsername(authors));
        forumPublication.setSubmittedBy(findUserById(userId));
        forumPublication.setSlug(generateUniqueSlug(forumPublication.getTitle()));
        forumPublication.setStatus(findStatusByName());
        forumPublication.setType(findTypeById(forumPublication.getType().getId()));
        forumPublication.setTools(findTools(forumPublication.getTools()));
        forumPublication.setTopics(findTopics(forumPublication.getTopics()));
        forumPublication.setIsActive(true);

        ForumPublicationBalance forumPublicationDefaultBalance = ForumPublicationBalance.builder()
                .forumPublication(forumPublication)
                .virtualCurrency(findVirtualCurrencyBySymbol())
                .debit(0L)
                .credit(0L)
                .build();

        List<ForumPublicationBalance> forumPublicationBalances = new ArrayList<>();
        forumPublicationBalances.add(forumPublicationDefaultBalance);
        forumPublication.setBalances(forumPublicationBalances);

        ForumPublication savedForumPublication = repository.save(forumPublication);

        Map<String, String> publicationToParameter = Map.of(
                "ARTICLE", "create_forum_article",
                "QUESTION", "create_forum_question",
                "COMMENT", "create_forum_comment"
        );

        String parameterName = publicationToParameter.get(forumPublication.getType().getName());

        if (parameterName != null) {
            TransactionParameter transactionParameter = findTransactionParameterByName(parameterName);

            if (transactionParameter != null) {
                Transaction transaction = Transaction.builder()
                        .transactionType(findTransactionTypeByName("EARN"))
                        .amount(transactionParameter.getReward())
                        .virtualCurrency(transactionParameter.getVirtualCurrency())
                        .relatedForumPublication(forumPublication)
                        .user(forumPublication.getSubmittedBy())
                        .build();

                transactionService.save(transaction);

                userWalletService.addBalance(transaction.getUser(), transaction.getVirtualCurrency(), transactionParameter.getReward());
            }
        }

        return savedForumPublication;
    }

    @Transactional
    public ForumPublication changeStatus(UUID id, Boolean active) {
        Optional<ForumPublication> optForumPublication = repository.findById(id);
        if (optForumPublication.isEmpty()) {
            throw new CustomNotFoundException("Publicação não encontrada");
        }
        ForumPublication forumPublication = optForumPublication.get();
        forumPublication.setIsActive(active);

        repository.save(forumPublication);
        return forumPublication;
    }

    private User findUserById(UUID userId) {
        return userService.findById(userId).orElse(null);
    }

    private ForumPublication findParentById(UUID parentId) {
        return this.findById(parentId).orElse(null);
    }

    private Status findStatusByName() {
        return statusService.findByName("PUBLISHED").orElse(null);
    }

    private VirtualCurrency findVirtualCurrencyBySymbol() {
        return virtualCurrencyService.findBySymbol("RD").orElse(null);
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

    private Type findTypeById(Long id) {
        return typeService.findById(id).orElse(null);
    }

    private List<Tool> findTools(List<Tool> tools) {
        List<Tool> toolsFound = new ArrayList<>();
        tools.forEach(tool -> toolService.findById(tool.getId()).ifPresent(toolsFound::add));
        return toolsFound;
    }

    private List<Topic> findTopics(List<Topic> topics) {
        List<Topic> topicsFound = new ArrayList<>();
        topics.forEach(topic -> topicService.findById(topic.getId()).ifPresent(topicsFound::add));
        return topicsFound;
    }

    private List<User> findUsersByUsername(List<User> users) {
        List<User> usersFound = new ArrayList<>();
        users.forEach(user -> userService.findByUsername(user.getUsername()).ifPresent(usersFound::add));
        return usersFound;
    }

    private String generateUniqueSlug(String title) {
        String baseSlug = java.text.Normalizer.normalize(title, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-");

        String slug = baseSlug;

        List<ForumPublication> existingForumPublicationSlugs = repository.findAllBySlugStartingWith(baseSlug);

        List<String> existingSlugs = new ArrayList<>();
        existingForumPublicationSlugs.forEach(forumPublication -> existingSlugs.add(forumPublication.getSlug()));

        if (existingSlugs.contains(slug)) {
            Random random = new Random();
            do {
                int suffix = random.nextInt(1000); // gera de 0 a 999
                slug = baseSlug + "-" + suffix;
            } while (existingSlugs.contains(slug));
        }

        return slug;
    }


}
