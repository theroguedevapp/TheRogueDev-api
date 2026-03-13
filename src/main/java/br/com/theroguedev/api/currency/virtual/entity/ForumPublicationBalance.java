package br.com.theroguedev.api.currency.virtual.entity;

import br.com.theroguedev.api.publication.entity.ForumPublication;
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
@Table(name = "forum_publication_virtual_balances")
public class ForumPublicationBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "virtual_publication_balance_id")
    private UUID id;

    @Column
    private Long credit = 0L;

    @Column
    private Long debit = 0L;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "forum_publication_id", nullable = false)
    private ForumPublication forumPublication;

    @ManyToOne
    @JoinColumn(name = "virtual_currency_id", nullable = false)
    private VirtualCurrency virtualCurrency;

    public void incrementCredit() { this.credit++; }

    public void incrementDebit() { this.debit++; }

}
