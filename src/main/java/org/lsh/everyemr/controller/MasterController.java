package org.lsh.everyemr.controller;

import org.lsh.everyemr.dto.UserRoleChangeRequest;
import org.lsh.everyemr.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/master")
public class MasterController {

    private final UserService userService;

    public MasterController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/change")
    public ResponseEntity<String> changeUserRole(@RequestBody UserRoleChangeRequest request) {
        try {
            boolean isUpdated = userService.changeUserRole(request.getUsername(), request.getNewRole());
            if (isUpdated) {
                return ResponseEntity.ok("User role updated successfully.");
            } else {
                return ResponseEntity.badRequest().body("Invalid role or user not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}
