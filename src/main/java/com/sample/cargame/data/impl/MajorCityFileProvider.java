package com.sample.cargame.data.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sample.cargame.data.CityProvider;
import com.sample.cargame.model.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MajorCityFileProvider implements CityProvider {

    private final ArrayNode cityList;

    public MajorCityFileProvider(@Value("${city.provider.file}") String fileName) {
        try {
            InputStream fileInputStream = new ClassPathResource(fileName).getInputStream();
            cityList = new ObjectMapper().readValue(fileInputStream, ArrayNode.class);
        } catch (IOException e) {
            throw new RuntimeException("couldn't load city file");
        }
    }

    @Override
    @Cacheable("all-cities")
    public List<City> getAllCities() {
        return StreamSupport.stream(cityList.spliterator(), false)
                .map(ObjectNode.class::cast)
                .map(objectNode -> objectNode.get("name").asText())
                .map(City::new)
                .collect(Collectors.toList());
    }
}
