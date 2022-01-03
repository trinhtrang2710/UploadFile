package crud.javacode.controller;

import crud.javacode.common.UtilsString;
import crud.javacode.dto.CustomerDTO;
import crud.javacode.model.dto.request.UpdateSettingRequest;
import crud.javacode.model.dto.request.UploadFileRequest;
import crud.javacode.model.entity.FileEntity;
import crud.javacode.model.entity.SettingEntity;
import crud.javacode.repository.FileRepository;
import crud.javacode.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
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
        CustomerDTO customerDTO = new CustomerDTO();
        ModelAndView mav = new ModelAndView("create-file");
//        customerDTO.setListResult(customerService.getCustomers());
//        mav.addObject("customerDTO", customerDTO);
        return mav;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(UploadFileRequest myFile, Model model) {

        try {
            Long size = myFile.getMultipartFile().getSize();

            List<SettingEntity> settings = settingRepository.findAll();
            SettingEntity settingNow = null;
            if (settings != null && settings.size() > 0) {
                settingNow = settings.get(0);
            }

            if (myFile.getMultipartFile().getSize() >= (size != null ? size.intValue() : 0)) {
                model.addAttribute("message", "Over size!");
                return "create-file";
            }

            MultipartFile multipartFile = myFile.getMultipartFile();
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(UtilsString.pathFileUpload, fileName);
            multipartFile.transferTo(file);

            FileEntity fileEntity = fileRepository.findByName(fileName);
            int version = fileEntity.getVersion();

            if (file == null) {
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
        return "create-file";
    }


    @RequestMapping(value = "/setting/update", method = RequestMethod.POST)
    public void updateSetting(UpdateSettingRequest setting, Model model) {
        if (setting == null) {
            model.addAttribute("message", "param null!!");
            return;
        }
        if (setting.getItemPerPage() == null) {
            model.addAttribute("message", "ItemPerPage null!!");
            return;
        }
        if (setting.getMaxFileSize() == null || setting.getMaxFileSize() <= 0) {
            model.addAttribute("message", "Size file invalid!!");
            return;
        }
        if (setting.getMimeType() == null || setting.getMimeType().trim().equals("")) {
            model.addAttribute("message", "MimeType null!!");
            return;
        }
        try{

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    //    --------------------HELPER---------------------

}

