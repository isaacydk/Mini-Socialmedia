package View;

import Backend.User;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Account extends Application {

    private User user; // Current logged-in user

    public Account() {}

    public Account(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene1 = new Scene(mainCondition(primaryStage), 500, 400);
        ThemeManager.applyTheme(scene1);
        primaryStage.setScene(scene1);
        primaryStage.setTitle("Account Manager");
        primaryStage.show();
    }

    public Region mainCondition(Stage stage) {
        VBox total = new VBox(topBar(stage), body());
        total.setSpacing(10);
        total.setPadding(new Insets(5));
        return total;
    }

    private VBox body() {
        VBox total = new VBox(userProfile(), bBody());
        total.setAlignment(Pos.CENTER);
        return total;
    }

    private HBox userProfile() {
        Circle profileCircle = new Circle(15);
        profileCircle.getStyleClass().add("circle");

        // Generate initials from user
        String initials = getUserInitials();
        Text userInitial = new Text(initials);
        userInitial.getStyleClass().add("text");

        StackPane profilePic = new StackPane(profileCircle, userInitial);

        HBox hbox = new HBox(profilePic, names());
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(25, 5, 5, 50));

        return hbox;
    }

    private HBox bBody() {
        HBox total = new HBox(bTheme(), bLogout());
        total.setAlignment(Pos.CENTER);
        total.setSpacing(20);
        total.setPadding(new Insets(10, 0, 0, 0));

        return total;
    }

    private Button bTheme() {
        Button theme = new Button("Theme");

        theme.setOnAction(e -> {
            Scene scene = theme.getScene(); // get the current scene
            if (scene != null) {
                ThemeManager.toggleTheme(scene);
            }
        });

        return theme;
    }

    private Button bLogout() {
        Button logout = new Button("Logout");

        logout.setOnAction(e -> {
            // Back to login
            Scene loginScene = new Scene(new Login().mainCondition(new Stage()), 500, 400);
            loginScene.getStylesheets().add(this.getClass().getResource("/css/Dark-theme.css").toExternalForm());
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setScene(loginScene);
        });

        return logout;
    }

    private VBox names() {
        // Use actual user details
        String fullName = (user != null) ? user.getFirstName() + " " + user.getLastName() : "Guest User";
        String username = (user != null) ? "@" + user.getUsername() : "@guest";

        Text userName = new Text(username);
        userName.getStyleClass().add("large");

        Text generalName = new Text(fullName);
        generalName.getStyleClass().add("small");

        VBox total = new VBox(userName, generalName);
        return total;
    }

    private Region topBar(Stage primaryStage) {
        Hyperlink home = new Hyperlink("Home");
        Hyperlink post = new Hyperlink("Post");
        Hyperlink account = new Hyperlink("Account");

        home.setOnAction(e -> {
            Scene homeScene = new Scene(new Home(user).mainCondition(primaryStage), 500, 400);
            homeScene.getStylesheets().add(this.getClass().getResource("/css/Dark-theme.css").toExternalForm());
            primaryStage.setScene(homeScene);
        });

        post.setOnAction(e -> {
            Scene postScene = new Scene(new PostView(user).mainCondition(primaryStage), 500, 400);
            postScene.getStylesheets().add(this.getClass().getResource("/css/Dark-theme.css").toExternalForm());
            primaryStage.setScene(postScene);
        });

        account.setOnAction(e -> {
            Scene accountScene = new Scene(new Account(user).mainCondition(primaryStage), 500, 400);
            accountScene.getStylesheets().add(this.getClass().getResource("/css/Dark-theme.css").toExternalForm());
            primaryStage.setScene(accountScene);
        });

        Circle profileCircle = new Circle(15);
        profileCircle.getStyleClass().add("circle");

        String initials = getUserInitials();
        Text userInitial = new Text(initials);
        userInitial.getStyleClass().add("text");

        StackPane profilePic = new StackPane(profileCircle, userInitial);

        HBox total = new HBox(profilePic, home, post, account);
        total.setSpacing(10);
        total.getStyleClass().add("top-bar");
        total.setAlignment(Pos.TOP_LEFT);

        return total;
    }

    // Helper to generate initials from first & last name
    private String getUserInitials() {
        if (user == null) return "GU"; // Guest User
        String first = (user.getFirstName() != null && !user.getFirstName().isEmpty())
                ? user.getFirstName().substring(0, 1).toUpperCase() : "";
        String last = (user.getLastName() != null && !user.getLastName().isEmpty())
                ? user.getLastName().substring(0, 1).toUpperCase() : "";
        return first + last;
    }
}
