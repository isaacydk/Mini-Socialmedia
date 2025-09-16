package View;

import javafx.scene.Scene;

public class ThemeManager {
    private static boolean darkMode = false; // start in dark mode

    public static void applyTheme(Scene scene) {
        scene.getStylesheets().clear();
        if (darkMode) {
            scene.getStylesheets().add(ThemeManager.class.getResource("/css/Dark-theme.css").toExternalForm());
        } else {
            scene.getStylesheets().add(ThemeManager.class.getResource("/css/Light-theme.css").toExternalForm());
        }
    }

    public static void toggleTheme(Scene scene) {
        darkMode = !darkMode;
        applyTheme(scene);
    }
}
