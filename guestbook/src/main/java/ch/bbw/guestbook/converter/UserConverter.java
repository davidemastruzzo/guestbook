package ch.bbw.guestbook.converter;

import ch.bbw.guestbook.domain.User;
import ch.bbw.guestbook.exchange.UserDto;
import lombok.NonNull;

import java.util.List;

import static java.util.stream.Collectors.toList;

public final class UserConverter {

    private UserConverter() {
        //no instances needed
    }

    public static UserDto convert(@NonNull User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    public static List<UserDto> convert(@NonNull List<User> users) {
        return users.stream().map(UserConverter::convert).collect(toList());
    }
}
