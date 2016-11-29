package edu.cmich.itc691.calcnames;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * @author Pradeep
 * Description: Input: names data from SSN and Output: Each name and its corresponding gender based on the frequency(count) of a specific name in an year.
 */
public class CalcNamesOnCount {

	public static void main(String a[]) {

		String fileName = "SSNnames";    // Path for SSN names data

		File file = new File(fileName);
		File[] files = file.listFiles();

		Set<String> fileNames = new HashSet<String>();
		Map<String, Name> map = new HashMap<String, Name>();

		String fileNameRegExpPattern = "yob([0-9]{4}).txt";

		String lineRegExPattern = "([a-zA-Z _-]+),(M|m|f|F),([0-9]+)";

		Pattern pattern = Pattern.compile(fileNameRegExpPattern);
		Matcher matcher = null;

		Pattern patternLine = Pattern.compile(lineRegExPattern);
		Matcher lineMatcher = null;

		String currentFileName = null;

		int year = 0;
		int resultYear = 0;

		FileReader fr = null;
		BufferedReader br = null;
		String line = null;

		String name = null;
		String gender = null;
		int count = 0;
		int maleCount = 0;
		int femaleCount = 0;
		boolean isUpdateOk = false;

		try {

			for (File f : files) {

				currentFileName = f.getName();
				if (fileNames.contains(currentFileName)) {
					continue;
				} else {
					fileNames.add(currentFileName);
				}
				matcher = pattern.matcher(currentFileName);
				if (matcher.find()) {

					year = Integer.parseInt(matcher.group(1));

					if(year<1951){
						continue;
					}
					
					fr = new FileReader(fileName+"\\"+currentFileName);
					br = new BufferedReader(fr);

					while ((line = br.readLine()) != null) {

						lineMatcher = patternLine.matcher(line);
						if (lineMatcher.find()) {

							name = lineMatcher.group(1);
							gender = lineMatcher.group(2);
							count = Integer.parseInt(lineMatcher.group(3));
							Name nameObj = null;

							if (map.containsKey(name)) {

								nameObj = map.get(name);

								if (gender.equals("M") && count > nameObj.getMaleCount()) {
									resultYear = year;
									maleCount = count;
									isUpdateOk = true;
								} else if (gender.equals("F") && count > nameObj.getFemaleCount()) {
									resultYear = year;
									femaleCount = count;
									isUpdateOk = true;
								}

							} else {

								nameObj = new Name();
								nameObj.setName(name);
								isUpdateOk = true;
							}

							if (isUpdateOk && gender.equals("M")) {
								nameObj.setMaleCount(maleCount);
								nameObj.setMaleYear(resultYear);
							} else if (isUpdateOk && gender.equals("F")) {
								nameObj.setFemaleCount(femaleCount);
								nameObj.setFemaleYear(resultYear);
							}

							if (isUpdateOk) {
								map.put(name, nameObj);
							}
							isUpdateOk = false;
						}

					}
					fr.close();
					br.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		// To get multiple names we need to iterate Map
		
		if(map.containsKey("Dyasia")){
			
			Name nameObj1=map.get("Dyasia");
			System.out.println(nameObj1.getFemaleCount());
			System.out.println(nameObj1.getMaleCount());
			System.out.println(nameObj1.getFemaleYear());
			System.out.println(nameObj1.getMaleYear());
		}
		
		
		// System.out.println(f.getName()+"---"+f.getName().matches(regExpPattern));

	}
}

class Name {

	String name;
	int femaleCount;
	int maleCount;
	int femaleYear;
	int maleYear;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFemaleCount() {
		return femaleCount;
	}

	public void setFemaleCount(int femaleCount) {
		this.femaleCount = femaleCount;
	}

	public int getMaleCount() {
		return maleCount;
	}

	public void setMaleCount(int maleCount) {
		this.maleCount = maleCount;
	}

	public int getFemaleYear() {
		return femaleYear;
	}

	public void setFemaleYear(int femaleYear) {
		this.femaleYear = femaleYear;
	}

	public int getMaleYear() {
		return maleYear;
	}

	public void setMaleYear(int maleYear) {
		this.maleYear = maleYear;
	}

}