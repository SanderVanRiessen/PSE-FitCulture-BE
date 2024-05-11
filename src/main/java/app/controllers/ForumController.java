package app.controllers;

import app.dtos.MessageResponse;
import app.dtos.forum.CreateTopicDTO;
import app.dtos.forum.TopicDetailDTO;
import app.dtos.forum.*;
import app.models.forum.Post;
import app.models.forum.Topic;
import app.services.forum.CategoryService;
import app.services.forum.PostService;
import app.services.forum.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ForumController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private PostService postService;

    @GetMapping("/public/forum/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.findAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/public/forum/frontpage")
    public ResponseEntity<List<CategoryWithTopicsDTO>> getCategoriesWithRecentTopics() {
        List<CategoryWithTopicsDTO> categories = categoryService.findAllCategoriesWithRecentTopics();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/public/forum/{categoryId}/topics")
    public ResponseEntity<List<TopicDTO>> getAllTopicsByCategoryId(@PathVariable Long categoryId) {
        List<TopicDTO> topics = topicService.findAllTopicsByCategoryId(categoryId);
        if (topics.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/public/forum/{topicId}")
    public ResponseEntity<TopicDetailDTO> getTopicById(@PathVariable Long topicId) {
        TopicDetailDTO topicDetail = topicService.findTopicById(topicId);
        if (topicDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(topicDetail);
    }

    @GetMapping("/public/forum/{topicId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByTopicId(@PathVariable Long topicId) {
        List<PostDTO> posts = postService.findPostsByTopicId(topicId);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/forum/topic")
    public ResponseEntity<GetCreateTopicDTO> createTopic(@RequestBody CreateTopicDTO createTopicDTO) {
        Topic newTopic = topicService.createTopic(createTopicDTO);
        MapperForum mapperForum = new MapperForum();
        GetCreateTopicDTO createdTopic = mapperForum.convertToGetCreateTopicDTO(newTopic);
        return ResponseEntity.ok(createdTopic);
    }

    @PostMapping("/forum/{topicId}/post")
    public ResponseEntity<GetCreatePostDTO> createPost(@RequestBody CreatePostDTO createPostDTO, @PathVariable Long topicId) {
        Post newPost = postService.createPost(createPostDTO, topicId);
        MapperForum mapperForum = new MapperForum();
        GetCreatePostDTO createdPost = mapperForum.convertToGetCreatePostDTO(newPost);
        return ResponseEntity.ok(createdPost);
    }

    @DeleteMapping("/forum/topic/{topicId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteTopic(@PathVariable Long topicId) {
        try {
            topicService.deleteTopic(topicId);
            return ResponseEntity.ok().body(new MessageResponse("Successfully deleted topic"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/forum/post/{postId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok().body(new MessageResponse("Successfully deleted post"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        }
    }
}
