import java.io.*;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

public class UnemploymentDatasetConverter {

	public void run() {
		CSVParser reader;
		int count = 0;
		String file = "Unemployment_Rate_by_Age_Groups.csv";
		reader = new CSVParser(file);
		reader.csvParse();

		Model model = ModelFactory.createDefaultModel();
		for (int i = 0; i < reader.getData().size(); i++) {
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
			String areaTypeValue = reader.getData().get(i)[0];

			String areaNameValue = reader.getData().get(i)[1];
			String dateValue = reader.getData().get(i)[2];
			String yearValue = reader.getData().get(i)[3];

			String monthValue = reader.getData().get(i)[4];
			String age16to19Value = reader.getData().get(i)[5];
			String age20to24Value = reader.getData().get(i)[6];
			String age25to34Value = reader.getData().get(i)[7];
			String age35to44Value = reader.getData().get(i)[8];
			String age45to54Value = reader.getData().get(i)[9];
			String age55to64Value = reader.getData().get(i)[10];
			String age65PlusValue = reader.getData().get(i)[11];
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
		try {
			model.write(new FileOutputStream("unemployment.rdf"), "RDF/XML");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}