package com.challenge.spiderapp.data;

public class Injection {

    public static ComicsRepository provideComicsRepository(){
        return new ComicsRepositoryImpl();
    }


}
