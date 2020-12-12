package org.rbat.players.service;

import org.modelmapper.TypeToken;
import org.rbat.players.dto.PlayerDto;
import org.rbat.players.entity.PlayerEntity;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.util.Set;

@Component
public class PlayerMapper {

    private ModelMapper modelMapper = new ModelMapper();

    protected PlayerEntity dtoToEntity(PlayerDto refCodeDto) {

        java.lang.reflect.Type targetListType = new TypeToken<Set<PlayerDto>>() {}.getType();
        return this.modelMapper.map(refCodeDto, PlayerEntity.class);
    }

    protected PlayerDto entityToDto(PlayerEntity entity) {
        return this.modelMapper.map(entity, PlayerDto.class);
    }


}
