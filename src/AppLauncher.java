import javax.swing.*;

public class AppLauncher{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable (){
            @Override
            public void run(){
                new WeatherAplGui().setVisible(true);
//                System.out.println(WeatherApp.getCurrentTime());
            }
        });
    }
}