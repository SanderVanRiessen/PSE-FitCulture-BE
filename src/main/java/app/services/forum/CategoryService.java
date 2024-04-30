package app.services.forum;

import app.dtos.forum.CategoryDTO;
import app.dtos.forum.CategoryWithTopicsDTO;
import app.dtos.forum.MapperForum;
import app.dtos.forum.TopicDTO;
import app.models.forum.Topic;
import app.repository.forum.CategoryRepository;
import app.repository.forum.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class  CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private MapperForum mapperForum;

    public List<CategoryDTO> findAllCategories() {
        return categoryRepository.findAll().stream()
                .map(mapperForum::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<CategoryWithTopicsDTO> findAllCategoriesWithRecentTopics() {
        return categoryRepository.findAll().stream()
                .map(category -> {
                    List<Topic> topics = topicRepository.findByCategoryId(category.getId());
                    topics.sort(Comparator.comparing(Topic::getCreatedAt).reversed());
                    List<TopicDTO> recentTopics = topics.stream().
                            limit(3).map(mapperForum::convertToDTO)
                            .collect(Collectors.toList());

                    return new CategoryWithTopicsDTO(category.getId(), category.getName(), category.getDescription(), recentTopics);
                })
                .collect(Collectors.toList());
    }
}
