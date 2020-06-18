package controllers;

import models.Movie;
import models.User;
import services.MovieService;
import services.UserService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/movies")
public class MovieController {

    @Inject
    private UserService userService;

    @Inject
    private MovieService movieService;

    @Path("/")
    @GET
    @Produces("application/json")
    public Response getAll(@QueryParam("title") String title) {
        List<Movie> movies;
        if (title == null) {
            movies = movieService.getAll();
        } else {
            movies = movieService.getByTitle(title);
        }
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Movie m : movies) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", m.getId());
            objectBuilder.add("title", m.getTitle());
            if (m.getUri() != null) {
                objectBuilder.add("uri", m.getUri());
            }
            builder.add(objectBuilder);
        }
        return Response.status(200).entity(builder.build()).build();
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public Response getOneById(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            Movie movie = movieService.getById(idInt);
            if (movie != null) {
                JsonArrayBuilder builder = Json.createArrayBuilder();
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", movie.getId());
                objectBuilder.add("title", movie.getTitle());
                if (movie.getUri() != null) {
                    objectBuilder.add("uri", movie.getUri());
                }
                builder.add(objectBuilder);
                return Response.status(200).entity(builder.build()).build();
            } else {
                return Response.status(404).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }


    @Path("/")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response add(Movie movie) {
        try {
            Movie newMovie = movieService.create(movie);
            return Response.status(201).entity(newMovie).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @Path("/{id}")
    @POST
    @Produces("application/json")
    public Response postError(@PathParam("id") String id) {
        return Response.status(403).build();
    }

    @Path("/{id}/users")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response add(@PathParam("id") String id, User user) {
        try {
            int idInt = Integer.parseInt(id);
            Movie m = movieService.getById(idInt);
            if (m != null) {
                User u = userService.addMovie(user.getId(), m.getId());
                return Response.status(201).build();
            } else {
                return Response.status(400).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }



    @Path("/")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateCollection(Movie movie) {
        return Response.status(403).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("id") String id, Movie movie) {
        try {
            int idInt = Integer.parseInt(id);
            if (movie.getId() == 0 || movie.getId() == idInt) {
                movie.setId(idInt);
                Movie newMovie = movieService.update(movie);
                JsonArrayBuilder builder = Json.createArrayBuilder();
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", newMovie.getId());
                objectBuilder.add("title", newMovie.getTitle());
                if (newMovie.getUri() != null) {
                    objectBuilder.add("uri", newMovie.getUri());
                }
                builder.add(objectBuilder);
                return Response.status(200).entity(builder.build()).build();
            } else {
                return Response.status(400).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }




    @Path("/")
    @DELETE
    @Produces("application/json")
    public Response deleteCollection() {
        return Response.status(403).build();
    }

    @Path("/{id}")
    @DELETE
    @Produces("application/json")
    public Response delete(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            Movie movie = movieService.deleteById(idInt);
            if (movie == null) {
                return Response.status(204).build();
            }
            JsonArrayBuilder builder = Json.createArrayBuilder();
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("id", movie.getId());
            objectBuilder.add("title", movie.getTitle());
            if (movie.getUri() != null) {
                objectBuilder.add("uri", movie.getUri());
            }
            builder.add(objectBuilder);
            return Response.status(200).entity(builder.build()).build();
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @Path("/{idMovie}/movies/{idUser}")
    @DELETE
    @Produces("application/json")
    public Response delete(@PathParam("idUser") String idUser, @PathParam("idMovie") String idMovie) {
        try {
            int idUInt = Integer.parseInt(idUser);
            int idMInt = Integer.parseInt(idMovie);
            Movie m = movieService.getById(idMInt);
            if (m != null) {
                userService.deleteMovie(idUInt, idMInt);
                return Response.status(200).build();
            } else {
                return Response.status(204).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }
}
