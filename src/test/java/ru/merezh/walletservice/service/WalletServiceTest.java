package ru.merezh.walletservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.merezh.walletservice.entity.Wallet;
import ru.merezh.walletservice.exception.WalletException;
import ru.merezh.walletservice.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    @Test
    void sumWallet_ValidData_NewBalance() {
        Wallet wallet = new Wallet(1L, new BigDecimal("0.00"));
        BigDecimal sum = new BigDecimal("5000.00");

        when(walletRepository.getWalletByUserId(1L)).thenReturn(Optional.of(wallet));

        BigDecimal result = walletService.sumWallet(1L, sum);

        assertEquals(sum, result);
    }

    @Test
    void subWallet_NoMoney_ThrowsWalletException() {
        Wallet wallet = new Wallet(1L, new BigDecimal("5000.00"));
        BigDecimal sub = new BigDecimal("6000.00");

        when(walletRepository.getWalletByUserId(1L)).thenReturn(Optional.of(wallet));

        WalletException result = assertThrows(WalletException.class, () -> walletService.subWallet(1L, sub));

        String expected = "Недостаточно средств на кошельке пользователя";

        assertEquals(expected, result.getMessage());
    }

    @Test
    void subWallet_ValidData_NewBalance() {
        Wallet wallet = new Wallet(1L, new BigDecimal("5000.00"));
        BigDecimal sub = new BigDecimal("5000.00");

        when(walletRepository.getWalletByUserId(1L)).thenReturn(Optional.of(wallet));

        BigDecimal result = walletService.subWallet(1L, sub);

        assertEquals(new BigDecimal("0.00"), result);
    }
}