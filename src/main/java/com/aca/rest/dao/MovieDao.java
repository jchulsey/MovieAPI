package com.aca.rest.dao;

import java.util.List;

import com.aca.rest.model.Movie;

public interface MovieDao {

	List<Movie> getAllMovies();

	List<Movie> getByGenre(String genre);

	List<Movie> getByReleaseYear(int releaseYear);

	List<Movie> getMovieById(int id);

	void deleteById(Movie movieToDelete);

	List<Movie> add(Movie newMovie);

	List<Movie> insert(Movie newMovie);

	List<Movie> update(Movie updateMovie);

}