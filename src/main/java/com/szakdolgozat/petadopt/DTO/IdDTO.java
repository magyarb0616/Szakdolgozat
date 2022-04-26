package com.szakdolgozat.petadopt.DTO;

public class IdDTO {
    private Long id;
    private Long secId;

    public IdDTO() {
    }

    public IdDTO(Long id) {
        this.id = id;
    }

    public IdDTO(Long id, Long secId) {
        this.id = id;
        this.secId = secId;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSecId() {
        return secId;
    }

    public void setSecId(Long secId) {
        this.secId = secId;
    }
}
