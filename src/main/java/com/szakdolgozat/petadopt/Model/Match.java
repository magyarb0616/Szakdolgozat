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

    public Match() {
    }

    public Match(User adopterID, Pet petID) {
        this.adopterID = adopterID;
        this.petID = petID;
    }

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


}