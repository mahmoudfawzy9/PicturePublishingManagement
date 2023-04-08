package com.mahmoud.picturepub.controller;

import com.mahmoud.picturepub.entity.Picture;
import com.mahmoud.picturepub.entity.Status;
import com.mahmoud.picturepub.request.PictureDTO;
import com.mahmoud.picturepub.service.PictureService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/pictures")
public class PictureController {

    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

@PostMapping("/upload")
public Picture uploadPicture(@RequestBody PictureDTO pictureDTO
               ,@RequestParam("username") String username){
        return pictureService.uploadPicture(pictureDTO, username);
    }
    @PutMapping("/{id}/status")
    public void updatePictureStatus(@PathVariable Long id
            ,@RequestParam Status status , @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
         pictureService.updatePictureStatus(id, username, status);
    }

    @GetMapping("/accepted")
    public List<PictureDTO> getAcceptedPictures() {
        return pictureService.getAcceptedPictures();
    }

    @GetMapping("/unprocessed")
    public List<PictureDTO> getUnProcessedPictures() {
        return pictureService.getUnprocessedPictures();
    }

    @GetMapping("/{id}")
    public Optional<Picture> getPictureById(@PathVariable Long id) {
        return pictureService.getPictureById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePicture(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        pictureService.deletePicture(id, username);
    }
}

