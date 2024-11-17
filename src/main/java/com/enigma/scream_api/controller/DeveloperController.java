package com.enigma.scream_api.controller;

import com.enigma.scream_api.constant.Constant;
import com.enigma.scream_api.dto.request.DeveloperCreateRequest;
import com.enigma.scream_api.dto.request.DeveloperUpdateRequest;
import com.enigma.scream_api.dto.response.DeveloperResponse;
import com.enigma.scream_api.service.DeveloperService;
import com.enigma.scream_api.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Constant.DEVELOPER_API)
public class DeveloperController {
    private final DeveloperService developerService;

    @PostMapping
    public ResponseEntity<?> createDeveloper(@RequestBody DeveloperCreateRequest request) {
        DeveloperResponse savedDeveloper = developerService.createDeveloper(request);

        return ResponseUtil.buildResponse(HttpStatus.CREATED, Constant.SUCCESS_CREATE_DEVELOPER, savedDeveloper);
    }

    @GetMapping
    public ResponseEntity<?> getAllDevelopers() {
        List<DeveloperResponse> developers = developerService.getAllDevelopers();
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully retrieved all developers", developers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeveloperById(@PathVariable String id) {
        DeveloperResponse developer = developerService.getDeveloperById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully retrieved developer by ID", developer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeveloper(@PathVariable String id, @RequestBody DeveloperUpdateRequest request) {
        DeveloperResponse updatedDeveloper = developerService.updateDeveloper(id, request);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully updated developer", updatedDeveloper);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeveloperById(@PathVariable String id) {
        developerService.deleteDeveloperById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully delete developer", null);
    }


}
