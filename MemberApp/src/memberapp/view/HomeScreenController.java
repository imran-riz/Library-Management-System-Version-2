package memberapp.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable ;
import javafx.scene.* ;
import javafx.scene.control.* ;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.* ;
import javafx.stage.Modality;
import javafx.stage.Stage;

import memberapp.resources.DataBaseController;
import memberapp.Main;
import memberapp.model.Book;

import java.io.IOException;
import java.net.URL ;
import java.util.Objects;
import java.util.ResourceBundle ;


public class HomeScreenController implements Initializable
{
    public AnchorPane mainAnchorPane, homeAnchorPane ;
    public BorderPane borderPane;
    public VBox btnVBox ;
    public HBox searchHBox ;
    public ChoiceBox<String> choiceBox ;
    public TableView<Book> tableView ;
    public TableColumn<Book, String> bookIDCol, titleCol, authorCol, copiesAvailableCol ;
    public TreeView<String> treeView ;
    public Label mainHeader ;
    public TextField searchField ;
    public ImageView imageView ;
    public Button signOutBtn, searchBtn ;

    private Parent reservedBooksLayout, issuedBooksLayout, requestedToReturnLayout, editProfileLayout ;

    private DataBaseController dataBaseController ;

    private final MessageBox messageBox = new MessageBox() ;
    private final ConfirmationBox confirmationBox = new ConfirmationBox() ;

    public static Book selectedBook ;
    public static TableView<Book> table ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController();
            table = tableView;

            mainHeader.setText("WELCOME, " + Main.userAccount.getFirstName().toUpperCase());

            initializeChoiceBox();
            initializeTreeView();
            initializeTableView();

