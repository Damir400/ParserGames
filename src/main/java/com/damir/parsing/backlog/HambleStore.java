package com.damir.parsing.backlog;


import com.damir.parsing.entity.Games;
import com.damir.parsing.service.Repository;
import com.google.gson.Gson;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.stereotype.Service;

@Service
public class HambleStore {

    static Games[] gamesArray;

    static Gson gson = new Gson();

    static JavascriptExecutor jse;
    public void dataParsing(WebDriver webDriver) throws InterruptedException {

        Repository<Games> repositoryGame = new Repository<Games>();
        //EdgeOptions edgeOptions = new EdgeOptions();
        //edgeOptions.addArguments("headless");
        webDriver = new EdgeDriver();
        webDriver.get("https://www.humblebundle.com/store/search?sort=discount&filter=onsale&hmb_source=navbar");
        //WebElement webElement = new WebDriverWait(webDriver, Duration.ofSeconds(1)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"mm-0\"]/div[1]/div[6]/div[2]/section/div[2]/div[2]/div/div/div[2]/div/div[3]/div/a[4]")));
        jse = (JavascriptExecutor) webDriver;
        Thread.sleep(5000);



        //jse = (JavascriptExecutor) webDriver;

        Games[] games = hambleParsing(jse);
        System.out.println("Hamble games: " + games.length);
        repositoryGame.addItems(games);
        webDriver.close();
    }
    public static Games[] hambleParsing(JavascriptExecutor jse){
        String json = null;
        while (json == null || json.isEmpty()){
            json = jse.executeScript(
                            " let result = [];\n" +
                                    "    let count = document.querySelectorAll('#mm-0 > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > ' +\n" +
                                    "        'div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > ' +\n" +
                                    "        'div.list-content.js-list-content.show-status-container > div > ul > li').length;\n" +
                                    "    for (let i=1; i < count; i++) {\n" +
                                    "        let element = document.querySelector('#mm-0 > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > ' +\n" +
                                    "            'section > div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > ' +\n" +
                                    "            'div.chunks-container > div.list-content.js-list-content.show-status-container > div > ul > li:nth-child('+ i + ') ').innerText.split('\\n');\n" +
                                    "        if(element[element.length-1]==='₽ 0.00'){\n" +
                                    "            result.push({\n" +
                                    "                'name': element[0],\n" +
                                    "                'url': document.querySelector('#mm-0 > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > ' +\n" +
                                    "                    'div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > ' +\n" +
                                    "                    'div.list-content.js-list-content.show-status-container > div > ul > li:nth-child('+ i +') > div > div > a').href,\n" +
                                    "                'img': document.querySelector('#mm-0 > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > ' +\n" +
                                    "                    'div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list >' +\n" +
                                    "                    ' div > div.chunks-container > div.list-content.js-list-content.show-status-container > div > ul > li:nth-child('+ i +') > div > div > a > div > img').src\n" +
                                    "            });\n" +
                                    "        }\n" +
                                    "        else {\n" +
                                    "            break;\n" +
                                    "        }\n" +
                                    "\n" +
                                    "\n" +
                                    "    }\n" +
                                    "    return JSON.stringify(result);")
                    .toString();
        }
        gamesArray = gson.fromJson(json, Games[].class);
        return gamesArray;
    }
}
