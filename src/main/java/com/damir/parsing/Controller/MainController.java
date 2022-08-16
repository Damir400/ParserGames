package com.damir.parsing.Controller;


import com.damir.parsing.Entity.Games;
import com.damir.parsing.Helper;
import com.damir.parsing.Service.HambleStore;
import com.damir.parsing.Service.Repository;
import com.damir.parsing.Service.SteamStore;
import com.damir.parsing.Service.UbisoftStore;
import com.google.gson.Gson;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class MainController {

    private Gson gson = new Gson();
    private HambleStore hambleStore;
    private UbisoftStore ubisoftStore;
    private SteamStore steamStore;
    private Repository<Games> repository = new Repository<>();

    static EdgeOptions edgeOptions = null;
    static WebDriver webDriver = null;

    static int pageMaxItems = 25;

    public static void initWebDriver() {
        System.setProperty("webdriver.edge.driver", "driver\\msedgedriver.exe");
        edgeOptions = new EdgeOptions();

        edgeOptions.addArguments("headless");
        webDriver = new EdgeDriver(edgeOptions);
        webDriver.manage().window().setSize(new Dimension(1920,1080));
    }

    public MainController(HambleStore hambleStore, UbisoftStore ubisoftStore, SteamStore steamStore){
        this.hambleStore = hambleStore;
        this.ubisoftStore = ubisoftStore;
        this.steamStore = steamStore;
    }

    @GetMapping("/load")
    public Long addGames() throws InterruptedException {
        repository.delItems();
        initWebDriver();

//        ubisoftStore.dataParsing(webDriver);
        steamStore.dataParsing(webDriver);
//        hambleStore.dataParsing(webDriver);
        webDriver.close();

        Long gamesCount = repository.getCountItems();
        BigDecimal result = new BigDecimal(gamesCount.doubleValue()/pageMaxItems);
        result = result.setScale(0, RoundingMode.CEILING);
        return result.longValue();
    }

    @GetMapping("/getPagesCount")
    public Long getPagesCount() {
        Long gamesCount = repository.getCountItems();
        BigDecimal result = new BigDecimal(gamesCount.doubleValue()/pageMaxItems);
        result = result.setScale(0, RoundingMode.CEILING);
        return result.longValue();
    }

    @GetMapping("/getgames")
    public String getGames() throws InterruptedException {
        List<Games> gamesList = repository.getItems(new ArrayList<Games>());
        String result = gson.toJson(gamesList);
        return result;
    }

    @GetMapping("/games/{page}")
    public String getGames(@PathVariable(value = "page") String page)  {
        int newPage = Helper.tryParseInt(page, 1);
        int offset = pageMaxItems * (newPage - 1);

        List<Games> gamesList = repository.getItems(pageMaxItems, offset);
        String result = gson.toJson(gamesList);
        return result;
    }
}
