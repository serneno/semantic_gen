import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.HashMap;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import arq.iri;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.function.library.substr;
import org.apache.jena.vocabulary.*;

/*

Class that reads in an XML dataset representing 2016 deaths in 122 US Cities and outputs 
it into an RDF format.

*/
public class DeathsDatasetConverter extends DatasetToRdfConverter {
    private Document doc;  // the document that is getting built for the output
    private Map<String, String> states;
    public void parseInputFile(final String inputFile) {
        try {
            File inp = new File(inputFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inp);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            System.out.println("Error in configuring document builder: " + e.getMessage());
        } catch (SAXException e) {
            System.out.println("Error in parsing document: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error. " + e + ": " + e.getMessage());
        }
    }

    public void run () {
        parseInputFile(Constants.DEATHS_2016_DATASET_INPUT_PATH);
        stateMap();
        buildRdfModel();
        outputToRdf(Constants.DEATHS_2016_DATASET_OUTPUT_PATH, "deaths");
     }

     private void buildRdfModel() {
        // Create an empty model
        model = ModelFactory.createDefaultModel();

        // Properties describing each city's statistics
        Property rArea = model.createProperty(Constants.DEATHS_URI + "reporting_area");
        Property mmwrYear = model.createProperty(Constants.DEATHS_URI + "mmwr_year");
        Property mmwrWeek = model.createProperty(Constants.DEATHS_URI + "mmwr_week");
        Property aAges = model.createProperty(Constants.DEATHS_URI + "all_causes_by_age_years_all_ages");
        Property a65 = model.createProperty(Constants.DEATHS_URI + "all_causes_by_age_years_65");
        Property a45_64 = model.createProperty(Constants.DEATHS_URI + "all_causes_by_age_years_45_64");
        Property a25_44 = model.createProperty(Constants.DEATHS_URI + "all_causes_by_age_years_25_44");
        Property a1_24 = model.createProperty(Constants.DEATHS_URI + "all_causes_by_age_years_1_24");
        Property lessT1 = model.createProperty(Constants.DEATHS_URI + "all_causes_by_age_years_lt_1");
        Property piTot = model.createProperty(Constants.DEATHS_URI + "all_causes_by_age_years_p_i_total");
        Property loc = model.createProperty(Constants.DEATHS_URI + "location_1");

        // Sets the prefix
        model.setNsPrefix("noncovidDeaths", Constants.DEATHS_URI);
        // Gather each data piece, denoted by the xml attribute "row"
        NodeList dataList = doc.getElementsByTagName("row");
        
        for (int i = 0; i < dataList.getLength(); i++) {
            Node node = dataList.item(i);
            
            if (node.getNodeType() == Node.ELEMENT_NODE && node.hasAttributes()) {
                Element elem = (Element) node;
                String cityputr = elem.getAttribute("_address");
                String reportingArea = elem.getElementsByTagName("reporting_area").item(0).getTextContent();
                String stateAbbr = reportingArea.substring(reportingArea.length() - 2); // Doesn't work for D.C.
                if(states.containsKey(stateAbbr)) {
                    reportingArea = states.get(stateAbbr);
                }
                // For D.C.
                else if(stateAbbr.compareTo("C.") == 0){
                    reportingArea = states.get("DC");
                }
                else {
                    reportingArea = "";
                }

                String year = elem.getElementsByTagName("mmwr_year").item(0).getTextContent();
                String week = elem.getElementsByTagName("mmwr_week").item(0).getTextContent();
                String allAges = "";
                String ages65 = "";
                String ages45_64 = "";
                String ages25_44 = "";
                String ages1_24 = "";
                String lt1 = "";
                String piTotal = "";
                String location = "";

                // Properties that might be blank
                if(elem.getElementsByTagName("all_causes_by_age_years_all_ages").getLength() > 0) {
                    allAges = elem.getElementsByTagName("all_causes_by_age_years_all_ages").item(0).getTextContent();
                }
                if(elem.getElementsByTagName("all_causes_by_age_years_65").getLength() > 0) {
                    ages65 = elem.getElementsByTagName("all_causes_by_age_years_65").item(0).getTextContent();
                }
                if(elem.getElementsByTagName("all_causes_by_age_years_45_64").getLength() > 0) {
                    ages45_64 = elem.getElementsByTagName("all_causes_by_age_years_45_64").item(0).getTextContent();
                }
                if(elem.getElementsByTagName("all_causes_by_age_years_25_44").getLength() > 0) {
                    ages25_44 = elem.getElementsByTagName("all_causes_by_age_years_25_44").item(0).getTextContent();
                }
                if(elem.getElementsByTagName("all_causes_by_age_years_1_24").getLength() > 0) {
                    ages1_24 = elem.getElementsByTagName("all_causes_by_age_years_1_24").item(0).getTextContent();
                }
                if(elem.getElementsByTagName("all_causes_by_age_years_lt_1").getLength() > 0) {
                    lt1 = elem.getElementsByTagName("all_causes_by_age_years_lt_1").item(0).getTextContent();
                }
                if(elem.getElementsByTagName("all_causes_by_age_years_p_i_total").getLength() > 0) {
                    piTotal = elem.getElementsByTagName("all_causes_by_age_years_p_i_total").item(0).getTextContent();
                }
                if(elem.getElementsByTagName("location_1").getLength() > 0) {
                    Element loc_1 = (Element) elem.getElementsByTagName("location_1").item(0);
                    String address = loc_1.getAttribute("human_address");
                    int startCity = address.indexOf("city");
                    int endCity = address.indexOf("state");
                    location = address.substring(startCity + 8, endCity - 4);
                }

                // Creates the resource for each city along with the associated properties
                Resource cityURI = model.createResource(cityputr);
                cityURI.addProperty(rArea, reportingArea);
                cityURI.addProperty(mmwrYear, year);
                cityURI.addProperty(mmwrWeek, week);
                cityURI.addProperty(aAges, allAges);
                cityURI.addProperty(a65, ages65);
                cityURI.addProperty(a45_64, ages45_64);
                cityURI.addProperty(a25_44, ages25_44);
                cityURI.addProperty(a1_24, ages1_24);
                cityURI.addProperty(lessT1, lt1);
                cityURI.addProperty(piTot, piTotal);
                cityURI.addProperty(loc, location);
              }
           }
    }

