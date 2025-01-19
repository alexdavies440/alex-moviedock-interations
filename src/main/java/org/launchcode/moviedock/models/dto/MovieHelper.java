package org.launchcode.moviedock.models.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.launchcode.moviedock.models.ApiMovie;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieHelper {

    class M {
        private  String imdbID;
    }

    ArrayList<ApiMovie> movies = new ArrayList<ApiMovie>();


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

    public String[] makeMovieList(String stringToParse) {
        String parsedString = getMoviesBySearch(stringToParse);
        parsedString = parsedString.substring(parsedString.indexOf("[")+1);
        System.out.println(parsedString);
        return parsedString.split("}",0);
    }


}