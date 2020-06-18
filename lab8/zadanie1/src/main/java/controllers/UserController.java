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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Path("/users")
public class UserController {

    @Inject
    private UserService userService;

    @Inject
    private MovieService movieService;

    @Path("/")
    @GET
    @Produces("application/json")
    public Response getAll() {
        List<User> users = userService.getAll();
        return Response.status(200).entity(users).build();
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public Response getOneById(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            User user = userService.getById(idInt);
            if (user != null) {
                return Response.status(200).entity(user).build();
            } else {
                return Response.status(404).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @Path("/{id}/img")
    @GET
    @Produces("image/png")
    public Response getImage(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            User user = userService.getById(idInt);
            if (user != null && user.getAvatar() != null) {
                return Response.status(200).entity(user.getAvatar()).build();
            } else {
                return Response.status(404).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @Path("/{id}/movies")
    @GET
    @Produces("application/json")
    public Response getMovies(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            JsonArrayBuilder builder = Json.createArrayBuilder();
            User user = userService.getById(idInt);
            if (user != null) {
                List<Movie> movies = user.getMovies();
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
    public Response add(User user) {
        try {
            User newUser = userService.create(user);
            return Response.status(201).entity(newUser).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @Path("/{id}")
    @POST
    @Produces("application/json")
    public Response postError(@PathParam("id") String id) {
        throw new WebApplicationException(403);
    }

    @Path("/{id}/movies")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response add(@PathParam("id") String id, Movie movie) {
        try {
            int idInt = Integer.parseInt(id);
            Movie m = movieService.getById(movie.getId());
            if (m != null) {
                User user = userService.addMovie(idInt, m.getId());
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
    public Response updateCollection(User user) {
        throw new WebApplicationException(403);
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("id") String id, User user) {
        try {
            int idInt = Integer.parseInt(id);
            if (user.getId() == 0 || user.getId() == idInt) {
                user.setId(idInt);
                userService.update(user);
                return Response.status(200).entity(user).build();
            } else {
                throw new WebApplicationException(400);
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
        throw new WebApplicationException(403);
    }

    @Path("/{id}")
    @DELETE
    @Produces("application/json")
    public Response delete(@PathParam("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            User user = userService.deleteById(idInt);
            if (user == null) {
                return Response.status(204).build();
            }
            return Response.status(200).entity(user).build();
        } catch (NumberFormatException e) {
            return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @Path("/{idUser}/movies/{idMovie}")
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
