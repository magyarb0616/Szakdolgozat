package com.szakdolgozat.petadopt.DTO;

public class MatchDTO {
    private Long id;
    private Long petID;
    private Long adopterID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPetID() {
        return petID;
    }

    public void setPetID(Long petID) {
        this.petID = petID;
    }

    public Long getAdopterID() {
        return adopterID;
    }

    public void setAdopterID(Long adopterID) {
        this.adopterID = adopterID;
    }
}
