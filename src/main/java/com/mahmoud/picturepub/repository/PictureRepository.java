package com.mahmoud.picturepub.repository;

import com.mahmoud.picturepub.entity.Picture;
import com.mahmoud.picturepub.entity.Status;
import com.mahmoud.picturepub.request.PictureDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> findByStatusOrderByIdAsc(Status status);

    Picture save(Picture entity);
}
