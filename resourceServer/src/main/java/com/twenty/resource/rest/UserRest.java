package com.twenty.resource.rest;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cuishang
 * @since 2020/1/4
 */
@RestController
public class UserRest {
    @GetMapping("/userDetail")
    public Authentication userDetail(Authentication authentication) {
        return authentication;
    }
    @GetMapping("/user")
    public Authentication user(Authentication authentication) {
        return authentication;
    }
}
//SecurityContextHolder.getContext()
