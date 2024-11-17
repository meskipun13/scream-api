package com.enigma.scream_api.service.impl;

import com.enigma.scream_api.dto.request.DeveloperCreateRequest;
import com.enigma.scream_api.dto.request.DeveloperUpdateRequest;
import com.enigma.scream_api.dto.response.DeveloperResponse;
import com.enigma.scream_api.dto.response.DeveloperResponse;
import com.enigma.scream_api.entity.Developer;
import com.enigma.scream_api.entity.Developer;
import com.enigma.scream_api.repository.DeveloperRepository;
import com.enigma.scream_api.service.DeveloperService;
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
public class DeveloperServiceImpl implements DeveloperService {
    private final DeveloperRepository developerRepository;
    private final ValidationUtil validationUtil;


    @Override
    public DeveloperResponse createDeveloper(DeveloperCreateRequest request) {
        validationUtil.validate(request);

        Developer developer = Developer.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .website(request.getWebsite())
                .build();

        developerRepository.createDeveloper(
                developer.getId(),
                developer.getName(),
                developer.getEmail(),
                developer.getPhoneNumber(),
                developer.getWebsite()
        );

        return toDeveloperResponse(developer);
    }

    @Override
    public DeveloperResponse getDeveloperById(String id) {
        Developer developer = getOne(id);
        return toDeveloperResponse(developer);
    }

    @Override
    public Developer getOne(String id) {
        return developerRepository.findDeveloperById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Developer not found"));
    }

    @Override
    public DeveloperResponse updateDeveloper(String id, DeveloperUpdateRequest request) {
        Developer developer = getOne(id);

        developerRepository.updateDeveloper(
                id,
                request.getName(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getWebsite()
        );

        developer.setName(request.getName());
        developer.setEmail(request.getEmail());
        developer.setPhoneNumber(request.getPhoneNumber());
        developer.setWebsite(request.getWebsite());

        return toDeveloperResponse(developer);
    }

    @Override
    public void deleteDeveloperById(String id) {
        Developer developer = getOne(id);
        developerRepository.deleteDeveloper(developer.getId());
    }

    @Override
    public List<DeveloperResponse> getAllDevelopers() {
        List<Developer> developers = developerRepository.findAllDevelopers();
        return developers.stream()
                .map(this::toDeveloperResponse)
                .collect(Collectors.toList());
    }

    private DeveloperResponse toDeveloperResponse(Developer developer) {
        return DeveloperResponse.builder()
                .id(developer.getId())
                .name(developer.getName())
                .phoneNumber(developer.getPhoneNumber())
                .email(developer.getEmail())
                .website(developer.getWebsite())
                .build();
    }

}
