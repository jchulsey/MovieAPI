package com.aca.rest.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.aca.rest.model.EmailMessage;
import com.aca.rest.model.Message;
import com.aca.rest.model.Movie;
import com.aca.rest.service.MovieService;

@Path("v1/movie")
public class MovieController {
	
	private MovieService service = new MovieService();
	
	@GET
	@Path("/fancysearch")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getByFancySearch(
		@QueryParam("english") String english,
		@QueryParam("french") String french,
		@QueryParam("german") String german,
		@QueryParam("spanish") String spanish,
		@QueryParam("media") String media,
		@QueryParam("startdate") String startdate,
		@QueryParam("enddate") String enddate) {
		
	System.out.println("english: " + english);
	System.out.println("french: " + french);
	System.out.println("german: " + german);
	System.out.println("spanish: " + spanish);
	System.out.println("media: " + media);
	System.out.println("startdate: " + startdate);
	System.out.println("enddate: " + enddate);
	
	return getAllMovies();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Movie> getAllMovies() {
		return service.getAllMovies();
	}
	
	@GET
	@Path("/genre/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getByGenre(@PathParam("value") String genre) {
		return service.getByGenre(genre);
	}
	
	@GET
	@Path("/id/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getMovieById(@PathParam("value") int id) {
		return service.getMovieById(id);
	}
	
	@GET
	@Path("/releaseYear/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getByReleaseYear(@PathParam("value") int releaseYear) {
		return service.getByReleaseYear(releaseYear);
	}
	
	@DELETE
	@Path("/id/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> deleteById(@PathParam("value") int id) {
		return service.deleteById(id);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> add(Movie newMovie) {
		System.out.println("newMovie: " + newMovie.toString());
		return service.add(newMovie);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> update(Movie updateMovie) {
		System.out.println("newMovie: " + updateMovie.toString());
		return service.update(updateMovie);
	}
	
	@POST	
	@Path("/email")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response sendEmail(EmailMessage emailMessage) {		
		MovieService service = new MovieService();
		String result = service.sendEmail(emailMessage);
		
		Message message = new Message();
		message.setMessage(result);
		
		return Response.status(200).entity(message).build();				
	}


}
