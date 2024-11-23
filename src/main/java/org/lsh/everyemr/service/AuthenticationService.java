package org.lsh.everyemr.service;

import org.lsh.everyemr.dto.SignInRequest;
import org.lsh.everyemr.entity.User;

public interface AuthenticationService {

    String signInAndReturnJWT(SignInRequest request);

    String generateToken(User user);
}
