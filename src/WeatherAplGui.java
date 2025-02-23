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
