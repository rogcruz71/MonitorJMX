package monitor.jmx.com.br;

import weblogic.management.scripting.utils.WLSTInterpreter;
import org.python.util.InteractiveInterpreter;
import java.io.ByteArrayOutputStream;

import javax.management.MBeanServerConnection;

public class CollectThreadDump {

	public String GetThreadDump(String username, String password, String url, String server) {
		
		String dump = null;

		final InteractiveInterpreter interpreter;
		interpreter = new WLSTInterpreter();
		ByteArrayOutputStream commandOut = new ByteArrayOutputStream();
		interpreter.setOut(commandOut);

		try {
			interpreter
			.exec("connect('" + username + "','" + password + "','" + url + "')");
			commandOut.reset();
			interpreter.exec("cd ('Servers')");
			interpreter.exec("cd ('" + server + "')");
			interpreter.exec("print(threadDump(writeToFile='false',serverName='" + server + "'))");

			dump = commandOut.toString();
			commandOut.reset();

		} catch (Exception e) {
			e.printStackTrace();
			dump = commandOut.toString();
		}

		return dump;
	}
	
	
}
