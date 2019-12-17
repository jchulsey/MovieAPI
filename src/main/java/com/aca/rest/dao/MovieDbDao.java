package com.aca.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.aca.rest.model.Movie;

public class MovieDbDao implements MovieDao {
	
	private final static String selectAllMovies = 
			"SELECT MovieId, Title, GenreId, ReleaseYear, CreateDate, UpdateDate " +
			"FROM movie";
	private final static String selectMoviesById = 
			"SELECT MovieId, Title, GenreId, ReleaseYear, CreateDate, UpdateDate " +
			"FROM movie " +
			" WHERE MovieId = ?";
	private final static String selectMoviesByGenre = 
			"SELECT MovieId, Title, GenreId, ReleaseYear, CreateDate, UpdateDate " +
			"FROM movie " +
			" WHERE GenreId = ?";
	private final static String selectMoviesByReleaseYear = 
			"SELECT MovieId, Title, GenreId, ReleaseYear, CreateDate, UpdateDate " +
			"FROM movie " +
			" WHERE ReleaseYear = ?";
	private final static String deleteMoviesById = 
			"DELETE FROM movie " +
			" WHERE MovieId = ?";
	private final static String insert = 
			"INSERT INTO movie (Title, ReleaseYear, GenreId) VALUES" +
			" (?, ?, ?)";
	private final static String update =
			" UPDATE Movie " +
			" SET Title = ?, " +
			" ReleaseYear = ?, " +
			" GenreId = ? " +
			" WHERE MovieId = ? ";

	@Override
	public List<Movie> getAllMovies() {
		List<Movie> movies = new ArrayList<Movie>();
		
		Connection conn = MariaDbUtil.getConnection();
		
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(selectAllMovies);
			while(rs.next()) {
				Movie movie = makeMovie(rs);
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				if (null != conn) {
				try {
						rs.close();
						statement.close();
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return movies;
	}

	private Movie makeMovie(ResultSet rs) throws SQLException {
		Movie movie = new Movie();
		movie.setId(rs.getInt("MovieId"));
		movie.setTitle(rs.getString("Title"));
		movie.setGenre(rs.getString("GenreId"));
		movie.setReleaseYear(rs.getInt("ReleaseYear"));
		movie.setCreateDate(rs.getObject("createDate", LocalDateTime.class));
		return movie;
	}

	@Override
	public List<Movie> getByGenre(String genre) {
		List<Movie> movies = new ArrayList<Movie>();
				
			Connection conn = MariaDbUtil.getConnection();
			
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;				
			try {
				preparedStatement = conn.prepareStatement(selectMoviesByGenre);
				preparedStatement.setString(1, genre);
				rs = preparedStatement.executeQuery();
				while(rs.next()) {
					Movie movie = makeMovie(rs);
					movies.add(movie);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
					if (null != conn) {
					try {
							rs.close();
							preparedStatement.close();
							conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
			return movies;
		}


	@Override
	public List<Movie> getByReleaseYear(int releaseYear) {
		List<Movie> movies = new ArrayList<Movie>();
		
		Connection conn = MariaDbUtil.getConnection();
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			preparedStatement = conn.prepareStatement(selectMoviesByReleaseYear);
			preparedStatement.setInt(1, releaseYear);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Movie movie = makeMovie(rs);
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				if (null != conn) {
				try {
						rs.close();
						preparedStatement.close();
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return movies;
	}


	@Override
	public List<Movie> getMovieById(int id) {
		List<Movie> movies = new ArrayList<Movie>();
		
		Connection conn = MariaDbUtil.getConnection();
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			preparedStatement = conn.prepareStatement(selectMoviesById);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Movie movie = makeMovie(rs);
				movies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				if (null != conn) {
				try {
						rs.close();
						preparedStatement.close();
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return movies;
	}

	@Override
	public void deleteById(Movie movieToDelete) {
		
		Connection conn = MariaDbUtil.getConnection();
		
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement(deleteMoviesById);
			preparedStatement.setInt(1, movieToDelete.getId());
			int rowCount = preparedStatement.executeUpdate();
			System.out.println("Row Count: " + rowCount);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				if (null != conn) {
				try {
						preparedStatement.close();
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<Movie> add(Movie newMovie) {
		List<Movie> movies = new ArrayList<Movie>();
				
				Connection conn = MariaDbUtil.getConnection();
				
				PreparedStatement preparedStatement = null;
				
				try {
					preparedStatement = conn.prepareStatement(insert);
					preparedStatement.setString(1, newMovie.getTitle());
					preparedStatement.setInt(2, newMovie.getReleaseYear());
					preparedStatement.setString(3, newMovie.getGenre());
					int rowCount = preparedStatement.executeUpdate();
					System.out.println("rows inserted: " + rowCount);
					
					newMovie.setId(getLastKey(conn));
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
						if (null != conn) {
						try {
								preparedStatement.close();
								conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				movies.add(newMovie);
				return movies;
			}

	@Override
	public List<Movie> insert(Movie newMovie) {
		List<Movie> movies = new ArrayList<Movie>();
		
		Connection conn = MariaDbUtil.getConnection();
		
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement(insert);
			preparedStatement.setString(1, newMovie.getTitle());
			preparedStatement.setInt(2, newMovie.getReleaseYear());
			preparedStatement.setString(3, newMovie.getGenre());
			int rowCount = preparedStatement.executeUpdate();
			System.out.println("rows inserted: " + rowCount);
			
			newMovie.setId(getLastKey(conn));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				if (null != conn) {
				try {
						preparedStatement.close();
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		movies.add(newMovie);
		return movies;
	}
	
	private Integer getLastKey(Connection conn) throws SQLException {
		Integer key = 0;
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(" SELECT LAST_INSERT_ID() ");
		
		while(result.next()) {
			key = result.getInt("LAST_INSERT_ID()");
		}
		return key;
	}

	@Override
	public List<Movie> update(Movie updateMovie) {
		List<Movie> movies = new ArrayList<Movie>();
		
		Connection conn = MariaDbUtil.getConnection();
		
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement(update);
			preparedStatement.setString(1, updateMovie.getTitle());
			preparedStatement.setInt(2, updateMovie.getReleaseYear());
			preparedStatement.setString(3, updateMovie.getGenre());
			preparedStatement.setInt(4, updateMovie.getId());
			int rowCount = preparedStatement.executeUpdate();
			System.out.println("rows inserted: " + rowCount);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				if (null != conn) {
				try {
						preparedStatement.close();
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		movies.add(updateMovie);
		return movies;
	}

	
}
