package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.catsgram.service.PostService;

@RestController
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> findAll(
            @RequestParam(value = "sort", defaultValue = "desc", required = false) String sort,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page
    ) {
        if (!(sort.equals("desc") || sort.equals("acs"))) {
            throw new IllegalArgumentException();
        }
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException();
        }

        Integer from = page * size;
        //log.debug("Текущее количество постов: {}", postService.findAll().size());
        return postService.findAll(sort, size, from);
    }

    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @GetMapping("/post/{id}")
    public Optional<Post> getById(@PathVariable int id) {
        return postService.getById(id);
    }

    @GetMapping("/posts/search")
    public List<Post> searchPosts(@RequestParam String author) {
        System.out.println("Ищем посты пользователя с именем " + author);
        return postService.searchPosts(author);
    }

    @GetMapping("/posts/search")
    public List<Post> searchPosts(
            @RequestParam String author,
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        System.out.println("Ищем посты пользователя с именем " + author +
                " и опубликованные " + date);
        return postService.searchPosts(author, date);
    }

    @GetMapping("/users/{userId}/posts/list")
    public List<Post> listPosts(
            @PathVariable Integer userId,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate to
    ) {
        System.out.println("Ищем посты пользователя " + userId +
                " с даты " + from.toString() + " по дату " + to.toString());
        return postService.listPosts(userId, from, to);
    }
}