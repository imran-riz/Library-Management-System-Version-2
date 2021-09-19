package librarianapp.view;

import javafx.stage.* ;
import javafx.scene.* ;
import javafx.scene.layout.* ;
import javafx.scene.control.* ;
import javafx.geometry.* ;

public class MessageBox
{
    public void show(String message, String title)
    {
        Stage primaryStage = new Stage() ;
        primaryStage.setTitle(title) ;

        Label lbl = new Label() ;
        lbl.setText(message) ;
        lbl.setPadding(new Insets(5)) ;

        Button btnOK = new Button() ;
        btnOK.setText("OK") ;
        btnOK.setOnAction(e -> primaryStage.close()) ;

        VBox vbox = new VBox(20) ;
        vbox.getChildren().addAll(lbl, btnOK) ;
        vbox.setAlignment(Pos.CENTER) ;
        vbox.setPadding(new Insets(15, 5, 15, 5)) ;

        Scene scene = new Scene(vbox) ;

        primaryStage.setScene(scene) ;
        primaryStage.setResizable(false) ;
        primaryStage.initModality(Modality.APPLICATION_MODAL) ;
        primaryStage.showAndWait() ;
    }
}