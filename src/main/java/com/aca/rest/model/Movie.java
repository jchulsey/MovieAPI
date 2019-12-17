package com.aca.rest.model;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbProperty;

public class Movie {
	
	@JsonbProperty(nillable = true)
	private String title;
	private int id;
	private String genre;
	private int releaseYear;
	private LocalDateTime createDate;
	
	public Movie() {
	
	}
	
	public Movie(int id, String title, String genre, int releaseYear) {
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.releaseYear = releaseYear;
		this.setCreateDate(LocalDateTime.now());
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}
	
	public String toString() {
		return "title: " + title + " release year: " + releaseYear + " genre: " + genre + " , " + id;
	}
	
	public boolean hasTitle() {
		if (this.getTitle() != null) {
			return true;
		} 
		return false;
	}
	
	public boolean hasGenre() {
		if (this.getGenre() != null) {
			return true;
		} 
		return false;
	}
	
	public boolean hasReleaseYear() {
		if (this.getReleaseYear() != 0) {
			return true;
		} 
		return false;
	}
	


}
