package com.info;




import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        SeTess newTess = new SeTess();
        newTess.setup();
        HashMap<String, String> map = newTess.init();
        ConvertPy convert = new ConvertPy();
        convert.convertToJson(map);
        convert.runPyScript();
}
}