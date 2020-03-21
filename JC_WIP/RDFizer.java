

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.ModelFactory;

public class RDFizer {

	public static String rdfize(ParsedCovid19Data data) {
		StringBuilder result = new StringBuilder();
		//result.append("<?xml version=\"1.0\"?>\n");
		
		applyRDFS();
		
		//processCovidData(result, data);
		
		//result.append("\n\n</rdf:RDF>");
		
		return result.toString();
	}
	
	static String classes[] = {"Location", "Day", "Case"};
	static String subclasses[] = {"Country", "Region"};
	static String properties[] = {"numberConfirmedCases", "hasRegion", "hasCountry", "hasCovid", "date"};
	static String subproperties[] = {"survialStatusUnknown", "Recovered", "Death"};
	
	static void applyRDFS() {
		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM );
        String NS = "http://example.com/test#";

        OntClass locationClass = m.createClass(NS + classes[0]);
        OntClass dayClass = m.createClass(NS + classes[1]);
        OntClass caseClass = m.createClass(NS + classes[2]);
        OntClass countryClass = m.createClass(NS + subclasses[0]);
        OntClass regionClass = m.createClass(NS + subclasses[1]);
        
        OntClass nonZeroClass = m.createClass("xs:nonNegativeInteger");
        OntClass dateClass = m.createClass("xs:date");

        locationClass.addSubClass(countryClass);
        locationClass.addSubClass(regionClass);

        OntProperty numConfirmedCasesProperty = m.createOntProperty(NS + properties[0]);
        OntProperty hasRegionProperty = m.createOntProperty(NS + properties[1]);
        OntProperty hasCountryProperty = m.createOntProperty(NS + properties[2]);
        OntProperty hasCovidProperty = m.createOntProperty(NS + properties[3]);
        OntProperty dateProperty = m.createOntProperty(NS + properties[4]);
        OntProperty survivalStatusUnknownProperty = m.createOntProperty(NS + subproperties[0]);
        OntProperty recoveredProperty = m.createOntProperty(NS + subproperties[1]);
        OntProperty deathProperty = m.createOntProperty(NS + subproperties[2]);
           
        numConfirmedCasesProperty.addSubProperty(survivalStatusUnknownProperty);
        numConfirmedCasesProperty.addSubProperty(recoveredProperty);
        numConfirmedCasesProperty.addSubProperty(deathProperty);
     
        
        numConfirmedCasesProperty.addDomain(caseClass);
        dateProperty.addDomain(dayClass);
        
        /**
         * applyRDFSRange(sb, properties[0], "xs:nonNegativeInteger", true);
			// ^ Implies same for sub-properties
		applyRDFSRange(sb, properties[4], "xs:date", true);
         *  NOT SURE I WROTE THESE 2 RIGHT.......................
         */
        numConfirmedCasesProperty.addRange(nonZeroClass);
        dateProperty.addRange(dateClass);
        hasRegionProperty.addRange(regionClass);
        hasCountryProperty.addRange(countryClass);
        hasCovidProperty.addRange(caseClass);
        
        m.write(System.out, "RDF/XML");
	}
	/**
	static void applyRDFS(StringBuilder sb) {
		for(String cl4ss : classes) { makeRDFSClass(sb, cl4ss); }
		sb.append("\n");
		
		makeRDFSSubclass(sb, subclasses[0], classes[0]);
		makeRDFSSubclass(sb, subclasses[1], classes[0]);
		sb.append("\n");
		
		for(String property : properties) { makeRDFSProperty(sb, property); }
		sb.append("\n");
		
		makeRDFSSubproperty(sb, subproperties[0], properties[0]);
		makeRDFSSubproperty(sb, subproperties[1], properties[0]);
		makeRDFSSubproperty(sb, subproperties[2], properties[0]);
		sb.append("\n");
		
		// Note: Multi-domains are difficult w/o owl. -JC
		applyRDFSDomain(sb, properties[0], classes[2]);
			// ^ Implies same for sub-properties
		applyRDFSDomain(sb, properties[4], classes[1]);
		sb.append("\n");
		
		applyRDFSRange(sb, properties[0], "xs:nonNegativeInteger", true);
			// ^ Implies same for sub-properties
		applyRDFSRange(sb, properties[1], subclasses[1], false);
		applyRDFSRange(sb, properties[2], subclasses[0], false);
		applyRDFSRange(sb, properties[3], classes[2], false);
		applyRDFSRange(sb, properties[4], "xs:date", true);
		sb.append("\n");
	}
	
	static void processCovidData(StringBuilder sb, ParsedCovid19Data data) {
		data.countries.forEach((x, country) -> processCountry(sb, country));
		data.provinces.forEach((x, prov) -> processRegion(sb, prov));
		data.dates.forEach((x, day) -> processDay(sb, day));
	}
	
	static void processCountry(StringBuilder sb, ParsedCovid19Data.Country country) {
		sb.append("<rdf:Description rdf:ID=\"Country_").append(country.name).append("\">\n");
		sb.append("  <rdf:type rdf:resource=\"#Country\"/>\n");
		sb.append("</rdf:Description>\n");
	}

	static void processRegion(StringBuilder sb, ParsedCovid19Data.Province province) {
		sb.append("<rdf:Description rdf:ID=\"Region_").append(province.name).append("\">\n");
		sb.append("  <rdf:type rdf:resource=\"#Region\"/>\n");
		sb.append("</rdf:Description>\n");
	}
	
	static void processDay(StringBuilder sb, ParsedCovid19Data.Date day) {
		sb.append("<rdf:Description rdf:ID=\"Day_").append(day.name.replace('/', '_')).append("\">\n");
		sb.append("  <rdf:type rdf:resource=\"#Day\"/>\n");
		sb.append("</rdf:Description>\n");
	}
	
	static void makeRDFSClass(StringBuilder sb, String name) { 
		sb.append("<rdfs:Class rdf:ID=\"").append(name).append("\"/>\n");
	}
	
	static void makeRDFSSubclass(StringBuilder sb, String name, String parent) {
		sb.append("<rdfs:Class rdf:ID=\"").append(name).append("\">\n")
		.append("  <rdfs:subClassOf rdf:resource=\"#").append(parent).append("\"/>\n")
		.append("</rdfs:Class>\n");
	}
	
	static void makeRDFSProperty(StringBuilder sb, String name) {
		sb.append("<rdf:Property rdf:ID=\"").append(name).append("\"/>\n");
	}
	
	static void makeRDFSSubproperty(StringBuilder sb, String name, String parent) {
		sb.append("<rdf:Property rdf:ID=\"").append(name).append("\">\n")
		.append("  <rdfs:subPropertyOf rdf:resource=\"#").append(parent).append("\"/>\n")
		.append("</rdf:Property>\n");
	}
	
	static void applyRDFSDomain(StringBuilder sb, String name, String domain) {
		sb.append("<rdf:Description rdf:about=\"#").append(name).append("\">\n")
		.append("  <rdfs:domain rdfs:resource=\"#").append(domain).append("\"/>\n")
		.append("</rdf:Description>\n");
	}
	
	static void applyRDFSRange(StringBuilder sb, String name, String range, boolean primative) {
		sb.append("<rdf:Description rdf:about=\"#").append(name).append("\">\n")
		.append("  <rdfs:range rdfs:resource=\"").append(primative?"":"#").append(range).append("\"/>\n")
		.append("</rdf:Description>\n");
	}
	
	*/
}














