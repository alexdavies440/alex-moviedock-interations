package org.launchcode.moviedock.models.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.launchcode.moviedock.models.ApiMovie;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MovieHelper {


    ArrayList<ApiMovie> movies = new ArrayList<ApiMovie>();


    public String getMoviesBySearch(String s){
        String url = "http://www.omdbapi.com/?apikey=b0901f52&s="+s;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    /*public void getMovies(String s) throws JsonProcessingException {


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(this.getMoviesBySearch(s));
        System.out.println(node);

    }*/

    public String[] makeMovieList(String stringToParse) {
        String parsedString = getMoviesBySearch(stringToParse);
        parsedString = parsedString.substring(parsedString.indexOf("[")+1);
        System.out.println(parsedString);

        String[] unparsedMovies = parsedString.split("}",0);


        //remove everything before the imdb id
        for (int i = 0; i < unparsedMovies.length; i++){
            unparsedMovies[i] =  unparsedMovies[i].substring(unparsedMovies[i].indexOf("imdbID")+9);
        }

        //remove everything after the imdb id
        for (int i = 0; i < unparsedMovies.length; i++){
            unparsedMovies[i] =  unparsedMovies[i].substring(0, unparsedMovies[i].indexOf('"'));
        }

        //removes last element
        String[] parsedMovies = Arrays.copyOf(unparsedMovies, unparsedMovies.length-1);


        return parsedMovies;
    }



   /* public void getMovies(String s) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map
                = objectMapper.readValue(s, new TypeReference<Map<String,Object>>(){});


    }*/

}