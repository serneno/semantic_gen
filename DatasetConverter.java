/*
This class houses all common methods for the 3 dataset converters.
*/
public abstract class DatasetConverter {
    // Constants to be used
    public static final String INPUT_ROOT = "./datasets/";
    public static final String OUTPUT_ROOT = "./outputs/";

    public static final String DEATHS_2016_DATASET_INPUT_PATH = INPUT_ROOT + "deaths_cities_2016.xml";
    public static final String DEATHS_2016_DATASET_OUTPUT_PATH = OUTPUT_ROOT + "deaths_cities_2016.rdf";

    public static final String UNEMPLOYMENT_DATASET_INPUT_PATH = INPUT_ROOT + "Unemployment_Rate_by_Age_Groups.csv";
    public static final String UNEMPLOYMENT_DATASET_OUTPUT_PATH = OUTPUT_ROOT + "Unemployment_Rate_by_Age_Groups.csv";

    // Method to run the converter from some input to some ontology file output (RDF or OWL)
    abstract void run();

    // Method to parse some input file containing a raw dataset
    abstract void parseInputFile(final String inputFile);

    // Method to output the dataset into an ontology file output
    abstract void outputToFile(final String outputFile);
}