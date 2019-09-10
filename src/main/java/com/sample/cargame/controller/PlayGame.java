package com.sample.cargame.controller;

import com.sample.cargame.service.NextCityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayGame {
    private final NextCityService nextCityService;

    public PlayGame(NextCityService nextCityService) {
        this.nextCityService = nextCityService;
    }

    @GetMapping(value = "/game/{city}")
    public String getDifficultAnswer(@PathVariable("city") String inputCity) {
        return nextCityService.nextMove(inputCity);
    }
}
