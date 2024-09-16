package com.vitiwari.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private String senderName;
    private String receiverName;
    private Object message;
    private String date;
}
