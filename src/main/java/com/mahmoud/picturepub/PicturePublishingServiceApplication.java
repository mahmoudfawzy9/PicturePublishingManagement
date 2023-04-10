package com.mahmoud.picturepub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@SpringBootApplication
public class PicturePublishingServiceApplication {

    public static void main(String[] args) {


        SpringApplication.run(PicturePublishingServiceApplication.class, args);
//        String password = "admin123";
//        String secret = "mySecretKey";
//        int iterations = 10000;
//        int keyLength = 256;
//
//        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder(secret, iterations, keyLength);
//        String hashedPassword = encoder.encode(password);
//
//        System.out.println("Original password: " + password);
//        System.out.println("Hashed password: " + hashedPassword);

    }

}
