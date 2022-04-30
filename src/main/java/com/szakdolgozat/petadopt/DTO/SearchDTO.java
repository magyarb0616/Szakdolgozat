package com.szakdolgozat.petadopt.DTO;

public class SearchDTO {
    //private Long specieId;
    private Long breedId;
    private Integer sex;
    private Integer size;
    private Integer hair;
    private Integer movement;


  /*  public Long getSpecieId() {
        return specieId;
    }

    public void setSpecieId(Long specieId) {
        this.specieId = specieId;
    } */

    public Long getBreedId() {
        return breedId;
    }

    public void setBreedId(Long breedId) {
        this.breedId = breedId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getHair() {
        return hair;
    }

    public void setHair(Integer hair) {
        this.hair = hair;
    }

    public Integer getMovement() {
        return movement;
    }

    public void setMovement(Integer movement) {
        this.movement = movement;
    }
}
