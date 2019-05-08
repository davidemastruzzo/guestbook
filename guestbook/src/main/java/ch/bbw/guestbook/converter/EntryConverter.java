package ch.bbw.guestbook.converter;

import ch.bbw.guestbook.domain.Entry;
import ch.bbw.guestbook.exchange.EntryDto;
import lombok.NonNull;

import java.util.List;

import static java.util.stream.Collectors.toList;

public final class EntryConverter {

    private EntryConverter() {
        //no instances needed
    }

    public static EntryDto convert(@NonNull Entry entry){
        return EntryDto.builder()
                .id(entry.getId())
                .message(entry.getMessage())
                .user(UserConverter.convert(entry.getUser()))
                .build();
    }

    public static List<EntryDto> convert(@NonNull List<Entry> entries){
        return entries.stream().map(EntryConverter::convert).collect(toList());
    }
}
