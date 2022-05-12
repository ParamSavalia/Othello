package othello;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;


/**
 * File name :          OthelloViewController.java
 * @since               javafx 2.0 
 * @author              Param Savalia (under guidelines of prof Daniel C.'s lectures and Notes)
 * course :             Java Application
 * Assignment :         1 part-1
 * Due date :           17 July 2021
 * Professor :          Daniel C.
 * Purpose :            Othello game controller such as move button chat box and player info      
 * Function :           OthelloViewController(),loadResources(),createPortion1(),createPortion2(),createPortion3(),createPortion4() 
 * Reference :          http://tutorials.jenkov.com/javafx/button.html (by Jakob Jenkov )
 *                      http://tutorials.jenkov.com/javafx/checkbox.html(by Jakob Jenkov )
 *                      http://tutorials.jenkov.com/javafx/scene.html(by Jakob Jenkov )
 *                      http://tutorials.jenkov.com/javafx/label.html(by Jakob Jenkov )
 *                      http://tutorials.jenkov.com/javafx/textarea.html(by Jakob Jenkov )
 *                      https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Button.html
 *                      http://useof.org/java-open-source/javafx.scene.control.Separator (separator spacing)
 *                      https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
 * 
 *                      https://math.hws.edu/javanotes/source/chapter13/BoundPropertyDemo.java
 *                      https://stackoverflow.com/questions/45198991/java-fx-how-to-set-image-in-a-gridpane-on-click-game-othello 
 *                      
 * 
 */

public class OthelloViewController extends BorderPane {

    private Stage primaryStage;

    private static final int delay = 1000;
    private static final int CHECK_DIMENSION = 60;
    private static final String BORDER = "-fx-border-color: gray; -fx-border-width: 5px;";
    
    // Background whiteBG = new Background(new BackgroundFill(Color.WHITE, null, null));
    // Background colorBG = new Background(new BackgroundFill(Color.PINK, null, null));
    
    private String[][] colors = {{"white", "black"}, {"white", "red"}, {"white", "aqua"}, {"white", "yellowgreen"}, {"white", "gray"}};
    private String[] currentColor = colors[0];
    private OthelloModel othelloModel;
    private int[] selectedCell = {-1, -1};
    private int currentPlayer = 1, countSkip;
    private boolean isSkip = false, isOver = true, isManAndCom = false, isSelectedValidMove;
    private ArrayList<int[]> validMoves;

    // East
    private ImageView upImg, downImg, leftImg, rightImg;
    private Image player1Image, player2Image, validImg;
    private Thread ComponentUpdater;


    private BorderPane[][] checkBoard;

    // North
    private CheckBox validMove, manAndCom;
    private Button upBtn, downBtn, leftBtn, rightBtn, moveBtn;
    private Label p1Piece, p2Piece, l1, l2;

    // textarea 
    private FlowPane chatBox;

    // command box
     private Menu boardColors; 
    private RadioMenuItem color1, color2, color3, color4, color5;

    private TextField commandBox;
    private Button submitBtn;
    private MenuBar menuBar;
    
    private Menu menuFile, menuGame, menuNetwork, menuHelp;
    private MenuItem itemNewGame, itemSave, itemLoad, itemExit;
    
   
    
    private Menu itemDebugScenarios;
    private RadioMenuItem s0, s1, s2, s3, s4, s5, s6, s7;
    
    private MenuItem about;
/***************************************************************************************************
    Purpose:            this method will display Controller of PS game 
    Method name:        OthelloViewController();
    Author:             Param Savalia
    Parameters:         
    Version:            2.0
    Return Value:       primartStage
    Called Functions:   loadResources(),createPortion2(),createPortion3(),createPortion4(),setRight(),
                        displaycreateMenu(),setTop(),setBottom().

    Reference:          http://tutorials.jenkov.com/javafx/gridpane.html
                        https://www.tabnine.com/code/java/methods/javafx.scene.layout.Pane/setStyle
                        https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html

***************************************************************************************************/
    


    public OthelloViewController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        othelloModel = new OthelloModel();
        this.loadResources(); // load all images

      
        // name = new Label();
        
        // name.setStyle("-fx-font-weight: bold;"); 
    

        this.setStyle("-fx-background-color: rgb(220, 220, 220);");
        this.setCenter(createPortion1()); //  check board

        // controls and chat box at top
        GridPane rightPane = new GridPane();
        // for right side of controller
        rightPane.add(createPortion2(), 0, 0);
        rightPane.add(createPortion3(), 0, 1);
        rightPane.add(createPortion4(), 0, 2);
        this.setRight(rightPane); 

        //display menu
        this.displaycreateMenu();
        //display menu area
         
        BorderPane bp = new BorderPane();
        // bp.setTop(menuBar);
        
            
            // borderPane.setTop(menuBar);
        
            // borderPane.setBottom(CreaPortion5());
        
        this.setTop(menuBar);

           // this will have textfield and button submit on cp5();         
        this.setBottom(createPortion5());
    }


