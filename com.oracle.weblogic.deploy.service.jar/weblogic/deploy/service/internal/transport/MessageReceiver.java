package weblogic.deploy.service.internal.transport;

public interface MessageReceiver {
   void receiveMessage(DeploymentServiceMessage var1) throws Exception;

   DeploymentServiceMessage receiveSynchronousMessage(DeploymentServiceMessage var1) throws Exception;

   void setDispatcher(MessageDispatcher var1);
}
