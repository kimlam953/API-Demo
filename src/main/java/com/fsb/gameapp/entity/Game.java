package com.fsb.gameapp.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Game {
	private @Id @GeneratedValue Long id;
	private @Column(unique=true) String name;
	private Date createDate;
	private boolean active;
	
	
	public Game() {

	}
	
	public Game(String name) {
		this.name = name;
	}
	
	public Game(String name, boolean active) {
		this.name = name;
		this.active = active;
	}
	
	public Game(Long id, String name, boolean active) {
		this.id = id;
		this.name = name;
		this.active = active;
	}
	
	public void setName(String name) {
		this.name = name;		
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
	
	public void setActive (boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return this.active;		
	}
	
	public void setId(Long id) {
		    this.id = id;
		  }
	
	  public Long getId() {
		    return this.id;
		  }

}
