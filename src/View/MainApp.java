package View;

import Backend.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainApp extends Application {
    private User user;           // currently logged-in user
    private VBox contentBox;     // placeholder for page body

    public MainApp(User user){
        this.user = user;
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(mainCondition(), 500, 400);
        ThemeManager.applyTheme(scene);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Social Media App");
        primaryStage.show();
    }

    private Region mainCondition() {
        VBox root = new VBox();
        root.setSpacing(10);

        // Top bar (constant)
        HBox topBar = createTopBar();

        // Content placeholder (start with Home)
        contentBox = new VBox();
        contentBox.getChildren().add(new Home(user).buildHomeBody());

        root.getChildren().addAll(contentBox);
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true); // stretch VBox to width
        scroll.setPannable(true);   // allow mouse drag scrolling
        scroll.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
        scroll.getStyleClass().add("scroll-pane");

        VBox total = new VBox(topBar, scroll);
        return total;
    }

    private HBox createTopBar() {
        Hyperlink home = new Hyperlink("Home");
        Hyperlink post = new Hyperlink("Post");
        Hyperlink account = new Hyperlink("Account");

        // Navigation (swap VBox body)
        home.setOnAction(e -> contentBox.getChildren().setAll(new Home(user).buildHomeBody()));
        post.setOnAction(e -> contentBox.getChildren().setAll(new PostView(user).buildPostBody(contentBox)));
        account.setOnAction(e -> contentBox.getChildren().setAll(new Account(user).buildAccountBody()));

        // Profile icon
        Circle profileCircle = new Circle(15);
        profileCircle.getStyleClass().add("circle");

        String initials = getUserInitials();  // ✅ fixed (no "new")
        Text userInitial = new Text(initials);
        userInitial.getStyleClass().add("text");

        StackPane profilePic = new StackPane(profileCircle, userInitial);

        HBox topBar = new HBox(profilePic, home, post, account);
        topBar.setSpacing(10);
        // topBar.getStyleClass().add("top-bar");
        topBar.setStyle("-fx-border-color: transparent transparent #218c5a transparent; -fx-border-width: 0 0 2px 0; -fx-padding: 15 25 15 25;");
        topBar.setAlignment(Pos.TOP_LEFT);

        return topBar;
    }

    private String getUserInitials() {
        if (user == null) return "GU"; // Guest User
        String first = (user.getFirstName() != null && !user.getFirstName().isEmpty())
                ? user.getFirstName().substring(0, 1).toUpperCase() : "";
        String last = (user.getLastName() != null && !user.getLastName().isEmpty())
                ? user.getLastName().substring(0, 1).toUpperCase() : "";
        return first + last;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
