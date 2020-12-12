package org.rbat.players.service;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rbat.players.dto.PlayerDto;
import org.rbat.players.entity.PlayerEntity;
import org.rbat.players.exception.RecordNotFoundException;
import org.rbat.players.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest extends TestCase {

    @InjectMocks
    PlayerService service;

    @Mock
    PlayerRepository repository;

    @Mock
    private PlayerMapper mapper;
    private PlayerDto playerDto = new PlayerDto();
    private PlayerEntity playerEntity = new PlayerEntity();

    @Before
    public void setUp() {
        when(mapper.dtoToEntity(any())).thenReturn(playerEntity);
        playerEntity.setId(1L);
        playerEntity.setName("Rob");
        playerEntity.setPlaying(false);

        when(mapper.entityToDto(any())).thenReturn(playerDto);
        playerDto.setId(1L);
        playerDto.setName("Rob");
        playerDto.setPlaying(true);
    }

    @Test
    public void testListAllPlayers() {

        List<PlayerDto> playerList = new ArrayList<>();
        PlayerDto player1 = new PlayerDto(1l, "Rob", Boolean.FALSE);
        PlayerDto player2 = new PlayerDto(2l, "John", Boolean.FALSE);
        PlayerDto player3 = new PlayerDto(3l, "Paul", Boolean.TRUE);
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);

        List<PlayerEntity> playerEntities =
                playerList.stream().map(dto -> mapper.dtoToEntity(dto)).collect(Collectors.toList());
        when(repository.findAll()).thenReturn(playerEntities);

        //test
        List<PlayerDto> allPlayers = service.listAllPlayers();
        assertEquals(3, allPlayers.size());
        verify(repository, times(1)).findAll();
        assertNotNull(allPlayers);
    }

    @Test
    public void testGetPlayerById() {
        try {
            when(repository.findById(anyLong())).thenReturn(Optional.of(playerEntity));

            //test
            PlayerDto playerById = service.getPlayerById(1l);
            assertEquals("Rob", playerById.getName());
            assertNotNull(playerById);
        } catch (RecordNotFoundException re) {
            Assert.assertNotNull("No player record exist for given id", re);
            Assert.assertTrue("NullPointerException SHOULD NOT be thrown!", re instanceof RecordNotFoundException);
        }
    }

    @Test
    public void testCreateOrUpdatePlayer() {
        PlayerDto player1 = new PlayerDto(1l, "Rob", Boolean.FALSE);
        PlayerEntity playerEntity = mapper.dtoToEntity(player1);

        //test
        service.createOrUpdatePlayer(player1);
        verify(repository, times(1)).save(playerEntity);
    }

    @Test
    public void testDeletePlayerById() {
        try {
            PlayerDto player1 = new PlayerDto(1l, "Rob", Boolean.FALSE);
            when(repository.findById(anyLong())).thenReturn(Optional.of(playerEntity));

            //test
            service.deletePlayerById(player1.getId());
            verify(repository, times(1)).deleteById(player1.getId());
        } catch (RecordNotFoundException re) {
            Assert.assertNotNull("No player record exist for given id", re);
            Assert.assertTrue("NullPointerException SHOULD NOT be thrown!", re instanceof RecordNotFoundException);
        }
    }

    @Test
    public void testSendIAmPlaying() {
        try {
            PlayerDto player1 = new PlayerDto(1l, "Rob", Boolean.FALSE);
            when(repository.findById(anyLong())).thenReturn(Optional.of(playerEntity));

            //test
            PlayerDto playerDto = service.sendIAmPlaying(player1.getId());
            verify(repository, times(1)).save(playerEntity);

            assertEquals(Boolean.TRUE, playerDto.getPlaying());
            assertNotNull(playerDto);
        } catch (RecordNotFoundException re) {
            Assert.assertNotNull("No player is present for given ID to send playing status", re);
            Assert.assertTrue("NullPointerException SHOULD NOT be thrown!", re instanceof RecordNotFoundException);
        }
    }

    @Test
    public void testFetchPlayingStatusAfter1minute() {
        try {
            List<PlayerDto> playerList = new ArrayList<>();
            PlayerDto player1 = new PlayerDto(1l, "Rob", Boolean.FALSE);
            PlayerDto player2 = new PlayerDto(2l, "John", Boolean.FALSE);
            PlayerDto player3 = new PlayerDto(3l, "Paul", Boolean.TRUE);
            playerList.add(player1);
            playerList.add(player2);
            playerList.add(player3);

            List<PlayerEntity> playerEntities =
                    playerList.stream().map(dto -> mapper.dtoToEntity(dto)).collect(Collectors.toList());

            service.fetchPlayingStatusAfter1minute();

        } catch (Exception re) {
            Assert.assertNotNull("No player record exist for given id", re);
            Assert.assertFalse("NullPointerException SHOULD NOT be thrown!", re instanceof NullPointerException);
            Assert.assertTrue("NullPointerException SHOULD NOT be thrown!", re instanceof RecordNotFoundException);
            Assert.assertNotNull("Exception message is null....weird!?", re.getMessage());
            Assert.fail(re.getMessage());
        }
    }
}