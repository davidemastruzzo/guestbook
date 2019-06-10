package ch.bbw.guestbook.rest;

import ch.bbw.guestbook.exchange.EntryDto;
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

    @GetMapping("/entries")
    @ResponseBody
    public ResponseEntity<List<EntryDto>> getAllEntries() {
        return new ResponseEntity<>(entryService.getAllEntries(), HttpStatus.OK);
    }

    @PostMapping("/entry")
    public ResponseEntity<EntryDto> createEntry(@RequestBody String message, Principal principal) {
        System.out.println(message);
        System.out.println(principal.getName());
        return new ResponseEntity<>(entryService.createEntry(message, principal), HttpStatus.OK);
    }
}
