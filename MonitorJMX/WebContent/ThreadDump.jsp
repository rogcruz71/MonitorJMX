<%@ page language="java" contentType="text/plain; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.*";
import="weblogic.management.scripting.utils.WLSTInterpreter";
import="org.python.util.InteractiveInterpreter";
import="monitor.jmx.com.br.*";
import="java.util.Properties";
import="java.io.FileInputStream";
import="java.io.InputStream";
import="java.net.URL" %>

<%
    URL myURL=application.getResource("/WEB-INF/classes/conf.properties");       
    InputStream in = myURL.openStream();       
    Properties props = new Properties();       
    props.load( in );       
   
	// inicia a conexao com o servidor
	
	String serverName = request.getParameter("servername");
	
	String protocol = props.getProperty("protocol." + serverName);
	String hostname = props.getProperty("hostname." + serverName);
	int port = Integer.parseInt(props.getProperty("port." + serverName));
	String username = props.getProperty("username." + serverName);
	String password = props.getProperty("password." + serverName);
	
	CollectThreadDump collectDump = new CollectThreadDump();

	String dump = null;

    String url = protocol + "://" + hostname + ":" + port;
    String server = request.getParameter("server");
    
	dump = collectDump.GetThreadDump(username,password,url,server);
	

%>

<%=dump%>

