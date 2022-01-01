package crud.javacode.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "setting")
public class SettingEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "maxFileSize")
    private int maxFileSize;

    @Column(name = "itemPerPage")
    private int itemPerPage;

    @Column(name = "mimeTypeAllowed")
    private String mimeTypeAllowed;

    @Column(name = "lastUpdatedTime")
    private String lastUpdatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

    public String getMimeTypeAllowed() {
        return mimeTypeAllowed;
    }

    public void setMimeTypeAllowed(String mimeTypeAllowed) {
        this.mimeTypeAllowed = mimeTypeAllowed;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
