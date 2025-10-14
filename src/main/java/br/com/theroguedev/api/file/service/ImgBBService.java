package br.com.theroguedev.api.file.service;


import br.com.theroguedev.api.file.client.ImgBBClient;
import br.com.theroguedev.api.file.dto.response.ImgBBResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@Service
public class ImgBBService {

    private final ImgBBClient imgBBClient;

    public String uploadImage(MultipartFile file) {
        ImgBBResponse response = imgBBClient.uploadImage(file);

        if (response != null && response.success() && response.data() != null) {
            return response.data().url();
        }

        throw new RuntimeException("Erro ao enviar imagem para o ImgBB");
    }

}
