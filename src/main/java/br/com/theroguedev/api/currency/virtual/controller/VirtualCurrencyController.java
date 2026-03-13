package br.com.theroguedev.api.currency.virtual.controller;


import br.com.theroguedev.api.config.security.annotation.create.CanCreateVirtualCurrency;
import br.com.theroguedev.api.config.security.annotation.read.CanReadVirtualCurrency;
import br.com.theroguedev.api.currency.virtual.controller.doc.CurrencyVirtualControllerDoc;
import br.com.theroguedev.api.currency.virtual.dto.request.VirtualCurrencyRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.VirtualCurrencyResponse;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import br.com.theroguedev.api.currency.virtual.mapper.VirtualCurrencyMapper;
import br.com.theroguedev.api.currency.virtual.service.VirtualCurrencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currency/virtual")
@RequiredArgsConstructor
public class VirtualCurrencyController implements CurrencyVirtualControllerDoc {

    private final VirtualCurrencyService virtualCurrencyService;
    private final VirtualCurrencyMapper virtualCurrencyMapper;

    @GetMapping
    @CanReadVirtualCurrency
    public ResponseEntity<List<VirtualCurrencyResponse>> getAll() {
        return ResponseEntity.ok(virtualCurrencyService.findAll()
                .stream()
                .map(virtualCurrencyMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @CanReadVirtualCurrency
    public ResponseEntity<VirtualCurrencyResponse> getById(@PathVariable Long id) {
        return virtualCurrencyService.findById(id)
                .map(type -> ResponseEntity.ok(virtualCurrencyMapper.toResponse(type)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @CanCreateVirtualCurrency
    public ResponseEntity<VirtualCurrencyResponse> save(@RequestBody @Valid VirtualCurrencyRequest request) {
        VirtualCurrency newVirtualCurrency = virtualCurrencyMapper.toVirtualCurrency(request);
        VirtualCurrency savedVirtualCurrency = virtualCurrencyService.save(newVirtualCurrency);
        return ResponseEntity.status(HttpStatus.CREATED).body(virtualCurrencyMapper.toResponse(savedVirtualCurrency));
    }

}
