package com.szakdolgozat.petadopt.Model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;



    @OneToMany(mappedBy = "country")
    private Set<City> cities = new LinkedHashSet<>();

    public Country(Long id, String name, Set<City> cities) {
        this.id = id;
        this.name = name;
        this.cities = cities;
    }

    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Country(String name) {
        this.name = name;
    }

    public Country() {

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

}