package org.rbat.players.web;

import java.util.List;

import org.rbat.players.exception.RecordNotFoundException;
import org.rbat.players.dto.PlayerDto;
import org.rbat.players.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.rbat.players.entity.PlayerEntity;

@RestController
@RequestMapping("/employees")
public class PlayerController {
    @Autowired
    private PlayerService service;

    @GetMapping
    public ResponseEntity<List<PlayerDto>> listAllPlayers() {
        List<PlayerDto> list = service.listAllPlayers();

        return new ResponseEntity(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getPlayerById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        PlayerDto entity = service.getPlayerById(id);
        if (entity.getPlaying()) {
            return new ResponseEntity<String>("I am playing", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("I am not playing", HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<PlayerEntity> createOrUpdatePlayer(PlayerDto employee)
            throws RecordNotFoundException {
        PlayerDto updated = service.createOrUpdatePlayer(employee);
        return new ResponseEntity(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deletePlayerById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        service.deletePlayerById(id);
        return HttpStatus.FORBIDDEN;
    }

    @PostMapping("/playing")
    public ResponseEntity<PlayerDto> sendIAmPlaying(Long id)
            throws RecordNotFoundException {
        PlayerDto updatedStatus = service.sendIAmPlaying(id);
        return new ResponseEntity(updatedStatus, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/playing")
    public ResponseEntity fetchPlayingStatusAfter1minute() {
        service.fetchPlayingStatusAfter1minute();
        return new ResponseEntity(new HttpHeaders(), HttpStatus.OK);
    }

}