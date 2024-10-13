package com.info;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ConvertPy {
    ProcessBuilder processBuilder;
    String jsonMap;
    public void convertToJson(HashMap<String, String> map){
        this.jsonMap = new com.google.gson.Gson().toJson(map);
    }
    public void runPyScript(){
        try{
            processBuilder=new ProcessBuilder("/Users/lichieler/CsProjects/myenv/bin/python3", "setup/src/main/java/com/info/contextResult.py",jsonMap);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            System.out.println("here");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            while(((line= errReader.readLine())!=null)){
                System.out.println(line);
            }
            process.waitFor();
        }catch(IOException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            System.out.println("Interrupted exception!");
        }
    }
}
