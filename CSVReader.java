import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    BufferedReader bReader;
    

    public CSVReader(String file) {
        bReader = new BufferedReader(new FileReader(file));
    }


}