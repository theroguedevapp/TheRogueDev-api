package br.com.theroguedev.api.currency.virtual.controller;


import br.com.theroguedev.api.config.security.annotation.read.CanReadCurrencyVirtualUserWallet;
import br.com.theroguedev.api.currency.virtual.controller.doc.UserWalletControllerDoc;
import br.com.theroguedev.api.currency.virtual.dto.request.UserWalletRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.UserWalletResponse;
import br.com.theroguedev.api.currency.virtual.entity.UserWallet;
import br.com.theroguedev.api.currency.virtual.mapper.UserWalletMapper;
import br.com.theroguedev.api.currency.virtual.service.UserWalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/currency/virtual/user/wallet")
@RequiredArgsConstructor
public class UserWalletController implements UserWalletControllerDoc {

    private final UserWalletService userWalletService;
    private final UserWalletMapper userWalletMapper;

    @GetMapping
    @CanReadCurrencyVirtualUserWallet
    public ResponseEntity<List<UserWalletResponse>> getAll() {
        return ResponseEntity.ok(userWalletService.findAll()
                .stream()
                .map(userWalletMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @CanReadCurrencyVirtualUserWallet
    public ResponseEntity<UserWalletResponse> getById(@PathVariable UUID id) {
        return userWalletService.findById(id)
                .map(type -> ResponseEntity.ok(userWalletMapper.toResponse(type)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @CanReadCurrencyVirtualUserWallet
    public ResponseEntity<UserWalletResponse> save(@RequestBody @Valid UserWalletRequest request) {
        UserWallet newUserWallet = userWalletMapper.toUserWallet(request);
        UserWallet savedUserWallet = userWalletService.save(newUserWallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(userWalletMapper.toResponse(savedUserWallet));
    }

}
