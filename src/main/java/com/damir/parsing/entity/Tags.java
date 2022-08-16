package com.damir.parsing.entity;

import com.damir.parsing.ICrudable;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class Tags implements ICrudable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private int id;
    @Column(name = "nametag")
    private String tagName;
    @Column(name = "count")
    private int count;

    public Tags() {
    }
    public Tags(int id, String tagName, int count) {
        this.id = id;
        this.tagName = tagName;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Tags{" +
                "nameTag='" + tagName + '\'' +
                ", count=" + count +
                '}';
    }

    public int getId() {
        return id;
    }
    public String getTagName() {
        return tagName;
    }
    public void setTagName(String nameTag) {
        this.tagName = nameTag;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
