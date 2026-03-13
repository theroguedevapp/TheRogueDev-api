package br.com.theroguedev.api.currency.virtual.entity;

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
@Table(name = "user_virtual_wallets")
public class UserWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_virtual_wallet_id")
    private UUID id;

    @Column
    private Long balance = 0L;

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

}
