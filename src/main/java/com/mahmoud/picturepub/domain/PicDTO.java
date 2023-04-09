package com.mahmoud.picturepub.domain;

import com.mahmoud.picturepub.data.entities.AppUser;
import com.mahmoud.picturepub.data.entities.PicCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PicDTO {

    //todo: to replace Pic on requests/responses

    private AppUser appUser;

    private String description;

    private PicCategory picCategory;


}
