package org.lsh.everyemr.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class SignInRequest {

    private String username;
    private String password;
}
