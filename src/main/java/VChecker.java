import model.art;


public interface VChecker {
	/*
	 * Recibe un objeto de tipo artefacto y comprueba si está en el repositorio correspondiente
	 */
	boolean  Check(art artifact);
}
