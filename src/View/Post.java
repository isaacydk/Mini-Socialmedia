package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Post {
    public VBox buildPostBody() {
        VBox postBox = new VBox();
        postBox.setSpacing(10);
        postBox.setPadding(new Insets(10));

        TextArea inputArea = new TextArea();
        inputArea.setPromptText("Write your post here...");
        inputArea.setWrapText(true);
        inputArea.setPrefRowCount(5);
        inputArea.getStyleClass().add("temporary");

        Button postBtn = new Button("Post");
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> inputArea.clear());


        HBox buttonBar = new HBox(10, postBtn, cancelBtn);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);

        postBox.getChildren().addAll(inputArea, buttonBar);
        // postBox.getStyleClass().add("border-one");

        return postBox;
    }
}
