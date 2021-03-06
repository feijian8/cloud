package com.gaoshin.hadoop.user;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import common.util.web.JerseyBaseResource;

@Path("/ws/v1/user")
@Component
@Produces({ "text/html;charset=utf-8", "text/xml;charset=utf-8", "application/json" })
public class UserResource extends JerseyBaseResource {
    @Autowired private UserService userService;

    @POST
    @Path("/signup")
    public User signup(User user) {
        return userService.signup(user);
    }
}
