import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class CDCCoronavirusDatasetConverter extends DatasetToRdfConverter {
	private String[] attributeNames;  // the attributes in the csv file from the first row
	private ArrayList<String[]> data;  // stores individual data based on the attributes

	public CDCCoronavirusDatasetConverter(){
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
            System.out.println(data);
            bReader.close();
        } 
        catch (IOException e) {
            System.out.println("Error. " + e + ": " + e.getMessage());
        }
	}

	public void run() {
		parseInputFile(Constants.CDC_CORONAVIRUS_DATASET_INPUT_PATH);
		buildRdfModel();
		outputToRdf(Constants.CDC_CORONAVIRUS_DATASET_OUTPUT_PATH, "unemployment");
	}
	private void buildRdfModel() {
		model = ModelFactory.createDefaultModel();

		// Sets the prefix
		model.setNsPrefix("covidRdf", Constants.CDC_CORONAVIRUS_URI);
		
		for (int i = 0; i < getData().size(); i++) {
			Property dataAsOf = model.createProperty(Constants.CDC_CORONAVIRUS_URI  + "DataAsOf");
			Property group = model.createProperty(Constants.CDC_CORONAVIRUS_URI  + "Group");
			Property state = model.createProperty(Constants.CDC_CORONAVIRUS_URI  + "State");
			Property indicator = model.createProperty(Constants.CDC_CORONAVIRUS_URI  + "Indicator");
			Property startWeek = model.createProperty(Constants.CDC_CORONAVIRUS_URI  + "StartWeek");
			Property endWeek = model.createProperty(Constants.CDC_CORONAVIRUS_URI + "EndWeek");
			Property allCovidDeaths = model.createProperty(Constants.CDC_CORONAVIRUS_URI + "AllCovidDeaths");
			Property deathsFromAllCauses = model.createProperty(Constants.CDC_CORONAVIRUS_URI + "DeathsFromAllCauses");
			Property percentOfExpectedDeaths = model.createProperty(Constants.CDC_CORONAVIRUS_URI + "PercentofExpectedDeaths");
			Property allPneumoniaDeaths = model.createProperty(Constants.CDC_CORONAVIRUS_URI + "AllPneumoniaDeaths");
			Property deathsWithPneumoniaAndCOVID = model.createProperty(Constants.CDC_CORONAVIRUS_URI + "DeathsWithPneumoniaAndCovid");
			Property allInfluenzaDeaths = model.createProperty(Constants.CDC_CORONAVIRUS_URI + "AllInfluenzaDeaths");
			Property pneumoniaInfluenzaCovidDeaths = model.createProperty(Constants.CDC_CORONAVIRUS_URI + "PneumoniaInfluenzaAndCovidDeaths");
			Property footnote = model.createProperty(Constants.CDC_CORONAVIRUS_URI + "Footnote");

			String dataValue;
			String groupValue;
			String stateValue;
			String indicatorValue;
			String startWeekValue;
			String endWeekValue;
			String allCovidDeathsValue;
			String deathsFromAllCausesValue;
			String percentOfExpectedDeathsValue;
			String allPneumoniaDeathsValue;
			String deathsWithPneumoniaAndCovidValue;
			String allInfluenzaDeathsValue;
			String pneumoniaInfluenzaCovidDeathsValue;
			String footnoteValue;

			try {
				dataValue = getData().get(i)[0];
			} catch(Exception e) {
				dataValue = "";
			}
			try {
				 groupValue = getData().get(i)[1];
			} catch(Exception e) {
				 groupValue="";
			}
			try {
				 stateValue = getData().get(i)[2];
			} catch(Exception e) {
				stateValue="";
			}
			try {
				 indicatorValue = getData().get(i)[3];
			} catch(Exception e) {
				indicatorValue="";
			}
			try {
				 startWeekValue = getData().get(i)[4];
			} catch(Exception e) {
				startWeekValue="";
			}
			try {
				 endWeekValue = getData().get(i)[5];
			} catch(Exception e) {
				endWeekValue="";			}
		
			try {
				 allCovidDeathsValue = getData().get(i)[6];
			} catch(Exception e) {
				allCovidDeathsValue="";
			}
			try {
				 deathsFromAllCausesValue = getData().get(i)[7];
			} catch(Exception e) {
				deathsFromAllCausesValue="";
			}
			try {
				 percentOfExpectedDeathsValue = getData().get(i)[8];
			} catch(Exception e) {
				percentOfExpectedDeathsValue="";
			}
			try {
				 allPneumoniaDeathsValue = getData().get(i)[9];
			} catch(Exception e) {
				allPneumoniaDeathsValue="";
			}
			try {
				 deathsWithPneumoniaAndCovidValue = getData().get(i)[10];
			} catch(Exception e) {
				deathsWithPneumoniaAndCovidValue="";
			}
			try {
				 allInfluenzaDeathsValue = getData().get(i)[11];
			} catch(Exception e) {
				allInfluenzaDeathsValue="";
			}
			try {
				 pneumoniaInfluenzaCovidDeathsValue = getData().get(i)[12];
			} catch(Exception e) {
				pneumoniaInfluenzaCovidDeathsValue="";
			}
			try {
				 footnoteValue = getData().get(i)[13];
			} catch(Exception e) {
				footnoteValue="";
			}


			Resource timeline = model.createResource("https://data.edd.ca.gov" + i);
			timeline.addProperty(dataAsOf, dataValue);
			timeline.addProperty(group, groupValue);
			timeline.addProperty(state, stateValue);
			timeline.addProperty(indicator, indicatorValue);
			timeline.addProperty(startWeek, startWeekValue);
			timeline.addProperty(endWeek, endWeekValue);
			timeline.addProperty(allCovidDeaths, allCovidDeathsValue);
			timeline.addProperty(deathsFromAllCauses, deathsFromAllCausesValue);
			timeline.addProperty(percentOfExpectedDeaths, percentOfExpectedDeathsValue);
			timeline.addProperty(allPneumoniaDeaths, allPneumoniaDeathsValue);
			timeline.addProperty(deathsWithPneumoniaAndCOVID, deathsWithPneumoniaAndCovidValue);
			timeline.addProperty(allInfluenzaDeaths, allInfluenzaDeathsValue);
			timeline.addProperty(pneumoniaInfluenzaCovidDeaths, pneumoniaInfluenzaCovidDeathsValue);

			timeline.addProperty(footnote, footnoteValue);

		}
	}

	private String[] getAttributes() {
		return attributeNames;
	}

	private ArrayList<String[]> getData() {
		return data;
	}
}
