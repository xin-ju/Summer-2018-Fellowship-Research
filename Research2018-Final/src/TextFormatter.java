
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.String;
public class TextFormatter {
	public static void main(String[] args) throws IOException, FileNotFoundException, NullPointerException {
		// Array of tags
		String[] tagList = { "SYM", "DE", "IE", "FG", "DX", "IX", "FT", "FP", "FMS", "FMD", "FMDS", "FMDD", "FMDM",
				"FMDF", "FC", "FF", "IM", "IF", "IEX", "LS", "FCL", "FV", "FS", "FM", "ANG", "DI", "FGR", "LN", "RAY" };

		// LinkedHashMap for tags and their occurrences (LinkedHashMap preserves
		// insertion order)
		HashMap<String, Integer> tagMap = new LinkedHashMap<>();
		for (int i = 0; i < tagList.length; i++) {
			tagMap.put(tagList[i], 0); // set values of all tags to 0 to start
		}

		//FileWriter writer = new FileWriter("Output.txt");
		FileWriter writer = new FileWriter("/Users/XinJu/Desktop/Summer2018-Research/Our-Cluster-Data/Output.txt");
		
		// Write to Console
		System.out.println("Olympiad #" + "\t" + "Set" + "\t" + "Number" + "\t" + "Suggested Time" + "\t" +  "Percent Correct");
		//Write to output txt file 
		writer.write("ID" + "\t" + "Olympiad #" + "\t" + "Set" + "\t" + "Number" + "\t" + "Suggested Time" + "\t" +  "Percent Correct");
        //"Olympiad #; Set; Number; Suggested Time; Percent Correct");
		//List out all tags in top row
		    for (String key : tagMap.keySet()) {
			System.out.print("; [" + key + "]");
			writer.write( "\t" + "[" + key + "]");
		    }
		    System.out.println("\t" + "text");
		    writer.write("\t" + "text");
		    //("; Question");
		//File[] files = new File("/Users/XinJu/Desktop/MathOlympiad/OriginalTxtFiles").listFiles();
		File[] files = new File("/Users/XinJu/Desktop/Summer2018-Research/MathOlympiad/OriginalTxtFiles").listFiles();
		for (File file : files) {
		    Scanner s = new Scanner(file);
		    
		    
		    String ID = "24A";
		    String number = "0";
		    String time = null;
		    String percent = null;
		    String question = "";
		    int numTags = 0;
		    String olympiadNum = "N/A";
		    String olympiadNumNext = "N/A";
		    String setNum = "N/A";
		    String setNumNext = "N/A";

		    while (s.hasNextLine()) { // Loop through each line
			String line = s.nextLine().trim();
			
			
			if (line.matches("^Olympiad [0-9]+$") || line.matches("^OLYMPIAD [0-9]+$")) {// Identify Olympiad number,
			    // EX: Olympiad 2 (Book 1)
				
				if (number.trim().equals("5") || number.trim().contains("E")) { //Don't update variable olympiadNum yet

					olympiadNumNext = line.replaceAll("[^0-9+]", "");
				}else {
                
                   olympiadNum = line.replaceAll("[^0-9+]", "");
				}

			} else if (line.matches("^SET [0-9]+$")) { // Identify set, EX: SET 2 (Book 2)
				if (number.trim().contains("E")) { //Don't update variable setNum yet
			    setNumNext = line.replaceAll("SET", "").trim();// Replace word "SET" with empty space

				}else {
					setNum = line.replaceAll("SET", "").trim();
				}

			} else if (line.matches("^[0-9]+\\.") || line.matches("^[0-9]+[A-Z]+")) { // Identify numbering, EX: 1. or
			    // 1A
			    //SOP when all variables have been filled
			    if (olympiadNum != null && number != "0" && time != null && percent != null && question != "") { 
				// Write to Output.txt
				writer.write("\r");// write to new line
				
				if (setNum == "N/A") { //Book 1
					writer.write("N/A" + "\t" + olympiadNum + "\t" + setNum + "\t" + number + "\t" + time + "\t" + percent);
					// Write to Console
					System.out.print("N/A" + ";" + olympiadNum + " ; " + setNum + " ; " + number + " ; " + time + " ; " + percent);
				
				}else {
				writer.write(ID + "\t" + olympiadNum + "\t" + setNum + "\t" + number + "\t" + time + "\t" + percent);
				// Write to Console
			System.out.print(ID + " ; " + olympiadNum + " ; " + setNum + " ; " + number + " ; " + time + " ; " + percent);
				}
			
				
			// Print values of each key
				for (String key : tagMap.keySet()) {
					System.out.print(" ; " + tagMap.get(key));
				    writer.write("\t" + tagMap.get(key));
				}
				// Print question
				  System.out.println(" ; " + question);
				  writer.write("\t" + question);
				
				
				//Search for certain word
				if (question.contains("increase") || question.contains("decrease") || question.contains("more") || question.contains("less")
					|| question.contains("fewer") || question.contains("most") || question.contains("greater") || question.contains("greatest")||
					question.contains("least") || question.contains("lower") || question.contains("fewest")) {
					System.out.println(" ; " + question);
					writer.write("\t" + question);
					
				}
			

			    } // end of if
			    // Reset variables
			    if (number.trim().equals("5") || number.trim().contains("E")) {
			    	  olympiadNum = olympiadNumNext;
			    }
			    if (number.trim().contains("E")) {
			    	  setNum = setNumNext;
			    }
			    
			    number = "0";
			    time = percent = null;
			    question = "";
			    numTags = 0;
			    
			    

			    for (String key : tagMap.keySet()) { // Clear values in tagMap
				tagMap.put(key, 0);
			    }

			    number = line.replaceAll("\\.", "").trim(); // Replace all "." with empty space

			} else if (line.matches("^[0-9]+ min.$") || line.matches("^[0-9]+ MINUTES$")) { // Identify minutes, EX: 5
			    // min. (Books 1, 3)

			    time = line.replaceAll("[^0-9+]", ""); // Replace any character that is not a number with empty space
			    

			} else if (line.matches("[0-9]+\\%")) { // Identify percents, EX: 12% (Books 1, 2, 3)

			    percent = line.replaceAll("%", ""); // Replace all % signs with empty space

			} else if (!line.isEmpty()) { // Identify question text

			    for (String key : tagMap.keySet()) { // for each key in tagMap (iterates through entire set of keys in
				// Map)
				// Find all instances of each key in each line
				Pattern p = Pattern.compile("\\[" + key + "\\]");
				Matcher m = p.matcher(line);
				while (m.find()) {
				    tagMap.put(key, tagMap.get(key) + 1); // increment value of found key
						
				}

			    } // end for

			    question += " " + line.replaceAll("\\[.*?\\]", ""); // Replace all tags with empty space

			} // end if else

		    } // end while loop

		    //SOP information for last question
		    if (olympiadNum != null && number != null && time != null && percent != null && question != "") { 
			// Write to Output.txt
			writer.write("\r");// write new line
			writer.write(olympiadNum + "\t" + setNum + "\t" + number + "\t" + time + "\t" + percent);
			// Write to Console
			System.out.print(olympiadNum + " ; " + setNum + " ; " + number + " ; " + time + " ; " + percent);
             
			for (String key : tagMap.keySet()) {
			    System.out.print("\t" + tagMap.get(key));
			    writer.write("\t" + tagMap.get(key));
			}
			System.out.println("\t" + question);
			writer.write("\t" + question);
		    } // end of if

		} // end loop through files
		
		writer.close();
	}	

}


