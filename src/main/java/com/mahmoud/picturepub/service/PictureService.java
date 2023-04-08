package com.mahmoud.picturepub.service;

import com.mahmoud.picturepub.entity.Picture;
import com.mahmoud.picturepub.entity.Status;
import com.mahmoud.picturepub.request.PictureDTO;

import java.util.List;
import java.util.Optional;

public interface PictureService {

//     Picture uploadPicture(String description, Category category, MultipartFile file);

     Picture uploadPicture(PictureDTO pictureDTO, String username);

     List<PictureDTO> getUnprocessedPictures();

     Optional<Picture> getPictureById(Long id);

     void updatePictureStatus(Long id, String username, Status status);

     void deletePicture(Long id, String username);
     void processPicture(Long id, String username, boolean accept);

      List<PictureDTO> getAcceptedPictures();

}