package edu.cmich.itc691.calcnames;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 
 * @author Pradeep
 * Description: Names match between Twitter and SSN names data.
 */
public class NamesTwitterSSN {
	public static void main(String[] args) throws IOException {

		String fileName = "Names1951-2001.txt";
		
		Map<String, String> names = new HashMap<String, String>();
		Map<String,String> namesGender=new HashMap<String,String>();
		FileReader fr = new FileReader(fileName);

		BufferedReader br = new BufferedReader(fr);
		String line = null;
		String str[] = null;
		String name = null;
		String gender = null;
		int count = 0;
		int compareCount = 0;
		while ((line = br.readLine()) != null) {

			str = line.split(",");

			if (str.length != 2) {
				continue;
			}

			name = str[0];
			gender = str[1].split("~")[0];
			count = Integer.parseInt(str[1].split("~")[1]);
			if (names.containsKey(name)) {

				String cnt = names.get(name).split("~")[1];
				compareCount = Integer.parseInt(cnt);

				if (count > compareCount) {
					names.put(name, str[1]);
				}

			} else {
				names.put(name, str[1]);
			}
		}

		br.close();
		fr.close();

		for (Entry<String, String> entry : names.entrySet()) {
//			System.out.println(entry.getKey() + ","+entry.getValue().split("~")[0]+","+entry.getValue().split("~")[2]);
namesGender.put(entry.getKey(), entry.getValue().split("~")[0]);
		}

		
		
		
		
		
		String fileName1 = "hashtagstwitterhealthexportnames.csv";
		FileReader fr1 = new FileReader(fileName1);

		BufferedReader br1 = new BufferedReader(fr1);
		String line1 = null;
		String nameSplit[]=null;
		int maleCount=0;
		int femaleCount=0;
		
		String nam=null;
		FileWriter fw=new FileWriter("TwitterNames.csv");
		
		while((line1=br1.readLine())!=null){
			
			nameSplit=line1.split(" |\\.|,");
			
			for(String naam:nameSplit){
				 nam = naam.replaceAll("[^a-zA-Z]+","");
				 if(namesGender.containsKey(nam)){
					 if(namesGender.get(nam).equalsIgnoreCase("Male")){
						 maleCount++;
						 fw.write(nam+","+namesGender.get(nam)+"\n");
						 break;
					 }
					 if(namesGender.get(nam).equalsIgnoreCase("Female")){
						 femaleCount++;
						 fw.write(nam+","+namesGender.get(nam)+"\n");
						 break;
					 }
				 }
			}
			
			
		}
		
		
		System.out.println("Male Count "+maleCount);
		System.out.println("Female Count "+femaleCount);
		
		br1.close();
		fr1.close();
		fw.close();
		
		
		
	}
}
