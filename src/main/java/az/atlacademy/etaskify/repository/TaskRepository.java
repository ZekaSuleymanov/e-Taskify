package az.atlacademy.etaskify.repository;

import az.atlacademy.etaskify.entity.OrganizationEntity;
import az.atlacademy.etaskify.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Page<TaskEntity> findAllByOrganization(OrganizationEntity organizationEntity, Pageable pageable);

}
