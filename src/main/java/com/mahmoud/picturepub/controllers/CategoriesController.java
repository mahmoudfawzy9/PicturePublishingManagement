package com.mahmoud.picturepub.controllers;


import com.mahmoud.picturepub.data.entities.PicCategory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mahmoud.picturepub.services.PicService;
import com.mahmoud.picturepub.utils.ImageMapper;

@Slf4j
@AllArgsConstructor
@Controller
public class CategoriesController {

    private final PicService picService;
    private final ImageMapper imageMapper;



    @RequestMapping(value = "/categories")
    public String getPicsFragment(Model model){
        log.info("{}:{}",getClass().getSimpleName(),"/categories");

        model.addAttribute("categories", PicCategory.values());

        return "/home/categories";
    }


}
