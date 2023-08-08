package org.example;

import javax.persistence.*;
@Entity
public class Fingerprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] image;

    private String clientId;

    // Getter for ID
    public Long getId() {
        return id;
    }

    // Setter for ID
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for Image
    public byte[] getImage() {
        return image;
    }

    // Setter for Image
    public void setImage(byte[] image) {
        this.image = image;
    }

    // Getter for Client ID
    public String getClientId() {
        return clientId;
    }

    // Setter for Client ID
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
