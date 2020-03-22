import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class Covid19DatasetConverter {
	public static final boolean UPDATE_DATA = false;
	public static final boolean CLEAN_DATA = false;
	
	public static final String COVID19_CONFIRMED_URL = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_19-covid-Confirmed.csv&filename=time_series_2019-ncov-Confirmed.csv";
	public static final String COVID19_RECOVERED_URL = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_19-covid-Recovered.csv&filename=time_series_2019-ncov-Recovered.csv";
	public static final String COVID19_DEATHS_URL = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_19-covid-Deaths.csv&filename=time_series_2019-ncov-Deaths.csv";
	
	public static final String CONFIRMED_DATA_PATH = "confirmed.csv";
	public static final String RECOVERED_DATA_PATH = "recovered.csv";
	public static final String DEATH_DATA_PATH = "deaths.csv";
	public static final String DATA_PATHS[] = {CONFIRMED_DATA_PATH, RECOVERED_DATA_PATH, DEATH_DATA_PATH};
	
	public static final String ONTOLOGY_URI = "http://www.notreal.org/covid_ontology_test#";
	
	public static final boolean SMALL_ONTOLOGY_FOR_FAST_VALIDATION = true;
	
	public static void main(String...args) throws IOException {
		// Querries live data from "the Johns Hopkins University Center for Systems Science and Engineering (JHU CSSE). Also, Supported by ESRI Living Atlas Team and the Johns Hopkins University Applied Physics Lab (JHU APL)."
		// Also does data cleaning
		QuerryData.updateData();
		
		// Inflates the .csv data into Objects
		ParsedCovid19Data data = new ParsedCovid19Data();
		
		// Just some queries out of curiousity
		//testQuery(data);
		//testQuery2(data);
		
		// Mixes statically generated ontology parts from Protege with newly generated lines from the inflated data.
		String ontology = OWLinator.owlate(data);
		
		Files.write(new File("CovidOntology4Class1.owl").toPath(), ontology.getBytes(), StandardOpenOption.CREATE);
	
		// Validator: https://www.w3.org/RDF/Validator/  
		// Passed
		// tested with ConvidOntology_small_for_validation_testing.owl the full file is too large for the website.
		
		// Validation 2: Large version opened and checked using protege.
		
		// Validation 3: Hermit in protege
	}
	
	static long sum;
	static long maxDeaths;
	static String badPlace;
	static void testQuery(ParsedCovid19Data data) {
		data.countries.forEach((x, country) -> {
			sum = 0;
			
			country.provinces.forEach((prov, x2) -> {
				prov.covid19cases.forEach((c4se) -> {
					sum += c4se.death;
				});
			});
			
			if (sum > maxDeaths) { maxDeaths = sum; badPlace = country.name; }
		});
		
		System.out.println(badPlace+" , with " +maxDeaths);
	}
	
	static long totalConfirmed;
	static long totalRecovered;
	static long totalDead;
	static void testQuery2(ParsedCovid19Data data) {
		data.countries.forEach((x, country) -> {
			sum = 0;
			
			country.provinces.forEach((prov, x2) -> {
				prov.covid19cases.forEach((c4se) -> {
					totalConfirmed += c4se.confirmed;
					totalRecovered += c4se.recovered;
					totalDead += c4se.death;
				});
			});			
		});
		
		System.out.println(totalConfirmed+", "+totalRecovered+", "+totalDead);
	}
}