            searchField.setOnKeyPressed(keyEvent ->
            {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    search();
                } else if (keyEvent.getCode() == KeyCode.BACK_SPACE || keyEvent.getCode() == KeyCode.DELETE) {
                    if (searchField.getText().isBlank())
                        this.tableView.setItems(dataBaseController.getAllBooks());
                }
            });

            borderPane.getStyleClass().add("borderpane");      // this is done as the BorderPane does not have any additional properties
            mainHeader.setId("header_style");
            searchBtn.setId("custom_btn_style");
            signOutBtn.setId("sign_out_btn_style");
            homeAnchorPane.setId("center_anchor_pane");
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }


    private void initializeChoiceBox()
    {
        choiceBox.getItems().add("By Book ID") ;
        choiceBox.getItems().add("By Title") ;
        choiceBox.getItems().add("By Author") ;
        choiceBox.getItems().add("By ISBN") ;

        choiceBox.setValue("By Book ID") ;
    }


    private void initializeTreeView()
    {
        TreeItem<String> rootItem, homeItem, reservedBooksItem, issuedBooksItem, requestedToReturnItem, editProfItem ;

        rootItem = new TreeItem<>() ;
        rootItem.setExpanded(true) ;

        homeItem = createBranch("HOME", rootItem) ;
        reservedBooksItem = createBranch("Reserved Books", rootItem) ;
        issuedBooksItem = createBranch("Issued Books", rootItem) ;
        requestedToReturnItem = createBranch("Pending Returns", rootItem) ;
        editProfItem = createBranch("Edit Profile", rootItem) ;

        treeView.setRoot(rootItem) ;
        treeView.setShowRoot(false) ;
        treeView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) ->
                {
                    if(newValue != null)
                    {
                        switch(newValue.getValue())
                        {
                            case "HOME":
                                mainHeader.setText("WELCOME, " + Main.userAccount.getFirstName().toUpperCase()) ;
                                this.borderPane.setCenter(homeAnchorPane) ;
                                break ;

                            case "Reserved Books":
                                try
                                {
                                    if(reservedBooksLayout == null)
                                        reservedBooksLayout = FXMLLoader.load(getClass().getResource("/memberapp/view/ReservedBooks_FXML.fxml")) ;

                                    this.borderPane.setCenter(reservedBooksLayout);
                                }
                                catch(IOException e)
                                {
                                    System.out.println("\nExceptions in HomeScreenController.initializeTreeView() -> \n" + e) ;
                                    e.printStackTrace();
                                }
                                break ;

                            case "Issued Books":
                                try
                                {
                                    if(issuedBooksLayout == null)
                                        issuedBooksLayout = FXMLLoader.load(getClass().getResource("/memberapp/view/IssuedBooks_FXML.fxml")) ;

                                    this.borderPane.setCenter(issuedBooksLayout);
                                }
                                catch(IOException e)
                                {
                                    System.out.println("\nExceptions in HomeScreenController.initializeTreeView() -> \n" + e) ;
                                    e.printStackTrace() ;
                                }
                                break ;

                            case "Pending Returns":
                                try
                                {
                                    if(requestedToReturnLayout == null)
                                        requestedToReturnLayout = FXMLLoader.load(getClass().getResource("/memberapp/view/RequestToReturn_FXML.fxml")) ;

                                    this.borderPane.setCenter(requestedToReturnLayout);
                                }
                                catch(IOException e)
                                {
                                    System.out.println("\nExceptions in HomeScreenController.initializeTreeView() -> \n" + e) ;
                                    e.printStackTrace() ;
                                }
                                break ;

                            case "Edit Profile":
                                try
                                {
                                    if(editProfileLayout == null)
                                        editProfileLayout = FXMLLoader.load(getClass().getResource("/memberapp/view/EditProfile_FXML.fxml")) ;

                                    this.borderPane.setCenter(editProfileLayout);
                                }
                                catch(IOException e)
                                {
                                    System.out.println("\nExceptions in HomeScreenController.initializeTreeView() -> \n" + e) ;
                                    e.printStackTrace() ;
                                }
                                break ;
                        }
                    }
                });
    }


    private TreeItem<String> createBranch(String itemValue, TreeItem<String> root)
    {
        TreeItem<String> item = new TreeItem<>(itemValue) ;
        item.setExpanded(false) ;
        root.getChildren().add(item) ;

        return item ;
    }


    private void initializeTableView()
    {
        // first initialize all the columns
        bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID")) ;
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title")) ;
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author")) ;
        copiesAvailableCol.setCellValueFactory(new PropertyValueFactory<>("copiesAvailable")) ;

        // get the list of books from the database controller and set it as the table's items
        tableView.setItems(dataBaseController.getAllBooks()) ;
        tableView.setOnMouseClicked(mouseEvent ->
        {
            if(mouseEvent.getClickCount() == 2)
            {
                selectedBook = tableView.getSelectionModel().getSelectedItem() ;

                if(selectedBook != null)
                {
                    Stage primary_stage = new Stage();
                    primary_stage.setResizable(false);
                    primary_stage.initModality(Modality.APPLICATION_MODAL);
                    primary_stage.setTitle("BOOK INFO");

                    Parent root = null;

                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/memberapp/view/BookView_FXML.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Scene scene = new Scene(root);
                    scene.getStylesheets().add("/memberapp/resources/css/InfoViewStyleSheet.css") ;

                    primary_stage.setScene(scene);
                    primary_stage.show();
                }
            }
        }) ;

        // set up the widths of all the columns
        bookIDCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15)) ;
        titleCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.40)) ;
        authorCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.25)) ;
        authorCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.20)) ;
    }


    // takes the entered string in the text field and checks the database for the book sought and the table view is updated
    public void search()
    {
        String field = "" ;

        switch (choiceBox.getValue().toLowerCase())
        {
            case "by book id": field = "BookID" ;
                break ;

            case "by title": field = "Title" ;
                break ;

            case "by author": field = "Author" ;
                break ;

            case "by isbn": field = "ISBN" ;
                break ;
        }

        this.tableView.setItems(dataBaseController.getFromBooks(field, this.searchField.getText())) ;
    }


    public void signOut(ActionEvent event) throws IOException
    {
        Main.userAccount = null ;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/memberapp/view/SignInPage_FXML.fxml"))) ;

        Scene scene = new Scene(root) ;
        scene.getStylesheets().add("/memberapp/resources/css/LoginPageStyleSheet.css") ;

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow() ;

        Stage stage = new Stage() ;
        stage.setTitle("M E M B E R   A P P L I C A T I O N  -  S I G N  I N") ;
        stage.setResizable(false) ;
        stage.setScene(scene) ;

        window.close() ;
        stage.show() ;
    }
}