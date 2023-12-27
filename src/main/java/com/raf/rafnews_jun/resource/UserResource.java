package com.raf.rafnews_jun.resource;

import com.raf.rafnews_jun.entities.User;
import com.raf.rafnews_jun.request.LoginRequest;
import com.raf.rafnews_jun.service.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {
    @Inject
    UserService userService;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid LoginRequest loginRequest){
        Map<String, String> response = new HashMap<>();
        String jwt = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (jwt == null) {
            response.put("message", "Incorrect username or password.");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        } else if (jwt == "inactive"){
            response.put("inactive", "inactive");
            return Response.ok(response).build();
        }
        response.put("jwt", jwt);

        return Response.ok(response).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> allUsers(){
        return this.userService.allUsers();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(User user){
        return this.userService.addUser(user);
    }

    @GET
    @Path("/find/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findUser(@PathParam("email") String email) {
        return this.userService.findUser(email);
    }

    @DELETE
    @Path("/delete/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUser(@PathParam("email") String email){
        this.userService.deleteUser(email);
    }

    @GET
    @Path("/status/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public void changeUserActivity(@PathParam("email") String email) {
        this.userService.changeUserActivity(email);
    }

    @POST
    @Path("/update/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(User user, @PathParam("email") String email){
        return this.userService.updateUser(user, email);
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findUserById(@PathParam("id") Integer id){
        return this.userService.findUserById(id);
    }
    @POST
    @Path("/updateid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUserById(User user, @PathParam("id") Integer id){
        return this.userService.updateUserById(user, id);
    }
}
