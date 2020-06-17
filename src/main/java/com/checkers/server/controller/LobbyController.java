package com.checkers.server.controller;

import com.checkers.server.logic.Game;
import com.checkers.server.model.GameStart;
import com.checkers.server.model.UserDestination;
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
    public void startGame(Principal principal, UserDestination userDestination) {
        String from = principal.getName();
        RequestedToStartGame requestedToStartGame = new RequestedToStartGame(from);

        messagingTemplate.convertAndSendToUser(
                userDestination.getToUser(),
                "/queue/invite",
                requestedToStartGame
        );
    }

    @MessageMapping("/accept")
    public void accept(Principal principal, UserDestination userDestination) {
        String from = principal.getName();
        String to = userDestination.getToUser();

        GameStart game = new GameStart(from, to);

        messagingTemplate.convertAndSendToUser(from,"/queue/start_game", game);
        messagingTemplate.convertAndSendToUser(to,"/queue/start_game", game);
    }
}
