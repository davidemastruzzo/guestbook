package ch.bbw.guestbook.rest;

import ch.bbw.guestbook.converter.UserConverter;
import ch.bbw.guestbook.domain.User;
import ch.bbw.guestbook.exchange.RegistrationRequest;
import ch.bbw.guestbook.exchange.UserDto;
import ch.bbw.guestbook.repository.UserRepository;
import ch.bbw.guestbook.service.UserService;
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

    @Resource
    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody RegistrationRequest registrationRequest) {

        if (userService.registrationRequestIsValid(registrationRequest)) {
            User account = User.builder()
                    .username(registrationRequest.getUsername())
                    .password(bCryptPasswordEncoder.encode(registrationRequest.getPassword()))
                    .build();
            account = userRepository.save(account);
            return new ResponseEntity<>(UserConverter.convert(account),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/username")
    public ResponseEntity<Boolean> usernameIsValid(@RequestBody String username) {
        if (userRepository.findAllByDeletedFalse().stream()
                .anyMatch(user -> user.getUsername().equals(username))) {
            return new ResponseEntity<>(false,HttpStatus.OK);
        }
        return new ResponseEntity<>(true,HttpStatus.OK);
    }
}
