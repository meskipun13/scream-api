package com.enigma.scream_api.controller;

import com.enigma.scream_api.constant.Constant;
import com.enigma.scream_api.dto.request.PlayerCreateRequest;
import com.enigma.scream_api.dto.request.PlayerUpdateRequest;
import com.enigma.scream_api.dto.response.PlayerResponse;
import com.enigma.scream_api.service.PlayerService;
import com.enigma.scream_api.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Constant.PLAYER_API)
public class PlayerController {
//    private static class CommonResponseCustomerResponse extends CommonResponse<PlayerResponse> {}
//    private static class CommonResponseListCustomerResponse extends CommonResponse<List<PlayerResponse>> {}

    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody PlayerCreateRequest request) {
        PlayerResponse savedPlayer = playerService.createPlayer(request);

        return ResponseUtil.buildResponse(HttpStatus.CREATED, Constant.SUCCESS_CREATE_PLAYER, savedPlayer);
    }

    @GetMapping
    public ResponseEntity<?> getAllPlayers() {
        List<PlayerResponse> players = playerService.getAllPlayers();
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully retrieved all players", players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlayerById(@PathVariable String id) {
        PlayerResponse player = playerService.getPlayerById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully retrieved player by ID", player);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable String id, @RequestBody PlayerUpdateRequest request) {
        PlayerResponse updatedPlayer = playerService.updatePlayer(id, request);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully updated player", updatedPlayer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayerById(@PathVariable String id) {
        playerService.deletePlayerById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully delete player", null);
    }


}
