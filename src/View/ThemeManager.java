package View;

import javafx.scene.Scene;
import java.net.URL;

public class ThemeManager {
    private static boolean darkMode = false;

    public static void applyTheme(Scene scene) {
        scene.getStylesheets().clear();
        
        String themePath = darkMode ? "/css/Dark-theme.css" : "/css/Light-theme.css";
        
        URL themeUrl = ThemeManager.class.getResource(themePath);
        if (themeUrl != null) {
            scene.getStylesheets().add(themeUrl.toExternalForm());
        }
        
    }

    public static void toggleTheme(Scene scene) {
        darkMode = !darkMode;
        applyTheme(scene);
    }
}
