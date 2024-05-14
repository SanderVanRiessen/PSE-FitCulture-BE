package app.dtos.forum;

import app.models.forum.Category;
import app.models.forum.Post;
import app.models.forum.Topic;
import org.springframework.stereotype.Component;

@Component
public class MapperForum {

    public TopicDTO convertToDTO(Topic topic) {
        if (topic == null) {
            return null;
        }

        return new TopicDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getText(),
                topic.getUser().getName(),
                topic.getCreatedAt(),
                topic.getPosts().size()
        );
    }
    public CategoryDTO convertToDTO(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public PostDTO convertToPostDTO(Post post) {
        if (post == null) {
            return null;
        }
        return new PostDTO(
                post.getId(),
                post.getBody(),
                post.getUser().getName(),
                post.getCreatedAt()
        );
    }

    public GetCreateTopicDTO convertToGetCreateTopicDTO(Topic topic){
        if (topic == null){
            return null;
        }
        return new GetCreateTopicDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getText(),
                topic.getUser().getName()
        );
    }

    public GetCreatePostDTO convertToGetCreatePostDTO(Post post) {
        if (post == null) {
            return null;
        }
        return new GetCreatePostDTO(
                post.getBody(),
                post.getUser().getName(),
                post.getTopic().getId()
        );
    }
    public TopicDetailDTO mapToTopicDetailDTO(Topic topic) {
        return new TopicDetailDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getText(),
                topic.getUser().getName(),
                topic.getCreatedAt(),
                topic.getPosts().size()
        );
    }
}
