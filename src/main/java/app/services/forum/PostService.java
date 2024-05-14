package app.services.forum;

import app.dtos.forum.CreatePostDTO;
import app.dtos.forum.MapperForum;
import app.dtos.forum.PostDTO;
import app.models.User;
import app.models.forum.Post;
import app.models.forum.Topic;
import app.repository.forum.PostRepository;
import app.repository.forum.TopicRepository;
import app.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private MapperForum mapperForum;

    public List<PostDTO> findPostsByTopicId(Long topicId) {
        List<Post> posts = postRepository.findByTopicId(topicId);
        return posts.stream()
                .map(mapperForum::convertToPostDTO)
                .collect(Collectors.toList());
    }
    public Post createPost(CreatePostDTO dto, Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with ID: " + topicId));

        Post post = new Post();
        post.setBody(dto.getBody());
        post.setTopic(topic);

        User user = userDetailsService.getCurrentUser();
        post.setUser(user);

        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));
        postRepository.delete(post);
    }
}
