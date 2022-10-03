package antifraud.controller;

import antifraud.model.User;
import antifraud.model.dto.ChangeAccessRequest;
import antifraud.model.dto.ChangeRoleRequest;
import antifraud.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    User createUser(@Valid @RequestBody User user) {
        return userService.register(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/role")
    User changeRole(@Valid @RequestBody ChangeRoleRequest changeRoleRequest) {
        return userService.changeRole(changeRoleRequest.getUsername(), changeRoleRequest.getRole());
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/access")
    Map<String, String> changeAccess(@Valid @RequestBody ChangeAccessRequest changeAccessRequest) {
        return userService.changeAccess(changeAccessRequest.getUsername(), changeAccessRequest.getOperation());
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'SUPPORT')")
    @GetMapping("/list")
    List<User> listUsers() {
        return userService.listUsers();
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/user/{username}")
    Map<String, String> delete(@PathVariable String username) {
        if(userService.delete(username)) {
            return Map.of(
                    "username", username,
                    "status", "Deleted successfully!"
            );
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/user/")
    public void deleteUser() {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

}