/***************************************************************************************************
    Purpose:            this method will display Menu for PS games 
    Method name:        displaycreateMenu();
    Author:             Param Savalia
    Parameters:         
    Version:            2.0
    Return Value:       
    Called Functions:   getMenus(),addAll(),setAccelerator(),getItems(),RadioMenuItem(),Controller(),
                        actionMenu(),disableScenarios()
    Reference:          http://tutorials.jenkov.com/javafx/menubar.html(by Jakob Jenkov)
                        https://www.tabnine.com/code/java/methods/java.awt.MenuBar/getMenus
                        https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/RadioMenuItem.html
                        http://www.java2s.com/Code/Java/JavaFX/SetacceleratorforMenu.htm

***************************************************************************************************/
    
    private void displaycreateMenu() {
        //create menu on menu bar
        // Menu menu = new Menu("Menu 1");
        // menu.setGraphic(new ImageView("file:volleyball.png"));
        
        menuBar = new MenuBar();
        menuFile = new Menu("File");
        menuFile.setGraphic(new ImageView("resources/file.jpg"));
        menuGame = new Menu("Game");
        menuNetwork = new Menu("Network");        
        menuHelp = new Menu("Help");
        // adding all menus
        menuBar.getMenus().addAll(menuFile, menuGame, menuNetwork, menuHelp);


        //create menu items on file menu
        itemNewGame = new MenuItem("New Game");
        itemSave = new MenuItem("Save");
        itemLoad = new MenuItem("Load");
        itemExit = new MenuItem("Exit");
        // shortcuts in sub-menus
        itemNewGame.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        itemSave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        itemLoad.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        itemExit.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        // get all sub-menus in the main menu.
        menuFile.getItems().addAll(itemNewGame, itemLoad, itemSave, itemExit);

        //create menu items on game menu
        boardColors = new Menu("Board Colours");
        itemDebugScenarios = new Menu("Debug Scenarios");

        RadioMenuItem radioItem = new RadioMenuItem("radio text");
        // radioItem.setSelected(false);

        // colors 1,2,3,4,5 is mixture of two color. colors are string array.
        color1 = new RadioMenuItem("Standard");
        color2 = new RadioMenuItem("Canadian");
        color3 = new RadioMenuItem("Sky");
        color4 = new RadioMenuItem("Clear");
        color5 = new RadioMenuItem("Gray");
        color1.setSelected(true);
        boardColors.getItems().addAll(color1, color2, color3, color4, color5);

        s0 = new RadioMenuItem("Normal Game");
        s1 = new RadioMenuItem("Corner Test");
        s2 = new RadioMenuItem("Side Tests");
        s3 = new RadioMenuItem("1x Capture Test");
        s4 = new RadioMenuItem("2x Capture Test");
        s5 = new RadioMenuItem("Empty Board");
        s6 = new RadioMenuItem("Inner Square Test");
        s7 = new RadioMenuItem("Up Arrow Orientation Test");
        itemDebugScenarios.getItems().addAll(s0, s1, s2, s3, s4, s5, s6, s7);
        menuGame.getItems().addAll(boardColors, itemDebugScenarios);



        //create menu items on help menu
        about = new MenuItem("About");
        menuHelp.getItems().addAll(about);
        actionMenu();
        disableScenarios();
        new Controller("new").handle(null);
    }

    /**
     * unselect items of colors
     */
    private void emptyAllSelect1() {
        color1.setSelected(false);
        color2.setSelected(false);
        color3.setSelected(false);
        color4.setSelected(false);
        color5.setSelected(false);
    }

    /**
     * unselect items of debug scenarios
     */
    private void emptyAllSelect2() {
        s0.setSelected(false);
        s1.setSelected(false);
        s2.setSelected(false);
        s3.setSelected(false);
        s4.setSelected(false);
        s5.setSelected(false);
        s6.setSelected(false);
        s7.setSelected(false);
    }

    /**
     * when any item of file menu is clicked, it is triggered
     */
    private void actionMenu() {
        itemNewGame.setOnAction(new Controller("new"));
        itemSave.setOnAction(new Controller("save"));
        itemLoad.setOnAction(new Controller("load"));
        itemExit.setOnAction(new Controller("exit"));
        color1.setOnAction(new Controller("color1"));
        color2.setOnAction(new Controller("color2"));
        color3.setOnAction(new Controller("color3"));
        color4.setOnAction(new Controller("color4"));
        color5.setOnAction(new Controller("color5"));
        s0.setOnAction(new Controller("scenario0"));
        s1.setOnAction(new Controller("scenario1"));
        s2.setOnAction(new Controller("scenario2"));
        s3.setOnAction(new Controller("scenario3"));
        s4.setOnAction(new Controller("scenario4"));
        s5.setOnAction(new Controller("scenario5"));
        s6.setOnAction(new Controller("scenario6"));
        s7.setOnAction(new Controller("scenario7"));
        about.setOnAction(new Controller("about"));
    }

    /**
     * enable debug scenarios
     */
    private void enableScenarios() {
        s0.setDisable(false);
        s1.setDisable(false);
        s2.setDisable(false);
        s3.setDisable(false);
        s4.setDisable(false);
        s5.setDisable(false);
        s6.setDisable(false);
        s7.setDisable(false);
        validMove.setDisable(false);
        manAndCom.setDisable(false);
    }

    /**
     * disable debug scenarios
     */
    private void disableScenarios() {
        s0.setDisable(true);
        s1.setDisable(true);
        s2.setDisable(true);
        s3.setDisable(true);
        s4.setDisable(true);
        s5.setDisable(true);
        s6.setDisable(true);
        s7.setDisable(true);
        validMove.setDisable(true);
        manAndCom.setDisable(true);
    }

    
    /***************************************************************************************************
    Purpose:            this method will initialize the pieces for PS games 
    Method name:        initPieces();
    Author:             Param Savalia
    Parameters:         
    Version:            2.0
    Return Value:       
    Called Functions:   emptyAllSelect2(),setText(),getChildren(),clear(),chipCount(),getSquare(),drawPiece()

    Reference:          https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Parent.html
                        https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
                        https://www.programiz.com/java-programming/examples/pyramid-pattern 

    ***************************************************************************************************/
 
    private void initPieces() {
        currentPlayer = 1;

        // boolean isOver = false;
        // boolean isSkip = false;
        isOver = false;
        isSkip = false;
        
        moveBtn.setText("move");
        emptyAllSelect2();
        chatBox.getChildren().clear();
                // chip count will count the number of black and white and in the end it will finalize the winner 
        /* 
               if (othelloModel.chipCount(1) > othelloModel.chipCount(2)){
                 String message = " P 1 "; 
                }
        */

        String message = "Player 1 initialized with " + othelloModel.chipCount(1) + " pieces\nPlayer 2 initialized with " + othelloModel.chipCount(2) + " pieces";
        chatBox.getChildren().add(new Label(message));
        
        /* 
            took the help of the priamid logic to drawsquare and pieces
        */

        for (int i = 0; i < 8; i++) { // horizontal
            for (int j = 0; j < 8; j++) { // vertical 
                int piece = othelloModel.getSquare(i, j);
                drawPiece(i, j, piece);
            }
        }
        
        p1Piece.setText(String.valueOf(othelloModel.chipCount(1)));
        p2Piece.setText(String.valueOf(othelloModel.chipCount(2)));
         
        ArrayList<int[]> validMoves;
        validMoves = new ArrayList<>();
        
        showValidMove();
        noteCurrentPlayer();
    }

    /**
     * show available positions
     */

     /***************************************************************************************************
    Purpose:            this method will guide for the best moves for the white or black in PS games 
    Method name:        showValidMove();
    Author:             Param Savalia
    Parameters:         
    Version:            2.0
    Return Value:       
    Called Functions:   get(),getChildern(),clear(),highlight(),canMove()

    Reference:          https://stackoverflow.com/questions/9128535/highlighting-strings-in-javafx-textarea
                        https://www.youtube.com/watch?v=Uj8rPV6JbCE (for selecting the particular cell on board)

    ***************************************************************************************************/
    private void showValidMove() {
        
        if (validMoves != null) {
            //clear all old valid move position
            for (int i = 0; i < validMoves.size(); i++) {
                checkBoard[validMoves.get(i)[0]][validMoves.get(i)[1]].getChildren().clear();
            }
        }
        else {
            System.out.print ("Error message");
            // validMoves.clear();
        }
        validMoves = new ArrayList<>();
        
        // moves for the cell specfic
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (othelloModel.canMove(i, j, currentPlayer)) {
                    //assign selected cell and add valid move to list
                    selectedCell[0] = i;
                    selectedCell[1] = j;
                    validMoves.add(new int[]{i, j});
                }
            }
        }
        if (isSelectedValidMove) {
            //show valid move position
            for (int i = 0; i < validMoves.size(); i++) {
                // multi dimension arrays   
                //remaining tick image 
                checkBoard[validMoves.get(i)[0]][validMoves.get(i)[1]].setCenter(new ImageView(validImg));
            }
        }
         // this method can change the color of the cell in game 
        highLight();
    }

    /**
     * whether current player have valid position or not
     */

    /***************************************************************************************************
        Purpose:            this method will show that the valid position for current player exist or not 
        Method name:        existValidPositionOfCurrentPlayer();
        Author:             Param Savalia
        Version:            2.0
        Return Value:       
        Called Functions:   
        Reference:          lecture videos
                            

    ***************************************************************************************************/
    private void existValidPositionOfCurrentPlayer() {
        if (!othelloModel.moveTest(currentPlayer)) {
            isSkip = true;
            
            System.out.println("Show Valid");

            // if(isSkip == false){
            //  System.out.println("No Valid Moves");
            //}

            moveBtn.setText("skip");
        } else {
            moveBtn.setText("move");
        }
        if (countSkip >= 2 || othelloModel.chipCount(1) + othelloModel.chipCount(2) >= 64) {
            isOver = true;
            disableScenarios();
        }
    }

    private void noteCurrentPlayer() {
        if (currentPlayer == 1) {
            l1.setStyle("-fx-border-color: blue");
            l2.setStyle("-fx-border-color: transparent");
        } else if (currentPlayer == 2) {
            l1.setStyle("-fx-border-color: transparent");
            l2.setStyle("-fx-border-color: blue");
        }
    }


    /**
     * loads all required images
     */
    private void loadResources() {
        upImg = new ImageView(new Image("resources/uparrow.png", 40, 40, false, false));
        downImg = new ImageView(new Image("resources/downarrow.png"));
        leftImg = new ImageView(new Image("resources/leftarrow.png"));
        rightImg = new ImageView(new Image("resources/rightarrow.png"));
        player1Image = new Image("resources/black.png");
        player2Image = new Image("resources/white.png");
        validImg = new Image("resources/check.png");
    }

    /**
     * @return check board (portion 1)
     */
    /***************************************************************************************************
    Purpose:            To load chessboard and the label aroud the board
    Method name:        createPortion1();
    Author:             Param Savalia
    Parameters:         
    Version:            2.0
    Return Value:       layout
    Called Functions:       
    Reference:          https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/Pane.html
                        https://www.javatpoint.com/java-string-valueof
                        https://www.programcreek.com/java-api-examples/?api=javafx.geometry.Insets

    ***************************************************************************************************/
    private Pane createPortion1() {

        // create checkboard
        GridPane layout = new GridPane();

        checkBoard = new BorderPane[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String boxColor = ((i + j) % 2 == 0) ? currentColor[0] : currentColor[1]; // initial color of row

            /*   Pane canvas = new Pane();
                 canvas.setStyle("-fx-background-color: black;");
                 canvas.setPrefSize(200,200);
                 Circle circle = new Circle(50,Color.BLUE);
                 circle.relocate(20, 20);
                 Rectangle rectangle = new Rectangle(100,100,Color.RED);
                 rectangle.relocate(70,70);
                 canvas.getChildren().addAll(circle,rectangle);
            */ 
                final int ii = i;
                final int jj = j;
                checkBoard[i][j] = new BorderPane();
                checkBoard[i][j].setPrefSize(CHECK_DIMENSION, CHECK_DIMENSION);
                checkBoard[i][j].setMinSize(CHECK_DIMENSION, CHECK_DIMENSION);
                checkBoard[i][j].setMaxSize(CHECK_DIMENSION, CHECK_DIMENSION);

                String style = "-fx-border-color: gray; -fx-border-width: 0.5px; -fx-background-color: " + boxColor;
                checkBoard[i][j].setStyle(style);
                checkBoard[i][j].setOnMouseClicked(event -> {
                    selectedCell[0] = ii;
                    selectedCell[1] = jj;
                    new Controller("moveButton").handle(null);
                });

                // add to layout
                layout.add(checkBoard[i][j], (j + 1), (i + 1));
            }
        }

        // add sequential labels
        Font font = Font.font("monospaced", FontWeight.BOLD, 20);

        for (int i = 1; i <= checkBoard.length; i++) {

            // bottom label
            Label bottomLabel = new Label(String.valueOf(i));
            bottomLabel.setAlignment(Pos.CENTER);
            bottomLabel.setPrefSize(CHECK_DIMENSION, CHECK_DIMENSION);
            bottomLabel.setMinSize(CHECK_DIMENSION, CHECK_DIMENSION);
            bottomLabel.setMaxSize(CHECK_DIMENSION, CHECK_DIMENSION);
            bottomLabel.setFont(font);
            layout.add(bottomLabel, i, 10);

            // top label
            Label topLabel = new Label(String.valueOf(i));
            topLabel.setAlignment(Pos.CENTER);
            topLabel.setPrefSize(CHECK_DIMENSION, CHECK_DIMENSION);
            topLabel.setMinSize(CHECK_DIMENSION, CHECK_DIMENSION);
            topLabel.setMaxSize(CHECK_DIMENSION, CHECK_DIMENSION);
            topLabel.setFont(font);
            layout.add(topLabel, i, 0);


            // left label
            Label leftLabel = new Label(String.valueOf((char) (64 + i)));
            leftLabel.setAlignment(Pos.CENTER);
            leftLabel.setPrefSize(CHECK_DIMENSION, CHECK_DIMENSION);
            leftLabel.setMinSize(CHECK_DIMENSION, CHECK_DIMENSION);
            leftLabel.setMaxSize(CHECK_DIMENSION, CHECK_DIMENSION);
            leftLabel.setFont(font);
            layout.add(leftLabel, 0, i);

            // right label
            Label rightLabel = new Label(String.valueOf((char) (64 + i)));
            rightLabel.setAlignment(Pos.CENTER);
            rightLabel.setPrefSize(CHECK_DIMENSION, CHECK_DIMENSION);
            rightLabel.setMinSize(CHECK_DIMENSION, CHECK_DIMENSION);
            rightLabel.setMaxSize(CHECK_DIMENSION, CHECK_DIMENSION);
            rightLabel.setFont(font);
            layout.add(rightLabel, 10, i);
        }

        // padding
        layout.setPadding(new Insets(10));
        layout.setStyle(BORDER);

        return layout;
    }

    /**
     * put all pieces changed
     */
    private void putAllPieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                drawPiece(i, j, othelloModel.getSquare(i, j));
            }
        }
    }

    /**
     * set box color
     */
    private void setBoxColor() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String boxColor = ((i + j) % 2 == 0) ? currentColor[0] : currentColor[1]; // initial color of row
                String style = "-fx-border-color: gray; -fx-border-width: 0.5px; -fx-background-color: " + boxColor;
                checkBoard[i][j].setStyle(style);
            }
        }
    }

    /**
     * put piece on specific position
     *
     * @param row    number of row
     * @param col    number of col
     * @param player number of player
     */
    private void drawPiece(int row, int col, int player) {
        if (player == 1) {
            checkBoard[row][col].setCenter(new ImageView(player1Image));
        } else if (player == 2) {
            checkBoard[row][col].setCenter(new ImageView(player2Image));
        } else if (player == 0) {
            checkBoard[row][col].getChildren().clear();
        }
    }

    /**
     * highlight of selected cell
     */
    private void highLight() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String boxColor = ((i + j) % 2 == 1) ? currentColor[0] : currentColor[1];
                ; // alternate colors
                String style = "-fx-border-color: gray; -fx-border-width: 0.5px; -fx-background-color: " + boxColor;
                checkBoard[i][j].setStyle(style);
            }
        }
        try {
            String boxColor = ((selectedCell[0] + selectedCell[1]) % 2 == 1) ? currentColor[0] : currentColor[1];
            ; // alternate colors
            String style = "-fx-border-color: #41e541; -fx-border-width: 5px; -fx-background-color: " + boxColor;
            checkBoard[selectedCell[0]][selectedCell[1]].setStyle(style);
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }


    /**
     * @return checkbox panel (portion 2)
     */
