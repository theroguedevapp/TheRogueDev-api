package br.com.theroguedev.api.user.service;

import br.com.theroguedev.api.exceptions.CustomNotFoundException;
import br.com.theroguedev.api.file.service.ImgBBService;
import br.com.theroguedev.api.user.entity.Permission;
import br.com.theroguedev.api.user.entity.UserProfile;
import br.com.theroguedev.api.user.repository.PermissionRepository;
import br.com.theroguedev.api.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository repository;
    private final ImgBBService imgBBService;

    public List<UserProfile> findAll() {
        return repository.findAll();
    }

    public Optional<UserProfile> findById(UUID id) {
        return repository.findById(id);
    }

    public UserProfile changeProfilePic(MultipartFile file, UUID userId) {
       UserProfile userProfile = repository.findById(userId)
               .orElseThrow(() -> new CustomNotFoundException("Perfil de usuário não encontrado"));


       String imageUrl = imgBBService.uploadImage(file);
       userProfile.setProfilePicUrl(imageUrl);

       return repository.save(userProfile);
    }

    public UserProfile changeProfileBanner(MultipartFile file, UUID userId) {
        UserProfile userProfile = repository.findById(userId)
                .orElseThrow(() -> new CustomNotFoundException("Perfil de usuário não encontrado"));


        String imageUrl = imgBBService.uploadImage(file);
        userProfile.setProfileBannerUrl(imageUrl);

        return repository.save(userProfile);
    }




}
