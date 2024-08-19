package com.vitiwari;

import com.vitiwari.model.Notes;
import com.vitiwari.model.User;
import com.vitiwari.repository.NotesRepository;
import com.vitiwari.repository.UserRepository;
import com.vitiwari.services.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class PtcMeetApp implements CommandLineRunner{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PtcMeetApp.class, args);
        context.close();
    }

    @Override
    public void run(String... args) throws Exception {

//        User u1 = new User(0,"vishnu", "V@p.c", "aaa");
//
//        Notes n1 = new Notes(0, "A","B", u1);
//
//        u1.setNotes(Set.of(n1));
//        userRepository.save(u1);
//
//        u1 = userRepository.findByUserName("vishnu");
//        Notes n2 = new Notes(0,"B", "C", u1);
//        u1.addNote(n2);
//        userRepository.save(u1);

        /**
        User u1 = new User(0,"vishnu", "V@p.c", "aaa");

        Notes n1 = new Notes(0, "A","B", u1);

        notesRepository.save(n1);
         **/

/**
        notesRepository.addNoteToUser("V", "T" , 1);

        User u2 = userRepository.findByUserName("vishnu");
        Set<Notes> n = u2.getNotes();

        for(Notes nn : n) System.out.println(nn);

        Notes n3 = notesRepository.findById(3);

        System.out.println(n3.getUser());
 **/


        emailService.sendMail("vitiwari@ptc.com", "<h2>Hello</h2>", "Timepass");
        System.out.println("Done");
    }

}



//    When the main method of a Spring Boot application runs, the Spring context has not yet been fully initialized.
//    Therefore, repositories and other beans are not ready for use.
//    In the main method, you manually instantiate and use classes, bypassing the Spring dependency
//    injection mechanism. This can lead to issues like missing dependencies or improper configuration.
//    Thus to resolve this issue we use CommandLineRunner.