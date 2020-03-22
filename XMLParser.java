import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

public class XMLParser {
    public static void main(String[] args) {

        try {
           File inputFile = new File("deaths_cities_2016.xml");
           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
           Document doc = dBuilder.parse(inputFile);
           doc.getDocumentElement().normalize();

           // create an empty model
           Model model = ModelFactory.createDefaultModel();
           
           // URI
           String NS = "https://data.cdc.gov/resource/rpjd-ejph#"; 

           // Properties describing each city's statistics
           Property rArea = model.createProperty(NS + "reporting_area");
           Property mmwrYear = model.createProperty(NS + "mmwr_year");
           Property mmwrWeek = model.createProperty(NS + "mmwr_week");
           Property aAges = model.createProperty(NS + "all_causes_by_age_years_all_ages");
           Property a65 = model.createProperty(NS + "all_causes_by_age_years_65");
           Property a45_64 = model.createProperty(NS + "all_causes_by_age_years_45_64");
           Property a25_44 = model.createProperty(NS + "all_causes_by_age_years_25_44");
           Property a1_24 = model.createProperty(NS + "all_causes_by_age_years_1_24");
           Property lessT1 = model.createProperty(NS + "all_causes_by_age_years_lt_1");
           Property piTot = model.createProperty(NS + "all_causes_by_age_years_p_i_total");
           Property loc = model.createProperty(NS + "location_1");
           NodeList nList = doc.getElementsByTagName("row");
           
           for (int temp = 0; temp < nList.getLength(); temp++) {
              Node nNode = nList.item(temp);
              
              if (nNode.getNodeType() == Node.ELEMENT_NODE && nNode.hasAttributes()) {
                Element eElement = (Element) nNode;
                String cityAddr = eElement.getAttribute("_address");
                String reportingArea = eElement.getElementsByTagName("reporting_area").item(0).getTextContent();
                String year = eElement.getElementsByTagName("mmwr_year").item(0).getTextContent();
                String week = eElement.getElementsByTagName("mmwr_week").item(0).getTextContent();
                String allAges = "";
                String ages65 = "";
                String ages45_64 = "";
                String ages25_44 = "";
                String ages1_24 = "";
                String lt1 = "";
                String piTotal = "";
                String location = "";
                // Properties that might be blank
                if(eElement.getElementsByTagName("all_causes_by_age_years_all_ages").getLength() > 0) {
                    allAges = eElement.getElementsByTagName("all_causes_by_age_years_all_ages").item(0).getTextContent();
                }
                if(eElement.getElementsByTagName("all_causes_by_age_years_65").getLength() > 0) {
                    ages65 = eElement.getElementsByTagName("all_causes_by_age_years_65").item(0).getTextContent();
                }
                if(eElement.getElementsByTagName("all_causes_by_age_years_45_64").getLength() > 0) {
                    ages45_64 = eElement.getElementsByTagName("all_causes_by_age_years_45_64").item(0).getTextContent();
                }
                if(eElement.getElementsByTagName("all_causes_by_age_years_25_44").getLength() > 0) {
                    ages25_44 = eElement.getElementsByTagName("all_causes_by_age_years_25_44").item(0).getTextContent();
                }
                if(eElement.getElementsByTagName("all_causes_by_age_years_1_24").getLength() > 0) {
                    ages1_24 = eElement.getElementsByTagName("all_causes_by_age_years_1_24").item(0).getTextContent();
                }
                if(eElement.getElementsByTagName("all_causes_by_age_years_lt_1").getLength() > 0) {
                    lt1 = eElement.getElementsByTagName("all_causes_by_age_years_lt_1").item(0).getTextContent();
                }
                if(eElement.getElementsByTagName("all_causes_by_age_years_p_i_total").getLength() > 0) {
                    piTotal = eElement.getElementsByTagName("all_causes_by_age_years_p_i_total").item(0).getTextContent();
                }
                if(eElement.getElementsByTagName("location_1").getLength() > 0) {
                    Element loc_1 = (Element) eElement.getElementsByTagName("location_1").item(0);
                    String address = loc_1.getAttribute("human_address");
                    int startCity = address.indexOf("city");
                    int endCity = address.indexOf("state");
                    location = address.substring(startCity + 8, endCity - 4);
                }

                // Creates the resource for each city along with the associated properties
                Resource cityURI = model.createResource(cityAddr);
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
           model.write(new FileOutputStream("test2.rdf"), "RDF/XML");
        } catch (Exception e) {
           e.printStackTrace();
        }
     }
}