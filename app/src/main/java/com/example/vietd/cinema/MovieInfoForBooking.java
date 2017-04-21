package com.example.vietd.cinema;

import java.io.Serializable;

/**
 * Created by Optimus on 4/21/2017.
 */

public class MovieInfoForBooking implements Serializable {
    int idmovie, format, duration;
    String name, img, director, actornactress, language, nation, startday, urltrailer, category, content;
    double imdb;

    public MovieInfoForBooking(int idmovie, String name, double imdb, String img, int duration, String director, String actornactress, String language, String nation, String startday, int format, String urltrailer, String category, String content) {
        this.idmovie = idmovie;
        this.format = format;
        this.duration = duration;
        this.name = name;
        this.img = img;
        this.director = director;
        this.actornactress = actornactress;
        this.language = language;
        this.nation = nation;
        this.startday = startday;
        this.urltrailer = urltrailer;
        this.category = category;
        this.content = content;
        this.imdb = imdb;
    }

    public int getIdmovie() {
        return idmovie;
    }

    public void setIdmovie(int idmovie) {
        this.idmovie = idmovie;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActornactress() {
        return actornactress;
    }

    public void setActornactress(String actornactress) {
        this.actornactress = actornactress;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getStartday() {
        return startday;
    }

    public void setStartday(String startday) {
        this.startday = startday;
    }

    public String getUrltrailer() {
        return urltrailer;
    }

    public void setUrltrailer(String urltrailer) {
        this.urltrailer = urltrailer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getImdb() {
        return imdb;
    }

    public void setImdb(double imdb) {
        this.imdb = imdb;
    }
}
