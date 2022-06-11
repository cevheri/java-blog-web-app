package com.cevheri.blog.repository;

import com.cevheri.blog.domain.PostLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PostLike entity.
 */
@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    default Optional<PostLike> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PostLike> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PostLike> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct postLike from PostLike postLike left join fetch postLike.post",
        countQuery = "select count(distinct postLike) from PostLike postLike"
    )
    Page<PostLike> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct postLike from PostLike postLike left join fetch postLike.post")
    List<PostLike> findAllWithToOneRelationships();

    @Query("select postLike from PostLike postLike left join fetch postLike.post where postLike.id =:id")
    Optional<PostLike> findOneWithToOneRelationships(@Param("id") Long id);
}
