package weblogic.deploy.service.internal.transport.http;

import java.rmi.RemoteException;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.internal.transport.DeploymentServiceMessage;
import weblogic.deploy.service.internal.transport.MessageDispatcher;
import weblogic.deploy.service.internal.transport.MessageReceiver;

public class HTTPMessageReceiver implements MessageReceiver {
   private static final HTTPMessageReceiver SINGLETON = new HTTPMessageReceiver();
   private MessageDispatcher dispatcher = null;

   public HTTPMessageReceiver() {
      Debug.serviceHttpDebug("Created HTTPMessageReceiver");
   }

   public static MessageReceiver getMessageReceiver() {
      return SINGLETON;
   }

   public void setDispatcher(MessageDispatcher dispatcher) {
      if (this.dispatcher == null) {
         this.dispatcher = dispatcher;
      }

   }

   public void receiveMessage(DeploymentServiceMessage message) throws Exception {
      this.dispatcher.dispatch(message);
   }

   public DeploymentServiceMessage receiveSynchronousMessage(DeploymentServiceMessage message) throws RemoteException {
      DeploymentServiceMessage result = this.dispatcher.blockingDispatch(message);
      return result;
   }
}
