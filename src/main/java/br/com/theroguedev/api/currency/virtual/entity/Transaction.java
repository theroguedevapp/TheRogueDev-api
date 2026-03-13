package br.com.theroguedev.api.currency.virtual.entity;

import br.com.theroguedev.api.publication.entity.ForumPublication;
import br.com.theroguedev.api.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "virtual_currency_transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "virtual_currency_transaction_id")
    private UUID id;

    @Column(nullable = false)
    private Long amount;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "virtual_currency_id", nullable = false)
    private VirtualCurrency virtualCurrency;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "related_forum_publication_id")
    private ForumPublication relatedForumPublication;

    @ManyToOne
    @JoinColumn(name = "related_user_id")
    private User relatedUser;

}
