package com.info;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import net.sourceforge.tess4j.Tesseract;
public class AppConfig {
    //Reference to wbDriver
    public static WebDriver driver = new ChromeDriver();
    public static Tesseract tesseract= new Tesseract();
    public static void init(){
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver-mac-arm64");
        System.setProperty("java.library.path", "/opt/homebrew/Cellar/tesseract/5.4.1/bin");
        System.setProperty("jna.library.path", "/opt/homebrew/lib");
        System.setProperty("TESSDATA_PREFIX", "/Users/lichieler/CsProjects/tessdata/eng.traineddata");
    }
    public static WebDriver getDrive(){

        return driver;
    }
    public static Tesseract getTesseract(){
        tesseract.setDatapath("/opt/homebrew/share/tessdata");
        tesseract.setLanguage("eng");
        return tesseract;
    }

    
}
