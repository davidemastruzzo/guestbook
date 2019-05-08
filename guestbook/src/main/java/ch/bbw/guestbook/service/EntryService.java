package ch.bbw.guestbook.service;

import ch.bbw.guestbook.converter.EntryConverter;
import ch.bbw.guestbook.domain.Entry;
import ch.bbw.guestbook.domain.User;
import ch.bbw.guestbook.exception.GuestbookRuntimeException;
import ch.bbw.guestbook.exchange.EntryDto;
import ch.bbw.guestbook.repository.EntryRepository;
import ch.bbw.guestbook.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@Service
public class EntryService {

    private final EntryRepository entryRepository;

    private final UserRepository userRepository;

    public List<EntryDto> createEntry(EntryDto entryDto, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        if (user == null) {
            throw new GuestbookRuntimeException();
        }
        Entry entry = Entry.builder().message(entryDto.getMessage()).user(user).build();
        entryRepository.save(entry);
        return getAllEntries();
    }

    public List<EntryDto> getAllEntries() {
        return EntryConverter.convert(entryRepository.findAllByDeletedFalse());
    }
}
