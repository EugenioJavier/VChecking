import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import model.art;

/**
 * 
 */

/**
 * @author eu
 *
 */
public class VCheckerBower implements VChecker{

	public VCheckerBower() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Check(art artifact) {
boolean result=false;
		
		String cmd="bower info "+artifact.getArtifact()+"#"+artifact.getVersion();
		Process process=null;
		InputStream is=null;
		
	    try {
	    	process=Runtime.getRuntime().exec(cmd);	
	    	System.out.println("\n");
			is=process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		    StringBuilder out = new StringBuilder();
		    String line;
		    String json="";
			while ((line = reader.readLine()) != null) {
			        out.append(line);			       
			}
			json=json+out.toString();
			if(json.equals(""))
				return false;
			
			System.out.println(json);  //Prints the string content read from input stream
		    reader.close();
		    ArrayList<String>palJson=ObtenerPalabrasJson(json);
		    if(artifact.getArtifact().equals(palJson.get(palJson.indexOf("name:")+1))){		    	
		    	if(artifact.getVersion().equals(palJson.get(palJson.indexOf("version:")+1))){
		    		return true;
		    	}		    		    	
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return result;
	}

	
	private static ArrayList<String> ObtenerPalabrasJson(String json) {
		ArrayList<String>result=new ArrayList<String>();
		//We have the information from npmjs.org in the string json
	    String delimiter=",{}'[]";
	    StringTokenizer st=new StringTokenizer(json,delimiter);
	    while(st.hasMoreTokens()){
	    	result.add(st.nextToken().trim());
	    }
		return result;
	}
}
