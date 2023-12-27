package com.raf.rafnews_jun.resource;

import com.raf.rafnews_jun.entities.News;
import com.raf.rafnews_jun.service.NewsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/news")

public class NewsResource {
    @Inject
    private NewsService newsService;

    @GET
    @Path("/allNews")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allNews(){
        return this.newsService.allNews();
    }
    @GET
    @Path("/byViews")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allNewsByViews(){
        return this.newsService.allNewsByViews();
    }
    @GET
    @Path("/category/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allNewsInCategory(@PathParam("id") Integer id){
        return this.newsService.allNewsInCategory(id);
    }
    @GET
    @Path("/tag/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allNewsWithTag(@PathParam("id") Integer id){
        return this.newsService.allNewsWithTag(id);
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public News addNews(@Valid News news){
        return this.newsService.addNews(news);
    }
    @POST
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News updateNews(@Valid News news, @PathParam("id") Integer id){
        return this.newsService.updateNews(news, id);
    }
    @GET
    @Path("/find/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News findNews(@PathParam("id") Integer id){
        return this.newsService.findNews(id);
    }

    @GET
    @Path("/findforcreator/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News findNewsForCreator(@PathParam("id") Integer id) { return this.newsService.findNewsForCreator(id) ;}

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteNews(@PathParam("id") Integer id){
        this.newsService.deleteNews(id);
    }
}
