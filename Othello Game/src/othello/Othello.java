package othello;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * file name :      Othello.java
 * version :        2.0 
 * @author          Param Savalia 
 * @since           2.0
 * course :         Java Application
 * Assignment :     1 part-2
 * Due date :       17 July 2021
 * Professor :      Daniel C.
 * Purpose :        Othello (main method)       
 * Function :       
 * Reference :      Code given by prof. Daniel C.
 *  
 */


public class Othello extends Application {

    private final int delay = 10000;
    private static final int WIDTH = 1100, HEIGHT = 770;  // game screen dimensions


/**
 * Purpose:            To initialize SplashScreen and OthelloGame
 * Author:             Param Savalia
 * Parameters:         Stage primaryStage
 * Version:            1.4
 * Called Functions:   OthelloSplashScreen(),OthelloViewController(),call(),handle()    
 * Reference:          https://stackoverflow.com/questions/14972199/how-to-create-splash-screen-with-transparent-background-in-javafx (by jewelsea)
 *                     https://docs.oracle.com/javafx/2/api/javafx/concurrent/Task.html
 *                     https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html
*/


    public void start(Stage primaryStage) { 
 /*  
            Stage splashStage = new Stage(); // Create a new stage
            splashStage.setTitle("SplashScreenDemoFX"); // Set the stage title
            splashStage.setScene(scene); // Place the scene in the stage
            splashStage.initStyle(StageStyle.UNDECORATED ); */ 
        //scene is class variable for new Scene (splashScreen, S_Witdh,S_Height)
        // show splash screen first
        
        Stage splashStage = new Stage(); // Create a new stage
        Pane splashPane = new OthelloSplashScreen(delay);
        splashStage.setTitle("SplashScreenDemoFX"); // Set the stage title
        
        Scene scene = new Scene(splashPane, 525, 320);

        splashStage.setScene(scene); // Place the scene in the stage
        splashStage.initStyle(StageStyle.UNDECORATED);
        // Display the splash stage
        splashStage.show();


        Pane gameScreen = new OthelloViewController(primaryStage);
        // game Screen is Pane type variable for controller
        primaryStage.setTitle("Param's Othello Client"); // Set the stage title
        // Set a scene with a text in the stage
        primaryStage.setScene(new Scene(gameScreen, 400, 200));
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false);


        ActionListener listener = e -> Platform.runLater(() -> {
                    splashStage.close();
                    primaryStage.show(); // Display the stage
                }
        );
        Timer t = new Timer(delay, listener);
        t.setRepeats(false);
        t.start();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
