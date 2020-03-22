// import java.io.BufferedReader;
// import java.io.BufferedWriter;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.PrintWriter;
// import java.util.Arrays;
// import java.util.Collection;
// import java.util.Iterator;
// import java.util.Scanner;

// import org.apache.commons.io.FileUtils;
// import org.apache.jena.graph.*;
// import org.apache.jena.query.*;
// import org.apache.jena.rdf.model.*;
// import org.apache.jena.util.FileManager;
// import org.apache.jena.vocabulary.RDF; 

// import org.apache.jena.graph.Node;
// import org.apache.jena.graph.NodeFactory;
// import org.apache.jena.graph.Triple;
// import org.apache.jena.propertytable.Column;
// import org.apache.jena.propertytable.PropertyTable;
// import org.apache.jena.propertytable.Row;
// import org.apache.jena.propertytable.graph.GraphCSV;
// import org.apache.jena.propertytable.lang.CSV2RDF;
// import org.apache.jena.query.Query;
// import org.apache.jena.query.QueryExecution;
// import org.apache.jena.query.QueryExecutionFactory;
// import org.apache.jena.query.QueryFactory;
// import org.apache.jena.query.QuerySolution;
// import org.apache.jena.query.ResultSet;
// import org.apache.jena.rdf.model.Model;
// import org.apache.jena.rdf.model.ModelFactory;
// import org.apache.jena.rdf.model.RDFNode;
// import org.apache.jena.rdf.model.ResourceFactory;
// import org.apache.jena.rdf.model.Statement;
// import org.apache.jena.util.FileManager;
// import org.apache.jena.vocabulary.RDF; 


// public class CSVToRDF {

// 	public static void main(String[] args) {
// 		convertCSVToRDF("https://data.humdata.org/dataset/5dff64bc-a671-48da-aa87-2ca40d7abf02/resource/4cd2eaa1-fd3e-4371-a234-a8ef2b44cc1f", 
// 				"confirmed.csv", "outputfile.txt");
// 	}
	
// 	public static void convertCSVToRDF(String file, String inputFilename, String outputFilename) {
// 		// Start up Jena and create an RDF graph from the csv file
// 		CSV2RDF.init();
// 		GraphCSV newGraph = new GraphCSV(inputFilename);
// 		Model model = ModelFactory.createModelForGraph(newGraph);
		
// 		//Collection<Column> columns = newGraph.getPropertyTable().getColumns();
// 		//Collection<Row> rows = newGraph.getPropertyTable().getAllRows();
// 		//System.out.println(columns.size() + " " + rows.size());
		
// 		//Manually insert class triples for each instance in the CSV file
//         String sparqlQueryString = "select distinct ?s where  {?s ?p ?o}";
//         Query query = QueryFactory.create(sparqlQueryString);
//         QueryExecution qexec = QueryExecutionFactory.create(sparqlQueryString, model);
//         ResultSet s = qexec.execSelect();
//         Model m2 = ModelFactory.createDefaultModel();

//         // Populate model 2
//         while(s.hasNext()) {
//            QuerySolution so = s.nextSolution();
//            Triple t = new Triple(so.getResource("s").asNode(),RDF.type.asNode(),
//         		   NodeFactory.createBlankNode(file));
//            Statement stmt = ResourceFactory.createStatement(so.getResource("s"), RDF.type, 
//         		   ResourceFactory.createResource(file));
//            m2.add(stmt);
//         }
        
//         //create a new RDF graph that is the union of the old graph with the new graph containing the new rows
//         Model m3 = ModelFactory.createUnion(model, m2); 

// 		// Write the result RDF to an output file 
//         try {
//                FileWriter out = new FileWriter(outputFilename);
//                m3.write(out);
//         } catch (Exception e) {
//                System.out.println("Error in the file output process!");
//                e.printStackTrace();
//         }
// 	}
// }








