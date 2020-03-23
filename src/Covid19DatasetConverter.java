import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/*
	Class that reads in multiple CSV datasets representing COVID-19 deaths/cases/recoveries
	and outputs them into an OWL file.

	@Notes
	OWL ontology has been verified in protege via Hermit. Also passed validation with https://www.w3.org/RDF/Validator/ 
	using ConvidOntology_small_for_validation_testing.owl since full file is too large for the website.
*/
public class Covid19DatasetConverter {

	// Set these flags to true if you want to query fresh data. Otherwise, cached data is used.
    public static final boolean UPDATE_DATA = false;
	public static final boolean CLEAN_DATA = false;
	
	public void run() throws IOException {
		// Queries live data from the Johns Hopkins University Center for Systems Science and Engineering (JHU CSSE).
		// Also, supported by ESRI Living Atlas Team and the Johns Hopkins University Applied Physics Lab (JHU APL).
		if (UPDATE_DATA) {
			Covid19DataHandler.updateData();
		}

		// Inflates the .csv data into Objects
		ParsedCovid19Data data = new ParsedCovid19Data();

		// Mixes statically generated ontology parts from Protege with newly generated lines from the inflated data.
		String ontology = OWLinator.owlate(data);

		// Finally, output the ontology into a .owl file
		System.out.println("Writing COVID-19 datasets into owl...");
		Files.write(new File(Constants.COVID19_DATASET_OUTPUT_PATH).toPath(), ontology.getBytes(), StandardOpenOption.CREATE);
		System.out.println("Done! Output file: '" + Constants.COVID19_DATASET_OUTPUT_PATH + "'");
	}

	/*
		Inner class that handles live data updating/processing to be used by the enclosing
		Covid19DatasetConverter class.
	*/
	static class Covid19DataHandler {
		static void updateData() {
			processData(Constants.COVID19_CONFIRMED_DATASET_INPUT_PATH, Constants.COVID19_CONFIRMED_URL);
			processData(Constants.COVID19_RECOVERED_DATASET_INPUT_PATH, Constants.COVID19_RECOVERED_URL);
			processData(Constants.COVID19_DEATHS_DATASET_INPUT_PATH, Constants.COVID19_DEATHS_URL);
		}

		static void processData(String filename, String url) {
			try (FileOutputStream fos = new FileOutputStream(filename)) {
				URL website = new URL(url);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			} catch(Exception e) {
				System.out.println("Could not download " + url + ", defaulting to backup ...");
			}

			if(CLEAN_DATA) {
				cleanData(filename);
			}
			
		}
		
		static void cleanData(String filename) {
			try {
				byte data[] = Files.readAllBytes(new File(filename).toPath());
				
				boolean inquotes = false;
				
				for(int i = 0; i < data.length; ++i) {
					if (data[i] == ' ' || data[i] == '*' || data[i] == '(' || data[i] == ')' || data[i] == '\'') { data[i] = '_'; }
					
					if (data[i] == '"') { inquotes = !inquotes; data[i] = '_'; }
					else if (inquotes && data[i] == ',') data[i] = '_';
				}
							
				Files.write(new File(filename).toPath(), data, StandardOpenOption.WRITE);
				
			} catch (IOException e) {
				System.out.println("Data cleaning failed. " + e + ": " + e.getMessage());
			}
		}
	}
}