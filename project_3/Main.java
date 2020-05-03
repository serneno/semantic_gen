import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;

/*
    @author: JC
*/
public class Main extends Application {
    static final String QUERY_SCENE = "layout";
    static final String RESULT_SCENE = "result_layout";
    
    private static Stage mStage;
    private static Class mClass;
    
    //// Shared package variables
    // These are used across scenes for communication
    static String mQueryText = null;
    static String mResultText = null;
    //

    public static void main(String[] args) {
        // Async background load
        new Thread(() -> Sparqler.init()).start();
        
        // GUI
        Application.launch(); 
    }

    public void start(Stage stage) {
        mClass = getClass();
        mStage = stage;
        
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
        stage.setTitle("Team 4: Interesting Search Engine");

        goToScene(QUERY_SCENE);
    }
    
    static void goToScene(String layout_file_path) {
		try {
			mStage.setScene(new Scene(FXMLLoader.load(mClass.getResource(layout_file_path+".fxml"))));
			mStage.show();
		} catch (IOException e) { e.printStackTrace(); }
    }
}
