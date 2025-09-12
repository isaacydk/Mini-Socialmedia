package View;



//i use this to check the database, change it if you finish yours.

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        // Root layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(40, 20, 40, 20));

        // Title
        Label title = new Label("Login");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setStyle("-fx-text-fill: #1E90FF;"); // Blue
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Center form
        VBox formBox = new VBox(15);
        formBox.setPadding(new Insets(30, 200, 30, 200));
        formBox.setAlignment(Pos.CENTER);

        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Button loginBtn = new Button("Login");
        loginBtn.setPrefHeight(40);
        loginBtn.setPrefWidth(200);
        loginBtn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");

        formBox.getChildren().addAll(username, password, loginBtn);
        root.setCenter(formBox);

        // Bottom link
        Hyperlink createAcc = new Hyperlink("Don't have an account? Create new one");
        createAcc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        BorderPane.setAlignment(createAcc, Pos.CENTER);
        root.setBottom(createAcc);

        // Scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
