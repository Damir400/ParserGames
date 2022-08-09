package com.damir.parsing.Entity;


import javax.persistence.*;

@Entity
@Table(name = "games")
public class Games {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
    @Column(name = "img")
    private String img;

    public Games() {
    }

    public Games(String name, String url, String img) {
        this.name = name;
        this.url = url;
        this.img = img;
    }

    @Override
    public String toString() {
        return "Games{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}


