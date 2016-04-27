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
public class VCheckerNpm implements VChecker{

	public VCheckerNpm() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Check(art artifact) {
//		 running the npm view command
		String cmd="npm view "+artifact.getArtifact()+"@"+artifact.getVersion();
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
			        out.append(line.trim());			       
			}
			json=json+out.toString();
			if(json.equals(""))
				return false;
			
			System.out.println(json);  //Prints the string content read from input stream
		    reader.close();
		    
		    ArrayList<String>palJson=ObtenerPalabrasJson(json);
		    if(artifact.getArtifact().equals(palJson.get(palJson.indexOf("name:")+1))){		    	
		    	for(int versions=palJson.indexOf("versions:")+1;versions<palJson.indexOf("maintainers:");versions++){
		    		if(artifact.getVersion().equals(palJson.get(versions))){
		    			return true;
		    		}
		    	}		    	
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
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
