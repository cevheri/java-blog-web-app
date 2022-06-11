package com.cevheri.blog.repository;

import com.cevheri.blog.domain.PostView;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PostView entity.
 */
@Repository
public interface PostViewRepository extends JpaRepository<PostView, Long> {
    default Optional<PostView> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PostView> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PostView> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct postView from PostView postView left join fetch postView.post",
        countQuery = "select count(distinct postView) from PostView postView"
    )
    Page<PostView> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct postView from PostView postView left join fetch postView.post")
    List<PostView> findAllWithToOneRelationships();

    @Query("select postView from PostView postView left join fetch postView.post where postView.id =:id")
    Optional<PostView> findOneWithToOneRelationships(@Param("id") Long id);
}
