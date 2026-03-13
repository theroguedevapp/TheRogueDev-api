package br.com.theroguedev.api.currency.virtual.controller;


import br.com.theroguedev.api.config.security.annotation.create.CanCreateCurrencyVirtualTransactionParameter;
import br.com.theroguedev.api.config.security.annotation.read.CanReadCurrencyVirtualTransactionParameter;
import br.com.theroguedev.api.currency.virtual.controller.doc.TransactionParameterControllerDoc;
import br.com.theroguedev.api.currency.virtual.dto.request.TransactionParameterRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.TransactionParameterResponse;
import br.com.theroguedev.api.currency.virtual.entity.TransactionParameter;
import br.com.theroguedev.api.currency.virtual.mapper.TransactionParameterMapper;
import br.com.theroguedev.api.currency.virtual.service.TransactionParameterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currency/virtual/transaction/parameter")
@RequiredArgsConstructor
public class TransactionParameterController implements TransactionParameterControllerDoc {

    private final TransactionParameterService transactionParameterService;
    private final TransactionParameterMapper transactionParameterMapper;

    @GetMapping
    @CanReadCurrencyVirtualTransactionParameter
    public ResponseEntity<List<TransactionParameterResponse>> getAll() {
        return ResponseEntity.ok(transactionParameterService.findAll()
                .stream()
                .map(transactionParameterMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @CanReadCurrencyVirtualTransactionParameter
    public ResponseEntity<TransactionParameterResponse> getById(@PathVariable Long id) {
        return transactionParameterService.findById(id)
                .map(type -> ResponseEntity.ok(transactionParameterMapper.toResponse(type)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @CanCreateCurrencyVirtualTransactionParameter
    public ResponseEntity<TransactionParameterResponse> save(@RequestBody @Valid TransactionParameterRequest request) {
        TransactionParameter newTransactionParameter = transactionParameterMapper.toTransactionParameter(request);
        TransactionParameter savedTransactionParameter = transactionParameterService.save(newTransactionParameter);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionParameterMapper.toResponse(savedTransactionParameter));
    }

}
