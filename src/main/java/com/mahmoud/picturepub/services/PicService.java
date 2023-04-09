package com.mahmoud.picturepub.services;

import com.mahmoud.picturepub.data.entities.AppUser;
import com.mahmoud.picturepub.data.entities.Pic;
import com.mahmoud.picturepub.data.entities.PicCategory;
import com.mahmoud.picturepub.data.entities.PicStatus;

import java.util.List;
import java.util.Optional;

public interface PicService {
    List<Pic>getAll();
    List<Pic>getAllByStatus(List<PicStatus> status);
    List<Pic>getAllByCategory(List<PicCategory>categories);
    List<Pic> getAllByUser(AppUser appUser);
    Optional<Pic>getById(Long id);
    Pic save(Pic pic);
    void delete(Pic pic);
}
