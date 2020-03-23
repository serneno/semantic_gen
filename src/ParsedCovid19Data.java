import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;


/*
	Class that contains the COVID-19 data parsed from the 3 input CSV files
*/
public class ParsedCovid19Data {
	public HashMap<String, Country> countries = new HashMap<>();
	public HashMap<String, Province> provinces = new HashMap<>();
	public TreeMap<String, Date> dates = new TreeMap<>();
	public ArrayList<Covid19Case> cases = new ArrayList<>();
	
	protected ArrayList<Date> indexedDates = new ArrayList<>();
	
	public ParsedCovid19Data() throws IOException {
		parseDates();
		parseCountriesAndProvinces();
		parseCases();
		indexedDates = null;
	}

	void parseDates() throws FileNotFoundException { 
		try(Scanner lineScanner = new Scanner(new File(Constants.COVID19_CONFIRMED_DATASET_INPUT_PATH))) {			
			String header = lineScanner.nextLine();
			try(Scanner s = new Scanner(header)) {
				s.useDelimiter(",");
				
				// ignore categories
				for(int i = 0; i < 4; ++i) { s.next(); }
			
				while(s.hasNext()) {
					String name = s.next();
					Date d = new Date(name);
					indexedDates.add(d);
					dates.put(name, d);
				}
			}
		}
	}
	
	void parseCountriesAndProvinces() throws FileNotFoundException {
		try(Scanner lineScanner = new Scanner(new File(Constants.COVID19_CONFIRMED_DATASET_INPUT_PATH))) {			
			lineScanner.nextLine();
			
			while(lineScanner.hasNextLine())
			{
				String line = lineScanner.nextLine();
				
				try (Scanner s = new Scanner(line)){
					s.useDelimiter(",");
					
					String provinceName = null;
					if(line.charAt(0) != ',') { provinceName = s.next(); }
					
					String countryName = s.next();
					if (provinceName == null) { provinceName = countryName; }
					
					if (!countries.containsKey(countryName)) {
						countries.put(countryName, new Country(countryName));
					}
					
					Country country = countries.get(countryName);
					
					if (!provinces.containsKey(provinceName)) {
						provinces.put(provinceName, new Province(provinceName, country));
					}
					
					Province province = provinces.get(provinceName);
					
					if(!country.provinces.containsKey(province)) {
						country.provinces.put(province, true);
					}
				}
			}
		}	
	}
	
	void parseCases() throws FileNotFoundException {
		Scanner scanner = null;
		try {
			for(int i = 0; i < Constants.ALL_COVID19_DATASET_INPUT_PATHS.length; ++i) {
				scanner = new Scanner(new File(Constants.ALL_COVID19_DATASET_INPUT_PATHS[i]));
				
				// Ignore header
				scanner.nextLine();
			
				while(scanner.hasNextLine()) {
					String line = scanner.nextLine();
					int dateIndex = 0;
					Province province;
					
					try (Scanner s = new Scanner(line)) {
						s.useDelimiter(",");
						{
							String provinceName = null;
							if(line.charAt(0) != ',') { provinceName = s.next(); }
							String countryName = s.next();
							if (provinceName == null) { provinceName = countryName; }
							province = provinces.get(provinceName);							
						}
						
						// Ignore lat and long
						s.next();
						s.next();
						
						while(s.hasNext())
						{							
							Date date = indexedDates.get(dateIndex++);
														
							Covid19Case covid19Case = new Covid19Case(String.valueOf(cases.size()), date, province);
							province.covid19cases.add(covid19Case);
							date.covid19cases.add(covid19Case);
						
							
							long value;
							{
								String valString = s.next();
								try {value = Long.parseLong(valString);} 
								catch(Exception e) { value = 0; }
							}
							
							switch(i) {
							case 0:
								covid19Case.confirmed += value;
								break;
							case 1:
								covid19Case.recovered += value;
								break;
							case 2:
								covid19Case.death += value;
								break;
							}
							
							cases.add(covid19Case);
						}
					}
				}
			}
		} finally {
			scanner.close();
		}
	}
	
	/*
		Inner classes that represent OWL properties
	*/
	public abstract class HasName {
		 protected String name;
		 public HasName(String name) { this.name = name; }
	}
	
	public class Province extends HasName {
		Country country;
		protected ArrayList<Covid19Case> covid19cases = new ArrayList<>();
		
		public Province(String name, Country country) { super(name); this.country = country; }
	}
	public class Country extends HasName {
		protected HashMap<Province, Boolean> provinces = new HashMap<>();
		
		public Country(String name) { super(name); }
	}
	public class Date extends HasName {
		protected ArrayList<Covid19Case> covid19cases = new ArrayList<>();
		public Date(String name) { super(name); }
	}
	public class Covid19Case extends HasName {
		Date date;
		Province province;
		public long confirmed = 0, recovered = 0, death = 0;
		public Covid19Case(String name, Date date, Province province) { super(name); this.date = date; this.province = province; }
	}
}
