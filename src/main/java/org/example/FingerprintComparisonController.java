package org.example;

import com.machinezoo.sourceafis.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

import org.springframework.web.multipart.MultipartFile;

@RestController
@SpringBootApplication
public class FingerprintComparisonController {

    @PostMapping("/compare")
    public ResponseEntity<String> compareFingerprints(
            @RequestParam("clientFile") MultipartFile clientFile,
            @RequestParam("serverFiles") MultipartFile[] serverFiles) throws IOException {

        InputStream clientImage = clientFile.getInputStream();
        FingerprintTemplate clientTemplate = createFingerprintTemplate(clientImage);

        for (MultipartFile serverFile : serverFiles) {
            InputStream serverImage = serverFile.getInputStream();
            FingerprintTemplate serverTemplate = createFingerprintTemplate(serverImage);
            double score = new FingerprintMatcher().index(clientTemplate).match(serverTemplate);
            if (score >= 40) {
                return ResponseEntity.ok("Success");
            }
        }

        return ResponseEntity.ok("Error");
    }




    private FingerprintTemplate createFingerprintTemplate(InputStream imageStream) throws IOException {
        BufferedImage fingerprintImage = ImageIO.read(imageStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(fingerprintImage, "bmp", baos);
        byte[] bytes = baos.toByteArray();
        return new FingerprintTemplate(new FingerprintImage(bytes, new FingerprintImageOptions().dpi(500)));
    }
}
