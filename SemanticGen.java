/*
 * Main class that drives the program
 */
public class SemanticGen {

	public static void main(String[] args) {
		if (args.length > 0) {
			CSVReader csvr = new CSVReader(args[0]);
			csvr.csvParse();
			//csvr.print();
			//csvr.exportRDF();
			System.out.println("Done.");
		}
	}

}
