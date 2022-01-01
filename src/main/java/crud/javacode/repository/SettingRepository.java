package crud.javacode.repository;

import crud.javacode.model.entity.SettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SettingRepository extends JpaRepository<SettingEntity, Long> {
    @Query("SELECT s FROM SettingEntity s ORDER BY s.lastUpdatedTime desc ")
    List<SettingEntity> findAll();
}
