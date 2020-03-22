import java.io.File;
import java.io.FileOutputStream;

import org.apache.jena.rdf.model.Model;

/*
This class houses all common methods for the 3 dataset converters.
*/
public abstract class DatasetConverter {
    // Constants to be used
    public static final String INPUT_ROOT = "./sem_gen_inputs/";
    public static final String OUTPUT_ROOT = "./sem_gen_outputs/";

    public static final String DEATHS_2016_DATASET_INPUT_PATH = INPUT_ROOT + "deaths_cities_2016.xml";
    public static final String DEATHS_2016_DATASET_OUTPUT_PATH = OUTPUT_ROOT + "deaths_cities_2016.rdf";

    public static final String UNEMPLOYMENT_DATASET_INPUT_PATH = INPUT_ROOT + "Unemployment_Rate_by_Age_Groups.csv";
    public static final String UNEMPLOYMENT_DATASET_OUTPUT_PATH = OUTPUT_ROOT + "Unemployment_Rate_by_Age_Groups.rdf";

    public Model model;  // the model that will be built upon

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