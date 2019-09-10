package com.sample.cargame.data.impl;

import com.sample.cargame.data.CityProvider;
import com.sample.cargame.model.City;
import org.eclipse.collections.api.factory.Lists;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("test")
@Service
public class StaticCityProvider implements CityProvider {
    @Override
    public List<City> getAllCities() {
        City firstCity = new City("New York");
        City secondCity = new City("London");
        City thirdCity = new City("Kansas");

        return Lists.immutable.of(firstCity, secondCity, thirdCity).castToList();
    }
}
