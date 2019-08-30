package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.LoginEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LoginEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoginEventRepository extends JpaRepository<LoginEvent, Long>, JpaSpecificationExecutor<LoginEvent> {

}
