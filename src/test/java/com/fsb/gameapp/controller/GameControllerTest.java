package com.fsb.gameapp.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fsb.gameapp.entity.Game;
import com.fsb.gameapp.service.GameService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {
	
	private GameController gameController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameController = new GameController(gameService);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @Test
    void listAllGames_ShouldReturnAllGames() throws Exception {
        // Arrange
        List<Game> games = new ArrayList<>();
        games.add(new Game(1L, "Game 1", true));
        games.add(new Game(2L, "Game 2", false));
        when(gameService.listAllGames()).thenReturn(games);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/games/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Game 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Game 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].active").value(false))
                .andDo(print());

        verify(gameService, times(1)).listAllGames();
    }

    @Test
    void createGame_ShouldCreateNewGame() throws Exception {
        // Arrange
        Game game = new Game("New Game", true);
        when(gameService.createGame(game)).thenReturn(game);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/games/newgame")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"New Game\", \"active\":true}"))     		
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Game"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))
                .andDo(print());

        verify(gameService, times(1)).createGame(game);
    }

    @Test
    void getGameById_ShouldReturnExistingGame() throws Exception {
        // Arrange
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", true);
        when(gameService.getGameById(gameId)).thenReturn(game);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/games/id/{id}", gameId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Game 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))
                .andDo(print());

        verify(gameService, times(1)).getGameById(gameId);
    }

    @Test
    void getGameByName_ShouldReturnExistingGame() throws Exception {
        // Arrange
        String gameName = "Game 1";
        Game game = new Game(1L, gameName, true);
        when(gameService.getGameByName(gameName)).thenReturn(game);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/games/name/{name}", gameName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(gameName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))
                .andDo(print());

        verify(gameService, times(1)).getGameByName(gameName);
    }

    @Test
    void updateGame_ShouldReturnUpdatedGame() throws Exception {
        // Arrange
        Long gameId = 1L;
        Game updatedGame = new Game(gameId, "Updated Game", false);
        when(gameService.updateGame(eq(gameId), any(Game.class))).thenReturn(updatedGame);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/games/id/{id}", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Game\", \"active\":false}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Game"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(false))
                .andDo(print());

        verify(gameService, times(1)).updateGame(eq(gameId), any(Game.class));
    }

    @Test
    void deleteGame_ShouldDeleteExistingGame() throws Exception {
        // Arrange
        Long gameId = 1L;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/games/id/{id}", gameId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        verify(gameService, times(1)).deleteGame(gameId);
    }
}
