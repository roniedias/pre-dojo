package br.com.amil.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import br.com.amil.LogReader;
import br.com.amil.model.Player;



public class Teste {
	
	// Adicione aqui o caminho onde se encontra o arquivo de log a ser lido no teste
	private static final String FILE_PATH = "/Users/roniedias/Desktop/game.log"; 
	
	File file = new File(FILE_PATH);
	LogReader logReader;
	Set<String> matches;
	List<Player> players;
	Map<String, Player> winners;
	
	@Before
    public void initObjects() {
		logReader = new LogReader(FILE_PATH);
    }
	
	@Test
	public void testFileExistance() {
		assertTrue(file.exists());
	}
	
	@Test
	public void assertMatchesListNotEmpty() {
		matches = logReader.getMatches();
		assertFalse(matches.isEmpty());	
	}
	
	@Test
	public void assertPlayersListNotEmpty() {
		players = logReader.getPlayers();
		assertFalse(players.isEmpty());	
	}

	@Test
	public void assertWinnersListNotEmpty() {
		winners = logReader.getWinners();
		assertFalse(winners.isEmpty());			
	}	
	

}
