package com.raf.rafnews_jun.service;

import com.raf.rafnews_jun.entities.Comment;
import com.raf.rafnews_jun.repository.comment.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {
    @Inject
    CommentRepository commentRepository;


    public List<Comment> allComments(Integer newsId){
        return this.commentRepository.allComments(newsId);
    }
    public Comment addComment(Comment comment, Integer newsId){
        return this.commentRepository.addComment(comment, newsId);
    }
    public Comment findComment(Integer id){
        return this.commentRepository.findComment(id);
    }
    public void deleteComment(Integer id){
        this.commentRepository.deleteComment(id);
    }
}
