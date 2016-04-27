import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.springframework.web.client.RestTemplate;

import model.RespArchiva;
import model.art;

/**
 * 
 */

/**
 * @author eu
 *
 */
public class VCheckerArchiva implements VChecker{
	
	

	public VCheckerArchiva() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Check(art artifact) {
		//building the url
		String url="http://"+artifact.getServer()+":8080/restServices/archivaServices/browseService/versionsList/org.kurento/"+artifact.getArtifact();
		try {
			URI uri=new URI(url);
			RestTemplate resttemplate=new RestTemplate();
			RespArchiva res=resttemplate.getForObject(uri, RespArchiva.class);
			//################################################################################################
			// At this point res contains a correct RespArchiva object returned by archiva repository
			//################################################################################################
			Iterator<String> it=res.getVersions().iterator();
			while(it.hasNext()){
				String[]structure=it.next().split("-");
				if(structure[0].equals(artifact.getVersion())){
					return true;
				}
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();			
		}		
		
		return false;
	}

}
