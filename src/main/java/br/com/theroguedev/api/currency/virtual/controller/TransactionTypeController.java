package br.com.theroguedev.api.currency.virtual.controller;


import br.com.theroguedev.api.currency.virtual.dto.request.TransactionTypeRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.TransactionTypeResponse;
import br.com.theroguedev.api.currency.virtual.entity.TransactionType;
import br.com.theroguedev.api.currency.virtual.mapper.TransactionTypeMapper;
import br.com.theroguedev.api.currency.virtual.service.TransactionTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currency/virtual/transaction/type")
@RequiredArgsConstructor
public class TransactionTypeController {

    private final TransactionTypeService transactionTypeService;
    private final TransactionTypeMapper transactionTypeMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('transaction_type:get_all')")
    public ResponseEntity<List<TransactionTypeResponse>> getAll() {
        return ResponseEntity.ok(transactionTypeService.findAll()
                .stream()
                .map(transactionTypeMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('transaction_type:get_by_id')")
    public ResponseEntity<TransactionTypeResponse> getById(@PathVariable Long id) {
        return transactionTypeService.findById(id)
                .map(type -> ResponseEntity.ok(transactionTypeMapper.toResponse(type)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('transaction_type:create')")
    public ResponseEntity<TransactionTypeResponse> save(@RequestBody @Valid TransactionTypeRequest request) {
        TransactionType newTransactionType = transactionTypeMapper.toTransactionType(request);
        TransactionType savedTransactionType = transactionTypeService.save(newTransactionType);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionTypeMapper.toResponse(savedTransactionType));
    }

}
