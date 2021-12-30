package crud.javacode.repository;

import crud.javacode.dto.CustomerDTO;
import crud.javacode.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    List<CustomerEntity> findAll();

    void delete(CustomerEntity entity);
//    or u.address =  :keyword or u.email = :keyword"
    @Query("select u from CustomerEntity u where u.name = :keyword")
    @Modifying
    List<CustomerEntity> findByName(@Param("keyword") String keyword);

}
