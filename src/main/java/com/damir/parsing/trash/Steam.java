package com.damir.parsing.trash;


import com.damir.parsing.Entity.Games;
import com.damir.parsing.Service.Repository;
import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

public class Steam {
    static List<WebElement> childs;
    static List<Games> gamesList;
    static Games[] gamesArray;
    static WebElement parent;
    static JavascriptExecutor jse;
    static Gson gson = new Gson();
    @PersistenceContext
    static EntityManager entityManager;

    public static void main(String[] args) throws InterruptedException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("fdfd");
         entityManager = entityManagerFactory.createEntityManager();

        //Date startDate = new Date();
        //System.out.println("Start: " + new Date());
        Repository<Games> gamesRepo = new Repository<Games>();
        //gamesRepo.delItems();
        System.setProperty("webdriver.edge.driver", "driver\\msedgedriver.exe");
        EdgeOptions edgeOptions = new EdgeOptions();
        //edgeOptions.addArguments("headless");
        WebDriver webDriver = new EdgeDriver(edgeOptions);

        Map<String, String> steamFreeGames = new HashMap<>();
        webDriver.get("https://store.steampowered.com/search/?sort_by=Price_ASC&maxprice=free&tags=113&category1=998&os=win&genre=Free+to+Play");

        jse = (JavascriptExecutor) webDriver;
        Thread.sleep(5000);

        parent = webDriver.findElement(By.xpath("//*[@id=\"search_resultsRows\"]"));

        int count = 0;
        do {
            if(count>=3000){
                break;
            }

//            count += gamesRepo.addItems(SteamGamesParsingDefault(count));
//            count += gamesRepo.addItems(SteamGamesParsingStream(count));
            count += gamesRepo.addItems(SteamGamesParsingJS(count));

            jse.executeScript("window.scrollBy(0,4000)");
        } while (true);

//        Date endDate = new Date();
//        System.out.println("START: " + startDate);
//        System.out.println("END: " + endDate);
    }

    public static List<Games> SteamGamesParsingDefault(int count){
        gamesList = new ArrayList<>();
        childs = parent.findElements(By.cssSelector("a:nth-child(n+" + (count) + ")"));
        for(WebElement webElement: childs) {
            gamesList.add(new Games(
                    webElement.getAttribute("innerText").split("\\n")[1],
                    webElement.getAttribute("href"),
                    webElement.findElement(By.tagName("img")).getAttribute("src")));
        }
        return gamesList;
    }

    public static List<Games> SteamGamesParsingStream(int count){
        childs = parent.findElements(By.cssSelector("a:nth-child(n+" + (count) + ")"));
        gamesList = childs.stream().map((p)-> new Games(
                p.getAttribute("innerText").split("\\n")[1],
                p.getAttribute("href"),
                p.findElement(By.tagName("img")).getAttribute("src"))).collect(Collectors.toList());
        return gamesList;
    }

    public static Games[] SteamGamesParsingJS(int count){
        String json = null;
        while (json == null || json.isEmpty()){
            json = jse.executeScript(
                    "let result = [];" +
                            "let all = document.querySelectorAll('#search_resultsRows > a:nth-child(n+" + count +")');\n" +
                            "for (let i=0, max=all.length; i < max; i++) {\n" +
                            "    result.push({\n" +
                            "        'name': all[i].innerText.split('\\n')[1],\n" +
                            "        'url': all[i].href,\n" +
                            "        'img': all[i].getElementsByTagName('img')[0].src\n" +
                            "    });\n" +
                            "}\n" +
                            "return JSON.stringify(result);")
                    .toString();
            }

        gamesArray = gson.fromJson(json, Games[].class);
        return gamesArray;
    }
}


