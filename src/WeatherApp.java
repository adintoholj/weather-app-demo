import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
    public static JSONObject getWeatherData(String locationName){
        JSONArray locationData = getLocationData(locationName);
    }

    private static JSONArray getLocationData(String locationName){
        locationName = locationName.replaceAll(" ", "+");
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + locationName + "&count=10&language=en&format=json";
    }

    try{
        HttpURLConnection kon = fetchApiResponse(urlString);
        if(kon.getResponseCode() != 200){
            System.out.println("Error, can't connect to the API");
            return null;
        }else{
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(kon.getInputStream());
        }
    }catch(Exception e){
        e.printStackTrace();
    }

    private static HttpURLConnection fetchApiResponse(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection kon = (HttpURLConnection) url.openConnection();
            kon.setRequestMethod("GET");
            kon.connect();
            return kon;
        }catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
