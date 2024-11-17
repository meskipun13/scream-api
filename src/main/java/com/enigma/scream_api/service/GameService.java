package com.enigma.scream_api.service;

import com.enigma.scream_api.dto.request.GameRequest;
import com.enigma.scream_api.dto.response.GameResponse;
import com.enigma.scream_api.dto.response.GameResponse;
import com.enigma.scream_api.entity.Game;

import java.util.List;

public interface GameService {
    GameResponse createGame(GameRequest request);
    GameResponse getGameById(String id);
    Game getOne(String id);
    GameResponse updateGame(String id, GameRequest request);
    void deleteGameById(String id);
    List<GameResponse> getAllGames();
}
