package org.rbat.players.service;

import java.util.*;
import java.util.stream.Collectors;

import org.rbat.players.dto.PlayerDto;
import org.rbat.players.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.rbat.players.exception.RecordNotFoundException;
import org.rbat.players.entity.PlayerEntity;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository repository;

    @Autowired
    private PlayerMapper mapper;


    public List<PlayerDto> listAllPlayers() {
        List<PlayerEntity> entities = repository.findAll();

        List<PlayerDto> playerList =
                entities.stream().map(playerEntity -> mapper.entityToDto(playerEntity)).collect(Collectors.toList());

        if (playerList.size() > 0) {
            return playerList;
        } else {
            return new ArrayList();
        }
    }

    public PlayerDto getPlayerById(Long id) throws RecordNotFoundException {
        Optional<PlayerEntity> player = repository.findById(id);
        if (player.isPresent()) {
            return mapper.entityToDto(player.get());
        } else {
            throw new RecordNotFoundException("No player record exist for given id");
        }
    }

    public PlayerDto createOrUpdatePlayer(PlayerDto dto) {
        Optional<PlayerEntity> player = repository.findById(dto.getId());
        if (player.isPresent()) {
            PlayerEntity newEntity = player.get();
            newEntity.setName(dto.getName());
            newEntity = repository.save(newEntity);
            return mapper.entityToDto(newEntity);
        } else {
            PlayerEntity save = repository.save(mapper.dtoToEntity(dto));
            return mapper.entityToDto(save);
        }
    }

    public void deletePlayerById(Long id) throws RecordNotFoundException {
        Optional<PlayerEntity> player = repository.findById(id);
        if (player.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No player record exist for given id");
        }
    }

    public PlayerDto sendIAmPlaying(Long id) throws RecordNotFoundException {
        Optional<PlayerEntity> player = repository.findById(id);
        if (player.isPresent()) {
            PlayerEntity newEntity = player.get();
            newEntity.setPlaying(Boolean.TRUE);
            repository.save(newEntity);
            return mapper.entityToDto(newEntity);
        } else {
            throw new RecordNotFoundException("No player is present for given ID to send playing status");
        }
    }

    public void fetchPlayingStatusAfter1minute() {
        int delay = 60000; // delay for 60 sec.
        int period = 60000; // repeat every 60 sec.

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                List<PlayerEntity> allPlayers = repository.findAll();
                allPlayers.stream().forEach(playerEntity -> {
                    if (playerEntity.isPlaying()) {
                        playerEntity.setPlaying(false);
                        repository.save(playerEntity);
                        return;
                    } else {
                        try {
                            deletePlayerById(playerEntity.getId());
                            return;
                        } catch (RecordNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, delay, period);
    }
}