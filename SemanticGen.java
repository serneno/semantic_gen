/*
 * Main class that drives the program
 */
public class SemanticGen {

	public static void main(String...args) {
		System.out.println("Running the Semantic Generator.\n");

		System.out.println("Dataset 1: 2016 Deaths in 122 US Cities");

		DeathsDatasetConverter deathsConverter = new DeathsDatasetConverter();
		deathsConverter.run();

		System.out.println("Dataset 2: Unemployment Rate by Age Groups");

		UnemploymentDatasetConverter unemploymentConverter = new UnemploymentDatasetConverter();
		unemploymentConverter.run();

	}

}
