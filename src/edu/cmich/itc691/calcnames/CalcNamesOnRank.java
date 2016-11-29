package edu.cmich.itc691.calcnames;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





/**
 * 
 * @author Pradeep
 * Description:- Input: names data from SSN and Output: Each name and its corresponding gender based on the rank of a specific name in an year.
 *
 */
public class CalcNamesOnRank {

	public static void main(String a[]) throws IOException {

		String fileName = "SSNnames";  // Path for SSN names data

		File file = new File(fileName);
		File[] files = file.listFiles();

		Set<String> fileNames = new HashSet<String>();
		Map<String, NameNode> map = new HashMap<String, NameNode>();

		String fileNameRegExpPattern = "yob([0-9]{4}).txt";

		String lineRegExPattern = "([a-zA-Z _-]+),(M|m|f|F),([0-9]+)";

		Pattern pattern = Pattern.compile(fileNameRegExpPattern);
		Matcher matcher = null;

		Pattern patternLine = Pattern.compile(lineRegExPattern);
		Matcher lineMatcher = null;

		String currentFileName = null;

		int year = 0;


		FileReader fr = null;
		BufferedReader br = null;
		String line = null;

		String name = null;
		String gender = null;
		int rank=0;
		boolean isRankReInitialize=false;
		
		
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

					if(year<1951||year>2001){
						continue;
					}
					
					rank=0;
					isRankReInitialize=false;
					fr = new FileReader(fileName + "\\" + currentFileName);
					br = new BufferedReader(fr);

					while ((line = br.readLine()) != null) {
		
						
						
						
						
						rank++;

						lineMatcher = patternLine.matcher(line);
						if (lineMatcher.find()) {

							name = lineMatcher.group(1);
							gender = lineMatcher.group(2);

							if(!isRankReInitialize&&gender.equals("M")){
								rank=1;
								isRankReInitialize=true;
							}
							
							
							NameNode nameObj = null;
					
							if (map.containsKey(name)) {

								nameObj = map.get(name);

								if (gender.equals("F") && rank < nameObj.getFemaleRank()) {

									nameObj.setFemaleRank(rank);
									nameObj.setFemaleYear(year);
									map.put(name, nameObj);
									
								} else if (gender.equals("M") && (nameObj.getMaleRank()==0 || rank < nameObj.getMaleRank())) {

									nameObj.setMaleRank(rank);
									nameObj.setMaleYear(year);
									map.put(name, nameObj);
									
								}
								

							}else{
								nameObj = new NameNode();
								nameObj.setName(name);
								
								if (gender.equals("F")) {

									nameObj.setFemaleRank(rank);
									nameObj.setFemaleYear(year);

									
								} else if (gender.equals("M")) {

									
									nameObj.setMaleRank(rank);
									nameObj.setMaleYear(year);
									
									
								}

								map.put(name, nameObj);								
								
							}
						}

					}
				
					fr.close();
					br.close();
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		FileWriter fw=new FileWriter("Names1951-2001.txt");
		
		for (Entry<String, NameNode> entry : map.entrySet()) {
//			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
			NameNode nameObj1 = map.get(entry.getKey());
			
			if(nameObj1.getFemaleRank()!=0)
			fw.write(nameObj1.getName()+","+"Female~"+nameObj1.getFemaleRank()+"~"+nameObj1.getFemaleYear()+"\n");
			
			if(nameObj1.getMaleRank	()!=0)
			fw.write(nameObj1.getName()+","+"Male~"+nameObj1.getMaleRank()+"~"+nameObj1.getMaleYear()+"\n");
			
			System.out.println(nameObj1.getName()+"  "+"Female  "+nameObj1.getFemaleRank()+"  "+nameObj1.getFemaleYear());
			System.out.println(nameObj1.getName()+"  "+"Male  "+nameObj1.getMaleRank()+"  "+nameObj1.getMaleYear());
			
			
		}
		
		fw.close();

		
//		map.forEach((k,v)->System.out.println("Key : " + k + " Value : " + v));


		// System.out.println(f.getName()+"---"+f.getN
		// ame().matches(regExpPattern));

	}
}

class NameNode {

	String name;
	int femaleRank;
	int maleRank;
	int femaleYear;
	int maleYear;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFemaleRank() {
		return femaleRank;
	}

	public void setFemaleRank(int femaleCount) {
		this.femaleRank = femaleCount;
	}

	public int getMaleRank() {
		return maleRank;
	}

	public void setMaleRank(int maleCount) {
		this.maleRank = maleCount;
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