/***************************************************************************************************
    Purpose:            this method will display checkbox in gui
    Method name:        createPortion2();
    Author:             Param Savalia
    Parameters:         
    Version:            2.0
    Return Value:       layout
    Called Functions:       
    Reference:          http://tutorials.jenkov.com/javafx/checkbox.html

***************************************************************************************************/
    private Pane createPortion2() {

        // check box
        validMove = new CheckBox("Show Valid Moves");
        validMove.setOnAction(new Controller("validMoveCheckBox"));

        manAndCom = new CheckBox("Man and computer");
        manAndCom.setOnAction(new Controller("manAndComCheckBox"));
        // container
        FlowPane layout = new FlowPane();
        layout.setAlignment(Pos.BASELINE_LEFT);
        layout.getChildren().add(validMove);
        layout.getChildren().add(manAndCom);

        layout.setPadding(new Insets(5));
        layout.setHgap(10);
        layout.setStyle(BORDER);

        return layout;
    }

    /**
     * @return button panel (portion 3 (A/B))
     */


/***************************************************************************************************
    Purpose:            this method will display arrow button in gui 
    Method name:        createPortion3();
    Author:             Param Savalia
    Parameters:         
    Version:            1.4
    Return Value:       layout
    Called Functions:   setGraphic(),setOnAction(),setPadding(),setHgap(),setVgap(),setAlignment(),    
    Reference:          http://tutorials.jenkov.com/javafx/checkbox.html
                        https://www.programcreek.com/java-api-examples/?class=javafx.scene.control.Button&method=setGraphic


***************************************************************************************************/

    private Pane createPortion3() {

        //press keys "up, down, left, right, enter" and action
        this.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.UP) {
                new Controller("upButton").handle(null);
            } else if (event.getCode() == KeyCode.DOWN) {
                new Controller("downButton").handle(null);
            } else if (event.getCode() == KeyCode.LEFT) {
                new Controller("leftButton").handle(null);
            } else if (event.getCode() == KeyCode.RIGHT) {
                new Controller("rightButton").handle(null);
            } else if (event.getCode() == KeyCode.ENTER) {
                new Controller("moveButton").handle(null);
            }
        });
        /* create buttons  */
        upBtn = new Button();
        upBtn.setGraphic(upImg);
        upBtn.setOnAction(new Controller("upButton"));

        downBtn = new Button();
        downBtn.setGraphic(downImg);
        downBtn.setOnAction(new Controller("downButton"));

        leftBtn = new Button();
        leftBtn.setGraphic(leftImg);
        leftBtn.setOnAction(new Controller("leftButton"));

        rightBtn = new Button();
        rightBtn.setGraphic(rightImg);
        rightBtn.setOnAction(new Controller("rightButton"));

        moveBtn = new Button("move");
        moveBtn.setFont(Font.font(10));
        moveBtn.setPadding(new Insets(15)); // important to make button equal
        moveBtn.setOnAction(new Controller("moveButton"));

        // add buttons to layout
        GridPane layout = new GridPane();
        layout.setHgap(5);
        layout.setVgap(5);

        layout.add(upBtn, 1, 0);
        layout.add(leftBtn, 0, 1);
        layout.add(moveBtn, 1, 1);
        layout.add(rightBtn, 2, 1);
        layout.add(downBtn, 1, 2);

        // player information
        BorderPane playerTitle = new BorderPane();
        playerTitle.setPadding(new Insets(15));
        Font font = Font.font("", FontWeight.EXTRA_BOLD, 15);

        // separator to create space between player pieces information
        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.HORIZONTAL);
        separator1.setPrefSize(20, 10);
        separator1.setVisible(false);

        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.HORIZONTAL);
        separator2.setPrefSize(20, 10);
        separator2.setVisible(false);

        // player 1 information
        FlowPane p1 = new FlowPane(5, 10);
        l1 = new Label("Player 1 Pieces:");
        l1.setFont(font);
        p1Piece = new Label();
        p1Piece.setFont(font);
        ImageView imageView1 = new ImageView(player1Image);
        l1.setStyle("-fx-border-style: solid;-fx-border-width: 2px;-fx-border-color: transparent");
        p1.getChildren().addAll(l1, separator1, imageView1, p1Piece);

        // player 2 information
        FlowPane p2 = new FlowPane(5, 10);
        l2 = new Label("Player 2 Pieces:");
        l2.setFont(font);
        l2.setStyle("-fx-border-style: solid;-fx-border-width: 2px;-fx-border-color: transparent");
        p2Piece = new Label();
        p2Piece.setFont(font);
        p2.getChildren().addAll(l2, separator2, new ImageView(player2Image), p2Piece);

        // put player information together
        playerTitle.setTop(p1);
        playerTitle.setBottom(p2);

        // add player information to layout
        layout.setPadding(new Insets(10));
        layout.setStyle(BORDER);
        layout.setPrefWidth(450);
        layout.add(playerTitle, 3, 0, 1, 3);
        layout.setAlignment(Pos.CENTER);
        return layout;
    }

    /**
     * @return chatbox (portion 4) NOTE: scroll-pane would be needed in future
     * to accommodate all messages sent throughout gameplay
     */


