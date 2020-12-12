package org.rbat.players.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto implements Serializable {
    private Long id;
    private String name;
    private Boolean playing;
}
