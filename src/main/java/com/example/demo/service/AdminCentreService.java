package com.example.demo.service;

import com.example.demo.dao.CentreDao;
import com.example.demo.exception.CentreDoesNotExistException;
import com.example.demo.model.Centre;
import com.example.demo.model.Timing;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class AdminCentreService {

    private final CentreDao centreDao;

    public AdminCentreService(final CentreDao centreDao) {
        this.centreDao = centreDao;
    }

    public void addCentre(final String name) {
        final Centre centre = new Centre();
        centre.setName(name);
        centreDao.addCentre(centre);
    }

    public void addCentreTimings(final String name, final List<Timing> timings) {
        Optional.ofNullable(centreDao.getCentre(name))
                .orElseThrow(() -> new CentreDoesNotExistException(format("Centre %s not found", name)))
                .setTimings(timings);
    }

    public void addCentreActivities(final String name, final List<String> activities) {
        Optional.ofNullable(centreDao.getCentre(name))
                .orElseThrow(() -> new CentreDoesNotExistException(format("Centre %s not found", name)))
                .setActivities(activities);
    }
}