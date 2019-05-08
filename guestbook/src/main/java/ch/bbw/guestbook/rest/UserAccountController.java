package ch.bbw.guestbook.rest;

import ch.bbw.guestbook.domain.User;
import ch.bbw.guestbook.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@AllArgsConstructor
@RequestMapping(path = WebServiceUrl.UserAccount)
public class UserAccountController {

    @Resource
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody User newUser) {
        if (userRepository.findAllByDeletedFalse().stream()
                .anyMatch(user -> user.getUsername().equals(newUser.getUsername()))) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        return new ResponseEntity(HttpStatus.OK);
    }
}
