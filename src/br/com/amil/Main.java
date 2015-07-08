package br.com.amil;

import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.amil.model.Player;

public class Main {
	
	public static void main(String[] args) {
		
		// Adicione aqui o caminho onde se encontra o arquivo de log a ser lido no teste
		String filePath = "/Users/roniedias/Desktop/game.log";
		
		LogReader logReader = new LogReader(filePath);
		Set<String> matches = logReader.getMatches(); 
		List<Player> players = logReader.getPlayers();
		Map<String, Player> winners = logReader.getWinners();
		
		System.out.println("*************************" );
		System.out.println("***** RANKING FINAL *****" );
		System.out.println("*************************" );
		System.out.println("\n");
		
		for(String m : matches) {
			System.out.println("*** PARTIDA " + m + " ***");
			for(Player p : players) {
				if(p.getMatch().equals(m)) {
					System.out.println("Jogador: " + p.getName() + "; Assassinato(s): " + p.getKills() + "; Morte(s): " + p.getDeaths());
				}
			}
			
			Player winner = new Player();
			
			for(Map.Entry<String, Player> entry : winners.entrySet()) {
				if(entry.getKey().equals(m)) {
					winner = entry.getValue();
				}
			}
			
			System.out.println("* VENCEDOR: " + winner.getName() + ", com " + winner.getKills() + " assassinato(s) e " + winner.getDeaths() + " morte(s). Ganhou um premio!!!!");
			System.out.println("* Arma preferida: " + logReader.getPreferredWeapon(winner.getName(), winner.getMatch()));
			System.out.println("\n");

		}	
	}
	
}
