import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WeatherApp {
    public static JSONObject getWeatherData(String locationName){
        JSONArray locationData = getLocationData(locationName);

        JSONObject location = (JSONObject) locationData.get(0);
        double paralele = (double) location.get("latitude");
        double meridijani = (double) location.get("longitude");

        String urlString = "https://api.open-meteo.com/v1/forecast?"
        + "latitude=" + paralele + "&longitude=" + meridijani + "&hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m&timezone=Europe%2FBerlin";

        try{
            HttpURLConnection kon = fetchApiResponse(urlString);
            if(kon.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }

            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(kon.getInputStream());
            while (scanner.hasNext()){
                resultJson.append(scanner.nextLine());
            }
            scanner.close();
            kon.disconnect();
            JSONParser parser = new JSONParser();
            JSONObject resultJsonObject = (JSONObject) parser.parse(String.valueOf(resultJson));

            JSONObject hourly = (JSONObject) resultJsonObject.get("hourly");

            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurrentTime(time);

            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");

            double temperature = (double) temperatureData.get(index);

            JSONArray weatherCode = (JSONArray) hourly.get("weathercode");
            String weatherCondition = convertWeatherCode((long) weatherCode.get(index));

            JSONArray relativeHumidity = (JSONArray) hourly.get("relativehumidity_2m");
            long humidity = (long) relativeHumidity.get(index);

            JSONArray windspeedData = (JSONArray) hourly.get("windspeed_10m");
            double windspeed = (double) windspeedData.get(index);

            JSONObject weatherData = new JSONObject();
            weatherData.put("temperature", temperature);
            weatherData.put("weatherCondition", weatherCondition);
            weatherData.put("humidity", humidity);
            weatherData.put("windspeed", windspeed);



        }catch(Exception e){e.printStackTrace();}
        return null;
    }

    public static JSONArray getLocationData(String locationName){
        locationName = locationName.replaceAll(" ", "+");
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + locationName + "&count=10&language=en&format=json";
        try{
            HttpURLConnection kon = fetchApiResponse(urlString);
            if(kon.getResponseCode() != 200){
                System.out.println("Error, can't connect to the API");
                return null;
            }else{
                StringBuilder resultJson = new StringBuilder();
                Scanner scanner = new Scanner(kon.getInputStream());

                while(scanner.hasNext()){
                    resultJson.append(scanner.nextLine());
                }

                scanner.close();
                kon.disconnect();

                JSONParser parser = new JSONParser();
                JSONObject resultJsonObject = (JSONObject) parser.parse(String.valueOf(resultJson));

                JSONObject hourly = (JSONObject) resultJsonObject.get("hourly");

                JSONArray time = (JSONArray) hourly.get("time");

                int index = findIndexOfCurrentTime(time);


                JSONArray locationData = (JSONArray) resultJsonObject.get("results");
                return locationData;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
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

    private static int findIndexOfCurrentTime(JSONArray timeList){
        String currentTime = getCurrentTime();

        for (int i = 0; i < timeList.size(); i++){
            String time = (String) timeList.get(i);
            if (time.equalsIgnoreCase(currentTime)){
                return i;
            }
        }

        return 0;
    }

    public static String getCurrentTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");

        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }

    private static String convertWeatherCode(long weatherCode){
        String weatherCondition = "";
        if(weatherCode == 0L){
            weatherCondition = "Clear";
        }else if(weatherCode <= 3L && weatherCode > 0L){
            weatherCondition = "Cloudy";
        }else if((weatherCode >= 51L && weatherCode <= 67L)
            || (weatherCode >= 80L && weatherCode <= 99L)){
            weatherCondition = "Rain";
        }else if(weatherCode >= 71L && weatherCode <= 77L){
            weatherCondition = "Snow";
        }

        return weatherCondition;
    }
}
