package com.aca.rest.service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.aca.rest.model.MovieError;

public class ErrorResponse {
	public static void invalidDelete(int id) {
		MovieError error = new MovieError();
		error.setId(101);
		error.setMessage("invalid delete request, movie id '" + id + "', does not exist");
		Response response = Response.status(400)
				.entity(error)
				.build();
		throw new WebApplicationException(response);
	}
	
	public static void invalidGenre(String genre) {
		MovieError error = new MovieError();
		error.setId(101);
		error.setMessage("invalid value for genre '" + genre + "', valid values are 'action', 'syfy', or 'comedy'");
		Response response = Response.status(400)
				.entity(error)
				.build();
		throw new WebApplicationException(response);
	}

	public static void invalidReleaseYear(int releaseYear, int minYear, int maxYear) {
		MovieError error = new MovieError();
		error.setId(101);
		error.setMessage("invalid release year '" + releaseYear + "', valid values are between '" 
		+ minYear + " and " + maxYear + "' , inclusive");
		Response response = Response.status(400)
				.entity(error)
				.build();
		throw new WebApplicationException(response);
	}

	public static void invalidUpdate() {
		MovieError error = new MovieError();
		error.setId(105);
		error.setMessage("Nope, invalid update");
//		throwError(error);
		Response response = Response.status(400)
				.entity(error)
				.build();
		throw new WebApplicationException(response);
	}

}
