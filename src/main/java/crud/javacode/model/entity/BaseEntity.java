package crud.javacode.model.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createddate")
    @LastModifiedDate
    private Date createdDate;

    @Column(name = "createdby")
    @CreatedBy
    private Date modifiedDate;

    @Column()
    private String createdBy;
    private String modifyBy;

}
