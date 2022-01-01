package crud.javacode.controller;

import crud.javacode.common.UtilsString;
import crud.javacode.dto.CustomerDTO;
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
        model.addAttribute("message", "upload success");

        try {
            MultipartFile multipartFile = myFile.getMultipartFile();
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(UtilsString.pathFileUpload, fileName);
            multipartFile.transferTo(file);

            List<SettingEntity> settings = settingRepository.findAll();
            SettingEntity settingNow = null;
            if (settings != null && settings.size() > 0) {
                settingNow = settings.get(0);
            }

            FileEntity fileEntity = fileRepository.findByName(fileName);
            if (file == null) {
                fileEntity = new FileEntity();
                fileEntity.setFileSize(Math.toIntExact(file.getTotalSpace()));
                fileEntity.setCreatedDateTime(new Date());
                fileEntity.setName(fileName);
                fileEntity.setPath(String.format("%s%s", UtilsString.pathFileUpload, fileName));
                fileEntity.setNumberOfDownload(0);
                fileEntity.setVersion(0);
                fileEntity.setStatus("Tồn tại");

                fileEntity = fileRepository.save(fileEntity);
            } else {
                FileEntity newFile = new FileEntity();
                newFile.setFileSize(Math.toIntExact(file.getTotalSpace()));
                newFile.setCreatedDateTime(new Date());
                newFile.setName(fileName);
                newFile.setPath(String.format("%s%s", UtilsString.pathFileUpload, fileName));
                newFile.setNumberOfDownload(0);
                newFile.setVersion(fileEntity.getVersion() + 1);
                newFile.setStatus("Tồn tại");
                newFile = fileRepository.save(newFile);
            }


        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Upload failed");
        }
        return "/file";
    }


    //    --------------------HELPER---------------------

}