/***************************************************************************************************
    Purpose:            this method will display blue chat box in gui (fx) 
    Method name:        createPortion4();
    Author:             Param Savalia
    Parameters:         
    Version:            1.4
    Return Value:       chatbox
    Called Functions:   setPrefSize(),setMinSize(),setMaxSize(),setStyle(),setAlignment(),    
    Reference:          https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
                        https://www.codota.com/code/java/methods/javafx.scene.layout.Pane/getChildren

***************************************************************************************************/
    private ScrollPane createPortion4() {
        /* create chat box*/
        chatBox = new FlowPane();
        chatBox.setStyle("-fx-background-color: rgb(175, 175, 255);-fx-font-size: 15px");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle(BORDER);
        scrollPane.setContent(chatBox);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(450, 450);
        return scrollPane;
    }

    /**
     * @return command box (portion 5)
     */
    private Pane createPortion5() {

        // command box
        commandBox = new TextField();

        // submit  button
        submitBtn = new Button("Submit");
        submitBtn.setTextFill(Paint.valueOf("red"));
        submitBtn.setStyle("-fx-background-color: black;");
        submitBtn.setOnAction(new Controller("submitButton"));

        // add to layout
        GridPane layout = new GridPane();
        GridPane.setHgrow(commandBox, Priority.ALWAYS); // textbar grows

        layout.add(commandBox, 0, 0);
        layout.add(submitBtn, 1, 0);

        layout.setPadding(new Insets(5));
        layout.setStyle(BORDER);
        layout.setHgap(5);

        return layout;
    }

    class Controller implements EventHandler<ActionEvent> {

        private String source = "";

        public Controller(String source) {
            this.source = source;
        }


    /***************************************************************************************************
    Purpose:            this method will do file handling 
    Method name:        handle();
    Author:             Param Savalia
    Parameters:         
    Version:            1.4
    Return Value:       event
    Called Functions:   
    Reference:          
                https://stackoverflow.com/questions/37769481/javafx-gui-that-opens-a-text-file-how-to-read-whats-in-text-file-and-edit-save
                https://curbsidebanter.wordpress.com/2016/05/11/file-inputoutput/
                https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html
    ***************************************************************************************************/
        @Override
        public void handle(ActionEvent event) {
            String message = ""; // to be displayed in box
            switch (source) {
                case "new":
                    othelloModel.prepareBoard(0);
                    initPieces();
                    enableScenarios();
                    s0.setSelected(true);
                    break;
                case "load":
                    FileChooser fileChooserToRead = new FileChooser();

                    FileChooser.ExtensionFilter extensionFilterLoad = new FileChooser.ExtensionFilter("Othello file(*.oth)", "*.oth");
                    fileChooserToRead.getExtensionFilters().add(extensionFilterLoad);

                    fileChooserToRead.setTitle("Load file");
                    File fileRead = fileChooserToRead.showOpenDialog(primaryStage.getScene().getWindow());

                    if (fileRead != null) {
                        try {
                            int[][] board = new int[8][8];
                            int row = 0;
                            BufferedReader br = new BufferedReader(new FileReader(fileRead));
                            String line = br.readLine();
                            while (line != null) {
                                for (int i = 0; i < 8; i++) {
                                    board[row][i] = Character.getNumericValue(line.charAt(i));
                                }
                                row++;
                                line = br.readLine();
                            }
                            br.close();
                            othelloModel.setBoard(board);
                            initPieces();
                            enableScenarios();
                            message = "Load " + fileRead.getName();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            message = "Load failed";
                        } catch (IOException e) {
                            e.printStackTrace();
                            message = "Load failed";
                        }
                    }
                    break;
                case "save":
                    FileChooser fileChooserToSave = new FileChooser();

                    FileChooser.ExtensionFilter extensionFilterSave = new FileChooser.ExtensionFilter("Othello file(*.oth)", "*.oth");
                    fileChooserToSave.getExtensionFilters().add(extensionFilterSave);

                    fileChooserToSave.setTitle("Save file");
                    File fileSave = fileChooserToSave.showSaveDialog(primaryStage.getScene().getWindow());

                    if (fileSave != null) {
                        int[][] board = othelloModel.getBoard();
                        try {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(fileSave));
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    bw.write(String.valueOf(board[i][j]));
                                }
                                bw.newLine();
                            }
                            bw.close();
                            message = "Save to " + fileSave.getName();
                        } catch (IOException e) {
                            e.printStackTrace();
                            message = "Save failed";
                        }
                    }
                    break;
                case "exit":
                    primaryStage.close();
                    break;
 
                case "Network":
                    break;
                case "about":
                    GridPane mainPane = new GridPane();
                    GridPane topPane = new GridPane();
                    GridPane bottomPane = new GridPane();
                    mainPane.add(topPane, 0, 0);
                    mainPane.add(bottomPane, 0, 1);
                    topPane.setPadding(new Insets(10, 10, 10, 10));
                    topPane.setVgap(10);
                    topPane.setHgap(20);
                    Text text = new Text("Othello Game\nPowered by Daniel Cormier and Algonquin college");
                    Text text1 = new Text("July 2021");
                    text.setFont(new Font("s", 15));
                    text1.setFont(new Font("s", 15));
                    Button button = new Button("OK");

                    topPane.add(text, 1, 0);
                    topPane.add(new ImageView("resources\\n.png"), 0, 0);
                    topPane.add(text1, 1, 1);

                    StackPane stackPane = new StackPane();
                    stackPane.setPrefWidth(300);
                    stackPane.getChildren().add(button);
                    bottomPane.add(stackPane, 0, 0);

                    Scene scene = new Scene(mainPane, 300, 150);
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("About");

                    primaryStage.setScene(scene);
                    primaryStage.show();
                    button.setOnAction(event1 -> {
                        primaryStage.close();
                    });
                    break;
                case "color1":
                    emptyAllSelect1();
                    color1.setSelected(true);
                    currentColor = colors[0];
                    setBoxColor();
                    break;
                case "color2":
                    emptyAllSelect1();
                    color2.setSelected(true);
                    currentColor = colors[1];
                    setBoxColor();
                    break;
                case "color3":
                    emptyAllSelect1();
                    color3.setSelected(true);
                    currentColor = colors[2];
                    setBoxColor();
                    break;
                case "color4":
                    emptyAllSelect1();
                    color4.setSelected(true);
                    currentColor = colors[3];
                    setBoxColor();
                    break;
                case "color5":
                    emptyAllSelect1();
                    color5.setSelected(true);
                    currentColor = colors[4];
                    setBoxColor();
                    break;
                case "scenario0":
                    othelloModel.prepareBoard(0);
                    initPieces();
                    s0.setSelected(true);
                    break;
                case "scenario1":
                    othelloModel.prepareBoard(1);
                    initPieces();
                    s1.setSelected(true);
                    break;
                case "scenario2":
                    othelloModel.prepareBoard(2);
                    initPieces();
                    s2.setSelected(true);
                    break;
                case "scenario3":
                    othelloModel.prepareBoard(3);
                    initPieces();
                    s3.setSelected(true);
                    break;
                case "scenario4":
                    othelloModel.prepareBoard(4);
                    initPieces();
                    s4.setSelected(true);
                    break;
                case "scenario5":
                    othelloModel.prepareBoard(5);
                    initPieces();
                    s5.setSelected(true);
                    break;
                case "scenario6":
                    othelloModel.prepareBoard(6);
                    initPieces();
                    s6.setSelected(true);
                    break;
                case "scenario7":
                    othelloModel.prepareBoard(7);
                    initPieces();
                    s7.setSelected(true);
                    break;
            }

            // if it is not over
            if (!isOver) {
                switch (source) {
                    case "validMoveCheckBox":
                        if (validMove.isSelected()) {
                            message = "ENABLED: Show Valid Move";
                            isSelectedValidMove = true;
                        } else {
                            message = "DISABLED: Show Valid Move";
                            isSelectedValidMove = false;
                        }
                        showValidMove();
                        break;
                    case "manAndComCheckBox":
                        isManAndCom = !isManAndCom;
                        break;
                    case "upButton":
                        //  message = "UP";
                        if (selectedCell[0] > 0) {
                            selectedCell[0]--;
                        }
                        break;
                    case "downButton":
                        //  message = "DOWN";
                        if (selectedCell[0] < 7) {
                            selectedCell[0]++;
                        }
                        break;
                    case "leftButton":
                        //  message = "LEFT";
                        if (selectedCell[1] > 0) {
                            selectedCell[1]--;
                        }
                        break;
                    case "rightButton":
                        //   message = "RIGHT";
                        if (selectedCell[1] < 7) {
                            selectedCell[1]++;
                        }
                        break;
                    case "moveButton":
                        message = move();
                        if (isManAndCom) {
                            automaticMove();
                        }
                        break;
                    case "submitButton":
                        message = commandBox.getText().trim(); // get typed text
                        break;
                }
                highLight();
                // no display for empty command
                if (message.isEmpty()) {
                    return;
                }
                Label messages = new Label(message);
                messages.setPrefWidth(450);
                chatBox.getChildren().add(messages);

            }
            // keep only 6 latest messages in chat box
           /* while (chatBox.getChildren().size() > 16) {
                chatBox.getChildren().remove(0);
            }*/
        }

        /**
         * when move, this triggers
         *
         * @return message of result moved
         */
        private String move() {
            String message = "";
            boolean isValidMove = othelloModel.canMove(selectedCell[0], selectedCell[1], currentPlayer);
            if (isValidMove) {
                countSkip = 0;
                isSkip = false;
                int capturedPieces = othelloModel.tryMove(selectedCell[0], selectedCell[1], currentPlayer);
                //    drawPiece(selectedCell[0], selectedCell[1], currentPlayer);
                message = "Player " + currentPlayer + " has captured " + capturedPieces + ((capturedPieces < 2) ? " piece" : " pieces");
                putAllPieces();
                p1Piece.setText(String.valueOf(othelloModel.chipCount(1)));
                p2Piece.setText(String.valueOf(othelloModel.chipCount(2)));
                currentPlayer = othelloModel.oppositePlayer(currentPlayer);
                for (int i = 0; i < validMoves.size(); i++) {
                    if (validMoves.get(i)[0] == selectedCell[0] && validMoves.get(i)[1] == selectedCell[1]) {
                        validMoves.remove(i);
                    }
                }
                showValidMove();
                existValidPositionOfCurrentPlayer();
                message += ". Player " + currentPlayer + " turn on";
            } else if (isSkip) {
                message = "Skip player " + currentPlayer;
                countSkip++;
                currentPlayer = othelloModel.oppositePlayer(currentPlayer);
                showValidMove();
                existValidPositionOfCurrentPlayer();
                message += ". Player " + currentPlayer + " turn on";
            }
            if (isOver) {
                String winner = "";
                int count1 = othelloModel.chipCount(1);
                int count2 = othelloModel.chipCount(2);
                if (count1 > count2) {
                    winner = "Player 1 win";
                } else if (count1 < count2) {
                    winner = "Player 2 win";
                } else {
                    winner = "Draw";
                }
                currentPlayer = othelloModel.oppositePlayer(currentPlayer);
                message = "Skip player " + currentPlayer + "\nGame over" + "\n\n" + winner;
            }
            noteCurrentPlayer();
            return message;
        }

        /**
         * this is moving of an automatic player
         * ref: https://www.programmersought.com/article/85591108229/
         */
        /*
        public boolean judgeGame(int x,int y){
        for(ChessPiece e:chessPane.getChessPieces()){
            if(e.getCol()==x&&e.getRow()==y&&(
                    e.getType()== PieceType.KINGBLACK||e.getType()==PieceType.KINGWHITE))
                return true;
        }

        */
        private void automaticMove() {
            if (currentPlayer == 2) {
                if (validMoves.size() > 0) {
                    selectedCell = validMoves.get(validMoves.size() / 2);
                    highLight();
                }
                ActionListener listener = e -> Platform.runLater(() -> {
                            String message = move();
                            if (message != "") {
                                Label messages = new Label(message);
                                messages.setPrefWidth(450);
                                chatBox.getChildren().add(messages);
                            }
                        }
                );
                Timer t = new Timer(delay, listener);
                
                t.setRepeats(false);
                t.start();
            }
        }
    }
}
