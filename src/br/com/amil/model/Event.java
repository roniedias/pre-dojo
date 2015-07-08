package br.com.amil.model;

import java.util.Date;

public class Event {
	
	private String killer;
	private String victim;
	private Date dateTime;
	private String weapon;
	private String match;
	
	public String getKiller() {
		return killer;
	}
	public void setKiller(String killer) {
		this.killer = killer;
	}
	
	public String getVictim() {
		return victim;
	}
	public void setVictim(String victim) {
		this.victim = victim;
	}
	
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getWeapon() {
		return weapon;
	}
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
	
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	
	public Event() {}
	
	public Event(String killer, String victim, Date dateTime, String weapon, String match) {
		this.killer = killer;
		this.victim = victim;
		this.dateTime = dateTime;
		this.weapon = weapon;
		this.match = match;
	}
	
	@Override
	public String toString() {
		return "Event [killer=" + killer + ", victim=" + victim + ", dateTime="
				+ dateTime + ", weapon=" + weapon + ", match=" + match + "]";
	}
	
}