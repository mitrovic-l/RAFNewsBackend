package com.raf.rafnews_jun.filter;

import com.raf.rafnews_jun.resource.NewsResource;
import com.raf.rafnews_jun.resource.NewsTagResource;
import com.raf.rafnews_jun.resource.UserResource;
import com.raf.rafnews_jun.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    private UserService userService;
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        if (!this.isAuthRequired(requestContext)) {
            return;
        }

        try {
            String token = requestContext.getHeaderString("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            if (!this.userService.isAuthorized(token, requestContext)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAuthRequired(ContainerRequestContext req) {
        if (req.getUriInfo().getPath().contains("login") || req.getUriInfo().getPath().contains("allNews")
            || req.getUriInfo().getPath().contains("find") || req.getUriInfo().getPath().contains("byViews")
        ) {
            return false;
        }

        if (req.getMethod().equals("DELETE")){
            return true;
        }

        if (req.getMethod().equals("POST")){
            if (req.getUriInfo().getPath().contains("comments")){
                return false;
            } else {
                return true;
            }
        }

        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof NewsResource || matchedResource instanceof UserResource) {
                if (req.getUriInfo().getPath().contains("category") || req.getUriInfo().getPath().contains("tag")) continue;
                return true;
            }
        }

        return false;
    }


}