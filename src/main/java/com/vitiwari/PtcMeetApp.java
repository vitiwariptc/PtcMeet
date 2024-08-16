package com.vitiwari;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PtcMeetApp implements CommandLineRunner{

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PtcMeetApp.class, args);
//        context.close();
    }

    @Override
    public void run(String... args) throws Exception {
//        User usr1 = new User(0,"vishnuT", "vitiwari@ptc.com", "vitiwari");
//        User usr2 = new User(0,"vishnu", "vitiwari@ptc.com", "vitiwari");
//        System.out.println(usr1.toString());
//        userRepository.save(usr1);
//        System.out.println(usr2.toString());
//        userRepository.save(usr2);



//        User usr = new User(1, "vishnu", "V@a.com", "vt");
//        userRepository.save(usr);
//        Notes note1 = new Notes(0,"A","B", usr);
//        Notes note2 = new Notes(0, "B", "B", usr);
//        notesRepository.save(note1);
//        notesRepository.save(note2);
//
//        usr.setNotes(Set.of(note1,note2));
//        userRepository.save(usr);
//        User usr = new User(1, "vishnu", "V@a.com", "vt");
//
//        Notes note1 = new Notes(0, "A", "B", usr);
//        Notes note2 = new Notes(0, "B", "B", usr);
//
//        notesRepository.save(note1);
//        notesRepository.save(note2);
//
//        Optional<User> usr1 = userRepository.findById(1);
//        System.out.println(usr1);
//        if (usr1.isPresent()) {
//            Set<Notes> nt = usr1.get().getNotes();
//            System.out.println(nt.size());
//        } else {
//            throw new RuntimeException("User not found");
//        }

    }
}
