package vn.hoidanit.jobhunter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.service.error.IdInvalidException;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {

    // Dependency Injection (DI)
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // @GetMapping(path = "/users/create")
    @PostMapping(path = "/users")
    public ResponseEntity<User> createNewUser(
            // @RequestBody thường được sử dụng trong các API POST, PUT, nơi client cần gửi
            // dữ liệu để tạo mới hoặc cập nhật dữ liệu trên server.
            @RequestBody User postManUser) {
        User ericUser = this.userService.handleCreateUser(postManUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(ericUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id)
            throws IdInvalidException {
        if (id >= 1500) {
            throw new IdInvalidException("Id khong lon hown 1501");
        }

        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok("ericUser");
        // return ResponseEntity.status(HttpStatus.OK).body("ericUser");
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        User user = this.userService.fetchUserByID(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<Iterable<User>> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser());
    }

    @PutMapping(path = "/users")
    public ResponseEntity<User> updateUser(
            @Valid @RequestBody User postManUser) {
        User updatedUser = this.userService.handleUpdateUser(postManUser);

        if (updatedUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

}
