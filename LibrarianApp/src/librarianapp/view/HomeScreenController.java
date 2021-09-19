package librarianapp.view ;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.* ;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.* ;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import librarianapp.* ;
import librarianapp.model.* ;
import librarianapp.resources.DataBaseController;


public class HomeScreenController extends Main implements Initializable
{
    public StackPane homeStackPane ;
    public AnchorPane rootAnchorPane, anchorPane ;
    public VBox vbox1 ;
    public Label  header ;
    public TableView<Book> tableView ;
    public TableColumn<Book, String> bookIDCol, titleCol, authorCol, isbnCol, categoryCol ;
    public ChoiceBox<String> choiceBox ;
    public TextField searchField ;
    public Button searchBtn ;

    private Button menuBtn ;
    private VBox menuVBox ;

    protected static Book selectedBook ;
    protected static TableView<Book> table ;

    private DataBaseController dataBaseController ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController() ;

            header.setText("WELCOME, " + Main.userAccount.getFirstName().toUpperCase());
            createMenu() ;

            initializeTable() ;
            initializeChoiceBox() ;
            table = tableView ;

            rootAnchorPane.setOnMouseClicked(mouseEvent ->
            {
                if(rootAnchorPane.getChildren().contains(menuVBox))
                {
                    rootAnchorPane.getChildren().remove(menuVBox) ;
                    rootAnchorPane.getChildren().add(menuBtn) ;
                }
            });

            searchField.setOnKeyPressed(keyEvent ->
            {
                switch (keyEvent.getCode())
                {
                    case ENTER:
                        search();
                        break ;

                    case BACK_SPACE: case DELETE:
                    if(searchField.getText().isBlank()) this.tableView.setItems(dataBaseController.getAllBooks()) ;
                }
            }) ;

