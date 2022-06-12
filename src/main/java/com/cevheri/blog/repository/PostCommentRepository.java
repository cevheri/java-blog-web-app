package com.cevheri.blog.repository;

import com.cevheri.blog.domain.PostComment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PostComment entity.
 */
@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    default Optional<PostComment> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PostComment> findAllWithEagerRelationships(Long postId) {
        return this.findAllWithToOneRelationships(postId);
    }

    default Page<PostComment> findAllWithEagerRelationships(Long postId, Pageable pageable) {
        return this.findAllWithToOneRelationships(postId, pageable);
    }

    @Query(
        value = "select distinct postComment from PostComment postComment left join fetch postComment.post where postComment.post.id =:postId",
        countQuery = "select count(distinct postComment) from PostComment postComment where postComment.post.id =:postId"
    )
    Page<PostComment> findAllWithToOneRelationships(@Param("postId") Long postId, Pageable pageable);

    @Query("select distinct postComment from PostComment postComment left join fetch postComment.post where postComment.post.id =:postId")
    List<PostComment> findAllWithToOneRelationships(@Param("postId") Long postId);

    @Query("select postComment from PostComment postComment left join fetch postComment.post where postComment.id =:id")
    Optional<PostComment> findOneWithToOneRelationships(@Param("id") Long id);

    Page<PostComment> findAllByPost_Id(@Param("postId") Long postId, Pageable pageable);
}
