package com.example.demo.user.client;

import com.example.demo.user.dto.UserCreateRequest;
import com.example.demo.user.dto.UserEditRequest;
import com.example.demo.user.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "user-svc", url = "http://localhost:9090")
public interface UserClient {

    @PostMapping("/api/v1/users")
    ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest request);

    @GetMapping("/api/v1/users/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable("id") UUID id);

    @GetMapping("/api/v1/users")
    ResponseEntity<List<UserResponse>> getAllUsers();

    @DeleteMapping("/api/v1/users/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id);

    @PatchMapping("/api/v1/users/{id}")
    ResponseEntity<UserResponse> updateUser(@PathVariable("id") UUID id, @RequestBody UserEditRequest request);

}
