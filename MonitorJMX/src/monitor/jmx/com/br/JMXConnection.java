package monitor.jmx.com.br;

import java.util.Hashtable;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;


/**
 * @author Rogerio Cruz
 *
 */
public class JMXConnection {
	
	public MBeanServerConnection connection(String username, String password, String protocol, String hostname, int port) {
		
		MBeanServerConnection connection = null;
		JMXConnector connector;
		
		try {
			//System.out.println("connection data:" + hostname + ":" + port);
			
			String jndiroot = "/jndi/";
		    String mserver = "weblogic.management.mbeanservers.domainruntime";
			JMXServiceURL serviceURL = new JMXServiceURL(protocol, hostname, port, jndiroot + mserver);
			Hashtable h = new Hashtable();
			h.put(Context.SECURITY_PRINCIPAL, username);
			h.put(Context.SECURITY_CREDENTIALS, password);
			h.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES, "weblogic.management.remote");
			connector = JMXConnectorFactory.connect(serviceURL, h);
			connection = connector.getMBeanServerConnection();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}

}
