package com.szakdolgozat.petadopt.DTO;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
public class CountryDTO {
    //@NotEmpty(message = "Name cannot be empty!")
   // @NotNull(message = "Name cannot be null!")
    private String name;
    private Integer id;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
