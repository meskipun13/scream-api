package com.enigma.scream_api.controller;

import com.enigma.scream_api.constant.Constant;
import com.enigma.scream_api.dto.request.GameRequest;
import com.enigma.scream_api.dto.request.GameRequest;
import com.enigma.scream_api.dto.response.GameResponse;
import com.enigma.scream_api.service.GameService;
import com.enigma.scream_api.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Constant.GAME_API)
public class GameController {
    private final GameService gameService;

    @PostMapping
    public ResponseEntity<?> createGame(@RequestBody GameRequest request) {
        GameResponse savedGame = gameService.createGame(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, Constant.SUCCESS_CREATE_GAME, savedGame);
    }

    @GetMapping
    public ResponseEntity<?> getAllGames() {
        List<GameResponse> games = gameService.getAllGames();
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully retrieved all games", games);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGameById(@PathVariable String id) {
        GameResponse game = gameService.getGameById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully retrieved game by ID", game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGame(@PathVariable String id, @RequestBody GameRequest request) {
        GameResponse updatedGame = gameService.updateGame(id, request);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully updated game", updatedGame);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGameById(@PathVariable String id) {
        gameService.deleteGameById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully delete game", null);
    }
}
