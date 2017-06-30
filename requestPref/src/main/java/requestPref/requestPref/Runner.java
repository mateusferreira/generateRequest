package requestPref.requestPref;



import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import control.Controller;
import entities.MyLog;

public class Runner {
	
	public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	

	public static void main(String[] args) {
		try {
			MyLog.setup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Problems with creating the log files");
		}
		
		//System.out.println("Iniciando.... "+System.getProperty("user.dir"));
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("Iniciando a Aplicação");
		
		new Controller().init();
	
	}

}
