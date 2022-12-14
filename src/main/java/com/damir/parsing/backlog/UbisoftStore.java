package com.damir.parsing.backlog;

import com.damir.parsing.entity.Games;
import com.damir.parsing.service.Repository;
import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UbisoftStore {
    static Games[] gamesArray;
    static Gson gson = new Gson();
    static JavascriptExecutor jse;

    public void dataParsing(WebDriver webDriver) throws InterruptedException {

        Repository<Games> repositoryGame = new Repository<Games>(Games.class);

        webDriver.get("https://store.ubi.com/us/free-pc-games/?lang=en_US");

        WebElement webElement = new WebDriverWait(webDriver, Duration.ofSeconds(1)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"privacy__modal__accept\"]")));
        webDriver.findElement(By.xpath("//*[@id=\"privacy__modal__accept\"]")).click();

        webElement = new WebDriverWait(webDriver, Duration.ofSeconds(1)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"georestricted\"]/button")));
        webDriver.findElement(By.xpath("//*[@id=\"georestricted\"]/button")).click();

        webElement = new WebDriverWait(webDriver, Duration.ofSeconds(1)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"primary\"]/div[5]/div[3]/button")));
        webDriver.findElement(By.xpath("//*[@id=\"primary\"]/div[5]/div[3]/button")).click();

        webElement = new WebDriverWait(webDriver, Duration.ofSeconds(1)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"primary\"]/div[2]/div[2]/div/p/span")));
        int k = Integer.parseInt(webDriver.findElement(By.xpath("//*[@id=\"primary\"]/div[2]/div[2]/div/p/span")).getText());
        Thread.sleep(5000);
        jse = (JavascriptExecutor) webDriver;
        Games[] games = ubisoftGamesParsing(k, jse);
        System.out.println("Ubisoft games: " + games.length);
        repositoryGame.addItems(games);

    }

    public static Games[] ubisoftGamesParsing(int count, JavascriptExecutor jse){
        String json = null;
        while (json == null || json.isEmpty()){
            json = jse.executeScript(
                            " let result = [];\n" +
                                    "    let count = " + count + ";\n" +
                                    "    for (let i=1; i <= count; i++) {\n" +
                                    "        result.push({\n" +
                                    "            'name': document.querySelector('#search-result-items > li:nth-child(n + ' + i*3 +')').innerText.split('\\n')[0],\n" +
                                    "            'url': document.querySelector('#search-result-items > li:nth-child(n + ' + i*3 +') > div:nth-child(2) > a').href,\n" +
                                    "            'img': document.querySelector('#search-result-items > li:nth-child(n + ' + i*3 +') > div > div').getElementsByTagName('img')[0].getAttribute('data-src')\n" +
                                    "            'tags':''" +
                                    "            'nameStore': ''" +
                                    "        });\n" +
                                    "\n" +
                                    "    }" +
                                    "return JSON.stringify(result);")
                    .toString();
        }

        gamesArray = gson.fromJson(json, Games[].class);
        return gamesArray;
    }
}

