
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

/*
    @author: JC
*/
public class ResultScene {
    @FXML TextArea mTextArea;
    @FXML Button   mButton;

    public void initialize() {
        mTextArea.setText(Main.mResultText);
    }

    public Object backClicked() { 
        Main.mResultText = null;
        Main.goToScene(Main.QUERY_SCENE); 
        return null;
    }
}