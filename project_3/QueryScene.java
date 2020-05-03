
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

/*
    @author: JC
*/
public class QueryScene {
    @FXML TextArea mTextArea;
    @FXML Button   mButton;

    public void initialize() {
        if(Main.mResultText != null) { mTextArea.setText("Error:\n\n" + Main.mResultText + "\n\n\nFrom:\n\n"); }
        if(Main.mQueryText != null) { mTextArea.setText(mTextArea.getText() + Main.mQueryText); }
    }

    public Object queryClicked() {
        if(mTextArea.getText().isEmpty()) { return null; }

        Main.mQueryText = mTextArea.getText();
        
        LikeADiamond.shineBright();
        
        return null;
    }
}