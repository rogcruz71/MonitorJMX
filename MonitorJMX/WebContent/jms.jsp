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
<meta http-equiv="refresh" content="60">
<style type="text/css">
<!--
.style2 {
	font-weight: bold;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 24px;
	color: #0000FF;
}
.style3 {
	color: #33CC00;
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
.style7 {
	font-size: 13px;
	font-family: Arial, Helvetica, sans-serif;
	color: #33CC00;
	font-weight: bold;
}
-->
</style>
</head>
<body>
<table width="754" border="0">
  <tr>
    
    <td></td><td nowrap class="style3">WebLogic JMS Monitoring</td>
    <td>&nbsp;</td>
    <td nowrap class="style4">Updated every 60 seconds</td>
    <td>&nbsp;</td>
    <td nowrap class="style4"><% out.println(new java.util.Date());%></td>
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
	   
	// inicia a conexao com o servidor
	String protocol = props.getProperty("protocol." + serverName);
	String hostname = props.getProperty("hostname." + serverName);
	int port = Integer.parseInt(props.getProperty("port." + serverName));
	String username = props.getProperty("username." + serverName);
	String password = props.getProperty("password." + serverName);
	

	JMXConnection mBeanConnection = new JMXConnection();
	MBeanServerConnection connection = mBeanConnection.connection(username, password, protocol, hostname, port);
	
	Hashtable jmsTable;
	CollectJMSStats statJMS = new CollectJMSStats();
	
	// hash with jms servers list (clustered jms module)
	Hashtable serverList = new Hashtable();
	serverList.put("AdminServer","JMSServer-0");
	//serverList.put("SourceAdminServer","JMSServer");
		
	jmsTable = statJMS.GetJMSStats(connection, serverList);	
		
	String str;
	String threadBackgroundColor = "";
	
    JMSModuleTO jmsInstance = new JMSModuleTO();
    NumberFormat formatter = new DecimalFormat("#,000");
    NumberFormat formatterDecimal = new DecimalFormat("#0.#");
 

    out.println("<tr>");
    out.println("<td nowrap>Server</td>");
    out.println("<td align='left'>JMS Queue Name</td>");
    out.println("<td align='right'>Consumers Current Count</td>");
    out.println("<td align='right'>Messages Current Count</td>");
    out.println("<td align='right'>Messages Pending Count</td>");
    out.println("<td align='right'>Messages Current High Count</td>");
    out.println("<td align='right'>Messages Total Received Count</td>");
    out.println("</tr>");
    
    Iterator itr; 
    TreeMap treeMap = new TreeMap(jmsTable); 
    itr = treeMap.keySet().iterator(); 
    int i = 1;
    int jmsServer;
    
    while (itr.hasNext()) {
    	
    	jmsServer = (Integer)itr.next();
    	jmsInstance = (JMSModuleTO)jmsTable.get(jmsServer);
        
        out.println("<tr ");
        float num;
        num = i++ % 2;
        if(num == 0) 
        	out.println("bgcolor='#D1E1F3'");

        out.println(" >");        		
        out.println("<td nowrap>" + jmsInstance.getServerName() + "</td>");
        out.println("<td nowrap>" + jmsInstance.getQueueName() + "</td>");
        
        out.println("<td align='right' nowrap>" + jmsInstance.getCurrentConsumersCount() + "</td>");
        out.println("<td align='right' nowrap>" + jmsInstance.getMessagesCurrentCount() + "</td>");
        out.println("<td align='right' nowrap>" + jmsInstance.getMessagesPendingCount() + "</td>");
        out.println("<td align='right' nowrap>" + jmsInstance.getMessagesHighCount() + "</td>");
        out.println("<td align='right' nowrap>" + jmsInstance.getMessagesReceivedCount() + "</td>");
        
        out.println("</tr>");
    	
    }
	


%>
</table>
</body>
</html>