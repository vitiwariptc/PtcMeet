package com.vitiwari.repository;

import com.vitiwari.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Integer> {
}
