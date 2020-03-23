import java.io.IOException;

/*
 * Main driver class that drives the program
 */
public class SemanticGen {

	public static void main(String...args) {
		try {
			System.out.println("Running the Semantic Generator...\n");

			System.out.println("Dataset 1: 2016 Deaths in 122 US Cities");

			DeathsDatasetConverter deathsConverter = new DeathsDatasetConverter();
			deathsConverter.run();

			System.out.println("Dataset 2: Unemployment Rate by Age Groups");

			UnemploymentDatasetConverter unemploymentConverter = new UnemploymentDatasetConverter();
			unemploymentConverter.run();

			System.out.println("Dataset 3: COVID-19 deaths, recoveries, and confirmed cases");

			Covid19DatasetConverter covid19Converter = new Covid19DatasetConverter();
			covid19Converter.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
