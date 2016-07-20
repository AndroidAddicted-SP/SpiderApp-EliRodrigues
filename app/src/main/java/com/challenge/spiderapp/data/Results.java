package com.challenge.spiderapp.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eliete on 7/14/16.
 */
public class Results {

    String copyright;
    int issue;
    String title;
    String description;
    int pageCount;
    String date;
    String price;
    String path;
    String extension;
    static List<Results> resultsList;

    public static List<Results> getResultsList() {
        return resultsList;
    }

    public void setResultsList(List<Results> resultsList) {
        this.resultsList = resultsList;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public static void getResultsListFromJson(String response) throws ParseException, JSONException {

        resultsList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(response);

        String attributionTest = jsonObject.getString("copyright");

        JSONObject data = jsonObject.getJSONObject("data");

        JSONArray resultsArray = data.getJSONArray("results");
        for (int i = 0; i < resultsArray.length(); i++){

            Results results = new Results();
            String unformattedDate;
            String unformattedPrice;

            results.setCopyright(attributionTest);
            results.setIssue(resultsArray.getJSONObject(i).getInt("issueNumber"));
            results.setTitle(resultsArray.getJSONObject(i).getString("title"));
            results.setDescription(resultsArray.getJSONObject(i).getString("description"));
            results.setPageCount(resultsArray.getJSONObject(i).getInt("pageCount"));

            JSONArray dateArray = resultsArray.getJSONObject(i).getJSONArray("dates");
            for (int j = 0; j < dateArray.length(); j++){
                String typeDate = dateArray.getJSONObject(j).getString("type");
                if (typeDate != null){
                    if (typeDate.equals("onSaleDate")){
                        unformattedDate = dateArray.getJSONObject(j).getString("date");
                        results.setDate(getFormattedDate(unformattedDate));
                    }
                }
            }


            JSONArray priceArray = resultsArray.getJSONObject(i).getJSONArray("prices");
            String typePrice = priceArray.getJSONObject(0).getString("type");
            if (typePrice != null){
                if (typePrice.equals("printPrice")){
                    unformattedPrice = priceArray.getJSONObject(0).getString("price");
                    results.setPrice(getFormattedPrice(unformattedPrice));
                }
            }

            JSONObject thumbJsonObject = resultsArray.getJSONObject(i).getJSONObject("thumbnail");
            if (thumbJsonObject != null) {
                results.setPath(thumbJsonObject.getString("path"));
                results.setExtension(thumbJsonObject.getString("extension"));
            }

            resultsList.add(results);
        }

    }

    private static String getFormattedPrice(String unformattedPrice) {
        return "R$ " + unformattedPrice.replace(".", ",");
    }

    private static String getFormattedDate(String unformattedDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ssZ");
        java.util.Date day = format.parse(unformattedDate);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        return sdf.format(day);
    }


    @Override
    public String toString() {
        return "Results{" +
                "thumbnail=" + path +
                ", issue='" + issue + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pageCount='" + pageCount + '\'' +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
