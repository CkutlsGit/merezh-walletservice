package ru.merezh.walletservice.controller;

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
    public ResponseEntity<Wallet> createWallet(@RequestHeader("X-User-Id") long userId) {
        return ResponseEntity.ok().body(walletService.addUserInWallet(userId));
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(@RequestHeader("X-User-Id") long userId) {
        return ResponseEntity.ok().body(walletService.getBalanceWallet(userId));
    }

    @PostMapping("/balance/sum")
    public ResponseEntity<BigDecimal> sumBalance(@RequestHeader("X-User-Id") long userId, @RequestBody AmountDto sum) {
        return ResponseEntity.ok().body(walletService.sumWallet(userId, sum.amount()));
    }

    @PostMapping("/balance/sub")
    public ResponseEntity<BigDecimal> subBalance(@RequestHeader("X-User-Id") long userId,@RequestBody AmountDto sub) {
        return ResponseEntity.ok().body(walletService.subWallet(userId, sub.amount()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWallet(@PathVariable long id) {
        return ResponseEntity.ok().body(walletService.deleteWallet(id));
    }
}
