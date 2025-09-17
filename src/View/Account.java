package View;

import java.time.LocalTime;
import Backend.User;
import java.util.List;
import Backend.Post;
import Backend.Database; 


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Account {
    private User user; // Current logged-in user

    public Account(User user) {
        this.user = user;
    }

    public VBox buildAccountBody() {
        VBox total = new VBox(userProfile(), bBody());
        total.setAlignment(Pos.CENTER);
        total.setSpacing(10);
        return total;
    }

    private HBox userProfile() {
        Circle profileCircle = new Circle(15);
        profileCircle.getStyleClass().add("circle");

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

    private HBox bBody() {
        Button themeBtn = new Button("Theme"); // global theme button (can be removed if in top bar)
        themeBtn.setOnAction(e -> ThemeManager.toggleTheme(themeBtn.getScene()));
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
        // Close current account window
        Stage currentStage = (Stage) logoutBtn.getScene().getWindow();
        currentStage.close();

        // Open login window
        Stage loginStage = new Stage();
        new Login().start(loginStage);
        });


        HBox box = new HBox(themeBtn, logoutBtn);
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10, 0, 0, 0));

        return box;
    }
    private String getUserInitials() {
        if (user == null) return "GU"; // Guest User
        String first = (user.getFirstName() != null && !user.getFirstName().isEmpty())
                ? user.getFirstName().substring(0, 1).toUpperCase() : "";
        String last = (user.getLastName() != null && !user.getLastName().isEmpty())
                ? user.getLastName().substring(0, 1).toUpperCase() : "";
        return first + last;
    }
}
