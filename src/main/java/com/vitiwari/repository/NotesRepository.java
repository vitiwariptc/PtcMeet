package com.vitiwari.repository;

import com.vitiwari.model.Notes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface NotesRepository extends JpaRepository<Notes, Integer> {
    Notes findById(int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT into NOTES1 (description, title, user_id)"
            + "values(:description, :title, :uid) "
            , nativeQuery = true)
    void addNoteToUser(@Param("title") String title,
                       @Param("description") String description,
                       @Param("uid") int uid
                      );

}

//    Create a custom query using @Query and reference the object's fields using SpEL (:#{#filter.fieldName}).
