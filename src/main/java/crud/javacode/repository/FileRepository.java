package crud.javacode.repository;

import crud.javacode.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.File;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByName(String name);
    FileEntity findById(Long id);
}
