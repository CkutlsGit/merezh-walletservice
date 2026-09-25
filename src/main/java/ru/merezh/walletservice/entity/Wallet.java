package ru.merezh.walletservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallets")
public class Wallet {

   public Wallet(long id) {
       this.userId = id;
       this.balance = new BigDecimal("0.00");
   }

    @Id
    @Column(nullable = false)
    private Long userId;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal balance;
}
