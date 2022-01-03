package crud.javacode.model.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

public class UploadFileRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<MultipartFile> files;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
