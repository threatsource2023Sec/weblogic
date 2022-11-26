package weblogic.deploy.service.internal.transport;

public interface MessageSender {
   void sendHeartbeatMessage(DeploymentServiceMessage var1, String var2) throws Exception;

   void sendMessageToTargetServer(DeploymentServiceMessage var1, String var2) throws Exception;

   void sendMessageToAdminServer(DeploymentServiceMessage var1) throws Exception;

   DeploymentServiceMessage sendBlockingMessageToAdminServer(DeploymentServiceMessage var1) throws Exception;

   void setLoopbackReceiver(MessageReceiver var1);
}
