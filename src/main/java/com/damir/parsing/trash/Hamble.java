package com.damir.parsing.trash;


import com.damir.parsing.Entity.Games;
import com.damir.parsing.Service.Repository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Hamble {
    public static void main(String[] args) throws InterruptedException {
        Repository<Games> repositoryGame = new Repository<Games>();

        System.setProperty("webdriver.edge.driver","driver\\msedgedriver.exe");
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("headless");
        WebDriver webDriver = new EdgeDriver(edgeOptions);

        webDriver.get("https://www.humblebundle.com/store/search?sort=discount&filter=onsale&hmb_source=navbar");
        Thread.sleep(5000);

        List<Games> hambleGames = new ArrayList<>();

        for(int i=1; i<=20; i++){
            String hambleGame = webDriver.findElement(By.xpath("//*[@id=\"mm-0\"]/div[1]/div[6]/div[2]/section/div[2]/div[2]/div/div/div[2]/div/div[2]/div[1]/div/ul/li[" + i + "]")).getAttribute("innerText");
            String[] game = hambleGame.split("\\n");
            if(game[1].contains("-100")){
                String urlGame = webDriver.findElement(By.xpath("//*[@id=\"mm-0\"]/div[1]/div[6]/div[2]/section/div[2]/div[2]/div/div/div[2]/div/div[2]/div[1]/div/ul/li[" + i + "]/div/div/a")).getAttribute("href");
                String imgGame = webDriver.findElement(By.xpath("//*[@id=\"mm-0\"]/div[1]/div[6]/div[2]/section/div[2]/div[2]/div/div/div[2]/div/div[2]/div[1]/div/ul/li[" + i + "]/div/div/a/div/img")).getAttribute("src");
                hambleGames.add(new Games(game[0],urlGame,imgGame));
            }
            else {
                break;
            }
        }
        repositoryGame.addItems(hambleGames);

//        List<Games> getGames = new ArrayList<>(10);
//        for (int i = 0; i < getGames.size(); i++) {
//            getGames.add(new Games());
//        }
//        getGames = repositoryGame.getItems(getGames);
//        for(Games games : getGames){
//            System.out.println(games);
//        }
    }
}
