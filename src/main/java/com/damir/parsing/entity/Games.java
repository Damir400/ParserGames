package com.damir.parsing.entity;

import com.damir.parsing.ICrudable;

import javax.persistence.*;

@Entity
@Table(name = "games")
public class Games implements ICrudable {
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
    @Column(name = "tags")
    private String tags;
    @Column(name = "nameStore")
    private String nameStore;

    public Games() {
    }

    public Games(String name, String url, String img, String tags, String nameStore) {
        this.name = name;
        this.url = url;
        this.img = img;
        this.tags = tags;
        this.nameStore = nameStore;
    }

    @Override
    public String toString() {
        return "Games{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", img='" + img + '\'' +
                ", genre='" + tags + '\'' +
                ", nameStore='" + nameStore + '\'' +
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

    public String getTags() {
        return tags;
    }

    public void setTags(String genre) {
        this.tags = genre;
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }
}


