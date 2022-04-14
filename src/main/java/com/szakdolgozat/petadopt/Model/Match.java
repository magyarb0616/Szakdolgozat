package com.szakdolgozat.petadopt.Model;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adopterID")
    private User adopterID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petID")
    private Pet petID;

    @Column(name = "isActive")
    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAdopterID() {
        return adopterID;
    }

    public void setAdopterID(User adopterID) {
        this.adopterID = adopterID;
    }

    public Pet getPetID() {
        return petID;
    }

    public void setPetID(Pet petID) {
        this.petID = petID;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}