package com.sample.cargame.service;

import com.sample.cargame.data.CityProvider;
import com.sample.cargame.model.City;
import org.eclipse.collections.api.map.primitive.CharObjectMap;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NextCityService {

    private final CityProvider cityProvider;

    @Autowired
    public NextCityService(CityProvider cityProvider) {
        this.cityProvider = cityProvider;
    }

    public String nextMove(String opponentsCity) {
        if(StringUtils.isEmpty(opponentsCity)) {
            throw new RuntimeException("empty city provided");
        }

        opponentsCity = opponentsCity.toUpperCase();

        List<String> allCities = cityProvider.getAllCities()
                .stream()
                .map(City::getDescription)
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        String letterToStartWith = opponentsCity.substring(opponentsCity.length()-1);

        Map<String, List<String>> firstLetterToListOfCities =
                allCities.stream()
                    .collect(Collectors.groupingBy(city -> city.substring(0,1)));

        Map<String, List<String>> lastLetterToListOfCities =
                allCities.stream()
                        .collect(Collectors.groupingBy(city -> city.substring(city.length()-1)));

        List<String> possibleResponses = firstLetterToListOfCities.get(letterToStartWith);

        Set<String> lastLetterOfPossibleResponses = possibleResponses.stream()
                .map(city -> city.substring(city.length()-1))
                .collect(Collectors.toSet());

        String firstLetterToRespondWith = "";
        int numberOfBackResponses = 0;
        for (String lastLetterOfPossibleResponse : lastLetterOfPossibleResponses) {
            int currentResponses = firstLetterToListOfCities.getOrDefault(lastLetterOfPossibleResponse,new ArrayList<>()).size();
            if( numberOfBackResponses == 0 || currentResponses < numberOfBackResponses && currentResponses!=0 ) {
                numberOfBackResponses = currentResponses;
                firstLetterToRespondWith = lastLetterOfPossibleResponse;
            }
        }

        final String firstLetter = firstLetterToRespondWith;
        List<String> possibleCities = possibleResponses.stream()
                .filter(city -> city.endsWith(firstLetter))
                .collect(Collectors.toList());
        return possibleCities.get(new Random().nextInt(possibleCities.size()));
    }
}
