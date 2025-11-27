package com.example.GestorFinanzas.Ingresos.Files.Infra.Controller;


import com.example.GestorFinanzas.Ingresos.Files.Domian.Services.StorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class FileUploadController {

    private final StorageService storageService;

    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/file")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        // redirectAttributes.addFlashAttribute("message",
        //         "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
}



