package com.enigma.scream_api.service;


import com.enigma.scream_api.dto.request.TransactionRequest;
import com.enigma.scream_api.dto.response.TransactionResponse;
import com.enigma.scream_api.entity.Player;
import com.enigma.scream_api.entity.Transaction;

import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);

    TransactionResponse getTransactionById(String id);

    Transaction getOne(String id);

    List<TransactionResponse> getAllTransactions();

    TransactionResponse updateTransaction(String id, TransactionRequest request);

    void deleteTransactionById(String id);
}

