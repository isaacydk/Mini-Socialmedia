package View;

import View.MainApp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Backend.Database;
import Backend.User;

import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Application {
    private Stage primaryStage;
    private Scene scene1; //signup scene
    private Scene scene2; //login scene
    private static final double wLabel = 100;

    // Store user values in variables (in memory)
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    private TextField tFirstName, tLastName, tUsername, tEmail;
    private PasswordField tPassword, tCpassword;
    
    private TextField tLoginUsername;
    private PasswordField tLoginPassword;

    private Database db = new Database();

    public void start(Stage stage) {
        primaryStage = stage;
        scene1 = new Scene(signupPage(), 800, 700);
        scene2 = new Scene(loginPage(), 800, 700);

        primaryStage.setTitle("Mini Social Media - Login/Signup");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    //SIGNUP PAGE

    private Region signupPage() {
        tFirstName = new TextField();
        tLastName = new TextField();
        tUsername = new TextField();
        tEmail = new TextField();
        tPassword = new PasswordField();
        tCpassword = new PasswordField();

        VBox total = new VBox(
            sHead("Welcome"),
            getRow("First Name", tFirstName, star()),
            getRow("Last Name", tLastName, none()),
            getRow("Username", tUsername, star()),
            getRow("Email", tEmail, star()),
            getRow("Password", tPassword, star()),
            getRow("Confirm Password", tCpassword, star()),
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
        Button Buttonabc = new Button("Sign up");
        Buttonabc.getStyleClass().add("Button");
        Buttonabc.setOnAction(evt -> handleSignup());
        HBox hButton = new HBox(Buttonabc);
        hButton.setAlignment(Pos.CENTER);
        return hButton;
    }

    private Node getSwitchToLogin() {
        Label t = new Label("* - required field");
        Label logIn = new Label("Already have an account? ");
        Hyperlink hyperlinkabc = new Hyperlink("Log in");
        hyperlinkabc.setStyle("-fx-underline: false; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-text-fill: #ffffff;");
        hyperlinkabc.setOnAction(evt -> primaryStage.setScene(scene2));
        HBox l = new HBox(t, logIn, hyperlinkabc);
        VBox log = new VBox(t, l);
        l.setAlignment(Pos.CENTER);
        log.setAlignment(Pos.CENTER);
        return log;
    }

    // modify it to not sign in if the username, first name, email, and password is empty

    private void handleSignup() {
        if (!tPassword.getText().equals(tCpassword.getText())) {
            showAlert(Alert.AlertType.ERROR, "Signup Error", "Passwords do not match!");
            return;
        }
        firstName = tFirstName.getText();
        lastName = tLastName.getText();
        username = tUsername.getText();
        email = tEmail.getText();
        password = tPassword.getText();

        if (firstName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Signup Error", "Please fill in all required fields!");
        return;
        }

        boolean success = db.insertUser(firstName, lastName, username, email, password);
        if (success) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

            try (Connection conn = db.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, username);
                pstmt.setString(2, password);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    User newUser = new User();
                    newUser.setId(rs.getInt("id"));
                    newUser.setFirstName(rs.getString("firstName"));
                    newUser.setLastName(rs.getString("lastName"));
                    newUser.setUsername(rs.getString("username"));
                    newUser.setEmail(rs.getString("email"));
                    newUser.setPassword(rs.getString("password")); 

                    MainApp homeApp = new MainApp(newUser);
                    Stage homeStage = new Stage();
                    homeApp.start(homeStage);

                    primaryStage.close();
                }
            }catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "Could not connect to the database.");
            }
            if (!success) {
                showAlert(Alert.AlertType.ERROR, "Signup Error", "Failed to register user. Try again.");
                return;
            }
            showAlert(Alert.AlertType.INFORMATION, "Signup Success",
                    "User registered successfully!\nWelcome " + firstName + " " + lastName);

            primaryStage.setScene(scene2);
        
        }
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
        Button Buttonabc = new Button("Log in");
        Buttonabc.getStyleClass().add("Button");
        Buttonabc.setOnAction(evt -> handleLogin());
        HBox hButton = new HBox(Buttonabc);
        hButton.setAlignment(Pos.CENTER);
        return hButton;
    }

    private Node getSwitchToSignup() {
        Label signUp = new Label("Don't have an account? ");
        Hyperlink hyperlinkabc = new Hyperlink("Sign up");
        hyperlinkabc.setStyle("-fx-underline: false; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-text-fill: #ffffff;");
        hyperlinkabc.setOnAction(evt -> primaryStage.setScene(scene1));
        HBox total = new HBox(signUp, hyperlinkabc);
        total.setAlignment(Pos.CENTER);
        return total;
    }

    private void handleLogin() {
        String inputUser = tLoginUsername.getText();
        String inputPass = tLoginPassword.getText();

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = db.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, inputUser);
            pstmt.setString(2, inputPass);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User loggedInUser = new User();
                loggedInUser.setId(rs.getInt("id"));
                loggedInUser.setFirstName(rs.getString("firstName"));
                loggedInUser.setLastName(rs.getString("lastName"));
                loggedInUser.setUsername(rs.getString("username"));
                loggedInUser.setEmail(rs.getString("email"));
                loggedInUser.setPassword(rs.getString("password")); // if you store it

                showAlert(Alert.AlertType.INFORMATION, "Login Success",
                        "Welcome back, " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + "!");

                MainApp homeApp = new MainApp(loggedInUser);
                Stage homeStage = new Stage();
                homeApp.start(homeStage);

                primaryStage.close();

            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not connect to the database.");
        }
    }

    // Helper methods
    private Region sHead(String title) {
        Label header = new Label(title);
        header.getStyleClass().add("header");
        HBox head = new HBox(header);
        head.setAlignment(Pos.CENTER);
        return head;
    }

    private Node getRow(String labelText, TextField field, Text t) {
        Label label = new Label(labelText);
        label.setMinWidth(wLabel);
        HBox row = new HBox(label, field, t);
        row.setSpacing(10);
        row.setAlignment(Pos.CENTER);
        return row;
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
    private Text star() {
        Text star = new Text("*");
        star.setStyle("-fx-fill: red;");
        return star;
    }
    private Text none() {
        Text none = new Text(" ");
        none.setStyle("-fx-fill: transparent;");
        return none;
    }

    public static void main(String[] args) {
        launch();
    }

    public Parent mainCondition(Stage stage) {
        throw new UnsupportedOperationException("Unimplemented method 'mainCondition'");
    }
}
