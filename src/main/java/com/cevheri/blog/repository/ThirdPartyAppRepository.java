package com.cevheri.blog.repository;

import com.cevheri.blog.domain.ThirdPartyApp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ThirdPartyApp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThirdPartyAppRepository extends JpaRepository<ThirdPartyApp, Long> {}
