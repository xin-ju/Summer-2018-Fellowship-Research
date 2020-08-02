import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TextFormatterProblemTypes {

	public static void main(String[] args) throws IOException, FileNotFoundException {
		// TODO Auto-generated method stub
		ArrayList<String> types = new ArrayList<>();

		FileWriter writer = new FileWriter("Output2.txt");
		System.out.println("Book" + "\t" + "Type" + "\t" + "Documents");
		writer.write("Book" + "\t" + "Type" + "\t" + "Documents");

		File[] files = new File("/Users/XinJu/Desktop/Summer2018-Research/ProblemTypes").listFiles();
		for (File file : files) {
			Scanner s = new Scanner(file);
			String book = null;
			String type = null;
			String docs = "";
			int count = 0;

			while (s.hasNextLine()) {
				String line = s.nextLine().trim();
				if (line.matches("^Book [0-9]+$")) { // EX: Book 2
					book = line.replaceAll("Book", "").trim();

				} else if (line.contains(":")) { // EX: Age problems:
					if (!docs.equals("") && type != null) {
						System.out.println(book + "\t" + type + "\t" + docs);
						writer.write("\r"); // write to new line
						writer.write(book + "\t" + type + "\t" + docs);
						docs = "";
					}

					type = line.replaceAll(":", "").trim(); // Remove : after problem type names

//                  Adds types to ArrayList
//					if (!types.contains(line)) {
//						types.add(line);
//					}

				} // if
				else { // list of question numbers, EX: 24A

					// Book 1 numbering
					line = line.replaceAll("-1", "A");
					line = line.replaceAll("-2", "B");
					line = line.replaceAll("-3", "C");
					line = line.replaceAll("-4", "D");
					line = line.replaceAll("-5", "E");

					docs += " " + line.trim();

				}

			} // while
			if (type != null) { // Final iteration for problem type Working Backwards
				System.out.println(book + "\t" + type + "\t" + docs);
				writer.write("\r"); // write to new line
				writer.write(book + "\t" + type + "\t" + docs);
			}
		}
		writer.close();

		// Displays number of Tags without repeats
		// int n = 0;
		// for(String t: types) {
		// n = n+1;
		// System.out.print(n);
		// System.out.println(" " + t);
		//
		// }

	}

}

