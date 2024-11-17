package com.enigma.scream_api.service.impl;

import com.enigma.scream_api.dto.request.GameRequest;
import com.enigma.scream_api.dto.response.GameResponse;
import com.enigma.scream_api.dto.response.GameResponse;
import com.enigma.scream_api.entity.Game;
import com.enigma.scream_api.entity.Game;
import com.enigma.scream_api.entity.Store;
import com.enigma.scream_api.repository.GameRepository;
import com.enigma.scream_api.service.GameService;
import com.enigma.scream_api.service.StoreService;
import com.enigma.scream_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final ValidationUtil validationUtil;
    private final GameRepository gameRepository;
    private final StoreService storeService;

    @Override
    public GameResponse createGame(GameRequest request) {
        validationUtil.validate(request);
        Store store = storeService.getOne(request.getStore());

        Game game = Game.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .price(request.getPrice())
                .platform(request.getPlatform())
                .category(request.getCategory())
                .stock(request.getStock())
                .description(request.getDescription())
                .store(store)
                .build();

        gameRepository.createGame(
                game.getId(),
                game.getName(),
                game.getPrice(),
                game.getPlatform(),
                game.getCategory(),
                game.getStock(),
                game.getDescription(),
                game.getStore().getId()
        );

        return toGameResponse(game);
    }

    @Override
    public GameResponse getGameById(String id) {
        Game game = getOne(id);
        return toGameResponse(game);
    }

    @Override
    public Game getOne(String id) {
        return gameRepository.findGameById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
    }

    @Override
    public GameResponse updateGame(String id, GameRequest request) {
        Game game = getOne(id);

        gameRepository.updateGame(
                id,
                request.getName(),
                request.getPrice(),
                request.getPlatform(),
                request.getCategory(),
                request.getStock(),
                request.getDescription(),
                request.getStore()
        );

        game.setName(request.getName());
        game.setPrice(request.getPrice());
        game.setPlatform(request.getPlatform());
        game.setCategory(request.getCategory());
        game.setStock(request.getStock());
        game.setDescription(request.getDescription());
        game.setStore(game.getStore());

        return toGameResponse(game);
    }

    @Override
    public void deleteGameById(String id) {
        Game game = getOne(id);
        gameRepository.deleteGame(game.getId());
    }

    @Override
    public List<GameResponse> getAllGames() {
        List<Game> games = gameRepository.findAllGames();
        return games.stream()
                .map(this::toGameResponse)
                .collect(Collectors.toList());
    }

    private GameResponse toGameResponse(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .name(game.getName())
                .price(game.getPrice())
                .platform(game.getPlatform())
                .category(game.getCategory())
                .stock(game.getStock())
                .description(game.getDescription())
                .id_store(game.getStore().getId())
                .name_store(game.getStore().getName())
                .build();
    }
}
