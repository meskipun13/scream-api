package com.enigma.scream_api.service.impl;

import com.enigma.scream_api.dto.request.PlayerCreateRequest;
import com.enigma.scream_api.dto.request.PlayerUpdateRequest;
import com.enigma.scream_api.dto.response.PlayerResponse;
import com.enigma.scream_api.entity.Player;
import com.enigma.scream_api.repository.PlayerRepository;
import com.enigma.scream_api.service.PlayerService;
import com.enigma.scream_api.service.UserService;
import com.enigma.scream_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final UserService userService;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PlayerResponse createPlayer(PlayerCreateRequest request) {
        validationUtil.validate(request);

        Player player = Player.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .build();

        playerRepository.createPlayer(
                player.getId(),
                player.getName(),
                player.getEmail(),
                player.getPhoneNumber(),
                player.getAddress()
        );

        return toPlayerResponse(player);
    }

    @Override
    public PlayerResponse getPlayerById(String id) {
        Player player = getOne(id);
        return toPlayerResponse(player);
    }

    @Override
    public Player getOne(String id) {
        return playerRepository.findPlayerById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
    }


    @Override
    public PlayerResponse updatePlayer(String id, PlayerUpdateRequest request) {
        Player player = getOne(id);

        playerRepository.updatePlayer(
                id,
                request.getName(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getAddress()
        );

        player.setName(request.getName());
        player.setEmail(request.getEmail());
        player.setPhoneNumber(request.getPhoneNumber());
        player.setAddress(request.getAddress());

        return toPlayerResponse(player);
    }

    @Override
    public void deletePlayerById(String id) {
        Player player = getOne(id);
        playerRepository.deletePlayer(player.getId());
    }

    @Override
    public List<PlayerResponse> getAllPlayers() {
        List<Player> players = playerRepository.findAllPlayers();
        return players.stream()
                .map(this::toPlayerResponse)
                .collect(Collectors.toList());
    }

    private PlayerResponse toPlayerResponse(Player player) {
        return PlayerResponse.builder()
                .id(player.getId())
                .name(player.getName())
                .address(player.getAddress())
                .phoneNumber(player.getPhoneNumber())
                .email(player.getEmail())

                .build();
    }

}
