package com.raf.rafnews_jun.resource;

import com.raf.rafnews_jun.service.NewsTagService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/newstag")
public class NewsTagResource {
    @Inject
    NewsTagService newsTagService;

    @GET
    @Path("/add/{news}/{tag}")
    @Produces(MediaType.APPLICATION_JSON)
    public void post(@PathParam("news") Integer news, @PathParam("tag") Integer tag){
        this.newsTagService.insert(news, tag);
    }
    @DELETE
    @Path("/delete/{news}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("news") Integer news){
        this.newsTagService.delete(news);
    }

    @GET
    @Path("/deletetag/{news}/{tag}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("news") Integer news, @PathParam("tag") Integer tag){
        this.newsTagService.deleteTag(news, tag);
    }


}
