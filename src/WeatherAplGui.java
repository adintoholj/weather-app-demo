import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAplGui extends JFrame{
    private JSONObject weatherData;

    public WeatherAplGui(){
        super("Vremenska prognoza");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents(){
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(30,30,350,45);
        searchTextField.setFont(new Font("Arial", Font.PLAIN, 24));
        add(searchTextField);

        JLabel weatherConditionIcon = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionIcon.setBounds(0, 80, 450, 217);
        add(weatherConditionIcon);

        JLabel weatherConditionText = new JLabel("18 °C");
        weatherConditionText.setBounds(0, 340, 450, 40);
        weatherConditionText.setFont(new Font("Arial", Font.BOLD, 36));
        weatherConditionText.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionText);

        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 390, 450, 40);
        weatherConditionDesc.setFont(new Font("Arial", Font.PLAIN, 30));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        JLabel humidityIcon = new JLabel(loadImage("src/assets/humidity.png"));
        humidityIcon.setBounds(40,470,70,70);
        add(humidityIcon);

        JLabel humidityText = new JLabel("<html><b>Humidity:</b> 100%</html>");
        humidityText.setBounds(120, 470, 85, 55);
        humidityText.setFont(new Font("Arial", Font.PLAIN, 15));
        add(humidityText);

        JLabel windSpeedIcon = new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedIcon.setBounds(240, 470, 80, 70);
        add(windSpeedIcon);
        JLabel windSpeedText = new JLabel("<html><b>Windspeed:</b><br> 18 km/h</html>");
        windSpeedText.setBounds(330, 470, 105, 55);
        windSpeedText.setFont(new Font("Arial", Font.PLAIN,15));
        add(windSpeedText);

        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        searchButton.setBounds(385,30,47,45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String userInput = searchTextField.getText();

                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                weatherData = WeatherApp.getWeatherData(userInput);


                String weatherCondition = (String) weatherData.get("weather_condition");

                switch(weatherCondition){
                    case "Clear":
                        weatherConditionIcon.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionIcon.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionIcon.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionIcon.setIcon(loadImage("src/assets/snow.png"));
                        break;
                }

                double temperature = (double) weatherData.get("temperature");
                weatherConditionText.setText(temperature + "C");

                weatherConditionDesc.setText(weatherCondition);

                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity<b>" + humidity + "%</html>");

                double windspeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>Windspeed<b>" + windspeed + "km/h</html>");
            }
        });
        add(searchButton);
    }

    private ImageIcon loadImage(String resourcePath){
        try{
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("Could not find resource");
        return null;
    }
}
