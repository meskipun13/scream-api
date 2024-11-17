package com.enigma.scream_api.service.impl;

import com.enigma.scream_api.dto.request.StoreRequest;
import com.enigma.scream_api.dto.response.StoreResponse;
import com.enigma.scream_api.dto.response.StoreResponse;
import com.enigma.scream_api.entity.Developer;
import com.enigma.scream_api.entity.Store;
import com.enigma.scream_api.repository.StoreRepository;
import com.enigma.scream_api.service.DeveloperService;
import com.enigma.scream_api.service.StoreService;
import com.enigma.scream_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final ValidationUtil validationUtil;
    private final DeveloperService developerService;

    @Override
    public StoreResponse createStore(StoreRequest request) {
        validationUtil.validate(request);
        Developer developer = developerService.getOne(request.getDeveloper());

        Store store = Store.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .region(request.getRegion())
                .currency(request.getCurrency())
                .developer(developer)
                .build();

        storeRepository.createStore(
                store.getId(),
                store.getName(),
                store.getRegion(),
                store.getCurrency(),
                store.getDeveloper().getId()
        );

        return toStoreResponse(store);
    }

    @Override
    public StoreResponse getStoreById(String id) {
        Store store = getOne(id);
        return toStoreResponse(store);
    }

    @Override
    public Store getOne(String id) {
        return storeRepository.findStoreById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found"));
    }

    @Override
    public StoreResponse updateStore(String id, StoreRequest request) {
        Store store = getOne(id);
        storeRepository.updateStore(
                id,
                store.getName(),
                store.getRegion(),
                store.getCurrency(),
                store.getDeveloper().getId()
        );

        store.setName(request.getName());
        store.setCurrency(request.getCurrency());
        store.setRegion(request.getRegion());
        store.setDeveloper(store.getDeveloper());

        return toStoreResponse(store);
    }

    @Override
    public void deleteStoreById(String id) {
        Store store = getOne(id);
        storeRepository.deleteStore(store.getId());
    }

    @Override
    public List<StoreResponse> getAllStores() {
        List<Store> stores = storeRepository.findAllStores();
        return stores.stream()
                .map(this::toStoreResponse)
                .collect(Collectors.toList());
    }

    private StoreResponse toStoreResponse(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .region(store.getRegion())
                .currency(store.getCurrency())
                .developer(store.getDeveloper().getId())
                .build();
    }
}
