package br.com.amil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.amil.model.Event;
import br.com.amil.model.Player;

public class LogReader {
	
	private List<Event> events;
	private Set<String> playerNames;
	private List<Player> players;
	private Set<String> matches;
	private Map<String, Player> winners;
	
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Set<String> getPlayerNames() {
		return playerNames;
	}
	public Set<String> getMatches() {
		return matches;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public Map<String, Player> getWinners() {
		return winners;
	}
		
	
	public LogReader(String filePath) {
		 
		BufferedReader br = null;
		
		List<Event> events = new ArrayList<Event>();
 
		try {
 
			String sCurrentLine;
			String match = new String();
			 
			br = new BufferedReader(new FileReader(filePath));
 
			while ((sCurrentLine = br.readLine()) != null) {
				
				if(getWordAfter(sCurrentLine, "match") != "-1") {
					match = getWordAfter(sCurrentLine, "match"); 
				}
				
				String killer = getWordBefore(sCurrentLine, "killed");
				String victim = getWordAfter(sCurrentLine, "killed");
				String weapon;
				
				if(!killer.equals("-1")) {
					if(killer.equals("<WORLD>")) {
						weapon = getWordAfter(sCurrentLine, "by");	
					}
					else {
						weapon = getWordAfter(sCurrentLine, "using");
					}
					events.add(new Event(killer.trim(), victim.trim(), getLogDateTime(sCurrentLine), weapon, match));
				}
			}				
 
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			
			try {
				if (br != null) {
					br.close();
				}
			} 
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		setEvents(events);
		setPlayerNames(events);
		setMatches(events);
		setPlayers(getMatches());
		setWinners(getPlayers(), getMatches());
	}
	

	private String getWordBefore(String phrase, String word) {
		int indx = phrase.indexOf(word);
		String w = "-1";
		if(indx > -1) { 
			w = new String();
			int end = indx - 2;
			for(int j = end; j > 0; j--) {
				w = phrase.charAt(j) + w;
				if(phrase.charAt(j) == ' ') {
					break;
				}
			}	
		}
		if(w.equals("-1")) {
			return w;
		}
		else {
			return w.substring(1);
		}
	}
	

	private String getWordAfter(String phrase, String word) {
		int indx = phrase.indexOf(word);
		String w = "-1";
		if(indx > -1) {
			w = new String();
			int end = indx + word.length() + 1;
			for(int i = end; i < phrase.length(); i++) {
				w += phrase.charAt(i);
				if(phrase.charAt(i) == ' ') {
					break;
				}
			}	
		}
		return w;
	}	
	
	
	private Date getLogDateTime(String phrase) {
		String date = "";
		String time = "";
		for(int i = 0; i < phrase.length(); i++) {
			date += phrase.charAt(i);
			if(phrase.charAt(i) == ' ') {
				for(int j = i+1; j < phrase.length(); j++) {
					time += phrase.charAt(j);
					if(phrase.charAt(j) == ' ') {
						break;
					}
				}
				break;
			}				
		}
		return toDate(date + time);
	}
	
	
	private Date toDate(String strDate) {
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = null;
		try {
			date = fmt.parse(strDate);
		} catch (ParseException e1) {
		}
		return date;
	}
	
	
	
	private void setPlayerNames(List<Event> events) {
		
		Set<String> players = new HashSet<String>();
		
		for(Event e : events) {
			players.add(e.getVictim());
			players.add(e.getKiller());
		}
		
		this.playerNames = players;
	}
		
	
	
	private int getNumberOfVictims(String player, String match) {
		List<Event> log = new ArrayList<Event>();
		log = getEvents();
		int numberOfVictims = 0;
		for(Event e : log) {
			if(e.getKiller().trim().equals(player.trim()) && e.getMatch().trim().equals(match.trim())) {
				numberOfVictims += 1;
			}
		}
		return numberOfVictims;
	}
	
	
	
	private int getTimesDied(String player, String match) {
		List<Event> log = new ArrayList<Event>();
		log = getEvents();
		int timesDied = 0;
		for(Event e : log) {
			if(e.getVictim().trim().equals(player.trim()) && e.getMatch().trim().equals(match.trim())) {
				timesDied += 1;
			}
		}
		return timesDied;		
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public String getPreferredWeapon(String player, String match) {
		List<Event> log = new ArrayList<Event>();
		@SuppressWarnings("unchecked")
		List<String> weapons = new ArrayList();
		log = getEvents();
		for(Event e : log) {
			if(e.getKiller().trim().equals(player.trim()) && e.getMatch().trim().equals(match.trim())) {
				weapons.add(e.getWeapon());
			}
		}
		
		Set<String> weaponNames = new HashSet<String>();
		for(String n : weapons) {
			weaponNames.add(n);
		}
		
		Map<String, Integer> weaponsUsage = new HashMap<String, Integer>();
		
		for(String n : weaponNames) {
			weaponsUsage.put(n, Collections.frequency(weapons, n));
		}
		
		int highestValue = 0;
		String name = new String();
		
		for(Map.Entry<String, Integer> entry : weaponsUsage.entrySet()) {
			if(entry.getValue() > highestValue) {
				highestValue = entry.getValue();
			}
		}
		
		for(Map.Entry<String, Integer> entry : weaponsUsage.entrySet()) {
			if(entry.getValue() == highestValue) {
				name += ", " + entry.getKey();
			}
		}
		
		if(highestValue == 0) {
			name = "No preferred weapon";
		}
		else {
			name = name.substring(2);
		}

		return name;		
		
	}
	
	
	private void setMatches(List<Event> events) {
		Set<String> matches = new HashSet<String>();
		for(Event e : events) {
			matches.add(e.getMatch().trim());
		}
		this.matches = matches;
	}
	
	
	private void setPlayers(Set<String> matches) {
		List<Player> players = new ArrayList<Player>();
		Player player = new Player();
		for(String m : matches) {
			for(String p : getPlayerNames()) {
				int numberOfVictims = getNumberOfVictims(p, m);
				int timesDied = getTimesDied(p, m);
				if(!(numberOfVictims == 0 && timesDied == 0)) {
					if(!p.trim().equals("<WORLD>")) {
						player = new Player(p, m, getNumberOfVictims(p, m), getTimesDied(p, m), getPreferredWeapon(p, m));
						players.add(player);
					}
				}
			}	
		}
		this.players = players;
	}
	
	
	
	private void setWinners(List<Player> players, Set<String> matches) {
		
		Map<String, Player> winners = new HashMap<String, Player>();
		int value = 0;
		Player player = new Player();
		
		for(String m : matches) {
			for(Player p : players) {
				if(m.trim().equals(p.getMatch().trim())) {
					int difference = p.getKills() - p.getDeaths(); 
				 	if(difference > value) {
				 		value = difference;
				 		player = p;
				 	}
				}
			}
			winners.put(m, player);
			value = 0;
			player = new Player();
		}
		this.winners = winners;
	}
	
	
	
	
	
}
