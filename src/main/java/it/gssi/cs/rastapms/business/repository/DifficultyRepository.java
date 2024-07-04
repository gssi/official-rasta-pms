package it.gssi.cs.rastapms.business.repository;

import it.gssi.cs.rastapms.domain.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyRepository extends JpaRepository<Difficulty, Long> {
    Difficulty findByName(String name);
}
