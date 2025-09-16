package View;

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
    public VBox buildAccountBody() {
        VBox total = new VBox(userProfile(), bBody());
        total.setAlignment(Pos.CENTER);
        total.setSpacing(10);
        return total;
    }

    private HBox userProfile() {
        Circle profileCircle = new Circle(15);
        profileCircle.getStyleClass().add("circle");

        Text userInitial = new Text("TN");
        userInitial.getStyleClass().add("text");

        StackPane profilePic = new StackPane(profileCircle, userInitial);

        Text generalName = new Text("Temporary Name");
        generalName.getStyleClass().add("small");
        Text userName = new Text("Tempo");
        userName.getStyleClass().add("large");

        VBox names = new VBox(userName, generalName);

        HBox hbox = new HBox(profilePic, names);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(25, 5, 5, 50));

        return hbox;
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
}
