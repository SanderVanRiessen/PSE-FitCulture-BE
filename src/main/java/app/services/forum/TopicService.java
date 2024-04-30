package app.services.forum;
import app.dtos.forum.CreateTopicDTO;
import app.dtos.forum.MapperForum;
import app.dtos.forum.TopicDTO;
import app.models.User;
import app.models.forum.Category;
import app.models.forum.Topic;
import app.repository.UserRepository;
import app.repository.forum.CategoryRepository;
import app.repository.forum.TopicRepository;
import app.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private MapperForum mapperForum;

    public List<TopicDTO> findAllTopicsByCategoryId(Long categoryId) {
        return topicRepository.findByCategoryId(categoryId).stream()
                .map(mapperForum::convertToDTO)
                .collect(Collectors.toList());
    }

    public Topic createTopic(CreateTopicDTO dto) {
        Topic topic = new Topic();
        topic.setTitle(dto.getTitle());
        topic.setText(dto.getText());

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + dto.getCategoryId()));

        User user = userDetailsService.getCurrentUser();

        topic.setCategory(category);
        topic.setUser(user);

        return topicRepository.save(topic);
    }


    public void deleteTopic(Long topicId) {
        Topic topic = topicRepository
                .findById(topicId).orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + topicId));
        topicRepository.delete(topic);
    }
}