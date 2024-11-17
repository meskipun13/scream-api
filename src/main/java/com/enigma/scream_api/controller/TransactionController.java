package com.enigma.scream_api.controller;


import com.enigma.scream_api.constant.Constant;
import com.enigma.scream_api.dto.request.TransactionRequest;
import com.enigma.scream_api.dto.response.TransactionResponse;
import com.enigma.scream_api.service.TransactionService;
import com.enigma.scream_api.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Constant.TRANSACTION_API)
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest request) {
        TransactionResponse savedTransaction = transactionService.createTransaction(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, Constant.SUCCESS_CREATE_TRANSACTION, savedTransaction);
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        List<TransactionResponse> transactions = transactionService.getAllTransactions();
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully retrieved all transactions", transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable String id) {
        TransactionResponse transaction = transactionService.getTransactionById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully retrieved transaction by ID", transaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable String id, @RequestBody TransactionRequest request) {
        TransactionResponse updatedTransaction = transactionService.updateTransaction(id, request);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully updated transaction", updatedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransactionById(@PathVariable String id) {
        transactionService.deleteTransactionById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully deleted transaction", null);
    }
}

