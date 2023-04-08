package com.mahmoud.picturepub;

import com.mahmoud.picturepub.entity.Category;
import com.mahmoud.picturepub.entity.Picture;
import com.mahmoud.picturepub.entity.User;
import com.mahmoud.picturepub.repository.PictureRepository;
import com.mahmoud.picturepub.repository.UserRepository;
import com.mahmoud.picturepub.request.PictureDTO;
import com.mahmoud.picturepub.service.PictureService;
import com.mahmoud.picturepub.entity.Status;
import com.mahmoud.picturepub.service.PictureServiceImp;
import org.json.JSONException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PictureServiceTest {



    @Mock
    private PictureRepository pictureRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PictureServiceImp pictureService;

    @Test
    public void testUploadPicture() throws IOException, JSONException {
        User user = new User("test_user", "test_password", "test@example.com");
        userRepository.save(user);
        MultipartFile file = new MockMultipartFile("test.jpg", "test.jpg", "image/jpeg", new byte[1024]);
        PictureDTO dto = new PictureDTO();
        dto.setDescription("test");
        dto.setCategory(Category.LIVING_THING);
        dto.setFile(file);
        pictureService.uploadPicture(dto, user.getUserName());
        List<Picture> pictures = pictureRepository.findAll();

        assertNotNull(pictures.get(0));
        Picture picture = pictures.get(0);
        assertNotNull(picture.getFile());

        assertEquals(picture.getDescription(),"test", true);
        assertEquals(picture.getCategory().toString(), Category.LIVING_THING.toString(), true);
        assertEquals(picture.getStatus().toString(),Status.NEW.toString(), true);
    }

    @Test
    public void testProcessPicture() throws IOException, JSONException {
        User admin = new User("admin", "admin123", "admin@yahoo.com");
        userRepository.save(admin);
        User user = new User("test_user", "test_password", "test@yahoo.com");
        userRepository.save(user);
        Picture picture = new Picture();
        picture.setDescription("test");
        picture.setCategory(Category.LIVING_THING);
        picture.setStatus(Status.NEW);
        picture.setFile(new byte[1024]);
        picture.setUser(user);
        pictureRepository.save(picture);

        pictureService.processPicture(picture.getId(), admin.getUserName(), true);

        Picture updatedPicture = pictureRepository.findById(picture.getId()).orElse(null);

        assertNotNull(updatedPicture);
        assertNotNull(updatedPicture.getId());
        assertNotNull(updatedPicture.getStatus());
        assertNotNull(updatedPicture.getUrl());

        assertEquals(updatedPicture.getStatus().toString(), String.valueOf(Status.ACCEPTED), true);
    }
}
