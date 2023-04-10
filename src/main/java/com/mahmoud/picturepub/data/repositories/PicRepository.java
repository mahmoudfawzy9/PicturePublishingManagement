package com.mahmoud.picturepub.data.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.mahmoud.picturepub.data.entities.PicCategory;
import com.mahmoud.picturepub.data.entities.Pic;
import com.mahmoud.picturepub.data.entities.PicStatus;
import com.mahmoud.picturepub.data.entities.AppUser;

import java.util.List;

public interface PicRepository extends JpaRepository<Pic,Long> {

    List<Pic>findAllByStatusIn(List<PicStatus>  status);

    @Query(value="update Pic m set m.status='DELETED' where m.id=?1")
    void delete(Long id);

    List<Pic>findAllByAppUser(AppUser appUser);

    List<Pic>findAllByCategoryIn(List<PicCategory>categories);
}
