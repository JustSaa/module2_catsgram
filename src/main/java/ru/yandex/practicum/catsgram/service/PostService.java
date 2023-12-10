package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.model.Post;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();

    public List<Post> findAll(String sort, Integer size, Integer from) {
        return posts.stream().sorted((p0, p1) -> {
            int comp = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
            if(sort.equals("desc")){
                comp = -1 * comp; //обратный порядок сортировки
            }
            return comp;
        }).skip(from).limit(size).collect(Collectors.toList());
    }

    public Post create(Post post) {
        posts.add(post);
        return post;
    }

    public Optional<Post> getById(int id) {
        return posts.stream().filter(x -> x.getId() == id).findFirst();
    }

    public List<Post> searchPosts(String author) {
        return posts.stream().filter(
                post -> Objects.equals(post.getAuthor(), author)
        ).collect(Collectors.toList());
    }

    public List<Post> searchPosts(String author, LocalDate date) {
        return posts.stream().filter(
                        post -> Objects.equals(post.getAuthor(), author)
                ).filter(post -> post.getCreationDate().atZone(ZoneId.systemDefault()).toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<Post> listPosts(Integer userId, LocalDate from, LocalDate to) {
        return posts.stream()
                .filter(post -> post.getId() == userId)
                .filter(post -> post.getCreationDate().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(from))
                .filter(post -> post.getCreationDate().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(to))
                .collect(Collectors.toList());
    }
}
