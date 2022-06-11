package com.cevheri.blog.repository;

import com.cevheri.blog.domain.Post;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PostRepositoryWithBagRelationshipsImpl implements PostRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Post> fetchBagRelationships(Optional<Post> post) {
        return post.map(this::fetchTags);
    }

    @Override
    public Page<Post> fetchBagRelationships(Page<Post> posts) {
        return new PageImpl<>(fetchBagRelationships(posts.getContent()), posts.getPageable(), posts.getTotalElements());
    }

    @Override
    public List<Post> fetchBagRelationships(List<Post> posts) {
        return Optional.of(posts).map(this::fetchTags).orElse(Collections.emptyList());
    }

    Post fetchTags(Post result) {
        return entityManager
            .createQuery("select post from Post post left join fetch post.tags where post is :post", Post.class)
            .setParameter("post", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Post> fetchTags(List<Post> posts) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, posts.size()).forEach(index -> order.put(posts.get(index).getId(), index));
        List<Post> result = entityManager
            .createQuery("select distinct post from Post post left join fetch post.tags where post in :posts", Post.class)
            .setParameter("posts", posts)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
