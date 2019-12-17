package com.aca.rest.service;

import java.time.LocalDate;
import java.util.List;
import com.aca.rest.dao.MovieDao;
import com.aca.rest.dao.MovieDbDao;
import com.aca.rest.model.EmailMessage;
import com.aca.rest.model.Movie;

public class MovieService {
	
	private MovieDao dao = new MovieDbDao();
	
	private void validateGenre(String genre) {
		if (genre.equalsIgnoreCase("Action") || genre.equals("Comedy") || genre.equals("SyFy")) {
		} else {
			ErrorResponse.invalidGenre(genre);
		}
		return;
	}
	
	public List<Movie> getMovieById(int id) {
		return dao.getMovieById(id);
	}
	
	public List<Movie> getAllMovies() {
		return dao.getAllMovies();
	}

	public List<Movie> getByGenre(String genre) {
		validateGenre(genre);
		return dao.getByGenre(genre);
	}
	
	public List<Movie> getByReleaseYear(int releaseYear) {
		return dao.getByReleaseYear(releaseYear);
	}
	
	public List<Movie> deleteById(int id) {
		List<Movie> movies = dao.getMovieById(id);
		if (movies.size() == 1) {
			dao.deleteById(movies.get(0));
		} else {
			ErrorResponse.invalidDelete(id);
		}
		return movies;
	}
	
	public String sendEmail(EmailMessage emailMessage) {
		SnsPublish service = new SnsPublish();
		String messageId = service.sendEmail(emailMessage);
		return messageId;
	}

	public List<Movie> add(Movie newMovie) {
		validateReleaseYear(newMovie.getReleaseYear());
//		List<Movie> movies = dao.add(newMovie);
//		SnsPublish.publishNewMovie(movies.get(0));
		return dao.add(newMovie);
	}
	
//	public List<Movie> insert(Movie newMovie) {
//		validateReleaseYear(newMovie.getReleaseYear());
//		List<Movie> movies = dao.add(newMovie);
//		SnsPublish.publishNewMovie(movies.get(0));
//		return dao.insert(newMovie);
//		return movies;
//	}

	private void validateReleaseYear(int releaseYear) {
		LocalDate localDate = LocalDate.now().plusYears(1);
		int maxYear = localDate.getYear();
		int minYear = 1920;
		
		if (releaseYear < minYear || releaseYear > maxYear) {
			ErrorResponse.invalidReleaseYear(releaseYear, minYear, maxYear);
		}
	}

	public List<Movie> update(Movie updateMovie) {
		boolean validUpdate = false;
		if (updateMovie.hasReleaseYear()) {
			validateReleaseYear(updateMovie.getReleaseYear());
			validUpdate = true;
		}
		
		if (updateMovie.hasGenre()) {
			validateGenre(updateMovie.getGenre());
			validUpdate = true;
		}
		
		if(updateMovie.hasTitle()) {
			//TODO validate title = between 1 and 40 characters
			validUpdate = true;
		}
		
		if(!validUpdate) {
			ErrorResponse.invalidUpdate();
		}
		
		return dao.update(updateMovie);
	}
}