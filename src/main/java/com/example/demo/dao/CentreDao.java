package com.example.demo.dao;

import com.example.demo.model.Centre;

import java.util.HashMap;
import java.util.Map;

public class CentreDao {

    private final Map<String, Centre> onboardedCentres;

    public CentreDao() {
        this.onboardedCentres = new HashMap<>();
    }

    public Map<String, Centre> onboardedCentres() {
        return onboardedCentres;
    }

    public void addCentre(final Centre centre) {
        onboardedCentres.put(centre.getName(), centre);
    }

    public Centre getCentre(final String name) {
        return onboardedCentres.get(name);
    }
}
