package com.mss.web.repository;

import com.mss.web.domain.Tester_setup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tester_setup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Tester_setupRepository extends JpaRepository<Tester_setup, Long> {

}
