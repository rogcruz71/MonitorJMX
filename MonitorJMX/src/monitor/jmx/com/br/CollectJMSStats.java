package monitor.jmx.com.br;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

/** 
 * 
 * @author rogerio cruz
 *
 * 2010.10.10
 * 
 */
public class CollectJMSStats {

	public Hashtable GetJMSStats(MBeanServerConnection connection, Hashtable serverList) {
		
		String JMSServerName = "";
		String WLSServerName = "";
		Hashtable jmsTable = new Hashtable();
	
		try {
			
			Enumeration e = serverList.keys();
			int serverOrder = 1;
			
			while(e.hasMoreElements()) {
				
				WLSServerName = (String)e.nextElement();
				JMSServerName = (String)serverList.get(WLSServerName);
				
				try {
					ObjectName[] destRT =
						(ObjectName[]) connection.getAttribute(new ObjectName("com.bea:Name="+JMSServerName+",ServerRuntime="+WLSServerName+",Location="+WLSServerName+",Type=JMSServerRuntime"),"Destinations");
					
					
					int appLength = (int) destRT.length;
					for (int x = 0; x < appLength; x++) {
						
						JMSModuleTO jmsProps = new JMSModuleTO();
						
						jmsProps.setServerName(WLSServerName);
						jmsProps.setQueueName((String)connection.getAttribute(destRT[x], "Name"));
						jmsProps.setCurrentConsumersCount(Long.parseLong(String.valueOf(connection.getAttribute(destRT[x], "ConsumersCurrentCount"))));				
						jmsProps.setMessagesCurrentCount(Long.parseLong(String.valueOf(connection.getAttribute(destRT[x], "MessagesCurrentCount"))));
						jmsProps.setMessagesPendingCount(Long.parseLong(String.valueOf(connection.getAttribute(destRT[x], "MessagesPendingCount"))));
						jmsProps.setMessagesHighCount(Long.parseLong(String.valueOf(connection.getAttribute(destRT[x], "MessagesHighCount"))));
						jmsProps.setMessagesReceivedCount(Long.parseLong(String.valueOf(connection.getAttribute(destRT[x], "MessagesReceivedCount"))));
						
						// remove this comment if you want to see only if current or pending messages exists
					    //if (jmsProps.getMessagesCurrentCount() > 0 || jmsProps.getMessagesPendingCount() > 0)
					    	jmsTable.put(serverOrder--, jmsProps);
					}
				}
				catch (InstanceNotFoundException ex) {
					System.out.println("Server Instance " + WLSServerName + " unavailble.");
				}
			}
			

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return jmsTable;
	}
	
	
	public static void main(String[] args) {
		
		// usage example
		String protocol = "t3";
		String hostname = "localhost";
		int port = 7001;
		String username = "weblogic";
		String password = "welcome1";
		
		JMXConnection mBeanConnection = new JMXConnection();
		MBeanServerConnection connection = mBeanConnection.connection(username, password, protocol, hostname, port);
		
		Hashtable jmsTable;
		CollectJMSStats statJMS = new CollectJMSStats();
		
		Hashtable serverList = new Hashtable();
		serverList.put("AdminServer","JMSServer-0");
		//serverList.put("ManagedSrv2","JMSServer2");
		//serverList.put("ManagedSrv3","JMSServer3");
		//serverList.put("ManagedSrv4","JMSServer4");
		
		jmsTable = statJMS.GetJMSStats(connection, serverList);	
		
	}
	
}
