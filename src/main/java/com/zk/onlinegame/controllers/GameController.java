package com.zk.onlinegame.controllers;

import com.zk.onlinegame.entities.Clan;
import com.zk.onlinegame.entities.ClansRequest;
import com.zk.onlinegame.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/onlinegame")
public class GameController {
    @Autowired
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @PostMapping("/calculate")
    List<List<Clan>> calculate(final @RequestBody ClansRequest request) {
        return gameService.calculate(request.groupCount(), request.clans());
    }
}
