package com.mahmoud.picturepub.controller;

import com.mahmoud.picturepub.entity.Category;
import com.mahmoud.picturepub.entity.Picture;
import com.mahmoud.picturepub.entity.Status;
import com.mahmoud.picturepub.entity.User;
import com.mahmoud.picturepub.exception.EntityNotFoundException;
import com.mahmoud.picturepub.request.PictureDTO;
import com.mahmoud.picturepub.service.PictureService;
import com.mahmoud.picturepub.utils.ImageMapper;
import com.mahmoud.picturepub.utils.PropertiesExtractor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api/v1")
public class PictureController {

    private final PictureService pictureService;

    private final ImageMapper imageMapper;

    public PictureController(PictureService pictureService, ImageMapper imageMapper) {
        this.pictureService = pictureService;
        this.imageMapper = imageMapper;
    }

    @RequestMapping(value = "/pictures")
    public String getPicsFragment(Model model) {
        log.info("{}:{}", getClass().getSimpleName(), "/pics");
        List<Picture> list = pictureService.getAllByStatus(List.of(Status.ACCEPTED));
        log.info("{}:{}", getClass().getSimpleName(), list.size());

        model.addAttribute(new Picture());//case user choose to upload a pic
        model.addAttribute("pics", list);
        model.addAttribute("categories", Category.values());

        return "/home/pics";
    }

    @RequestMapping(value = "/pictures/pending")
    public String getPendingPicsFragment(Model model) {
        log.info("{}:{}", getClass().getSimpleName(), "/pics");
        List<Picture> list = pictureService.getAllByStatus(List.of(Status.NEW));

        log.info("{}:{}", getClass().getSimpleName(), list.size());

        model.addAttribute("pics", list);
        return "/home/pendingPictures";
    }

    @PostMapping(value = "/pictures/add")
    public RedirectView addPic(@ModelAttribute("pic") @Valid Picture pic, @RequestParam("image") MultipartFile multipartFile, Model model
            , @AuthenticationPrincipal User appUser) throws IOException {
        log.info("{}:{}", getClass().getSimpleName(), "/pics/add");
        String fileName = null;
        if (multipartFile.getOriginalFilename() != null) {
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            log.info("{}:{}", getClass().getSimpleName(), fileName);
        }
        // your picture will be reviewed by  admins before being approved.

        imageMapper.saveFile(PropertiesExtractor.FILE_SERVER_PATH, fileName, multipartFile);
        pic.setStatus(Status.NEW);
        pic.setPath(fileName);

        pic.setUser(appUser);

        pictureService.save(pic);
        return new RedirectView("/index", true);//return to home page
    }

    @GetMapping("/pictures/image/{id}")
    public void showProductImage(@PathVariable Long id, HttpServletResponse response)
            throws IOException {
        response.setContentType("image/jpeg"); // Or whatever format you want to  use

        Picture product = pictureService.getPictureById(id).orElseThrow(()
                -> new EntityNotFoundException("Picture with id "+id+ " isn't found."));

        InputStream is = new ByteArrayInputStream(imageMapper.getImageUnWrapped(product.getPath()));
        IOUtils.copy(is, response.getOutputStream());
    }

    @GetMapping("/pictures/pendingPictures/approve/{id}")
    public String approvePic(@PathVariable("id") Long id, Model model) {

        Picture product = pictureService.getPictureById(id).orElseThrow(()
                -> new EntityNotFoundException("Picture with id "+id+ " isn't found"));

        product.setStatus(Status.ACCEPTED);
        pictureService.save(product);

        model.addAttribute("pics", pictureService.getAllByStatus(List.of(Status.NEW)));

        return "/home/pendingPics";
    }

    @GetMapping("/pics/pendingPictures/decline/{id}")
    public String declinePic(@PathVariable("id") Long id, Model model) {
        Picture product = pictureService.getPictureById(id).orElseThrow(()
                -> new EntityNotFoundException("Picture with id "+id+" isn't found."));
        product.setStatus(Status.REJECTED);
        pictureService.save(product);
        model.addAttribute("pics", pictureService.getAllByStatus(List.of(Status.NEW)));
        return "/home/pendingPics";
    }


}

