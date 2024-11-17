package com.enigma.scream_api.controller;

import com.enigma.scream_api.constant.Constant;
import com.enigma.scream_api.dto.request.StoreRequest;
import com.enigma.scream_api.dto.request.StoreRequest;
import com.enigma.scream_api.dto.response.StoreResponse;
import com.enigma.scream_api.service.StoreService;
import com.enigma.scream_api.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Constant.STORE_API)
public class StoreController {
    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<?> createStore(@RequestBody StoreRequest request) {
        StoreResponse savedStore = storeService.createStore(request);

        return ResponseUtil.buildResponse(HttpStatus.CREATED, Constant.SUCCESS_CREATE_STORE, savedStore);
    }

    @GetMapping
    public ResponseEntity<?> getAllStores() {
        List<StoreResponse> stores = storeService.getAllStores();
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully retrieved all stores", stores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable String id) {
        StoreResponse store = storeService.getStoreById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully retrieved store by ID", store);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStore(@PathVariable String id, @RequestBody StoreRequest request) {
        StoreResponse updatedStore = storeService.updateStore(id, request);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully updated store", updatedStore);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStoreById(@PathVariable String id) {
        storeService.deleteStoreById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully delete store", null);
    }
}
