import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

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
        String currLine = "";    // current line in file
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(fileName));
            // Attribute Names
            String attributeList = bReader.readLine();
            // Replaces all spaces with underscore
            attributeList = attributeList.replaceAll("\\s", "_").toLowerCase();
            // Fixes some of the attributes that are comma separated within an attribute
            attributeList = attributeList.replaceAll(",_", "_");
            attributeList = attributeList.replace("\"", "");
            attributeNames = attributeList.split(",");

            // Stores each line in ArrayList data
            int test = 0;
            while((currLine = bReader.readLine()) != null) {
                System.out.println(currLine + " " + test++);
                String[] attributes = currLine.split(",(?=(?:[^\"]*\"[^\"]*\")$)");
                //System.out.println(attributes.length);
                // for(String attr: attributes) {
                //     System.out.print(attr + "|");
                // }
                // System.out.println();
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
            System.out.println(attr + " ");
        }
        System.out.println();
        // for(String[] s : data) {
        //     for(int i = 0; i < s.length; i++) {
        //         System.out.print(s[i] + " ");
        //     }
        //     System.out.println();
        // }
    }

    public void exportRDF() {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // URI
        String NS = "https://data.cdc.gov/resource/rpjd-ejph#"; 

        // Populates array of properties
        Property[] properties = new Property[attributeNames.length];
        for(int i = 0; i < attributeNames.length - 1; i++) {
            properties[i] = model.createProperty(NS + attributeNames[i]);
        }

        // Adds each city info along with it's associated properties
        for(String[] dataS : data) {
            Resource cityURI = model.createResource(dataS[0]);
            System.out.println(properties.length == dataS.length);
            for(int i = 0; i < dataS.length - 1; i++) {
                cityURI.addProperty(properties[i], dataS[i]);
            }
        }
        model.write(System.out);
    }
}