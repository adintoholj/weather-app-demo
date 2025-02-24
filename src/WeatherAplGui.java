import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAplGui extends JFrame{
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

        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        searchButton.setBounds(385,30,47,45);
        add(searchButton);

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
