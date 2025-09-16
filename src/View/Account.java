package View;

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

public class Account extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage){
        Scene scene1 = new Scene(mainCondition(primaryStage), 500, 400);
        ThemeManager.applyTheme(scene1);
        primaryStage.setScene(scene1);
        primaryStage.setTitle("Account Manager");
        primaryStage.show();
    }
    public Region mainCondition(Stage stage){
        VBox total = new VBox(topBar(stage), body());
        total.setSpacing(10);
        total.setPadding(new Insets(5));

        return total;
    }
    private VBox body(){
        VBox total = new VBox(userProfile(),bBody());
        total.setAlignment(Pos.CENTER);
        return total;
    }
    private HBox userProfile() {
        Circle profileCircle = new Circle(15);
        profileCircle.getStyleClass().add("circle");

        Text userInitial = new Text("TN");
        userInitial.getStyleClass().add("text");
        
        StackPane profilePic = new StackPane(profileCircle, userInitial);
        
        HBox hbox = new HBox(profilePic, names());
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(25,5,5,50));

        return hbox;
    }
    private HBox bBody(){
        HBox total = new HBox(bTheme(), bLogout());
        total.setAlignment(Pos.CENTER);
        total.setSpacing(20);
        total.setPadding(new Insets(10,0,0,0));
        
        return total;
    }
    //undecided
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
        return logout;
    }
    
    private VBox names(){
        Text generalName = new Text("Temporary Name");
        generalName.getStyleClass().add("small");

        Text userName = new Text("Tempo");
        userName.getStyleClass().add("large");
        
        VBox total = new VBox(userName, generalName);
        return total;
    }

    //SIDE/TOP PANEL

    private Region topBar(Stage primaryStage){
        Hyperlink home = new Hyperlink("Home");
        Hyperlink post = new Hyperlink("Post");
        Hyperlink account = new Hyperlink("Account");

        home.setOnAction(e -> {
            Scene homeScene = new Scene(new Home().mainCondition(primaryStage), 500, 400);
            homeScene.getStylesheets().add(this.getClass().getResource("/Dark-theme.css").toExternalForm());
            primaryStage.setScene(homeScene);
        });

        post.setOnAction(e -> {
            Scene postScene = new Scene(new Post().mainCondition(primaryStage), 500, 400);
            postScene.getStylesheets().add(this.getClass().getResource("/Dark-theme.css").toExternalForm());
            primaryStage.setScene(postScene);
        });

        account.setOnAction(e -> {
            Scene accountScene = new Scene(new Account().mainCondition(primaryStage), 500, 400);
            accountScene.getStylesheets().add(this.getClass().getResource("/Dark-theme.css").toExternalForm());
            primaryStage.setScene(accountScene);
        });


        Circle profileCircle = new Circle(15);
        profileCircle.getStyleClass().add("circle");
        
        Text userInitial = new Text("TU");
        userInitial.getStyleClass().add("text");
        
        StackPane profilePic = new StackPane(profileCircle, userInitial);
        
        HBox total = new HBox(profilePic, home, post, account);
        total.setSpacing(10);
        total.getStyleClass().add("top-bar");
        total.setAlignment(Pos.TOP_LEFT);

        return total;
    }
    
}
