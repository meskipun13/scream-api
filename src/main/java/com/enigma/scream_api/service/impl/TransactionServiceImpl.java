package com.enigma.scream_api.service.impl;

import com.enigma.scream_api.constant.PaymentStatus;
import com.enigma.scream_api.dto.request.TransactionRequest;
import com.enigma.scream_api.dto.response.TransactionResponse;
import com.enigma.scream_api.entity.Game;
import com.enigma.scream_api.entity.Player;
import com.enigma.scream_api.entity.Store;
import com.enigma.scream_api.entity.Transaction;
import com.enigma.scream_api.repository.TransactionRepository;
import com.enigma.scream_api.service.GameService;
import com.enigma.scream_api.service.PlayerService;
import com.enigma.scream_api.service.StoreService;
import com.enigma.scream_api.service.TransactionService;
import com.enigma.scream_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final GameService gameService;
    private final PlayerService playerService;
    private final StoreService storeService;
    private final ValidationUtil validationUtil;

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {
        String transactionId = UUID.randomUUID().toString();
        LocalDateTime transactionDate = LocalDateTime.now();

        validationUtil.validate(request);

        Player player = playerService.getOne(request.getPlayerId());
        Game game = gameService.getOne(request.getGameId());
        Store store = storeService.getOne(request.getStoreId());

        Transaction transaction = Transaction.builder()
                .id(transactionId)
                .transactionDate(transactionDate)
                .totalPrice(request.getTotalPrice())
                .player(player)
                .game(game)
                .store(store)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        transactionRepository.save(transaction);

        return toTransactionResponse(transaction);
    }

    @Override
    public TransactionResponse getTransactionById(String id) {
        Transaction transaction = getOne(id);
        return toTransactionResponse(transaction);
    }

    @Override
    public Transaction getOne(String id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(this::toTransactionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionResponse updateTransaction(String id, TransactionRequest request) {
        Transaction transaction = getOne(id);

        Player player = playerService.getOne(request.getPlayerId());
        Game game = gameService.getOne(request.getGameId());
        Store store = storeService.getOne(request.getStoreId());

        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setTotalPrice(request.getTotalPrice());
        transaction.setPlayer(player);
        transaction.setGame(game);
        transaction.setStore(store);
        transaction.setPaymentStatus(PaymentStatus.PENDING);

        transactionRepository.save(transaction);

        return toTransactionResponse(transaction);
    }

    @Override
    public void deleteTransactionById(String id) {
        Transaction transaction = getOne(id);
        transactionRepository.delete(transaction);
    }

    private TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .transactionDate(transaction.getTransactionDate())
                .totalPrice(transaction.getTotalPrice())
                .playerId(transaction.getPlayer().getId())
                .gameId(transaction.getGame().getId())
                .storeId(transaction.getStore().getId())
                .paymentStatus(transaction.getPaymentStatus().getDescription())
                .build();
    }
}
