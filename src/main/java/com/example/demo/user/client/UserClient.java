package com.example.demo.user.client;

import com.example.demo.user.dto.UserCreateRequest;
import com.example.demo.user.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-svc" , url = "http://localhost:9090")
public interface UserClient {

    @PostMapping("/api/v1/users")
    ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest request);

}
