

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVParser {
    String fileName;                    // file name
    private String[] attributeNames;    // attribute names
    private ArrayList<String[]> data;   // stores individual data


    public CSVParser(String file) {
        fileName = file;
        attributeNames = null;
        data = new ArrayList<String[]>();
    }

    public void csvParse() {
        String currLine;    // current line in file
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(fileName));
            // Attribute Names
            attributeNames = bReader.readLine().split(",");

            // Stores each line in ArrayList data
            while((currLine = bReader.readLine()) != null) {
                String[] attributes = currLine.split(",");
                data.add(attributes);
            }
            bReader.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getAttributes() {
        return attributeNames;
    }

    public ArrayList<String[]> getData() {
        return data;
    }
}
