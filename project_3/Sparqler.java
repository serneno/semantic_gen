import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.query.*;

import java.io.*;

/*
    @author: JC

    // https://jena.apache.org/documentation/query/app_api.html

    // Async background load - JC 4/24/2020
    // Combined model file - JC 4/24/2020
*/
public class Sparqler {
    // @input:
    // Main.mQueryText - contains the query

    // @output:
    // Main.mResultText

    //// On success
    // This function should perform the query
    // from the string Main.mQueryText and placing
    // the result in Main.mResultText, then return true.
    
    //// On failure
    // This function should place a user readable error
    // message into Main.mResultText, then return false.
    public static boolean sparql() {
        boolean success = true;

        try (QueryExecution queryExecution = QueryExecutionFactory.create(QueryFactory.create(Main.mQueryText), model)) {
            ResultSet results = queryExecution.execSelect();
            StringBuilder sb = new StringBuilder();
            
            while(results.hasNext()) { sb.append(results.nextSolution().toString()).append("\n"); }

            Main.mResultText = "Results:\n\n"+sb.toString();
        } catch (Exception e) { Main.mResultText = e.toString(); success = false;}

        return success;
    }

    public static Model init() { return model; }
    static Model model;
    static {
        model = ModelFactory.createDefaultModel();
        InputStream modelStream = FileManager.get().open("DB/combined.owl");
        if(modelStream == null) { throw new RuntimeException("Can't find ontology file"); }
        model.read(modelStream, null);
    }
}