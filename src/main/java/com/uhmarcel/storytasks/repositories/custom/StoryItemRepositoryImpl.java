package com.uhmarcel.storytasks.repositories.custom;

import com.uhmarcel.storytasks.models.StoryItem;
import com.uhmarcel.storytasks.models.common.Priority;
import com.uhmarcel.storytasks.models.common.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StoryItemRepositoryImpl implements StoryItemRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public StoryItemRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<StoryItem> findAllWithFilters(UUID userId, Long parent, Status status, Priority priority, Pageable page, boolean includeParent) {
        Criteria criteria = generateSearchCriteria(userId, parent, status, priority, includeParent);
        Query query = new Query().with(page).addCriteria(criteria);
        return mongoTemplate.find(query, StoryItem.class);
    }

    private Criteria generateSearchCriteria(UUID userId, Long parent, Status status, Priority priority, boolean includeParent) {
        // Organized as OR of ANDS
        List<Criteria> mainCriteria = new ArrayList<>();

        List<Criteria> firstCriteria = new ArrayList<>();
        if (userId != null) firstCriteria.add(Criteria.where("identifier.userId").is(userId));
        if (parent != null) firstCriteria.add(Criteria.where("parent").is(parent));
        if (status != null) firstCriteria.add(Criteria.where("status").is(status.toString()));
        if (priority != null) firstCriteria.add(Criteria.where("priority").is(priority.toString()));
        if (!firstCriteria.isEmpty()) {
            mainCriteria.add(new Criteria().andOperator(firstCriteria.toArray(new Criteria[firstCriteria.size()])));
        }

        List<Criteria> secondCriteria = new ArrayList<>();
        if (parent != null && includeParent) secondCriteria.add(Criteria.where("id").is(parent));
        if (!secondCriteria.isEmpty()) {
            mainCriteria.add(new Criteria().andOperator(secondCriteria.toArray(new Criteria[secondCriteria.size()])));
        }

        Criteria combinedCriteria = new Criteria();
        if (!mainCriteria.isEmpty()) {
            combinedCriteria.orOperator(mainCriteria.toArray(new Criteria[mainCriteria.size()]));
        }
        return combinedCriteria;
    }
}
