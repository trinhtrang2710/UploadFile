package crud.javacode.controller;

import crud.javacode.common.UtilsString;
import crud.javacode.model.dto.request.UpdateSettingRequest;
import crud.javacode.model.dto.request.UploadFileRequest;
import crud.javacode.model.entity.FileEntity;
import crud.javacode.model.entity.SettingEntity;
import crud.javacode.repository.FileRepository;
import crud.javacode.repository.SettingRepository;
import crud.javacode.service.FileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "")
public class FileController extends BaseController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public String homePage(@RequestParam("page") Integer page, Model model) {
        List<FileEntity> fileEntity = new ArrayList<>();
        fileEntity = fileService.page(page);
        int total = fileService.count();
        model.addAttribute("files", fileEntity);
        model.addAttribute("total", total);
        model.addAttribute("p", page);
        int totalPage = pageCalculation(total, 10);
        setPagingProperty(model, page, total, totalPage, 10);
        return "create-file";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(UploadFileRequest myFile, Model model, HttpServletRequest request) {
//        UploadFileRequest để làm cảnh, k lấy file từ nó. Lấy file theo dòng 55
//        Check param
        try {
            List<MultipartFile> files = ((DefaultMultipartHttpServletRequest) request)
                    .getFiles("multipartFile");
            if (files == null || files.size() <= 0) {
                model.addAttribute("message", "File invalid");
                return "redirect: /file?page=1";
            }
//            Mảng này chứa những file thêm thành công
            List<FileEntity> fileAddSuccess = new ArrayList<>();

            for (MultipartFile multipartFile : files) {

                try {
                    Long size = multipartFile.getSize();

                    if (size == 0) {
                        model.addAttribute("message", "File invalid");
                        return "redirect: /file?page=1";
                    }
//            tim tat ca setting
                    List<SettingEntity> settings = settingRepository.findAll();
                    SettingEntity settingNow = null;
                    if (settings != null && settings.size() > 0) {
//                lay setting moi nhat
                        settingNow = settings.get(0);
                    }

                    if (settingNow != null) {
                        //                kiem tra dung luong file
                        if ((size.intValue()) > (settingNow.getMaxFileSize() * 1024 * 1024)) {
                            model.addAttribute("message", "Over size!");
                            return "redirect: /file?page=1";
                        }

//                Kiem tra kieu file
                        String filenameOrigin = multipartFile.getOriginalFilename();
                        if (settingNow.getMimeTypeAllowed() != null && !UtilsString.FileTail.checkFile(settingNow.getMimeTypeAllowed(), filenameOrigin.split(".")[1])) {
                            model.addAttribute("message", "Not same setting");
                            return "redirect: /file?page=1";
                        }
                    }

                    String fileName = multipartFile.getOriginalFilename();

                    List<FileEntity> fileEntitys = fileRepository.findAll();
                    FileEntity fileEntity = null;
                    for (FileEntity file : fileEntitys) {
                        String name = file.getName().replace(String.format("%s_", file.getName().split("_")[0]), "");
                        if (fileName.trim().equalsIgnoreCase(name)) {
                            fileEntity = file;
                        }
                    }

                    fileName = String.format("%s_%s", new Date().getTime(), multipartFile.getOriginalFilename());
                    File file = new File(UtilsString.pathFileUpload, fileName);
                    multipartFile.transferTo(file);

                    if (fileEntity == null) {
                        fileEntity = new FileEntity();
                        fileEntity.setFileSize(size != null ? size.intValue() : 0);
                        fileEntity.setCreatedDateTime(new Date());
                        fileEntity.setName(fileName);
                        fileEntity.setPath(String.format("%s%s", UtilsString.pathFileUpload, fileName));
                        fileEntity.setNumberOfDownload(0);
                        fileEntity.setVersion(0);
                        fileEntity.setStatus("Tồn tại");

                        fileEntity = fileRepository.save(fileEntity);
                    } else {
                        int version = fileEntity.getVersion();
                        fileEntity = new FileEntity();
                        fileEntity.setFileSize(size != null ? size.intValue() : 0);
                        fileEntity.setCreatedDateTime(new Date());
                        fileEntity.setName(fileName);
                        fileEntity.setPath(String.format("%s%s", UtilsString.pathFileUpload, fileName));
                        fileEntity.setNumberOfDownload(0);
                        fileEntity.setVersion(version + 1);
                        fileEntity.setStatus("Tồn tại");
                        fileEntity = fileRepository.save(fileEntity);
                    }
                    fileAddSuccess.add(fileEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            model.addAttribute("fileAddSuccess", fileAddSuccess);
            model.addAttribute("message", "Upload success!");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Upload failed!");
        }
        return "redirect: /file?page=1";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String download(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        if (id == null) {
            return "redirect: /file?page=1";
        }
        try {
            FileEntity fileEntity = fileRepository.findById(id);
            if (fileEntity == null) {
                return "redirect: /file?page=1";
            }
            fileEntity.setNumberOfDownload(fileEntity.getNumberOfDownload() + 1);
            fileRepository.save(fileEntity);

            File file = new File(fileEntity.getPath());
            byte[] data = FileUtils.readFileToByteArray(file);
            // Thiết lập thông tin trả về
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
            response.setContentLength(data.length);
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect: /file?page=1";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        if (id == null) {
            return "redirect: /file?page=1";
        }
        try {
            FileEntity fileEntity = fileRepository.findById(id);
            if (fileEntity == null) {
                return "redirect: /file?page=1";
            }

            File file = new File(fileEntity.getPath());
            file.delete();
            fileRepository.delete(fileEntity);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect: /file?page=1";
    }

    @RequestMapping(value = "/setting/update", method = RequestMethod.POST)
    public String updateSetting(@ModelAttribute("settingg") UpdateSettingRequest setting, Model model, BindingResult result) {
        if (setting == null) {
            model.addAttribute("message", "param null!!");
            return "redirect: /file?page=1";
        }
        if (setting.getItemPerPage() == null) {
            model.addAttribute("message", "ItemPerPage null!!");
            return "redirect: /file?page=1";
        }
        if (setting.getMaxFileSize() == null || setting.getMaxFileSize() <= 0) {
            model.addAttribute("message", "Size file invalid!!");
            return "redirect: /file?page=1";
        }
        if (setting.getMimeType() == null || setting.getMimeType().trim().equals("")) {
            model.addAttribute("message", "MimeType null!!");
            return "redirect: /file?page=1";
        }
        try {
            SettingEntity settingEntity = new SettingEntity();
            settingEntity.setItemPerPage(setting.getItemPerPage());
            settingEntity.setMimeTypeAllowed(setting.getMimeType());
            settingEntity.setMaxFileSize(setting.getMaxFileSize());
            settingEntity.setLastUpdatedTime(new Date());

            settingEntity = settingRepository.save(settingEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect: /file?page=1";
    }
    //    --------------------HELPER---------------------

}

