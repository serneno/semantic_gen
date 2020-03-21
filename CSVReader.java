import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    String fileName;                    // file name
    private String[] attributeNames;    // attribute names
    private ArrayList<String[]> data;   // stores individual data
    
    public CSVReader(String file) {
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

    public void print() {
        for(String attr : attributeNames) {
            System.out.print(attr + " ");
        }
        System.out.println();
        for(String[] s : data) {
            for(int i = 0; i < s.length; i++) {
                System.out.print(s[i] + " ");
            }
            System.out.println();
        }
    }
}