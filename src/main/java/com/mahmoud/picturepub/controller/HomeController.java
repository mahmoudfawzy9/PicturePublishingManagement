package com.mahmoud.picturepub.controller;

import com.mahmoud.picturepub.entity.Picture;
import com.mahmoud.picturepub.request.PictureDTO;
import com.mahmoud.picturepub.service.PictureService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    private final PictureService pictureService;

    public HomeController(PictureService pictureService) {
        this.pictureService = pictureService;
    }
    @GetMapping("/")
    public String home(Model model) {
        List<PictureDTO> pictures = pictureService.getAcceptedPictures();
        model.addAttribute("pictures", pictures);
        return "home";
    }


}
