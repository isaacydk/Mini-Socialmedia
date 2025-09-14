package View;

import Backend.Database;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Application {
    private Stage primaryStage;
    private Scene scene1; // Signup scene
    private Scene scene2; // Login scene
    private static final double wLabel = 100;

    // Store user values in variables (in memory)
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    // Input fields for signup
    private TextField tFirstName, tLastName, tUsername, tEmail;
    private PasswordField tPassword, tCpassword;
    
    // Input fields for login
    private TextField tLoginUsername;
    private PasswordField tLoginPassword;

    // Database instance
    private Database db = new Database();

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        scene1 = new Scene(signupPage(), 800, 700);
        scene2 = new Scene(loginPage(), 800, 700);

        primaryStage.setTitle("Mini Social Media - Login/Signup");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    // SIGNUP PAGE
    private Region signupPage() {
        tFirstName = new TextField();
        tLastName = new TextField();
        tUsername = new TextField();
        tEmail = new TextField();
        tPassword = new PasswordField();
        tCpassword = new PasswordField();

        VBox total = new VBox(
            sHead("Welcome"),
            getRow("First Name", tFirstName),
            getRow("Last Name", tLastName),
            getRow("Username", tUsername),
            getRow("Email", tEmail),
            getRow("Password", tPassword),
            getRow("Confirm Password", tCpassword),
            getSignupButton(),
            getSwitchToLogin()
        );
        total.setSpacing(10);
        total.setAlignment(Pos.CENTER_LEFT);
        total.setPadding(new Insets(20));

        total.getStylesheets().add(getClass().getResource("/css/Login.css").toExternalForm());

        return total;
    }

    private Node getSignupButton() {
        Button button = new Button("Sign up");
        button.setOnAction(evt -> handleSignup());
        HBox hButton = new HBox(button);
        hButton.setAlignment(Pos.CENTER);
        return hButton;
    }

    private Node getSwitchToLogin() {
        Label logIn = new Label("Already have an account? ");
        Button bLog = new Button("Log in");
        bLog.setOnAction(evt -> primaryStage.setScene(scene2));
        HBox log = new HBox(logIn, bLog);
        log.setAlignment(Pos.CENTER);
        return log;
    }

    private void handleSignup() {
        if (!tPassword.getText().equals(tCpassword.getText())) {
            showAlert(Alert.AlertType.ERROR, "Signup Error", "Passwords do not match!");
            return;
        }

        // Store values in variables
        firstName = tFirstName.getText();
        lastName = tLastName.getText();
        username = tUsername.getText();
        email = tEmail.getText();
        password = tPassword.getText();

        boolean success = db.insertUser(firstName, lastName, username, email, password);
        if (!success) {
            showAlert(Alert.AlertType.ERROR, "Signup Error", "Failed to register user. Try again.");
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Signup Success",
                "User registered successfully!\nWelcome " + firstName + " " + lastName);

        // Switch to login page
        primaryStage.setScene(scene2);
    }

    // LOGIN PAGE
    private Region loginPage() {
        tLoginUsername = new TextField();
        tLoginPassword = new PasswordField();

        VBox total = new VBox(
            sHead("Log In"),
            getRow("Username", tLoginUsername),
            getRow("Password", tLoginPassword),
            getLoginButton(),
            getSwitchToSignup()
        );
        total.setSpacing(10);
        total.setAlignment(Pos.CENTER_LEFT);
        total.setPadding(new Insets(20));

        total.getStylesheets().add(getClass().getResource("/css/Login.css").toExternalForm());

        return total;
    }

    private Node getLoginButton() {
        Button button = new Button("Log in");
        button.setOnAction(evt -> handleLogin());
        HBox hButton = new HBox(button);
        hButton.setAlignment(Pos.CENTER);
        return hButton;
    }

    private Node getSwitchToSignup() {
        Label signUp = new Label("Don't have an account? ");
        Button bSign = new Button("Sign up");
        bSign.setOnAction(evt -> primaryStage.setScene(scene1));
        HBox sign = new HBox(signUp, bSign);
        sign.setAlignment(Pos.CENTER);
        return sign;
    }

    private void handleLogin() {
        String inputUser = tLoginUsername.getText();
        String inputPass = tLoginPassword.getText();

        if (inputUser.equals(username) && inputPass.equals(password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Success",
                    "Welcome back, " + firstName + " " + lastName + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password!");
        }
    }

    // Helper methods
    private Region sHead(String title) {
        Label header = new Label(title);
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        HBox head = new HBox(header);
        head.setAlignment(Pos.CENTER);
        return head;
    }

    private Node getRow(String labelText, TextField field) {
        Label label = new Label(labelText);
        label.setMinWidth(wLabel);
        HBox row = new HBox(label, field);
        row.setSpacing(10);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
