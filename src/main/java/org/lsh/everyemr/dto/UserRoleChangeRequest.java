package org.lsh.everyemr.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRoleChangeRequest {
    // Getter and Setter
    private String username;
    private String newRole;

}
