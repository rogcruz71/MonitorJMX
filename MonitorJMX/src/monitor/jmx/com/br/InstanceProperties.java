package monitor.jmx.com.br;

/**
 * @author Rogerio Cruz
 *
 */
public class InstanceProperties {

	private String name;
	private Double heapFreeCurrent;
	private Long heapFreePercent;
	private Double heapSizeMax;
	private String javaVersion;
	private String upTime;
	private Double completedRequestCount;
	private Long executeThreadTotaCount;
	private Long executeThreadIdleCount;
	private Long executeThreadHoggingCount;
	private Long pendingUserRequestCount;
	private Long queueLength;
	private Long standbyThreadCount;
	private boolean suspended;
	private Double throughtput;
	private long port;
	private String state;
	private Long requestUptime;
	private Long openSockets;
	private Long nodeId; 

	public Long getOpenSockets() {
		return openSockets;
	}
	public void setOpenSockets(Long openSockets) {
		this.openSockets = openSockets;
	}
	public Long getRequestUptime() {
		return requestUptime;
	}
	public void setRequestUptime(Long requestUptime) {
		this.requestUptime = requestUptime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Double getCompletedRequestCount() {
		return completedRequestCount;
	}
	public void setCompletedRequestCount(Double completedRequestCount) {
		this.completedRequestCount = completedRequestCount;
	}
	public Long getExecuteThreadHoggingCount() {
		return executeThreadHoggingCount;
	}
	public void setExecuteThreadHoggingCount(Long executeThreadHoggingCount) {
		this.executeThreadHoggingCount = executeThreadHoggingCount;
	}
	public Long getExecuteThreadIdleCount() {
		return executeThreadIdleCount;
	}
	public void setExecuteThreadIdleCount(Long executeThreadIdleCount) {
		this.executeThreadIdleCount = executeThreadIdleCount;
	}
	public Long getExecuteThreadTotaCount() {
		return executeThreadTotaCount;
	}
	public void setExecuteThreadTotaCount(Long executeThreadTotaCount) {
		this.executeThreadTotaCount = executeThreadTotaCount;
	}
	public Double getHeapFreeCurrent() {
		return heapFreeCurrent;
	}
	public void setHeapFreeCurrent(Double heapFreeCurrent) {
		this.heapFreeCurrent = heapFreeCurrent;
	}
	public Long getHeapFreePercent() {
		return heapFreePercent;
	}
	public void setHeapFreePercent(Long heapFreePercent) {
		this.heapFreePercent = heapFreePercent;
	}
	public Double getHeapSizeMax() {
		return heapSizeMax;
	}
	public void setHeapSizeMax(Double heapSizeMax) {
		this.heapSizeMax = heapSizeMax;
	}
	public String getJavaVersion() {
		return javaVersion;
	}
	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPendingUserRequestCount() {
		return pendingUserRequestCount;
	}
	public void setPendingUserRequestCount(Long pendingUserRequestCount) {
		this.pendingUserRequestCount = pendingUserRequestCount;
	}
	public Long getQueueLength() {
		return queueLength;
	}
	public void setQueueLength(Long queueLength) {
		this.queueLength = queueLength;
	}
	public Long getStandbyThreadCount() {
		return standbyThreadCount;
	}
	public void setStandbyThreadCount(Long standbyThreadCount) {
		this.standbyThreadCount = standbyThreadCount;
	}
	public boolean isSuspended() {
		return suspended;
	}
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}
	public Double getThroughtput() {
		return throughtput;
	}
	public void setThroughtput(Double throughtput) {
		this.throughtput = throughtput;
	}
	public String getUpTime() {
		return upTime;
	}
	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}
	public Long getPort() {
		return port;
	}
	public void setPort(long port) {
		this.port = port;
	}	
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
		
}
