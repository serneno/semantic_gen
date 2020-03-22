public class OWLinator {	
	// 0 == body, 1 == distinctCountry, 2 == distinctRegion, 3 == distinctDay, 4 == distinctCase;
	static StringBuilder textSection[] = new StringBuilder[5];
	
	static int limit_entries, limit;
	
	public static String owlate(ParsedCovid19Data data) {
		for(int i = 0; i < textSection.length; ++i) { textSection[i] = new StringBuilder(); }
		for(int i = 1; i < textSection.length; ++i) { prepDistinct(textSection[i]); }
		
		limit = 10;
		
		limit_entries = 0;
		data.countries.forEach((x, country) -> generateCountry(country));
		limit_entries = 0;
		data.provinces.forEach((x, province) -> generateRegion(province));
		limit_entries = 0;
		data.dates.forEach((x, day) -> generateDay(day));
		limit_entries = 0;
		data.cases.forEach(c4se -> generateCase(c4se));
		
		for(int i = 1; i < textSection.length; ++i) { finishDistinct(textSection[i]); }
		
		StringBuilder result = new StringBuilder(STATIC_BODY1);
		result.append(textSection[0]);
		result.append(STATIC_BODY2);
		result.append(textSection[1]);
		result.append(textSection[2]);
		result.append(textSection[3]);
		result.append(textSection[4]);
		result.append(STATIC_BODY3);
		return result.toString();
	}

	static void generateCountry(ParsedCovid19Data.Country country) {
		if(Main.SMALL_ONTOLOGY_FOR_FAST_VALIDATION && ++limit_entries > 100) { return; }
		
		textSection[0].append("    <owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Country_").append(country.name).append("\">\r\n");
		textSection[0].append("        <rdf:type rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Country\"/>\r\n");
		country.provinces.forEach((prov, x) -> {
		textSection[0].append("        <hasRegion rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Region_").append(prov.name).append("\"/>\r\n");
		});
		textSection[0].append("    </owl:NamedIndividual>\r\n");
		
		setDistinct(textSection[1], "Country_", country.name);
	}
	
	static void generateRegion(ParsedCovid19Data.Province region) {
		if(Main.SMALL_ONTOLOGY_FOR_FAST_VALIDATION && ++limit_entries > 100) { return; }
		
		textSection[0].append("    <owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Region_").append(region.name).append("\">\r\n");
		textSection[0].append("        <rdf:type rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Region\"/>\r\n");
		textSection[0].append("        <hasCountry rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Country_").append(region.country.name).append("\"/>\r\n");
		region.covid19cases.forEach(c4se -> {
		textSection[0].append("        <hasCase rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case_").append(c4se.name).append("\"/>\r\n");
		});
		textSection[0].append("    </owl:NamedIndividual>\r\n");
		
		setDistinct(textSection[2], "Region_", region.name);
	}
	
	static void generateDay(ParsedCovid19Data.Date day) {
		if(Main.SMALL_ONTOLOGY_FOR_FAST_VALIDATION && ++limit_entries > 100) { return; }
		
		textSection[0].append("    <owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Day_").append(day.name).append("\">\r\n");
		textSection[0].append("        <rdf:type rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Day\"/>\r\n");
		textSection[0].append("        <date rdf:datatype=\"http://www.w3.org/2001/XMLSchema#dateTime\" xml:lang=\"en\">").append(toXSDDateTime(day.name)).append("</date>\r\n");
		day.covid19cases.forEach(c4se -> {
		textSection[0].append("        <hasCase rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case_").append(c4se.name).append("\"/>\r\n");
		});
		textSection[0].append("    </owl:NamedIndividual>\r\n");
		
		setDistinct(textSection[3], "Day_", day.name);
	}
	
	static void generateCase(ParsedCovid19Data.Covid19Case c4se) {
		if(Main.SMALL_ONTOLOGY_FOR_FAST_VALIDATION && ++limit_entries > 100) { return; }
		
		textSection[0].append("    <owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case_").append(c4se.name).append("\">\r\n");
		textSection[0].append("        <rdf:type rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case\"/>\r\n");
		textSection[0].append("        <hasRegion rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Region_").append(c4se.province.name).append("\"/>\r\n");
		textSection[0].append("        <hasDay rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Day_").append(c4se.date.name).append("\"/>\r\n");
		textSection[0].append("        <death rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\" xml:lang=\"en\">").append(c4se.death).append("</death>\r\n");
		textSection[0].append("        <recovered rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\" xml:lang=\"en\">").append(c4se.recovered).append("</recovered>\r\n");
		textSection[0].append("        <unknownSurvival rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\" xml:lang=\"en\">").append(c4se.confirmed - c4se.recovered - c4se.death).append("</unknownSurvival>\r\n");
		textSection[0].append("    </owl:NamedIndividual>\r\n");
		
		setDistinct(textSection[4], "Case_", c4se.name);
	}
	
	static void prepDistinct(StringBuilder b) {
		b.append(
				"    <rdf:Description>\r\n" + 
				"        <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#AllDifferent\"/>\r\n" + 
				"        <owl:distinctMembers rdf:parseType=\"Collection\">\r\n"
		);
		
		
	}
	
	static void setDistinct(StringBuilder b, String prefixName, String name) {
		b.append("            <rdf:Description rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#").append(prefixName).append(name).append("\"/>\n");		
	}
	
	static void finishDistinct(StringBuilder b) {
		b.append(
				"        </owl:distinctMembers>\r\n" + 
				"    </rdf:Description>\r\n"
		);
	}
	
	// Conversion
	// from "1/27/20"	//	01/27/20     1/3/20   01/26/20
	// to   YYYY-MM-DD
	static String toXSDDateTime(String dayName) {
		String dateString = "2020-";
		{
			int index = 0;

			// Fill in month
			if(dayName.charAt(1) == '/') {
				dateString += "0" + dayName.charAt(0);
				index = 2;
			}
			else {
				dateString += dayName.charAt(0) + dayName.charAt(1);
				index = 3;
			}
			
			dateString += '-';
			
			// Fill in day
			if(dayName.charAt(index + 1) == '/') {
				dateString += "0" + dayName.charAt(index);
			}
			else {
				dateString += String.valueOf(dayName.charAt(index)) + dayName.charAt(index + 1);
			}
		}
		
		return dateString + "T00:00:00";
	}
	
	
	// Static portions generated with protege:
	// ( They do not change based on the live update of the data )
	private static final String STATIC_BODY1 = "<?xml version=\"1.0\"?>\r\n" + 
			"<rdf:RDF xmlns=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#\"\r\n" + 
			"     xml:base=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0\"\r\n" + 
			"     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n" + 
			"     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n" + 
			"     xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\r\n" + 
			"     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\r\n" + 
			"     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\r\n" + 
			"     xmlns:CovidOntology=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#\">\r\n" + 
			"    <owl:Ontology rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0\"/>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- \r\n" + 
			"    ///////////////////////////////////////////////////////////////////////////////////////\r\n" + 
			"    //\r\n" + 
			"    // Object Properties\r\n" + 
			"    //\r\n" + 
			"    ///////////////////////////////////////////////////////////////////////////////////////\r\n" + 
			"     -->\r\n" + 
			"\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasCase -->\r\n" + 
			"\r\n" + 
			"    <owl:ObjectProperty rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasCase\">\r\n" + 
			"        <rdfs:subPropertyOf rdf:resource=\"http://www.w3.org/2002/07/owl#topObjectProperty\"/>\r\n" + 
			"        <rdfs:range rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case\"/>\r\n" + 
			"        <owl:propertyDisjointWith rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasDay\"/>\r\n" + 
			"        <owl:propertyDisjointWith rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasRegion\"/>\r\n" + 
			"        <rdfs:comment>This is not a specific instance of covid. It is a set of known covid occurences in a given time and place.</rdfs:comment>\r\n" + 
			"    </owl:ObjectProperty>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasCountry -->\r\n" + 
			"\r\n" + 
			"    <owl:ObjectProperty rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasCountry\">\r\n" + 
			"        <rdfs:subPropertyOf rdf:resource=\"http://www.w3.org/2002/07/owl#topObjectProperty\"/>\r\n" + 
			"        <rdfs:domain rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Region\"/>\r\n" + 
			"        <rdfs:range rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Country\"/>\r\n" + 
			"        <rdfs:comment>This property entails what country a Region is within.\r\n" + 
			"It is not an inverse of hasRegion since both Countries and Cases have regions.</rdfs:comment>\r\n" + 
			"    </owl:ObjectProperty>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasDay -->\r\n" + 
			"\r\n" + 
			"    <owl:ObjectProperty rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasDay\">\r\n" + 
			"        <rdfs:subPropertyOf rdf:resource=\"http://www.w3.org/2002/07/owl#topObjectProperty\"/>\r\n" + 
			"        <rdfs:domain rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case\"/>\r\n" + 
			"        <rdfs:range rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Day\"/>\r\n" + 
			"        <rdfs:comment>This is the Day entity in which the case of covid occured.\r\n" + 
			"Allows for quick reference to other cases during a given day.</rdfs:comment>\r\n" + 
			"    </owl:ObjectProperty>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasRegion -->\r\n" + 
			"\r\n" + 
			"    <owl:ObjectProperty rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasRegion\">\r\n" + 
			"        <rdfs:subPropertyOf rdf:resource=\"http://www.w3.org/2002/07/owl#topObjectProperty\"/>\r\n" + 
			"        <rdfs:range rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Region\"/>\r\n" + 
			"        <rdfs:comment>This is the region to which an entity relates.\r\n" + 
			"For Country this means the country contains said region.\r\n" + 
			"For a Case this means the case in which it was located.</rdfs:comment>\r\n" + 
			"    </owl:ObjectProperty>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- \r\n" + 
			"    ///////////////////////////////////////////////////////////////////////////////////////\r\n" + 
			"    //\r\n" + 
			"    // Data properties\r\n" + 
			"    //\r\n" + 
			"    ///////////////////////////////////////////////////////////////////////////////////////\r\n" + 
			"     -->\r\n" + 
			"\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#date -->\r\n" + 
			"\r\n" + 
			"    <owl:DatatypeProperty rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#date\">\r\n" + 
			"        <rdfs:subPropertyOf rdf:resource=\"http://www.w3.org/2002/07/owl#topDataProperty\"/>\r\n" + 
			"        <rdfs:domain rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Day\"/>\r\n" + 
			"        <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#dateTime\"/>\r\n" + 
			"        <owl:propertyDisjointWith rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#numberConfirmedCases\"/>\r\n" + 
			"        <rdfs:comment>The literal calender day.\r\n" + 
			"The time in all values is 00:00:00. This is not meaningful, protege just prefers dateTime rather than simply date.</rdfs:comment>\r\n" + 
			"    </owl:DatatypeProperty>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#death -->\r\n" + 
			"\r\n" + 
			"    <owl:DatatypeProperty rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#death\">\r\n" + 
			"        <rdfs:subPropertyOf rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#numberConfirmedCases\"/>\r\n" + 
			"        <rdfs:comment>Number of deaths from Covid19 infection during a given region/day.</rdfs:comment>\r\n" + 
			"    </owl:DatatypeProperty>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#numberConfirmedCases -->\r\n" + 
			"\r\n" + 
			"    <owl:DatatypeProperty rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#numberConfirmedCases\">\r\n" + 
			"        <rdfs:domain rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case\"/>\r\n" + 
			"        <rdfs:range rdf:resource=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\"/>\r\n" + 
			"        <rdfs:comment>Total number of confirmed cases.</rdfs:comment>\r\n" + 
			"    </owl:DatatypeProperty>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#recovered -->\r\n" + 
			"\r\n" + 
			"    <owl:DatatypeProperty rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#recovered\">\r\n" + 
			"        <rdfs:subPropertyOf rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#numberConfirmedCases\"/>\r\n" + 
			"        <rdfs:comment>Number of people who had Covid but are known to have survived in a given Region / Day.</rdfs:comment>\r\n" + 
			"    </owl:DatatypeProperty>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#unknownSurvival -->\r\n" + 
			"\r\n" + 
			"    <owl:DatatypeProperty rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#unknownSurvival\">\r\n" + 
			"        <rdfs:subPropertyOf rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#numberConfirmedCases\"/>\r\n" + 
			"        <rdfs:comment>Most cases, where there is a confirmed infection with Covid19 however the outcome is not well documented.</rdfs:comment>\r\n" + 
			"    </owl:DatatypeProperty>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- \r\n" + 
			"    ///////////////////////////////////////////////////////////////////////////////////////\r\n" + 
			"    //\r\n" + 
			"    // Classes\r\n" + 
			"    //\r\n" + 
			"    ///////////////////////////////////////////////////////////////////////////////////////\r\n" + 
			"     -->\r\n" + 
			"\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case -->\r\n" + 
			"\r\n" + 
			"    <owl:Class rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case\">\r\n" + 
			"        <rdfs:comment>This is not an individual case of Covid19. Instead this the set of known cases during a specific day and place.</rdfs:comment>\r\n" + 
			"        <rdfs:label>Covid19CaseSet</rdfs:label>\r\n" + 
			"    </owl:Class>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Country -->\r\n" + 
			"\r\n" + 
			"    <owl:Class rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Country\">\r\n" + 
			"        <rdfs:subClassOf rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Location\"/>\r\n" + 
			"        <owl:disjointWith rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Region\"/>\r\n" + 
			"        <rdfs:comment>Country entities have regions with specific cases. The countries themselves do not reference the cases within themselves directly.</rdfs:comment>\r\n" + 
			"    </owl:Class>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Day -->\r\n" + 
			"\r\n" + 
			"    <owl:Class rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Day\">\r\n" + 
			"        <rdfs:comment>This entity references all the cases that occured during a specific calender day.</rdfs:comment>\r\n" + 
			"    </owl:Class>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Location -->\r\n" + 
			"\r\n" + 
			"    <owl:Class rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Location\"/>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Region -->\r\n" + 
			"\r\n" + 
			"    <owl:Class rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Region\">\r\n" + 
			"        <rdfs:subClassOf rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Location\"/>\r\n" + 
			"        <rdfs:comment>Places within a country where case instances occured.</rdfs:comment>\r\n" + 
			"    </owl:Class>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- \r\n" + 
			"    ///////////////////////////////////////////////////////////////////////////////////////\r\n" + 
			"    //\r\n" + 
			"    // Individuals\r\n" + 
			"    //\r\n" + 
			"    ///////////////////////////////////////////////////////////////////////////////////////\r\n" + 
			"     -->\r\n" + 
			"\r\n" + 
			"    \r\n" + 
			"";
	
	private static final String STATIC_BODY2 = " <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Australia -->\r\n" + 
			"\r\n" + 
			"    <owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Australia\">\r\n" + 
			"        <rdf:type rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Country\"/>\r\n" + 
			"        <hasRegion rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Syndey\"/>\r\n" + 
			"    </owl:NamedIndividual>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case0 -->\r\n" + 
			"\r\n" + 
			"    <owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case0\">\r\n" + 
			"        <rdf:type rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case\"/>\r\n" + 
			"        <hasDay rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Day0\"/>\r\n" + 
			"        <hasRegion rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Syndey\"/>\r\n" + 
			"        <death rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">50</death>\r\n" + 
			"        <recovered rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">100</recovered>\r\n" + 
			"        <unknownSurvival rdf:datatype=\"http://www.w3.org/2001/XMLSchema#nonNegativeInteger\">1000</unknownSurvival>\r\n" + 
			"    </owl:NamedIndividual>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case1 -->\r\n" + 
			"\r\n" + 
			"    <owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case1\">\r\n" + 
			"        <rdf:type rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case\"/>\r\n" + 
			"        <hasDay rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Day0\"/>\r\n" + 
			"        <hasRegion rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Syndey\"/>\r\n" + 
			"    </owl:NamedIndividual>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Day0 -->\r\n" + 
			"\r\n" + 
			"    <owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Day0\">\r\n" + 
			"        <rdf:type rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Day\"/>\r\n" + 
			"        <hasCase rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case0\"/>\r\n" + 
			"        <hasCase rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case1\"/>\r\n" + 
			"        <hasRegion rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Syndey\"/>\r\n" + 
			"        <date rdf:datatype=\"http://www.w3.org/2001/XMLSchema#dateTime\">2020-03-12T00:00:00</date>\r\n" + 
			"    </owl:NamedIndividual>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Syndey -->\r\n" + 
			"\r\n" + 
			"    <owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Syndey\">\r\n" + 
			"        <rdf:type rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Region\"/>\r\n" + 
			"        <hasCase rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case0\"/>\r\n" + 
			"        <hasCase rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case1\"/>\r\n" + 
			"        <hasCountry rdf:resource=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Australia\"/>\r\n" + 
			"    </owl:NamedIndividual>\r\n" + 
			"    \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"    <!-- \r\n" + 
			"    ///////////////////////////////////////////////////////////////////////////////////////\r\n" + 
			"    //\r\n" + 
			"    // General axioms\r\n" + 
			"    //\r\n" + 
			"    ///////////////////////////////////////////////////////////////////////////////////////\r\n" + 
			"     -->\r\n" + 
			"\r\n" + 
			"    <rdf:Description>\r\n" + 
			"        <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#AllDisjointClasses\"/>\r\n" + 
			"        <owl:members rdf:parseType=\"Collection\">\r\n" + 
			"            <rdf:Description rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Case\"/>\r\n" + 
			"            <rdf:Description rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Day\"/>\r\n" + 
			"            <rdf:Description rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#Location\"/>\r\n" + 
			"        </owl:members>\r\n" + 
			"    </rdf:Description>\r\n" + 
			"    <rdf:Description>\r\n" + 
			"        <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#AllDisjointProperties\"/>\r\n" + 
			"        <owl:members rdf:parseType=\"Collection\">\r\n" + 
			"            <rdf:Description rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasCountry\"/>\r\n" + 
			"            <rdf:Description rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasDay\"/>\r\n" + 
			"            <rdf:Description rdf:about=\"http://www.semanticweb.org/user/ontologies/2020/CovidOntology/1.0.0#hasRegion\"/>\r\n" + 
			"        </owl:members>\r\n" + 
			"    </rdf:Description>";
	
	private static final String STATIC_BODY3 = "</rdf:RDF>\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"<!-- Generated by the OWL API (version 4.5.9.2019-02-01T07:24:44Z) https://github.com/owlcs/owlapi -->\r\n" + 
			"";
}









