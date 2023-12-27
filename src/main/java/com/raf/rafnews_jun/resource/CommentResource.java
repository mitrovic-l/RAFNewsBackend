package com.raf.rafnews_jun.resource;

import com.raf.rafnews_jun.entities.Comment;
import com.raf.rafnews_jun.service.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comments")

public class CommentResource {
    @Inject
    private CommentService commentService;

    @GET
    @Path("/news/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> allComments(@PathParam("newsId") Integer newsId){
        return this.commentService.allComments(newsId);
    }
    @POST
    @Path("/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment addComment(@Valid Comment comment, @PathParam("newsId") Integer newsId){
        return this.commentService.addComment(comment, newsId);
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment findComment(@PathParam("id") Integer id){
        return this.commentService.findComment(id);
    }
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteComment(@PathParam("id") Integer id){
        this.commentService.deleteComment(id);
    }

}
