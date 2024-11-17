package com.enigma.scream_api.service;

import com.enigma.scream_api.dto.request.PlayerCreateRequest;
import com.enigma.scream_api.dto.request.PlayerUpdateRequest;
import com.enigma.scream_api.dto.response.PlayerResponse;
import com.enigma.scream_api.entity.Player;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PlayerService {
    PlayerResponse createPlayer(PlayerCreateRequest request);
    PlayerResponse getPlayerById(String id);
    Player getOne(String id);
    PlayerResponse updatePlayer(String id, PlayerUpdateRequest request);
    void deletePlayerById(String id);
    List<PlayerResponse> getAllPlayers();

//    boolean existByCustomerIdAndUserId(String customerId, String userId);
}
