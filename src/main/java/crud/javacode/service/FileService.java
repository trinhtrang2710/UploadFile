package crud.javacode.service;

import crud.javacode.model.entity.FileEntity;
import crud.javacode.model.entity.SettingEntity;
import crud.javacode.repository.FileRepository;
import crud.javacode.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class FileService {
    @PersistenceContext(unitName = "persistence-data")
    private EntityManager em;

    @Autowired
    private SettingRepository settingRepository;
    @Autowired
    private FileRepository fileRepository;

    public List<FileEntity> page(int page) {
        List<FileEntity> files = null;
        try {
            List<SettingEntity> settingEntities = settingRepository.findAll();
            int numberPerPage = 100;
            if (settingEntities != null && settingEntities.size() > 0) {
                numberPerPage = settingEntities.get(0).getItemPerPage();
            }
            int offset = (page - 1) * numberPerPage;
            Query query = em.createNativeQuery("SELECT * FROM file f LIMIT " + offset + "," + numberPerPage, FileEntity.class);

            files = query.getResultList();
            return files;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int count() {
        int counnt = 0;
        try {
            counnt = fileRepository.findAll().size();
            return counnt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
