package softuni.library.repositories;
import org.springframework.data.jpa.repository.Query;
import softuni.library.models.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    @Query("SELECT c FROM Character c WHERE c.age >= 32 ORDER BY c.book.name, c.lastName DESC, c.age")
    Set<Character> exportCharactersByAge();

    Optional<Character> findAllByFirstNameAndMiddleNameAndLastName(String f, String m, String l);
}
