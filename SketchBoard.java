/*
//  Description: This file sets up the window size and stage of the program.
*/

import javafx.application.Application; 
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SketchBoard extends Application
{
    public static final int WINSIZE_X = 800, WINSIZE_Y = 600;
    private final String WINTITLE = "Sketchy";

    @Override
    public void start(Stage stage) throws Exception
    {
        SketchPane rootPane = new SketchPane();
        rootPane.setPrefSize(WINSIZE_X, WINSIZE_Y);
        Scene scene = new Scene(rootPane, WINSIZE_X, WINSIZE_Y);
        stage.setTitle(WINTITLE);
        stage.setScene(scene);
        stage.show();
    }

    
    public static void main(String[] args)
    {
        launch(args);
    }
    
}