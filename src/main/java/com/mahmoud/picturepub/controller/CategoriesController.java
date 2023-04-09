package com.mahmoud.picturepub.controller;

import com.mahmoud.picturepub.entity.Category;
import com.mahmoud.picturepub.service.PictureService;
import com.mahmoud.picturepub.utils.ImageMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@AllArgsConstructor
@Controller
public class CategoriesController {

    private final PictureService picService;
    private final ImageMapper imageMapper;


    @RequestMapping(value = "/categories")
    public String getPicsFragment(Model model) {
        log.info("{}:{}", getClass().getSimpleName(), "/categories");

        model.addAttribute("categories", Category.values());

        return "/home/categories";
    }
}