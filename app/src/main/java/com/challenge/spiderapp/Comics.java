package com.challenge.spiderapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by eliete on 7/14/16.
 */
public class Comics{

    String attributionTest;
    String issue;
    String title;
    String description;
    String pageCount;
    String dateString;
    String priceString;
    String thumb;

    public static List<Comics> comicsList = new ArrayList<>();

    public Comics() {
    }

    public static List<Comics> getComicsList() {
        return comicsList;
    }

    public static void getComicListFromRetrofitJson(Response<Model> response) throws ParseException {
        String attributionTest = response.body().copyright;
        Data data = response.body().data;
        List<Results> resultsList = data.resultsList;
        for (int i = 0; i < resultsList.size(); i++){

            Comics comics = new Comics();

            Results results = resultsList.get(i);
            String issue = results.issue;
            String title = results.title;
            String description = results.description;
            String page =results.pageCount;

            List<Date> dateList = results.dateList;
            Date date = dateList.get(0);
            String typeDate = (date.type.equals("onsaleDate"))? date.type : null;
            String unformattedDate = typeDate != null ? date.date : null;

            List<Price> priceList = results.priceList;
            Price price = priceList.get(0);
            String typePrice = (price.type.equals("printPrice"))? price.type : null;
            String unformattedPrice = typePrice != null ? price.price : null;

            Thumbnail thumbnail = results.thumbnail;

            comics.attributionTest = attributionTest;
            comics.title = title;
            comics.description = description;
            comics.issue = issue;
            comics.pageCount = page;
            comics.dateString = getFormattedDate(unformattedDate);
            comics.priceString = getFormattedPrice(unformattedPrice);
            comics.thumb = thumbnail.path;

            comicsList.add(comics);

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
        return "Comics{" +
                "attributionTest='" + attributionTest + '\'' +
                ", issue='" + issue + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pageCount='" + pageCount + '\'' +
                ", dateString='" + dateString + '\'' +
                ", priceString='" + priceString + '\'' +
                ", thumb='" + thumb + '\'' +
                '}';
    }
}
