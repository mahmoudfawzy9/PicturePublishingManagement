package com.mahmoud.picturepub.request;

import com.mahmoud.picturepub.entity.Category;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PictureDTO {

    private Long id;

    private String description;

    private Category category;

    private MultipartFile file;

    private String url;

}
