import java.io.File;
import java.io.FileOutputStream;

import org.apache.jena.rdf.model.Model;

/*
This class houses all common methods for the 3 dataset converters as well as constant values.
*/
public abstract class DatasetToRdfConverter {
    public Model model;  // the RDF model that will be built upon

    /* Method to run the converter from some input to some ontology file output (RDF or OWL) */
    abstract void run();

    /* Method to parse some input file containing a raw dataset */
    abstract void parseInputFile(final String inputFile);

    /* Method to output the dataset into an ontology file output */
    public void outputToRdf(final String outputFile, final String datasetSubject) {
        try {
            System.out.println("Writing " + datasetSubject + " dataset into rdf...");
            model.write(new FileOutputStream(outputFile), "RDF/XML");
            System.out.println("Done! Output file: '" + outputFile + "'\n");
        } catch (Exception e) {
            System.out.println("Error outputting data into rdf. " + e + ": " + e.getMessage());
        }
    }
}