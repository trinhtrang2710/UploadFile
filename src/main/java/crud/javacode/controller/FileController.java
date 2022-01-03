package crud.javacode.controller;

import crud.javacode.common.UtilsString;
import crud.javacode.model.dto.request.UpdateSettingRequest;
import crud.javacode.model.dto.request.UploadFileRequest;
import crud.javacode.model.entity.FileEntity;
import crud.javacode.model.entity.SettingEntity;
import crud.javacode.repository.FileRepository;
import crud.javacode.repository.SettingRepository;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private SettingRepository settingRepository;

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public ModelAndView homePage() {
        List<FileEntity> fileEntity = new ArrayList<>();
        ModelAndView mav = new ModelAndView("create-file");
        fileEntity = (fileRepository.findAll());
        mav.addObject("files", fileEntity);
        return mav;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(UploadFileRequest myFile, Model model) {
//        Check param
        if (myFile == null) {
            model.addAttribute("message", "Bad request");
            return "redirect: /file";
        }
        if (myFile.getMultipartFile() == null) {
            model.addAttribute("message", "File invalid");
            return "redirect: /file";
        }
        try {
//            get file
            MultipartFile multipartFile = myFile.getMultipartFile();
            Long size = multipartFile.getSize();

            if (size == 0) {
                model.addAttribute("message", "File invalid");
                return "redirect: /file";
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
                    return "redirect: /file";
                }

//                Kiem tra kieu file
                String filenameOrigin = multipartFile.getOriginalFilename();
                if (!UtilsString.FileTail.checkFile(settingNow.getMimeTypeAllowed(), filenameOrigin.split(".")[1])) {
                    model.addAttribute("message", "Not same setting");
                    return "redirect: /file";
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
            model.addAttribute("ObjectInfo", fileEntity);
            model.addAttribute("message", "Upload success!");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Upload failed!");
        }
        return "redirect: /file";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String download(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        if (id == null) {
            return "redirect: /file";
        }
        try {
            FileEntity fileEntity = fileRepository.findById(id);
            if (fileEntity == null) {
                return "redirect: /file";
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
        return "redirect: /file";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        if (id == null) {
            return "redirect: /file";
        }
        try {
            FileEntity fileEntity = fileRepository.findById(id);
            if (fileEntity == null) {
                return "redirect: /file";
            }

            File file = new File(fileEntity.getPath());
            file.delete();
            fileRepository.delete(fileEntity);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect: /file";
    }

    @RequestMapping(value = "/setting/update", method = RequestMethod.POST)
    public String updateSetting(@ModelAttribute("settingg") UpdateSettingRequest setting, Model model, BindingResult result) {
        if (setting == null) {
            model.addAttribute("message", "param null!!");
            return "redirect: /file";
        }
        if (setting.getItemPerPage() == null) {
            model.addAttribute("message", "ItemPerPage null!!");
            return "redirect: /file";
        }
        if (setting.getMaxFileSize() == null || setting.getMaxFileSize() <= 0) {
            model.addAttribute("message", "Size file invalid!!");
            return "redirect: /file";
        }
        if (setting.getMimeType() == null || setting.getMimeType().trim().equals("")) {
            model.addAttribute("message", "MimeType null!!");
            return "redirect: /file";
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
        return "redirect: /file";
    }
    //    --------------------HELPER---------------------

}

