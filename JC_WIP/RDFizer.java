
public class RDFizer {

	public static String rdfize(ParsedCovid19Data data) {
		StringBuilder result = new StringBuilder();
		
		result.append("<?xml version=\"1.0\"?>\n")
			.append("\n")
			.append("<rdf:RDF\n")
			.append("xmlns:rdf = \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n")
			.append("xmlns:rdfs = \"http://www.w3.org/2000/01/rdf-schema#\">\n")
			.append("\n");
		
		processCovidData(result, data);
		
		result.append("\n\n</rdf:RDF>");
		
		return result.toString();
	}
	
	static void processCovidData(StringBuilder sb, ParsedCovid19Data data) {
		
		
		data.countries.forEach((x, country) -> {
			processCountry(sb, country);
		});
		
		
	}
	
	static void processCountry(StringBuilder sb, ParsedCovid19Data.Country country) {
		
		
		
	}
}