    // Maps State Abbreviation to State Name
    private void stateMap() {
        states = new HashMap<String, String>();
        states.put("AL", "Alabama");
        states.put("AK", "Alaska");
        states.put("AZ", "Arizona");
        states.put("AR", "Arkansas");
        states.put("CA", "California");
        states.put("CO", "Colorado");
        states.put("CT", "Connecticut");
        states.put("DE", "Delaware");
        states.put("DC", "District Of Columbia");
        states.put("FL", "Florida");
        states.put("GA", "Georgia");
        states.put("HI", "Hawaii");
        states.put("ID", "Idaho");
        states.put("IL", "Illinois");
        states.put("IN", "Indiana");
        states.put("IA", "Iowa");
        states.put("KS", "Kansas");
        states.put("KY", "Kentucky");
        states.put("LA", "Louisiana");
        states.put("ME", "Maine");
        states.put("MD", "Maryland");
        states.put("MA", "Massachusetts");
        states.put("MI", "Michigan");
        states.put("MN", "Minnesota");
        states.put("MS", "Mississippi");
        states.put("MO", "Missouri");
        states.put("MT", "Montana");
        states.put("NE", "Nebraska");
        states.put("NV", "Nevada");
        states.put("NH", "New Hampshire");
        states.put("NJ", "New Jersey");
        states.put("NM", "New Mexico");
        states.put("NY", "New York");
        states.put("NC", "North Carolina");
        states.put("ND", "North Dakota");
        states.put("OH", "Ohio");
        states.put("OK", "Oklahoma");
        states.put("OR", "Oregon");
        states.put("PA", "Pennsylvania");
        states.put("RI", "Rhode Island");
        states.put("SC", "South Carolina");
        states.put("SD", "South Dakota");
        states.put("TN", "Tennessee");
        states.put("TX", "Texas");
        states.put("UT", "Utah");
        states.put("VT", "Vermont");
        states.put("VA", "Virginia");
        states.put("WA", "Washington");
        states.put("WV", "West Virginia");
        states.put("WI", "Wisconsin");
        states.put("WY", "Wyoming");
    }
}