package monitor.jmx.com.br;

import java.util.Hashtable;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/** 
 * 
 * @author rogerio cruz
 *
 * 2010.10.10
 * 
 * 2016.02.10 - implementacao da coleta do estado adicional das instancias:  warning, overloaded.
 * 
 */
public class CollectInstanceStats {

	public Hashtable<String, InstanceProperties> GetInstanceStats(MBeanServerConnection connection) {
		
		ObjectName service;
		Hashtable<String, InstanceProperties> instanceTable = new Hashtable<String, InstanceProperties>();

		try {
			service = new ObjectName(
					"com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean");
		} catch (MalformedObjectNameException e) {
			throw new AssertionError(e.getMessage());
		}  
		
		try {
			
			TimeFormat HourFormat = new TimeFormat();

			
			// exibe o status dos servidores
			ObjectName[] serverRT = (ObjectName[]) connection.getAttribute(service,"ServerRuntimes");
			
			int length = (int) serverRT.length;
			
			for (int i = 0; i < length; i++) {
				
				InstanceProperties instanceProps = new InstanceProperties();
				
				instanceProps.setNodeId((long) i);
				
				instanceProps.setName((String) connection.getAttribute(serverRT[i],"Name"));
//				instanceProps.setState(String.valueOf(connection.getAttribute(serverRT[i], "State")));
				instanceProps.setState(String.valueOf(connection.getAttribute(serverRT[i], "OverallHealthState")));
				
				weblogic.health.HealthState serverHealthState=( weblogic.health.HealthState) connection.getAttribute(serverRT[i],"HealthState");
				int hState=serverHealthState.getState(); 
				
				//System.out.println(connection.getAttribute(serverRT[i],"OverallHealthState"));
		
				String instanceHealth = instanceProps.getState();
				if(instanceHealth.contains("HEALTH_OK"))
					instanceProps.setState("HEALTH OK");
				
						
				if(hState==weblogic.health.HealthState.HEALTH_OK) 
					//System.out.println("Server: "+instanceProps.getName()+" Health: HEALTH_OK");
				if(hState==weblogic.health.HealthState.HEALTH_WARN)
					//System.out.println("Server: "+instanceProps.getName()+" Health: HEALTH_WARN");
				if(hState==weblogic.health.HealthState.HEALTH_CRITICAL)
					//System.out.println("Server: "+instanceProps.getName()+" Health: HEALTH_CRITICAL");
				if(hState==weblogic.health.HealthState.HEALTH_FAILED)
					//System.out.println("Server: "+instanceProps.getName()+" Health: HEALTH_FAILED");
				if(hState==weblogic.health.HealthState. HEALTH_OVERLOADED)
					//System.out.println("Server: "+instanceProps.getName()+" Health: HEALTH_OVERLOADED");
			    
				
				instanceProps.setPort(Long.parseLong(String.valueOf(connection.getAttribute(serverRT[i], "ListenPort"))));		
				instanceProps.setOpenSockets(Long.parseLong(String.valueOf(connection.getAttribute(serverRT[i], "OpenSocketsCurrentCount"))));		
				
				ObjectName jvmRT =  (ObjectName) connection.getAttribute(serverRT[i],"JVMRuntime");
				
				instanceProps.setUpTime(HourFormat.millisToTime(Long.parseLong(String.valueOf(connection.getAttribute(jvmRT, "Uptime")))));
				instanceProps.setRequestUptime(Long.parseLong(String.valueOf(connection.getAttribute(jvmRT, "Uptime"))));
				instanceProps.setHeapFreeCurrent(Double.parseDouble(String.valueOf(connection.getAttribute(jvmRT, "HeapFreeCurrent"))));
				instanceProps.setHeapSizeMax(Double.parseDouble(String.valueOf(connection.getAttribute(jvmRT, "HeapSizeMax"))));
				instanceProps.setHeapFreePercent(Long.parseLong(String.valueOf(connection.getAttribute(jvmRT, "HeapFreePercent"))));
				instanceProps.setJavaVersion(String.valueOf(connection.getAttribute(jvmRT, "JavaVersion")));
				
				ObjectName threadRT =  (ObjectName) connection.getAttribute(serverRT[i],"ThreadPoolRuntime");
				
				instanceProps.setCompletedRequestCount(Double.parseDouble(String.valueOf(connection.getAttribute(threadRT, "CompletedRequestCount"))));
				instanceProps.setThroughtput(Double.parseDouble(String.valueOf(connection.getAttribute(threadRT, "Throughput"))));
				instanceProps.setExecuteThreadTotaCount(Long.parseLong(String.valueOf(connection.getAttribute(threadRT, "ExecuteThreadTotalCount"))));
				instanceProps.setExecuteThreadIdleCount(Long.parseLong(String.valueOf(connection.getAttribute(threadRT, "ExecuteThreadIdleCount"))));
				instanceProps.setExecuteThreadHoggingCount(Long.parseLong(String.valueOf(connection.getAttribute(threadRT, "HoggingThreadCount"))));
				instanceProps.setPendingUserRequestCount(Long.parseLong(String.valueOf(connection.getAttribute(threadRT, "PendingUserRequestCount"))));
				instanceProps.setQueueLength(Long.parseLong(String.valueOf(connection.getAttribute(threadRT, "QueueLength"))));
				instanceProps.setStandbyThreadCount(Long.parseLong(String.valueOf(connection.getAttribute(threadRT, "StandbyThreadCount"))));
				instanceProps.setSuspended(Boolean.parseBoolean(String.valueOf(connection.getAttribute(threadRT, "Suspended"))));
				
				
				//instanceTable.put(Long.toString(instanceProps.getPort()), instanceProps);
				instanceTable.put(Long.toString(instanceProps.getNodeId()), instanceProps);
				
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return instanceTable;
	}
	
	
}
