package com.damir.parsing.service;

import com.damir.parsing.common.Constants;
import com.damir.parsing.entity.Games;
import com.google.gson.Gson;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

@Service
public class SteamStore {
    private Games[] gamesArray;
    private JavascriptExecutor jse;
    private Gson gson = new Gson();

    public void dataParsing(WebDriver webDriver) throws InterruptedException {
        Repository<Games> gamesRepo = new Repository<Games>();
        webDriver.get("https://store.steampowered.com/search/?sort_by=Price_ASC&maxprice=free&tags=113&category1=998&os=win&genre=Free+to+Play");

        jse = (JavascriptExecutor) webDriver;
        Thread.sleep(5000);

        int count = 0;
        do {
            if(count>=3000){
                break;
            }
            count += gamesRepo.addItems(SteamGamesParsingJS());
            jse.executeScript("window.scrollBy(0,4000)");
        } while (true);
    }

    public Games[] SteamGamesParsingJS(){

        String json = null;
        while (json == null || json.isEmpty()){
            json = jse.executeScript(Constants.getSteamParingJsScript()).toString();
        }
        gamesArray = gson.fromJson(json, Games[].class);
        System.out.println("Steam games: " + gamesArray.length);
        return gamesArray;
    }
}
