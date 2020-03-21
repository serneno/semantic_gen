import java.io.IOException;
import java.io.FileWriter;
public class Main {
	public static final boolean UPDATE_DATA = false;
	public static final boolean CLEAN_DATA = false;
	
	public static final String COVID19_CONFIRMED_URL = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_19-covid-Confirmed.csv&filename=time_series_2019-ncov-Confirmed.csv";
	public static final String COVID19_RECOVERED_URL = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_19-covid-Recovered.csv&filename=time_series_2019-ncov-Recovered.csv";
	public static final String COVID19_DEATHS_URL = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_19-covid-Deaths.csv&filename=time_series_2019-ncov-Deaths.csv";
	
	public static final String CONFIRMED_DATA_PATH = "confirmed.csv";
	public static final String RECOVERED_DATA_PATH = "recovered.csv";
	public static final String DEATH_DATA_PATH = "deaths.csv";
	public static final String DATA_PATHS[] = {CONFIRMED_DATA_PATH, RECOVERED_DATA_PATH, DEATH_DATA_PATH};
	
	public static void main(String...args) throws IOException {
		QuerryData.updateData();
		
		ParsedCovid19Data data = new ParsedCovid19Data();
		
		//testQuery(data);
		
		//testQuery2(data);
		
		String rdf = RDFizer.rdfize(data);
		
		//Validator: https://www.w3.org/RDF/Validator/
		
		//System.out.println(rdf);
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
			
			if (sum > maxDeaths) { maxDeaths = sum; badPlace = country.name; }
		});
		
		System.out.println(totalConfirmed+", "+totalRecovered+", "+totalDead);
	}
}
