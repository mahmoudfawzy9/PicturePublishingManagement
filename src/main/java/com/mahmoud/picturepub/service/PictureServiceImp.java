package com.mahmoud.picturepub.service;

import com.mahmoud.picturepub.entity.Picture;
import com.mahmoud.picturepub.entity.Status;
import com.mahmoud.picturepub.entity.User;
import com.mahmoud.picturepub.exception.EntityNotFoundException;
import com.mahmoud.picturepub.repository.PictureRepository;
import com.mahmoud.picturepub.repository.UserRepository;
import com.mahmoud.picturepub.request.PictureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PictureServiceImp implements PictureService{


    private final PictureRepository pictureRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${upload-dir}")
    private String uploadDir;
    public PictureServiceImp(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    //

    public Picture uploadPicture(PictureDTO pictureDTO, String username) {

        //validate file extension
        String filename= pictureDTO.getFile().getOriginalFilename();
        if(filename.matches(".*\\.(jpg|jpeg|gif)$")) {
            throw new IllegalArgumentException("Invalid file extension. . Only JPG, JPEG, and GIF files are allowed.");
        }

        // Validate file size
        long fileSize = pictureDTO.getFile().getSize();
        if (fileSize > 2 * 1024 * 1024) {
            throw new IllegalArgumentException("File size exceeds the maximum allowed limit of 2MB.");
        }

        User user = userRepository.findByUserName(username);
        Picture picture = new Picture();
        picture.setUser(user);
        picture.setDescription(pictureDTO.getDescription());
        picture.setCategory(pictureDTO.getCategory());
        picture.setStatus(Status.NEW);
        try {
            picture.setFile(pictureDTO.getFile().getBytes());

        }catch (IOException e){
            e.printStackTrace();
        }

        pictureRepository.save(picture);
        return picture;
    }

    public void processPicture(Long id, String username, boolean accept) {
        User admin = userRepository.findByUserName(username);
        Picture picture = pictureRepository.findById(id).orElseThrow(() ->
         new EntityNotFoundException("Picture  with id " +id + " is not found."));
        if (picture.getStatus() == Status.NEW) {
            if (accept) {
                // Generate URL
                String url = generateUrl();
                picture.setUrl(url);

                picture.setStatus(Status.ACCEPTED);
            }else {
                // Delete file and record
                try {
                    Files.delete(Paths.get(uploadDir + "/" + picture.getId() + ".jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                picture.setStatus(Status.REJECTED);
                pictureRepository.delete(picture);
                return;
            }
            pictureRepository.save(picture);
        }
    }

    private String generateUrl() {
        // TODO: Implementing URL generation logic, to be edited before production
        String url = "https://yeshtery.com/picture/";
        String uuid = UUID.randomUUID().toString();
        url += uuid;
        return url;
    }

    @Override
    public List<PictureDTO> getUnprocessedPictures() {
        List<Picture> pictures =  pictureRepository.findByStatusOrderByIdAsc(Status.NEW);
        List<PictureDTO> pictureDTOs = new ArrayList<>();
        for (Picture picture : pictures) {
            PictureDTO pictureDTO = new PictureDTO();
            pictureDTO.setId(picture.getId());
            pictureDTO.setDescription(picture.getDescription());
            pictureDTO.setCategory(picture.getCategory());
            pictureDTO.setFile((MultipartFile) new ByteArrayResource(picture.getFile()));
            pictureDTOs.add(pictureDTO);
        }
        return pictureDTOs;
    }

    @Override
    public Optional<Picture> getPictureById(Long id) {
        return pictureRepository.findById(id);
    }

    @Override
    public void updatePictureStatus(Long id, String username, Status status) {

        User user = userRepository.findByUserName(username);
        Picture picture = pictureRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id "+id+ " isn't found."));

        if (picture.getUser().equals(user)) {
            picture.setStatus(status);
            pictureRepository.save(picture);
        } else {
            throw new AccessDeniedException("You do not have permission to update the status of this picture.");
        }
    }

    @Override
    public void deletePicture(Long id, String username) {
        User user = userRepository.findByUserName(username);
        Picture picture = pictureRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id "+id+ " isn't found."));
        if (picture.getUser().equals(user)) {
            pictureRepository.delete(picture);
        } else {
            throw new AccessDeniedException("You do not have permission to delete this picture.");
        }
    }
    @Override
    public List<PictureDTO> getAcceptedPictures() {
        List<Picture> pictures =  pictureRepository.findByStatusOrderByIdAsc(Status.ACCEPTED);

        List<PictureDTO> pictureDTOS = new ArrayList<>();
        for(Picture picture: pictures){
            PictureDTO pictureDTO = new PictureDTO();
            pictureDTO.setId(picture.getId());

            pictureDTO.setDescription(picture.getDescription());

            pictureDTO.setCategory(picture.getCategory());
            pictureDTO.setUrl(picture.getUrl());
            pictureDTOS.add(pictureDTO);
        }
        return pictureDTOS;
    }
}
