package com.cevheri.blog.repository;

import com.cevheri.blog.domain.IntegrationLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IntegrationLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntegrationLogRepository extends JpaRepository<IntegrationLog, Long> {}
