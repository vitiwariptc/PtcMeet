package com.vitiwari.controller;

import com.vitiwari.response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Optional;


@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private ArrayList<String> activeUsers = new ArrayList<>();

    // mapped as /app/addUser
    @MessageMapping("/addUser")
    @SendTo("/chatroom/activeUsers")
    public ArrayList<String> addUserAndGetActiveUsersList(@RequestBody Message message) {

        Optional <String> name = activeUsers.stream()
                                .filter(ele -> ele.equals(message.getMessage()))
                                .findFirst();

        if(name.isEmpty())
            activeUsers.add(message.getSenderName());

        return activeUsers;
    }

    @MessageMapping("/removeUser")
    @SendTo("/chatroom/removeUser")
    public Message removeUser(@RequestBody Message message) {
        activeUsers.remove(message.getSenderName());
        return message;
    }

    // mapped as /app/message
    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@RequestBody Message message){
        return message;
    }

    @MessageMapping("/sendFile")
    @SendTo("/chatroom/getFile")
    public Message receiveFile(@RequestBody Message message){
        return message;
    }


    // mapped as /app/private-message
    @MessageMapping("/private-message")
    public void recMessage(@RequestBody Message message){

        // client needs to be subscribed to /user/{username}/private
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);

        System.out.println(message.toString());

    }

    @MessageMapping("/private-file")
    public void recFile(@RequestBody Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "privateFile", message);
    }

    @MessageMapping("/private-offer")
    public void recOffer(@RequestBody Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/privateOffer",message);
        System.out.println(message);
    }

    @MessageMapping("/private-answer")
    public void recAnswer(@RequestBody Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/privateAnswer",message);
        System.out.println(message);
    }

    @MessageMapping("/private-icecandidate")
    public void recIceCandidate(@RequestBody Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/privateIceCandidate",message);
        System.out.println(message);
    }
}