            rootAnchorPane.setId("root_anchor_pane_style") ;
            header.setId("main_header_style") ;
            menuBtn.setId("menu_btn_style") ;
            searchField.setId("search_field_style") ;
            searchBtn.setId("search_edit_print_btn_style") ;
        }
        catch (Exception exception)
        {
            exception.printStackTrace() ;
        }
    }


    private void createMenu()
    {
        double BUTTON_WIDTH = 200.0 ;
        double BUTTON_HEIGHT = 30.0 ;

        // create the menuVBox and anchor it
        menuVBox = new VBox() ;
        menuVBox.setPrefWidth(BUTTON_WIDTH) ;
        menuVBox.setId("menu_vbox_style") ;
        menuVBox.setOpacity(0.8) ;
        AnchorPane.setTopAnchor(menuVBox, 0.0) ;
        AnchorPane.setBottomAnchor(menuVBox, 0.0) ;

        menuBtn = new Button("MENU") ;
        menuBtn.setOnAction(e ->
        {
            rootAnchorPane.getChildren().remove(menuBtn) ;
            rootAnchorPane.getChildren().add(menuVBox) ;
        });
        AnchorPane.setTopAnchor(menuBtn, 15.0);
        AnchorPane.setLeftAnchor(menuBtn, 5.0);

        Button homeBtn = new Button("HOME") ;
        homeBtn.setPrefWidth(BUTTON_WIDTH) ;
        homeBtn.setPrefHeight(BUTTON_HEIGHT) ;
        homeBtn.setId("menu_option_style") ;
        homeBtn.setOnAction(event ->
        {
            rootAnchorPane.getChildren().clear() ;
            rootAnchorPane.getChildren().addAll(homeStackPane, menuBtn) ;
        }) ;

        // create the buttons to be put in the menuVBox
        Button reservedBooksBtn = new Button("Reserved books") ;
        reservedBooksBtn.setPrefWidth(BUTTON_WIDTH) ;
        reservedBooksBtn.setPrefHeight(BUTTON_HEIGHT) ;
        reservedBooksBtn.setId("menu_option_style") ;
        reservedBooksBtn.setOnAction(event ->
        {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ReservedBooks_FXML.fxml")));

                rootAnchorPane.getChildren().clear() ;
                rootAnchorPane.getChildren().addAll(root, menuBtn) ;

                AnchorPane.setTopAnchor(root, 0.0);
                AnchorPane.setBottomAnchor(root, 0.0) ;
                AnchorPane.setRightAnchor(root, 0.0) ;
                AnchorPane.setLeftAnchor(root, 0.0) ;
            }
            catch (Exception e1) {
                System.out.println("\nExceptions in HomeScreenController.createMenu() -> \n" + e1) ;
                e1.printStackTrace() ;
            }
        }) ;

        Button issuedBooksBtn = new Button("Issued books") ;
        issuedBooksBtn.setPrefWidth(BUTTON_WIDTH) ;
        issuedBooksBtn.setPrefHeight(BUTTON_HEIGHT) ;
        issuedBooksBtn.setId("menu_option_style") ;
        issuedBooksBtn.setOnAction(e ->
        {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("IssuedBooks_FXML.fxml")));

                rootAnchorPane.getChildren().clear() ;
                rootAnchorPane.getChildren().addAll(root, menuBtn) ;

                AnchorPane.setTopAnchor(root, 0.0);
                AnchorPane.setBottomAnchor(root, 0.0) ;
                AnchorPane.setRightAnchor(root, 0.0) ;
                AnchorPane.setLeftAnchor(root, 0.0) ;
            }
            catch (Exception e2) {
                System.out.println("\nExceptions in HomeScreenController.createMenu() -> \n" + e2) ;
                e2.printStackTrace() ;
            }
        }) ;

        Button requestReturnBtn = new Button("Books requested to return") ;
        requestReturnBtn.setPrefWidth(BUTTON_WIDTH) ;
        requestReturnBtn.setPrefHeight(BUTTON_HEIGHT) ;
        requestReturnBtn.setId("menu_option_style") ;
        requestReturnBtn.setOnAction(e ->
        {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ReturnRequests_FXML.fxml")));

                rootAnchorPane.getChildren().clear() ;
                rootAnchorPane.getChildren().addAll(root, menuBtn) ;

                AnchorPane.setTopAnchor(root, 0.0);
                AnchorPane.setBottomAnchor(root, 0.0) ;
                AnchorPane.setRightAnchor(root, 0.0) ;
                AnchorPane.setLeftAnchor(root, 0.0) ;
            }
            catch (Exception e3) {
                System.out.println("\nExceptions in HomeScreenController.createMenu() -> \n" + e3) ;
                e3.printStackTrace() ;
            }
        }) ;

        Button allRecordsBtn = new Button("Book records") ;
        allRecordsBtn.setPrefWidth(BUTTON_WIDTH) ;
        allRecordsBtn.setPrefHeight(BUTTON_HEIGHT) ;
        allRecordsBtn.setId("menu_option_style") ;
        allRecordsBtn.setOnAction(e ->
        {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AllRecords_FXML.fxml")));

                rootAnchorPane.getChildren().clear() ;
                rootAnchorPane.getChildren().addAll(root, menuBtn) ;

                AnchorPane.setTopAnchor(root, 0.0);
                AnchorPane.setBottomAnchor(root, 0.0) ;
                AnchorPane.setRightAnchor(root, 0.0) ;
                AnchorPane.setLeftAnchor(root, 0.0) ;
            }
            catch (Exception e4) {
                System.out.println("\nExceptions in HomeScreenController.createMenu() -> \n" + e4) ;
                e4.printStackTrace() ;
            }
        }) ;

        Button editProfileBtn = new Button("Edit profile") ;
        editProfileBtn.setPrefWidth(BUTTON_WIDTH) ;
        editProfileBtn.setPrefHeight(BUTTON_HEIGHT) ;
        editProfileBtn.setId("menu_option_style") ;
        editProfileBtn.setOnAction(e ->
        {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EditProfile_FXML.fxml"))) ;

                rootAnchorPane.getChildren().clear() ;
                rootAnchorPane.getChildren().addAll(root, menuBtn) ;

                AnchorPane.setTopAnchor(root, 0.0);
                AnchorPane.setBottomAnchor(root, 0.0) ;
                AnchorPane.setRightAnchor(root, 0.0) ;
                AnchorPane.setLeftAnchor(root, 0.0) ;
            }
            catch (Exception e5) {
                System.out.println("\nExceptions in HomeScreenController.createMenu() -> \n" + e5) ;
                e5.printStackTrace() ;
            }
        });

        Button signOutBtn = new Button("Sign Out") ;
        signOutBtn.setPrefWidth(BUTTON_WIDTH) ;
        signOutBtn.setPrefHeight(BUTTON_HEIGHT) ;
        signOutBtn.setId("menu_option_style") ;
        signOutBtn.setOnAction(e ->
        {
           try
           {
               Stage window = (Stage) this.rootAnchorPane.getScene().getWindow() ;

               Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignInPage_FXML.fxml"))) ;

               Scene scene = new Scene(root) ;
               scene.getStylesheets().add("/librarianapp/resources/css/LoginPageStyleSheet.css") ;

               Stage stage = new Stage() ;
               stage.setTitle("L I B R A R I A N   A P P L I C A T I O N  -  S I G N  I N");
               stage.setScene(scene) ;
               stage.setResizable(false) ;

               stage.show() ;
               window.close() ;
           }
           catch (Exception e6) {
               System.out.println("\nExceptions in HomeScreenController.createMenu() -> \n" + e6) ;
               e6.printStackTrace() ;
           }

        }) ;

        // fill the menuVBox with its items
        menuVBox.getChildren().addAll(homeBtn, reservedBooksBtn, issuedBooksBtn, requestReturnBtn, allRecordsBtn, editProfileBtn, signOutBtn) ;

        // add the menuBtn to the anchor pane
        rootAnchorPane.getChildren().add(menuBtn) ;
    }


    private void initializeTable()
    {
        // first initialize all the columns
        bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID")) ;
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title")) ;
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author")) ;
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn")) ;
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category")) ;

        tableView.setItems(dataBaseController.getAllBooks()) ;
        tableView.setOnMouseClicked(mouseEvent ->
        {
            if(rootAnchorPane.getChildren().contains(menuVBox))
            {
                rootAnchorPane.getChildren().remove(menuVBox) ;
                rootAnchorPane.getChildren().add(menuBtn) ;
            }

            if(mouseEvent.getClickCount() == 2)
            {
                selectedBook = tableView.getSelectionModel().getSelectedItem();

                if (selectedBook != null)
                {
                    try
                    {
                        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("BookView_FXML.fxml")));

                        Scene scene = new Scene(pane);
                        scene.getStylesheets().add("/librarianapp/resources/css/InfoViewStyleSheet.css");

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    }
                    catch (Exception e)
                    {
                        System.out.println("\nExceptions HomeScreenController.initializeTable() -> " + e);
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void initializeChoiceBox()
    {
        ObservableList<String> list = FXCollections.observableArrayList() ;
        list.addAll("by book ID", "by title", "by author", "by ISBN") ;

        choiceBox.setItems(list) ;
        choiceBox.setValue(list.get(0)) ;
    }


    public void search()
    {
        String fieldName ;

        if(!searchField.getText().isBlank())
        {
            switch(choiceBox.getValue().toLowerCase())
            {
                case "by title":
                    fieldName = "Title" ;
                    break ;
                case "by author":
                    fieldName = "Author" ;
                    break ;
                case "by isbn":
                    fieldName = "ISBN" ;
                    break ;
                default:
                    fieldName = "BookID" ;
                    break ;
            }

            tableView.setItems(dataBaseController.getFromBooks(fieldName, searchField.getText())) ;
        }
    }
}