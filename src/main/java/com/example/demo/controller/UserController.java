package com.example.demo.controller;

import com.example.demo.User.UserApp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    private static final List<UserApp> USERS = Arrays.asList(
            new UserApp(1, "tom"),
            new UserApp(2, "hary")
    );

    @GetMapping("student/{studentId}")
    public ResponseEntity<UserApp> getUser(@PathVariable("studentId") Integer studentId) {
        UserApp userApp = USERS
                .stream()
                .filter(user -> studentId.equals(user.getUserId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student " + studentId + "not exits"));

        return ResponseEntity.ok().body(userApp);
    }

    @GetMapping("students")
    @PreAuthorize("hasAuthority('student:read')")
    public ResponseEntity<List<UserApp>> getUsers() {
        return ResponseEntity.ok().body(USERS);
    }

    @PostMapping("course")
    public ResponseEntity<String> createCourse() {
        return ResponseEntity.ok().body("Create Course !");
    }
}
