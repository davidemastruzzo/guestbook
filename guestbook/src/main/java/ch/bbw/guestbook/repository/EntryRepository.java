package ch.bbw.guestbook.repository;

import ch.bbw.guestbook.domain.Entry;
import org.springframework.stereotype.Repository;

/**
 * Provides a Repository for Users.
 */
@Repository
public interface EntryRepository extends BaseRepository<Entry> {
}
