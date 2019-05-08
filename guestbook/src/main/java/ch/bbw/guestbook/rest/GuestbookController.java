package ch.bbw.guestbook.rest;

import ch.bbw.guestbook.converter.EntryConverter;
import ch.bbw.guestbook.domain.User;
import ch.bbw.guestbook.exchange.EntryDto;
import ch.bbw.guestbook.repository.EntryRepository;
import ch.bbw.guestbook.repository.UserRepository;
import ch.bbw.guestbook.service.EntryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = WebServiceUrl.Guestbook)
public class GuestbookController {

    @Resource
    private EntryService entryService;

    private final UserRepository userRepository;

    @GetMapping("/entries")
    @ResponseBody
    public ResponseEntity<List<EntryDto>> getAllEntries() {
        return new ResponseEntity<>(entryService.getAllEntries(), HttpStatus.OK);
    }

    @PostMapping("/entry")
    @ResponseBody
    public ResponseEntity createEntry(@RequestBody EntryDto entryDto, Principal principal) {
        return new ResponseEntity<>(entryService.createEntry(entryDto, principal), HttpStatus.OK);
    }

}
