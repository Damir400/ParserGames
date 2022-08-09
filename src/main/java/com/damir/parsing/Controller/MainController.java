package com.damir.parsing.Controller;


import com.damir.parsing.Entity.Games;
import com.damir.parsing.Service.HambleStore;
import com.damir.parsing.Service.Repository;
import com.damir.parsing.Service.SteamStore;
import com.damir.parsing.Service.UbisoftStore;
import com.google.gson.Gson;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class MainController {


    private HambleStore hambleStore;
    private UbisoftStore ubisoftStore;
    private SteamStore steamStore;

    static EdgeOptions edgeOptions = null;
    static WebDriver webDriver = null;

    public static void initWebDriver() {
        System.setProperty("webdriver.edge.driver", "driver\\msedgedriver.exe");
        edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("headless");
        webDriver = new EdgeDriver(edgeOptions);
    }

    public MainController(HambleStore hambleStore, UbisoftStore ubisoftStore, SteamStore steamStore){
        this.hambleStore = hambleStore;
        this.ubisoftStore = ubisoftStore;
        this.steamStore = steamStore;
    }

    @GetMapping("/load")
    public void addGames() throws InterruptedException {
        Repository<Games> repository = new Repository<>();
        repository.delItems();
        initWebDriver();

        ubisoftStore.dataParsing(webDriver);
        steamStore.dataParsing(webDriver);
        hambleStore.dataParsing(webDriver);
        webDriver.close();
    }

    @GetMapping("/getgames")
    public String getGames() throws InterruptedException {
        Gson gson = new Gson();
        Repository<Games> repository = new Repository<>();
        List<Games> gamesList = repository.getItems(new ArrayList<Games>());
        String result = gson.toJson(gamesList);
        return result;
    }
}
