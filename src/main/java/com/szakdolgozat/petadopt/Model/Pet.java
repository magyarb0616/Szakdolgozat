package com.szakdolgozat.petadopt.Model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adoptive")
    private User adoptive;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "sex", nullable = false)
    private Boolean sex = false;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "hair", nullable = false)
    private Integer hair;

    @Column(name = "movement")
    private Integer movement;

    @Column(name = "description")
    private String description;

    @Column(name = "score")
    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed")
    private Breed breed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city")
    private City city;

    @OneToMany(mappedBy = "petID")
    private Set<Image> images = new LinkedHashSet<>();

    @OneToMany(mappedBy = "petID")
    private Set<Match> matches = new LinkedHashSet<>();


    public Pet() {
    }

    public Pet(User adoptive, String name, Integer age, Boolean sex, Integer size, Integer hair, Integer movement, String description, Integer score, Breed breed, City city) {
        this.adoptive = adoptive;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.size = size;
        this.hair = hair;
        this.movement = movement;
        this.description = description;
        this.score = score;
        this.breed = breed;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAdoptive() {
        return adoptive;
    }

    public void setAdoptive(User adoptive) {
        this.adoptive = adoptive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }

}