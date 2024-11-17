package com.enigma.scream_api.service;

import com.enigma.scream_api.dto.request.DeveloperCreateRequest;
import com.enigma.scream_api.dto.request.DeveloperUpdateRequest;
import com.enigma.scream_api.dto.response.DeveloperResponse;
import com.enigma.scream_api.entity.Developer;

import java.util.List;

public interface DeveloperService {
    DeveloperResponse createDeveloper(DeveloperCreateRequest request);
    DeveloperResponse getDeveloperById(String id);
    Developer getOne(String id);
    DeveloperResponse updateDeveloper(String id, DeveloperUpdateRequest request);
    void deleteDeveloperById(String id);
    List<DeveloperResponse> getAllDevelopers();
}
