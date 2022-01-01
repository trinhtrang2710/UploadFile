package crud.javacode.controller;

import crud.javacode.common.UtilsString;
import crud.javacode.dto.CustomerDTO;
import crud.javacode.model.dto.request.UploadFileRequest;
import crud.javacode.model.entity.FileEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

@Controller
@RequestMapping(value = "")
public class FileController {

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public ModelAndView homePage() {
        CustomerDTO customerDTO = new CustomerDTO();
        ModelAndView mav = new ModelAndView("create-file");
//        customerDTO.setListResult(customerService.getCustomers());
//        mav.addObject("customerDTO", customerDTO);
        return mav;
    }


//    // Phương thức này được gọi mỗi lần có Submit.
//    @InitBinder
//    public void initBinder(WebDataBinder dataBinder) {
//        Object target = dataBinder.getTarget();
//        if (target == null) {
//            return;
//        }
//        System.out.println("Target=" + target);
//
//        if (target.getClass() == UploadFileRequest.class) {
//
//            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
//            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
//        }
//    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(UploadFileRequest myFile, Model model) {
        model.addAttribute("message", "upload success");
        model.addAttribute("type", myFile.getDescription());

        try {
            MultipartFile multipartFile = myFile.getMultipartFile();
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(UtilsString.pathFileUpload, fileName);
            multipartFile.transferTo(file);

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileSize(Math.toIntExact(file.getTotalSpace()));
            fileEntity.setCreatedDateTime();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Upload failed");
        }
        return "/file";
    }


    //    --------------------HELPER---------------------

}

