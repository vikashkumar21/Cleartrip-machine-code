package com.example.demo.admin;

import com.example.demo.model.Centre;
import com.example.demo.model.Timing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OnboardCentre {

    private final Map<String, Centre> onboardedCentres;

    public OnboardCentre() {
        this.onboardedCentres = new HashMap<>();
    }

    public Map<String, Centre> onboardedCentres() {
        return onboardedCentres;
    }

    public void addCentre(final String name) {
        final Centre centre = new Centre();
        centre.setName(name);
        onboardedCentres.put(name, centre);
    }

    public void addCentreTimings(final String name, final List<Timing> timings) {
        Optional.ofNullable(onboardedCentres.get(name))
                .orElseThrow()
                .setTimings(timings);
    }

    public void addCentreActivities(final String name, final List<String> activities) {
        Optional.ofNullable(onboardedCentres.get(name))
                .orElseThrow()
                .setActivities(activities);
    }
}
