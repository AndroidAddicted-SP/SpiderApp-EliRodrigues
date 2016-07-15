package com.challenge.spiderapp;

import java.util.List;

/**
 * Created by eliete on 7/14/16.
 */
public class Comics  extends Results{
    String attributionTest;
    List<Results> resultsList;

    public Comics() {
    }

    public String getAttributionTest() {
        return attributionTest;
    }

    public void setAttributionTest(String attributionTest) {
        this.attributionTest = attributionTest;
    }

    public List<Results> getResults() {
        return resultsList;
    }

    public void setResults(List<Results> resultsList) {
        this.resultsList = resultsList;
    }

    @Override
    public String toString() {
        return "Comics{" +
                "attributionTest='" + attributionTest + '\'' +
                ", resultsList=" + resultsList +
                '}';
    }
}
