package com.raf.rafnews_jun.resource;

import com.raf.rafnews_jun.entities.Tag;
import com.raf.rafnews_jun.service.TagService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/tags")
public class TagResource {
    @Inject
    TagService tagService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> allTags(){
        return this.tagService.allTags();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Tag addTag(@Valid Tag tag){
        return this.tagService.addTag(tag);
    }
    @GET
    @Path("/find/{tag}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag findTag(@PathParam("tag") String tag){
        return this.tagService.findTag(tag);
    }
    @GET
    @Path("/findById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag findTag(@PathParam("id") Integer id){
        return this.tagService.findTagById(id);
    }
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteTag(@PathParam("id") Integer id){
        this.tagService.deleteTag(id);
    }
}
