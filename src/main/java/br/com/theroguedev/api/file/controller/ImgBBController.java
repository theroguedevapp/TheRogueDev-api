package br.com.theroguedev.api.file.controller;


import br.com.theroguedev.api.file.service.ImgBBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/image")
public class ImgBBController {

    private final ImgBBService imgBBService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('image:upload')")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(imgBBService.uploadImage(file));
    }


}
