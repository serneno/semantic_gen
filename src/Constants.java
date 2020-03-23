public final class Constants {
    public static final String INPUT_ROOT = "./sem_gen_inputs/";
    public static final String OUTPUT_ROOT = "./sem_gen_outputs/";

    public static final String DEATHS_2016_DATASET_INPUT_PATH = INPUT_ROOT + "deaths_cities_2016.xml";
    public static final String DEATHS_2016_DATASET_OUTPUT_PATH = OUTPUT_ROOT + "deaths_cities_2016.rdf";
    public static final String DEATHS_URI = "https://data.cdc.gov/resource/rpjd-ejph#";

    public static final String UNEMPLOYMENT_DATASET_INPUT_PATH = INPUT_ROOT + "Unemployment_Rate_by_Age_Groups.csv";
    public static final String UNEMPLOYMENT_DATASET_OUTPUT_PATH = OUTPUT_ROOT + "Unemployment_Rate_by_Age_Groups.rdf";
    public static final String UNEMPLOYMENT_URI = "https://data.edd.ca.gov/api/views/bcij-5wym/rows.csv?accessType=DOWNLOAD";
    
    // Live data sources for COVID-19 datasets
	public static final String COVID19_CONFIRMED_URL = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_19-covid-Confirmed.csv&filename=time_series_2019-ncov-Confirmed.csv";
	public static final String COVID19_RECOVERED_URL = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_19-covid-Recovered.csv&filename=time_series_2019-ncov-Recovered.csv";
	public static final String COVID19_DEATHS_URL = "https://data.humdata.org/hxlproxy/api/data-preview.csv?url=https%3A%2F%2Fraw.githubusercontent.com%2FCSSEGISandData%2FCOVID-19%2Fmaster%2Fcsse_covid_19_data%2Fcsse_covid_19_time_series%2Ftime_series_19-covid-Deaths.csv&filename=time_series_2019-ncov-Deaths.csv";
    
    // Stored local data sources for COVID-19 datasets
	public static final String COVID19_CONFIRMED_DATASET_INPUT_PATH = INPUT_ROOT + "covid_confirmed.csv";
	public static final String COVID19_RECOVERED_DATASET_INPUT_PATH = INPUT_ROOT + "covid_recovered.csv";
	public static final String COVID19_DEATHS_DATASET_INPUT_PATH = INPUT_ROOT + "covid_deaths.csv";
    public static final String ALL_COVID19_DATASET_INPUT_PATHS[] = {COVID19_CONFIRMED_DATASET_INPUT_PATH, COVID19_RECOVERED_DATASET_INPUT_PATH, COVID19_DEATHS_DATASET_INPUT_PATH};
    public static final String COVID19_DATASET_OUTPUT_PATH = OUTPUT_ROOT + "CovidOntology4Class1.owl";
    
    private Constants(){
        throw new AssertionError("'Constants' class shall not be instantiated.");
      }
}