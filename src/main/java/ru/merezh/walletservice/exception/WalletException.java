package ru.merezh.walletservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WalletException extends RuntimeException {

    private HttpStatus code;

    public WalletException(String message) {
        super(message);
        this.code = HttpStatus.BAD_GATEWAY;
    }

    public WalletException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }
}
