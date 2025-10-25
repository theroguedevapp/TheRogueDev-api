package br.com.theroguedev.api.currency.virtual.controller;


import br.com.theroguedev.api.config.security.JWTUserData;
import br.com.theroguedev.api.config.security.annotation.create.CanCreateCurrencyVirtualTransaction;
import br.com.theroguedev.api.config.security.annotation.read.CanReadCurrencyVirtualTransaction;
import br.com.theroguedev.api.currency.virtual.controller.doc.TransactionControllerDoc;
import br.com.theroguedev.api.currency.virtual.dto.request.TransactionRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.TransactionResponse;
import br.com.theroguedev.api.currency.virtual.entity.Transaction;
import br.com.theroguedev.api.currency.virtual.mapper.TransactionMapper;
import br.com.theroguedev.api.currency.virtual.service.TransactionService;
import br.com.theroguedev.api.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/currency/virtual/transaction")
@RequiredArgsConstructor
public class TransactionController implements TransactionControllerDoc {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @GetMapping
    @CanReadCurrencyVirtualTransaction
    public ResponseEntity<List<TransactionResponse>> getAll() {
        return ResponseEntity.ok(transactionService.findAll()
                .stream()
                .map(transactionMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @CanReadCurrencyVirtualTransaction
    public ResponseEntity<TransactionResponse> getById(@PathVariable UUID id) {
        return transactionService.findById(id)
                .map(type -> ResponseEntity.ok(transactionMapper.toResponse(type)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @CanCreateCurrencyVirtualTransaction
    public ResponseEntity<TransactionResponse> save(@RequestBody @Valid TransactionRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        Transaction newTransaction = transactionMapper.toTransaction(request);
        newTransaction.setUser(User.builder().id(userData.id()).build());

        Transaction savedTransaction = transactionService.save(newTransaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionMapper.toResponse(savedTransaction));
    }

}
