package com.checkers.server.controller;

import com.checkers.server.model.RequestToStartGame;
import com.checkers.server.model.RequestedToStartGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class LobbyController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/invite")
    public void startGame(Principal principal, RequestToStartGame requestToStartGame) {
        String from = principal.getName();
        RequestedToStartGame requestedToStartGame = new RequestedToStartGame(from);

        messagingTemplate.convertAndSendToUser(
                requestToStartGame.getToUser(),
                "/queue/startGame",
                requestedToStartGame
        );
    }
}
