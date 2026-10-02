package ru.merezh.walletservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.merezh.walletservice.dto.AmountDto;
import ru.merezh.walletservice.entity.Wallet;
import ru.merezh.walletservice.service.WalletService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/create")
    @Operation(summary = "Метод для создания кошелька")
    public ResponseEntity<Wallet> createWallet(@RequestHeader("X-User-Id") long userId) {
        return ResponseEntity.ok().body(walletService.addUserInWallet(userId));
    }

    @GetMapping("/balance")
    @Operation(summary = "Метод для получения баланса пользователя")
    public ResponseEntity<BigDecimal> getBalance(@RequestHeader("X-User-Id") long userId) {
        return ResponseEntity.ok().body(walletService.getBalanceWallet(userId));
    }

    @PostMapping("/balance/sum")
    @Operation(summary = "Метод для пополнения баланса")
    public ResponseEntity<BigDecimal> sumBalance(@RequestHeader("X-User-Id") long userId, @RequestBody AmountDto sum) {
        return ResponseEntity.ok().body(walletService.sumWallet(userId, sum.amount()));
    }

    @PostMapping("/balance/sub")
    @Operation(summary = "Метод для снятие баланса")
    public ResponseEntity<BigDecimal> subBalance(@RequestHeader("X-User-Id") long userId,@RequestBody AmountDto sub) {
        return ResponseEntity.ok().body(walletService.subWallet(userId, sub.amount()));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Метод для удаления кошелька")
    public ResponseEntity<String> deleteWallet(@PathVariable long id) {
        return ResponseEntity.ok().body(walletService.deleteWallet(id));
    }
}
