package org.lsh.everyemr.controller;

import lombok.RequiredArgsConstructor;
import org.lsh.everyemr.model.Role;
import org.lsh.everyemr.security.UserPrinciple;
import org.lsh.everyemr.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/join")
    public void join(){

    }
    @GetMapping("/login")
    public void login(){

    }

    @PutMapping("/role-change")
    public ResponseEntity<String> roleChange() {
        try {
            boolean isRequested = userService.requestRoleChange();
            if (isRequested) {
                return ResponseEntity.ok("Role change request submitted successfully.");
            } else {
                return ResponseEntity.badRequest().body("You have already submitted a request.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }


//    @PutMapping("/change/{role}")
//    public ResponseEntity<Object> changeRole(@AuthenticationPrincipal UserPrinciple userPrinciple,
//                                             @PathVariable Role role) {
//        userService.changeRole(role, userPrinciple.getUsername());
//        return ResponseEntity.ok(true);
//    }
}
