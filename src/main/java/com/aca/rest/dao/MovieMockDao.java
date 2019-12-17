package com.aca.rest.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.aca.rest.model.Movie;

public class MovieMockDao implements MovieDao {
	
	private static int keyCounter = 1;
	private static List<Movie> movies = new ArrayList<Movie>();
	
	private static int getNextKey() {
		return keyCounter = keyCounter + 1;
	}
	
	static {
		movies.add(new Movie(keyCounter, "Goldfinger", "Action", 1956));
		movies.add(new Movie(getNextKey(), "Star Trek", "SyFy", 1975));
		movies.add(new Movie(getNextKey(), "The Jerk", "Comedy", 1980));
		movies.add(new Movie(getNextKey(), "XXX", "Action", 2009));
		movies.add(new Movie(getNextKey(), "", "Comedy", 2019));
		movies.add(new Movie(getNextKey(), null, "Comedy", 2019));
		
	}
	
	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#getAllMovies()
	 */
	@Override
	public List<Movie> getAllMovies() {
		List<Movie> myMovies = new ArrayList<Movie>();
		myMovies.addAll(MovieMockDao.movies);
		
		return myMovies;
	}

	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#getByGenre(java.lang.String)
	 */
	@Override
	public List<Movie> getByGenre(String genre) {
		List<Movie> myMovies = new ArrayList<Movie>();
		
		for (Movie movie : MovieMockDao.movies) {
			if (movie.getGenre().equalsIgnoreCase(genre)) {
				myMovies.add(movie);
			}
		}
		
		return myMovies;
	}
	
	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#getByReleaseYear(int)
	 */
	@Override
	public List<Movie> getByReleaseYear(int releaseYear) {
		List<Movie> myMovies = new ArrayList<Movie>();
		
		for (Movie movie : MovieMockDao.movies) {
			if (movie.getReleaseYear() == releaseYear) {
				myMovies.add(movie);
			}
		}
		
		return myMovies;
	}
	
	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#getMovieById(int)
	 */
	@Override
	public List<Movie> getMovieById(int id) {
		List<Movie> myMovies = new ArrayList<Movie>();
		
		for (Movie movie : movies) {
			if (movie.getId() == id) {
				myMovies.add(movie);
			}
		}
		
		return myMovies;
	}

	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#deleteById(com.aca.rest.model.Movie)
	 */
	@Override
	public void deleteById(Movie movieToDelete) {
		movies.remove(movieToDelete);
	}

	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#add(com.aca.rest.model.Movie)
	 */
	@Override
	public List<Movie> add(Movie newMovie) {
		newMovie.setCreateDate(LocalDateTime.now());
		newMovie.setId(getNextKey());
		movies.add(newMovie);
		
		List<Movie> myMovies = new ArrayList<Movie>();
		myMovies.add(newMovie);
		return myMovies;
	}

	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#insert(com.aca.rest.model.Movie)
	 */
	@Override
	public List<Movie> insert(Movie newMovie) {
		newMovie.setCreateDate(LocalDateTime.now());
		newMovie.setId(getNextKey());
		movies.add(newMovie);
		
		List<Movie> myMovies = new ArrayList<Movie>();
		myMovies.add(newMovie);
		return myMovies;
	}

	/* (non-Javadoc)
	 * @see com.aca.rest.dao.MovieDao#update(com.aca.rest.model.Movie)
	 */
	@Override
	public List<Movie> update(Movie updateMovie) {
		List<Movie> myMovies = new ArrayList<Movie>();
		
		for (Movie movie : movies) {
			if (movie.getId() == updateMovie.getId()) {
				movie.setGenre(updateMovie.getGenre());
				movie.setTitle(updateMovie.getTitle());
				movie.setReleaseYear(updateMovie.getReleaseYear());
				myMovies.add(movie);
			}
		}
		
		return myMovies;
	}

}
