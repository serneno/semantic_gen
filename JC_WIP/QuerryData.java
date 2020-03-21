import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class QuerryData {
	public static void updateData() {
		processData(Main.CONFIRMED_DATA_PATH, Main.COVID19_CONFIRMED_URL);
		processData(Main.RECOVERED_DATA_PATH, Main.COVID19_RECOVERED_URL);
		processData(Main.DEATH_DATA_PATH, Main.COVID19_DEATHS_URL);
	}

	public static void processData(String filename, String url) {
		if(Main.UPDATE_DATA) {
			// https://stackoverflow.com/questions/921262/how-to-download-and-save-a-file-from-internet-using-java
			try (FileOutputStream fos = new FileOutputStream(filename)) {
				URL website = new URL(url);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			} catch(Exception e) {
				System.out.println("Could not download "+url+", defaulting to backup ...");
			}
		}
		
		if(Main.CLEAN_DATA) {
			cleanData(filename);
		}
		
	}
	
	static void cleanData(String filename) {
		try {
			byte data[] = Files.readAllBytes(new File(filename).toPath());
			
			boolean inquotes = false;
			
			for(int i = 0; i < data.length; ++i) {
				if (data[i] == ' ' || data[i] == '*' || data[i] == '(' || data[i] == ')' || data[i] == '\'') { data[i] = '_'; }
				
				if (data[i] == '"') { inquotes = !inquotes; data[i] = '_'; }
				else if (inquotes && data[i] == ',') data[i] = '_';
			}
						
			Files.write(new File(filename).toPath(), data, StandardOpenOption.WRITE);
			
		} catch (IOException e) {
			System.out.println("Data cleaning failed ...");
		}
	}
}










