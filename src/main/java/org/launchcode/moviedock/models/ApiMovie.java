package org.launchcode.moviedock.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Entity
public class ApiMovie extends AbstractEntity{

    //api key
    //private String url = "http://www.omdbapi.com/?apikey=b0901f52&t=";






    public String getMovieByName(String t) {
        String url = "http://www.omdbapi.com/?apikey=b0901f52&t="+t;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    public String getMovieById(String i){
        String url = "http://www.omdbapi.com/?apikey=b0901f52&i="+i;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }





    public String getMoviesBySearch(String s){
        String url = "http://www.omdbapi.com/?apikey=b0901f52&s="+s;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    public void getMovies(String s) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(this.getMoviesBySearch(s));
        System.out.println(node);

    }



    private String title;
    private String director;
    private String plot;
    private String poster;
    private String year;
    private String apiID;

    private int viewCount;




    public ApiMovie() {
        this.viewCount = 0;
    }



    public void setMovieInfoByName(String t) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(this.getMovieByName(t));

        if (node.get("Title")!=null) {
            this.title = node.get("Title").asText();
            this.director = node.get("Director").asText();
            this.plot = node.get("Plot").asText();
            this.poster = node.get("Poster").asText();
            this.year = node.get("Year").asText();
            this.apiID = node.get("imdbID").asText();
        }
    }

    public void setMovieInfoById(String i) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(this.getMovieById(i));

        if (node.get("Title")!=null) {
            this.title = node.get("Title").asText();
            this.director = node.get("Director").asText();
            this.plot = node.get("Plot").asText();
            this.poster = node.get("Poster").asText();
            this.year = node.get("Year").asText();
            this.apiID = node.get("imdbID").asText();
        }
    }

    public void userView(){
        this.viewCount++;
    }

    public int getViewCount() {
        return viewCount;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getPlot() {
        return plot;
    }

    public String getPoster() {
        return poster;
    }

    public String getYear() {
        return year;
    }

    public String getApiID() {
        return apiID;
    }

    @Override
    public String toString() {
        return "name";
    }
}