package othello;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * file name :      OthelloSplashScreen.java
 * version :        1.0 
 * @author :        Param Savalia
 * @since :         Javafx 2.0
 * course :         Java Application
 * Assignment :     1 part-2
 * Due date :       17 July 2021
 * Professor :      Daniel C.
 * Purpose :        Othello Splash Screen;       
 * Function :       
 * Reference :      Code given by prof. Daniel C.;
 */


public class OthelloSplashScreen extends BorderPane {


/***************************************************************************************************
    Purpose:            Display the image of splash screen without header and footer(correction from assigment 1 p-1)  
    Author:             Param Savalia
    Parameters:         int width, int height
    Version:            1.4
    Called Functions:   setFont(),setTextAlignment(),setPadding(),setAligment(),setBottom()
    Reference:          //http://www.java2s.com/Tutorials/Java/java.awt/Graphics/Java_Graphics_setFont_Font_font_.htm
                        //https://www.programcreek.com/java-api-examples/?class=javafx.scene.text.Text&method=setTextAlignment
                        // ref: https://www.codota.com/code/java/methods/javafx.scene.layout.HBox/setPadding
                        
                        https://www.geeksforgeeks.org/timeunit-tonanos-method-in-java-with-examples/
                        https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/TimeUnit.html#MILLISECONDS
                        https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html
                        https://docs.oracle.com/javase/8/javafx/api/javafx/animation/AnimationTimer.html
                        http://tutorials.jenkov.com/javafx/progressbar.html
***************************************************************************************************/


    private final int interval = 10;
    private int delay;
    private ProgressBar progressBar;
    private Label label;
    // array of messages will show different messages for micro secs  
    private final String[] mess = {"PS Games", "Othello game", "Thank you sir", "for this assignment", "We are learning somthing new "};

    public OthelloSplashScreen(int delay) {
        this.delay = delay;
        BorderPane borderPane = new BorderPane();
        //Borderpane declaration 
        Image splashImage = new Image("resources/Othello.jpg");
        // Image 
        // borderPane.setTop(toolbar);
        // borderPane.setCenter(appContent);
        // borderPane.setBottom(statusbar);
 
        borderPane.setCenter(new ImageView(splashImage));
        // center aligment        
        label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 14));
      //  label.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2.0))));
        StackPane stackPane = new StackPane();
        progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);
        autoRun(); // re-calling the method in same class so for the progress bar
        /*
        ProgressBar progressBar = new ProgressBar(0);

        progressBar.setProgress(0.5);

        */
        stackPane.getChildren().addAll(label, progressBar);
        label.setTranslateY(-30);
        borderPane.setBottom(stackPane);
        borderPane.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(6.0))));
        this.setCenter(borderPane);
    }

    private void autoRun() {
        final AnimationTimer timer = new AnimationTimer() {
            private long upPrev = 0;
            private long started = 0;

            @Override
            public void handle(long now) {
                if (started == 0) {
                    started = now;
                }
                if (now - upPrev >= TimeUnit.MILLISECONDS.toNanos(interval)) {
                    upPrev = now;
                    double percent = (now - started) / (delay * 10e5); // 10e5 is logical 0 
                    progressBar.setProgress(percent);
                }
                if (started + TimeUnit.MILLISECONDS.toNanos(delay) <= now) {
                    this.stop();
                }
                // this loop will go till the end of array of mess so that it will display messages
                // long started = new Date().getTime();
                // TimeUnit time = TimeUnit.MILLISECONDS;
                /* int i = 0;
                    while ( i < mess.length){
                    i++;
                    if (started + TimeUnit.MILLISECONDS.toNanos(delay *i / mess.length) <= now){
                                        label.setText(mess[i]);
                    }
                }
                */
                for (int i = 0; i < mess.length; i++) {
                    if (started + TimeUnit.MILLISECONDS.toNanos(delay *i / mess.length) <= now) {
                        label.setText(mess[i]);
                    }
                }
            }
        };
        timer.start();
    }
}
