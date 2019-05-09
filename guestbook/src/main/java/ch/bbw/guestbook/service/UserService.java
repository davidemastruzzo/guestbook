package ch.bbw.guestbook.service;

import ch.bbw.guestbook.exchange.RegistrationRequest;
import ch.bbw.guestbook.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ch.bbw.guestbook.domain.User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }

    public boolean registrationRequestIsValid(RegistrationRequest registrationRequest) {
        if (userRepository.findAllByDeletedFalse().stream()
                .anyMatch(user -> user.getUsername().equals(registrationRequest.getUsername()))) {
            return false;
        }

        if (!registrationRequest.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$^+|§°¦}{'_=!-*()@%&]).{8,100}$")) {
            return false;
        }

        if (!registrationRequest.getAcceptedTerms()) {
            return false;
        }

        if (!registrationRequest.getPassword().equalsIgnoreCase(registrationRequest.getRetypePassword())) {
            return false;
        }

        return true;
    }
}
