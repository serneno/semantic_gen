/*
 * Main class that drives the program
 */
public class SemanticGen {

	public static void main(String[] args) {
		CSVReader csvr = new CSVReader(args[0]);
		csvr.csvParse();

		System.out.println("Done.");
	}

}
