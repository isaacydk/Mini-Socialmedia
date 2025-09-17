package View;

import javafx.scene.Scene;

public class ThemeManager {
    private static boolean darkMode = false; // start in dark mode

    // public static void applyTheme(Scene scene) {
    //     scene.getStylesheets().clear();
    //     if (darkMode) {
    //         scene.getStylesheets().add(ThemeManager.class.getResource("/css/Dark-theme.css").toExternalForm());
    //     } else {
    //         scene.getStylesheets().add(ThemeManager.class.getResource("/css/Light-theme.css").toExternalForm());
    //     }
    // }
    // public static void applyTheme(Scene scene) {
    //     scene.getStylesheets().clear();
    //     String path = darkMode ? "/css/Dark-theme.css" : "/css/Light-theme.css";

    //     java.net.URL url = ThemeManager.class.getResource(path);
    //     if (url == null) {
    //         // fallback if resources are packaged under /resources/css/...
    //         url = ThemeManager.class.getResource("/resources" + path);
    //     }

    //     if (url != null) {
    //         scene.getStylesheets().add(url.toExternalForm());
    //     } else {
    //         System.err.println("Theme CSS not found: " + path + " (checked /css and /resources/css)");
    //     }
    // }
    public static void applyTheme(Scene scene) {
        scene.getStylesheets().clear();
        
        // Load theme first, then component-specific styles
        String themePath = darkMode ? "/css/Dark-theme.css" : "/css/Light-theme.css";
        
        java.net.URL themeUrl = ThemeManager.class.getResource(themePath);
        if (themeUrl != null) {
            scene.getStylesheets().add(themeUrl.toExternalForm());
        }
        
        // Load Login.css only if needed (for login screen)
        // This prevents conflicts in the main app
    }

    public static void toggleTheme(Scene scene) {
        darkMode = !darkMode;
        applyTheme(scene);
    }
}
