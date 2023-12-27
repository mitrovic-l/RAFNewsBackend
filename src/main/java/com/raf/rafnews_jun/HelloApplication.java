package com.raf.rafnews_jun;

import com.raf.rafnews_jun.repository.category.CategoryRepository;
import com.raf.rafnews_jun.repository.category.MySqlCategoryRepository;
import com.raf.rafnews_jun.repository.comment.CommentRepository;
import com.raf.rafnews_jun.repository.comment.MySqlCommentRepository;
import com.raf.rafnews_jun.repository.news.MySqlNewsRepository;
import com.raf.rafnews_jun.repository.news.NewsRepository;
import com.raf.rafnews_jun.repository.newstag.MySqlNewsTagRepository;
import com.raf.rafnews_jun.repository.newstag.NewsTagRepository;
import com.raf.rafnews_jun.repository.tag.MySqlTagRepository;
import com.raf.rafnews_jun.repository.tag.TagRepository;
import com.raf.rafnews_jun.repository.user.MySqlUserRepository;
import com.raf.rafnews_jun.repository.user.UserRepository;
import com.raf.rafnews_jun.service.*;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {
    public HelloApplication(){
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlUserRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bind(MySqlCategoryRepository.class).to(CategoryRepository.class).in(Singleton.class);
                this.bind(MySqlTagRepository.class).to(TagRepository.class).in(Singleton.class);
                this.bind(MySqlNewsRepository.class).to(NewsRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);
                this.bind(MySqlNewsTagRepository.class).to(NewsTagRepository.class).in(Singleton.class);

                this.bindAsContract(UserService.class);
                this.bindAsContract(CategoryService.class);
                this.bindAsContract(TagService.class);
                this.bindAsContract(NewsService.class);
                this.bindAsContract(CommentService.class);
                this.bindAsContract(NewsTagService.class);

            }
        };
        register(binder);
        packages("com.raf.rafnews_jun");
    }
}