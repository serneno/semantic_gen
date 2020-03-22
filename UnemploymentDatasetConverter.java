import java.io.*;
import java.util.ArrayList;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

/*

Class that reads in a CSV dataset representing unemployment rates by age group in the
US and outputs it into an RDF format.

*/
public class UnemploymentDatasetConverter extends DatasetConverter{
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
		parseInputFile(UNEMPLOYMENT_DATASET_INPUT_PATH);
		buildRdfModel();
		outputToRdf(UNEMPLOYMENT_DATASET_OUTPUT_PATH, "unemployment");
	}

	private void buildRdfModel() {
		model = ModelFactory.createDefaultModel();

		for (int i = 0; i < getData().size(); i++) {
			String URI = "https://data.edd.ca.gov/api/views/bcij-5wym/rows.csv?accessType=DOWNLOAD";
			Property areaType = model.createProperty(URI + "AreaType");
			Property areaName = model.createProperty(URI + "AreaName");
			Property date = model.createProperty(URI + "Date");
			Property year = model.createProperty(URI + "Year");
			Property month = model.createProperty(URI + "Month");
			Property age16to19 = model.createProperty(URI + "SixteenToNineteen");
			Property age20to24 = model.createProperty(URI + "TwentyToTwentyFour");
			Property age25to34 = model.createProperty(URI + "TwentyFivetoThirtyFour");
			Property age35to44 = model.createProperty(URI + "ThirtyFiveToFortyFour");
			Property age45to54 = model.createProperty(URI + "FortyFiveToFiftyFour");
			Property age55to64 = model.createProperty(URI + "FiftyFivetoSixtyFour");
			Property age65Plus = model.createProperty(URI + "SixtyFivePlus");
			String areaTypeValue = getData().get(i)[0];

			String areaNameValue = getData().get(i)[1];
			String dateValue = getData().get(i)[2];
			String yearValue = getData().get(i)[3];

			String monthValue = getData().get(i)[4];
			String age16to19Value = getData().get(i)[5];
			String age20to24Value = getData().get(i)[6];
			String age25to34Value = getData().get(i)[7];
			String age35to44Value = getData().get(i)[8];
			String age45to54Value = getData().get(i)[9];
			String age55to64Value = getData().get(i)[10];
			String age65PlusValue = getData().get(i)[11];
			// placeholder till i figure out what the correct resource should be
			Resource timeline = model.createResource("https://data.edd.ca.gov" + i);
			timeline.addProperty(areaType, areaTypeValue);
			timeline.addProperty(areaName, areaNameValue);
			timeline.addProperty(date, dateValue);
			timeline.addProperty(year, yearValue);
			timeline.addProperty(month, monthValue);
			timeline.addProperty(age16to19, age16to19Value);
			timeline.addProperty(age20to24, age20to24Value);
			timeline.addProperty(age25to34, age25to34Value);
			timeline.addProperty(age35to44, age35to44Value);
			timeline.addProperty(age45to54, age45to54Value);
			timeline.addProperty(age55to64, age55to64Value);
			timeline.addProperty(age65Plus, age65PlusValue);
		}
	}

	private String[] getAttributes() {
		return attributeNames;
	}

	private ArrayList<String[]> getData() {
		return data;
	}
}