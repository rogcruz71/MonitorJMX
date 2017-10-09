<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Hashtable" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.TreeMap" %>
<%@page import="javax.management.MBeanServerConnection" %>
<%@page import="monitor.jmx.com.br.*" %>
<%@page import="java.text.DecimalFormat" %>
<%@page import="java.text.NumberFormat" %>
<%@page import="java.util.Properties" %>
<%@page import="java.io.FileInputStream" %>
<%@page import="java.io.InputStream" %>
<%@page import="java.net.URL" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Monitor JMX - WebLogic</title>
<meta http-equiv="refresh" content="30">
<style type="text/css">
<!--
.style3 {
	color: #000099;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 16px;
	font-weight: bold;
	font-style: italic;
}
.style4 {
	color: #0000FF;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 10px;
	font-weight: normal;
}
.style5 {font-size: 11px; font-family: Arial, Helvetica, sans-serif;}
.style6 {color: #000099; font-family: Arial, Helvetica, sans-serif; font-size: 10px; font-weight: normal; }
-->
</style>
</head>
<body>
<table width="1100" border="0">
  <tr>
    
    <td width="147"><span class="style4"><img src="images/acs_logo.jpg" width="102" height="46"></span></td>
    <td width="147"><span class="style4"><img src="images/logo.jpg" ></span></td>
    <td width="321" nowrap class="style3">WebLogic JMX Monitoring</td>
    <td width="11">&nbsp;</td>
    <td width="191" nowrap class="style6">Updated every 30 seconds</td>
    <td width="11">&nbsp;</td>
    <td width="29" nowrap class="style6"><% out.println(new java.util.Date());%></td>
	<td width="14" align="right" nowrap class="style4">&nbsp;</td>
  </tr>
</table>

<table width="200" border="1" cellpadding="4" cellspacing="0" bordercolor="#CCCCCC" class="style5">
<%
   
    URL myURL=application.getResource("/WEB-INF/classes/conf.properties");       
    InputStream in = myURL.openStream();       
    Properties props = new Properties();       
    props.load( in );       
   
	// Start connection with the server
	String serverName = request.getParameter("servername");
	
	String protocol = props.getProperty("protocol." + serverName);
	String hostname = props.getProperty("hostname." + serverName);
	int port = Integer.parseInt(props.getProperty("port." + serverName));
	String username = props.getProperty("username." + serverName);
	String password = props.getProperty("password." + serverName);

	JMXConnection mBeanConnection = new JMXConnection();
	MBeanServerConnection connection = mBeanConnection.connection(username, password, protocol, hostname, port);
	
	Hashtable instanceTable;
	CollectInstanceStats statInstances = new CollectInstanceStats();
	
	instanceTable = statInstances.GetInstanceStats(connection);	

	Set<String> set = instanceTable.keySet();
	
	String str;
	String threadBackgroundColor = "";
	
    InstanceProperties instance = new InstanceProperties();
    NumberFormat formatter = new DecimalFormat("#,000");
    NumberFormat formatterDecimal = new DecimalFormat("#0.#");
    TimeFormat timeFormat = new TimeFormat();

    out.println("<tr>");
    out.println("<td nowrap>Instance Name</td>");
    out.println("<td align='right'>Heap Free Current</td>");
    out.println("<td align='right'>Heap Free Percent</td>");
    out.println("<td align='right'>Heap Size Max</td>");
    out.println("<td align='right'>Java Version</td>");
    out.println("<td align='right'>Up Time</td>");
    out.println("<td align='right'>Completed Request Count</td>"); 
    out.println("<td align='right'>Requests / Uptime Count</td>");
    out.println("<td align='right'>Execute Thread Total Count</td>");
    out.println("<td align='right'>Execute Thread Idle Count</td>");
    out.println("<td align='right'>Execute Thread Hogging Count</td>");
    out.println("<td align='right'>Currently Open Sockets</td>"); 
    out.println("<td align='right'>Pending User Request Count</td>");
    out.println("<td align='right'>Queue Length</td>");
    out.println("<td align='right'>Standby Thread Count</td>");
    out.println("<td align='right'>Throughput</td>");
    out.println("<td align='center'>Thread Dump</td>");
    out.println("<td align='left'>Overall Health</td>");
    out.println("</tr>");
    
    Iterator itr; 
    TreeMap treeMap = new TreeMap(instanceTable); 
    itr = treeMap.keySet().iterator(); 
    int i = 1;
    
    while (itr.hasNext()) {
    	
    	str = (String)itr.next();
        instance = (InstanceProperties)instanceTable.get(str);
        
        out.println("<tr ");
        float num;
        num = i++ % 2;
        if(num == 0) 
        	out.println("bgcolor='#D1E1F3'");

        out.println(" >");        		
        out.println("<td nowrap>" + instance.getName() + "</td>");
        out.println("<td nowrap align='right'>" + formatter.format(instance.getHeapFreeCurrent()) + "</td>");

        // pinta de amarelo com heap menos que 5%
        threadBackgroundColor = "";
        if (instance.getHeapFreePercent() < 3) {
        	threadBackgroundColor = "bgcolor='yellow'";
        }

        out.println("<td " + threadBackgroundColor + " nowrap align='right'>" + instance.getHeapFreePercent() + "</td>");
        out.println("<td nowrap align='right'>" + formatter.format(instance.getHeapSizeMax()) + "</td>");
        out.println("<td nowrap align='right'>" + instance.getJavaVersion() + "</td>");
        out.println("<td nowrap align='right'>" + instance.getUpTime() + "</td>");
        out.println("<td nowrap align='right'>" + formatterDecimal.format(instance.getCompletedRequestCount()) + "</td>");        

        out.println("<td nowrap align='right'>" + formatterDecimal.format(instance.getCompletedRequestCount() / (instance.getRequestUptime() / 1000)) + "</td>");

        out.println("<td nowrap align='right'>" + instance.getExecuteThreadTotaCount() + "</td>");
        out.println("<td nowrap align='right'>" + instance.getExecuteThreadIdleCount() + "</td>");
        
        // pinta de vermelho com hogging threads > 30
        threadBackgroundColor = "";
        if (instance.getExecuteThreadHoggingCount() > 10) {
        	threadBackgroundColor = "bgcolor='yellow'";
        }
        if (instance.getExecuteThreadHoggingCount() > 30) {
        	threadBackgroundColor = "bgcolor='red'";
        }
        
        out.println("<td " + threadBackgroundColor + " nowrap align='right'>" + instance.getExecuteThreadHoggingCount() + "</td>");

        out.println("<td nowrap align='right'>" + instance.getOpenSockets() + "</td>");
        out.println("<td nowrap align='right'>" + instance.getPendingUserRequestCount() + "</td>");
        out.println("<td nowrap align='right'>" + instance.getQueueLength() + "</td>");
        out.println("<td nowrap align='right'>" + instance.getStandbyThreadCount() + "</td>");

        out.println("<td nowrap align='right'>" + formatterDecimal.format(instance.getThroughtput()) + "</td>");
        
        out.println("<td nowrap align='center'><a href=ThreadDump.jsp?server=" + instance.getName() + "&servername=" + serverName + " target='_blank'><img src='images/dump.jpg' border='0' width='25' height='25'/></a></td>");
        out.println("<td nowrap align='left'>" + instance.getState() + "</td>");
        out.println("</tr>");
        
    	
    }
	


%>
</table>
</body>
</html>