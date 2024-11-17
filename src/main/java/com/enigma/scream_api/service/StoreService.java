package com.enigma.scream_api.service;

import com.enigma.scream_api.dto.request.PlayerCreateRequest;
import com.enigma.scream_api.dto.request.PlayerUpdateRequest;
import com.enigma.scream_api.dto.request.StoreRequest;
import com.enigma.scream_api.dto.response.StoreResponse;
import com.enigma.scream_api.entity.Player;
import com.enigma.scream_api.entity.Store;

import java.util.List;

public interface StoreService {
    StoreResponse createStore(StoreRequest request);
    StoreResponse getStoreById(String id);
    Store getOne(String id);
    StoreResponse updateStore(String id, StoreRequest request);
    void deleteStoreById(String id);
    List<StoreResponse> getAllStores();
}
