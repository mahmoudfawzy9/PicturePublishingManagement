package com.mahmoud.picturepub.bootstrap;

import com.mahmoud.picturepub.entity.*;
import com.mahmoud.picturepub.repository.PictureRepository;
import com.mahmoud.picturepub.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final PictureRepository picRepository;

    @Autowired
    public DevBootstrap(UserRepository userRepository, PictureRepository picRepository) {
        log.debug(getClass().getSimpleName() + ":I'm at the Bootstrap phase.");
        this.userRepository = userRepository;
        this.picRepository = picRepository;
    }

    @Override
    public void onApplicationEvent(@Nullable ContextRefreshedEvent contextRefreshedEvent) {
        clearOldData();
        initData();
    }

    void clearOldData() {
        userRepository.deleteAll();
        picRepository.deleteAll();
    }

    @Transactional
    void initData() {
        log.info("initData()");

        Picture pic1 = new Picture();
        pic1.setCategory(Category.LIVING_THING);
        pic1.setPath("cat.png");
        pic1.setFilename("cat.png");
        pic1.setStatus(Status.ACCEPTED);
        pic1.setDescription("Be honest! it's better than a dog.");
        //pic1.setUser(user);

        Picture pic2 = new Picture();
        pic2.setCategory(Category.MACHINE);
        pic2.setPath("f22.png");
        pic2.setFilename("f22.png");
        pic2.setStatus(Status.ACCEPTED);
        pic2.setDescription("King Raptor is here, If you like it ,leave a comment below.\nOps!! no comment section available.");
        //pic2.setUser(user);

        Picture pic3 = new Picture();
        pic3.setCategory(Category.NATURE);
        pic3.setPath("waterfall.png");
        pic3.setFilename("waterfall.png");
        pic3.setStatus(Status.ACCEPTED);
        pic3.setDescription("Love is all, a waterfall.\npulls u in, takes u down.\nIt's a sad affair");

        Picture pic4 = new Picture();
        pic4.setCategory(Category.NATURE);
        pic4.setPath("moon.gif");
        pic4.setStatus(Status.NEW);
        pic4.setFilename("moon.gif");
        pic4.setDescription("Reminds u with something?!");

        Picture pic5 = new Picture();
        pic5.setCategory(Category.MACHINE);
        pic5.setPath("megatron.png");
        pic5.setStatus(Status.NEW);
        pic5.setFilename("megatron.png");
        pic5.setDescription("I'm megatron.");
        //pic3.setUser(user);

        User admin = new User();
        admin.setUserName("admin@yahoo.com");
        admin.setPassword("{pbkdf2.2018}1f1fab5f4176f86513f10136f70d4984ef97860490176f501843bb503489");//admin123
        admin.setEmail("admin@yahoo.com");
        admin.grantAuthority(Role.ADMIN);//admin123

        User appUser = new User();
        appUser.setUserName("mahmoud.fawzy@yahoo.com");
        appUser.setPassword("{pbkdf2.2018}5905ba87b762cad9626cbaa59777af0ef3a8bd5fdb08a4b0974cf8f5bb8f");//user123
        appUser.setEmail("mahmoud.fawzy@yahoo.com");
        appUser.grantAuthority(Role.USER);//user123

        pic1.setUser(appUser);
        pic2.setUser(appUser);
        pic3.setUser(appUser);
        appUser.setPictures(Set.of(pic1,pic2,pic3,pic4,pic5));

        //todo: we need the uploaded by

        userRepository.saveAll(List.of(admin, appUser));
    }
}

