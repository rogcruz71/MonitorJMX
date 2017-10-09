package monitor.jmx.com.br;

/**
 * @author Rogerio Cruz
 *
 */
public class JMSModuleTO {
	
	private String serverName;
	private String queueName;
	private Long MessagesCurrentCount;
	private Long MessagesPendingCount;
	private Long MessagesHighCount;
	private Long MessagesReceivedCount;
	private Long CurrentConsumersCount;
	
	public Long getCurrentConsumersCount() {
		return CurrentConsumersCount;
	}
	public void setCurrentConsumersCount(Long currentConsumersCount) {
		CurrentConsumersCount = currentConsumersCount;
	}
	public Long getMessagesCurrentCount() {
		return MessagesCurrentCount;
	}
	public void setMessagesCurrentCount(Long messagesCurrentCount) {
		MessagesCurrentCount = messagesCurrentCount;
	}
	public Long getMessagesHighCount() {
		return MessagesHighCount;
	}
	public void setMessagesHighCount(Long messagesHighCount) {
		MessagesHighCount = messagesHighCount;
	}
	public Long getMessagesPendingCount() {
		return MessagesPendingCount;
	}
	public void setMessagesPendingCount(Long messagesPendingCount) {
		MessagesPendingCount = messagesPendingCount;
	}
	public Long getMessagesReceivedCount() {
		return MessagesReceivedCount;
	}
	public void setMessagesReceivedCount(Long messagesReceivedCount) {
		MessagesReceivedCount = messagesReceivedCount;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	
}
