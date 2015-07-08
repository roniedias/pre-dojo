package br.com.amil.model;

public class Player {

	private String name;
	private String match;
	private int kills;
	private int deaths;
	private String preferredWeapon;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	
	public int getKills() {
		return kills;
	}
	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public int getDeaths() {
		return deaths;
	}
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public String getPreferredWeapon() {
		return preferredWeapon;
	}
	public void setPreferredWeapon(String preferredWeapon) {
		this.preferredWeapon = preferredWeapon;
	}
		
	
	public Player(){}
	
	public Player(String name, String match, int kills, int deaths, String preferredWeapon) {
		this.name = name;
		this.match = match;
		this.kills = kills;
		this.deaths = deaths;
		this.preferredWeapon = preferredWeapon;
	}
	
	@Override
	public String toString() {
		return "Player [name=" + name + ", match=" + match + ", kills=" + kills
				+ ", deaths=" + deaths + ", preferredWeapon=" + preferredWeapon + "]";
	}
	
	
}
