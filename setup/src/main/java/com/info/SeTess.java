package com.info;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.util.Scanner;
import java.util.HashMap;
import java.io.File;
import net.sourceforge.tess4j.ITesseract;


public class SeTess {

        //variables
        WebDriver driver;
        Scanner scnr;
        JavascriptExecutor executor;
        ITesseract tesseract;
        boolean notEntered;
        String categories ;
        String URL;
        HashMap<String, String> map;
        String[] list;
        public void setup(){
        //Scanner //general setup
        scnr = new Scanner(System.in);
        System.out.println("Enter your desired information categories, comma in-between");
        notEntered = true;
        //Gets categories in list
        list = new String[10];
        while(notEntered){
            categories = scnr.nextLine();
            list = categories.split(",");
            notEntered = !notEntered;
        }
        } 
        public HashMap<String, String> init(){
        //enters when user is done inputting
            map = new HashMap<>();
            if(notEntered ==false){
                AppConfig.init();
                driver = AppConfig.getDrive();       
                //tesseract = AppConfig.getTesseract(); 
                //Initializes driver
                URL ="https://www.google.com/search?q=";
    
                try{
                    for(String str: list){
                        String temp = str.replaceAll(" ", "+");
                        driver.get(URL+temp);
                        executor = (JavascriptExecutor)driver;
                        while(!executor.executeScript("return document.readyState").equals("complete")){
                            Thread.sleep(250);
                        }
                        executor.executeScript("document.body.style.zoom = '0.8'");
                        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                        
                        String s =imageBytes.fileToBytes(screenshot);
                        map.put("question" , "What is the" +str);
                        map.put("image", s);
                        //System.out.print(s.length());
                        screenshot.delete();         
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    scnr.close();
                    driver.close();
                }
                }

                return map;
        }

}
