/*
This class houses all common methods for the 3 dataset converters.
*/
public abstract class DatasetConverter {
    // Constants to be used
    public static final String DEATHS_2016_DATASET_INPUT_PATH = "./dataset_inputs/deaths_cities_2016.xml";
    public static final String DEATHS_2016_DATASET_OUTPUT_PATH = "./dataset_outputs/deaths_cities_2016.rdf";

    // Method to run the converter from some input to some ontology file output (RDF or OWL)
    abstract void run();

    // Method to parse some input file containing a raw dataset
    abstract void parseInputFile(final String inputFile);

    // Method to output the dataset into an ontology file output
    abstract void outputToFile(final String outputFile);
}