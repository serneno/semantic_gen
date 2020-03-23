import java.io.*;
import java.util.ArrayList;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

/*

Class that reads in a CSV dataset representing unemployment rates by age group in the
US and outputs it into an RDF format.

*/
public class UnemploymentDatasetConverter extends DatasetToRdfConverter {
	private String[] attributeNames;  // the attributes in the csv file from the first row
	private ArrayList<String[]> data;  // stores individual data based on the attributes

	/* Constructor */
	public UnemploymentDatasetConverter() {
		attributeNames = null;
		data = new ArrayList<String[]>();
	}

	public void parseInputFile(final String inputFile) {
		String currLine;    // current line in file
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(inputFile));
            attributeNames = bReader.readLine().split(",");

            // Stores each line in ArrayList data
            while((currLine = bReader.readLine()) != null) {
                String[] attributes = currLine.split(",");
                data.add(attributes);
            }
            bReader.close();
        } 
        catch (IOException e) {
            System.out.println("Error. " + e + ": " + e.getMessage());
        }
	}

	public void run() {
		parseInputFile(Constants.UNEMPLOYMENT_DATASET_INPUT_PATH);
		buildRdfModel();
		outputToRdf(Constants.UNEMPLOYMENT_DATASET_OUTPUT_PATH, "unemployment");
	}

	private void buildRdfModel() {
		model = ModelFactory.createDefaultModel();

		for (int i = 0; i < getData().size(); i++) {
			Property areaType = model.createProperty(Constants.UNEMPLOYMENT_URI  + "AreaType");
			Property areaName = model.createProperty(Constants.UNEMPLOYMENT_URI  + "AreaName");
			Property date = model.createProperty(Constants.UNEMPLOYMENT_URI  + "Date");
			Property year = model.createProperty(Constants.UNEMPLOYMENT_URI  + "Year");
			Property month = model.createProperty(Constants.UNEMPLOYMENT_URI  + "Month");
			Property seasonalAdj = model.createProperty(Constants.UNEMPLOYMENT_URI  + "SeasonAdjustment");
			Property nonSeasonalAdj = model.createProperty(Constants.UNEMPLOYMENT_URI  + "NonSeasonalAdjustment");

			String areaTypeValue = getData().get(i)[0];
			String areaNameValue = getData().get(i)[1];
			String dateValue = getData().get(i)[2];
			String yearValue = getData().get(i)[3];
			String monthValue = getData().get(i)[4];
			String seasonalAdjValue = getData().get(i)[5];
			String nonSeasonalAdjValue = getData().get(i)[6];

			Resource timeline = model.createResource("https://data.edd.ca.gov" + i);
			timeline.addProperty(areaType, areaTypeValue);
			timeline.addProperty(areaName, areaNameValue);
			timeline.addProperty(date, dateValue);
			timeline.addProperty(year, yearValue);
			timeline.addProperty(month, monthValue);
			timeline.addProperty(seasonalAdj, seasonalAdjValue);
			timeline.addProperty(nonSeasonalAdj, nonSeasonalAdjValue);
		}
	}

	private String[] getAttributes() {
		return attributeNames;
	}

	private ArrayList<String[]> getData() {
		return data;
	}
}