import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import model.RespArchiva;
import model.RespMavenCentral;
import model.art;
import model.artifactList;
import model.doc;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Javier Cabezas y Eugenio F. González (eugeniofidel@gmail.com)
 * 
 */
public class VCheckerApplication {	
	//Application parameters. The name of the file wich is going to be processed
    public static void main(String[] args) {   	
    	
    	String file="./"+args[0];
    	CheckVersions(file);
    	  	
    }

	private static void CheckVersions(String file) {
		boolean comprobar=false;

		//We get the artifacts and the versions from config.json
		ObjectMapper mapper = new ObjectMapper();			
		artifactList al=null;
		try {
			al = mapper.readValue(new File(file), artifactList.class);
		} catch (JsonParseException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//We construct an iterator with al to process all the artifacts contained in it
		Iterator<art> it=al.getArtifacts().iterator();
				
		while(it.hasNext()){
			art artefacto=it.next();
			//We look at the server attribute to select the function that will process each artifact
			if (artefacto.getServer().equals("search.maven.org")){
				VCheckerMaven vcm=new VCheckerMaven();				
				comprobar=vcm.Check(artefacto);
			}else if (artefacto.getServer().equals("bower.io")){
				VCheckerBower vcb=new VCheckerBower();				
				comprobar=vcb.Check(artefacto);
			}else if (artefacto.getServer().equals("npmjs.com")){
				VCheckerNpm vcn=new VCheckerNpm();				
				comprobar=vcn.Check(artefacto);
			}else{
				VCheckerArchiva vca=new VCheckerArchiva();				
				comprobar=vca.Check(artefacto);
			}
			
			
			if(!comprobar){
				 System.out.println("\nThe artifact "+artefacto.getArtifact()+", version "+artefacto.getVersion()
						 +" is notlocated in "+artefacto.getServer()+"\n");
			}else{
				System.out.println("\nThe artifact "+artefacto.getArtifact()+", version "+artefacto.getVersion()
						 +" is located in "+artefacto.getServer()+"\n");
			}
		}		
	}	
}

