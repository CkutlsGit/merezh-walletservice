package ru.merezh.walletservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.merezh.walletservice.entity.Wallet;
import ru.merezh.walletservice.exception.WalletException;
import ru.merezh.walletservice.repository.WalletRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    public Wallet addUserInWallet(long id) {
        if (walletRepository.existsWalletByUserId(id)) {
            throw new WalletException("Кошелек пользователя с id " + id + " уже существует", HttpStatus.CONFLICT);
        }

        return walletRepository.save(new Wallet(id));
    }

    @Transactional
    public BigDecimal getBalanceWallet(long id) {
        if (!walletRepository.existsWalletByUserId(id)) {
            return walletRepository.save(new Wallet(id)).getBalance();
        }

        return walletRepository.findWalletByUserId(id).getBalance();
    }

    @Transactional
    public String deleteWallet(long id) {
        Wallet wallet = walletRepository.getWalletByUserId(id)
                .orElseThrow(() -> new WalletException("Кошелек с id " + id + " не существует", HttpStatus.NOT_FOUND));

        walletRepository.delete(wallet);

        return "Кошелек пользователя с id " + wallet.getUserId() + " удален";
    }

    @Transactional
    public BigDecimal sumWallet(long id, BigDecimal sum) {
        Wallet wallet = walletRepository.getWalletByUserId(id)
                .orElseThrow(() -> new WalletException("Кошелек с id " + id + " не существует", HttpStatus.NOT_FOUND));

        wallet.setBalance(wallet.getBalance().add(sum));

        return wallet.getBalance();
    }

    @Transactional
    public BigDecimal subWallet(long id, BigDecimal sub) {
        Wallet wallet = walletRepository.getWalletByUserId(id)
                .orElseThrow(() -> new WalletException("Кошелек с id " + id + " не существует", HttpStatus.NOT_FOUND));

        if (wallet.getBalance().compareTo(sub) < 0) {
            throw new WalletException("Недостаточно средств на кошельке пользователя");
        }

        wallet.setBalance(wallet.getBalance().subtract(sub));

        return wallet.getBalance();
    }
}
