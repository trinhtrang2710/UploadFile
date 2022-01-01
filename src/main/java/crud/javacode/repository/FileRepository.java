package crud.javacode.repository;

import crud.javacode.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.File;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
