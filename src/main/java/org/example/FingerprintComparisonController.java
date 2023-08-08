package org.example;

import com.machinezoo.sourceafis.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.multipart.MultipartFile;

@RestController
@SpringBootApplication(exclude = { JdbcRepositoriesAutoConfiguration.class })
public class FingerprintComparisonController {

    private final JdbcTemplate jdbcTemplate;

    public FingerprintComparisonController() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:/home/sanarip03/Desktop/verificate_finger/fingerprints.db"); // Set the path to your SQLite file here
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostMapping("/compare")
    public ResponseEntity<String> compareFingerprints(
            @RequestParam("clientFile") MultipartFile clientFile,
            @RequestParam("user_id") Integer userId) throws IOException {

        InputStream clientImage = clientFile.getInputStream();
        FingerprintTemplate clientTemplate = createFingerprintTemplate(clientImage);

        List<File> serverFiles = getFingerprintFilesByUserId(userId);

        for (File serverFile : serverFiles) {
            InputStream serverImage = new FileInputStream(serverFile);
            FingerprintTemplate serverTemplate = createFingerprintTemplate(serverImage);
            double score = new FingerprintMatcher().index(clientTemplate).match(serverTemplate);
            if (score >= 40) {
                return ResponseEntity.ok("Success");
            }
        }

        return ResponseEntity.ok("Error");
    }

    private List<File> getFingerprintFilesById(String id) {
        // This method should implement fetching the fingerprint files from your SQLite database
        // using the provided ID. This is a placeholder, and you would need to replace it
        // with your actual implementation.

        String sql = "SELECT filepath FROM fingerprints WHERE id = ?";
        List<String> filePaths = jdbcTemplate.queryForList(sql, new Object[]{id}, String.class);

        List<File> files = new ArrayList<>();
        for (String path : filePaths) {
            files.add(new File(path));
        }
        return files;
    }

    private List<File> getFingerprintFilesByUserId(Integer userId) {
        String sql = "SELECT filepath FROM fingerprints WHERE user_id = ?";
        List<String> filePaths = jdbcTemplate.queryForList(sql, new Object[]{userId}, String.class);

        List<File> files = new ArrayList<>();
        for (String path : filePaths) {
            files.add(new File(path));
        }
        return files;
    }





    private FingerprintTemplate createFingerprintTemplate(InputStream imageStream) throws IOException {
        BufferedImage fingerprintImage = ImageIO.read(imageStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(fingerprintImage, "bmp", baos);
        byte[] bytes = baos.toByteArray();
        return new FingerprintTemplate(new FingerprintImage(bytes, new FingerprintImageOptions().dpi(500)));
    }

}