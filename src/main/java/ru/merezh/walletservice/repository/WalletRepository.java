package ru.merezh.walletservice.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import ru.merezh.walletservice.entity.Wallet;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Wallet> getWalletByUserId(long id);

    Wallet findWalletByUserId(long id);
    boolean existsWalletByUserId(long id);
}
