package weblogic.cluster.messaging.internal;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.work.WorkManagerFactory;

public final class ClusterMessageSenderWrapper implements ClusterMessageSender {
   private static final DebugCategory debugClusterMessaging = Debug.getCategory("weblogic.cluster.leasing.ClusterMessaging");
   private static final boolean DEBUG = debugEnabled();
   private ClusterMessageSender delegate;
   private ArrayList listeners = new ArrayList();

   ClusterMessageSenderWrapper(ClusterMessageSender sender) {
      this.delegate = sender;
   }

   synchronized void addMessageDeliveryFailureListener(MessageDeliveryFailureListener listener) {
      this.listeners.add(listener);
   }

   synchronized void removeMessageDeliveryFailureListener(MessageDeliveryFailureListener listener) {
      this.listeners.remove(listener);
   }

   public ClusterResponse[] send(ClusterMessage message, ServerInformation[] runningServers) throws ClusterMessageProcessingException {
      try {
         return this.delegate.send(message, runningServers);
      } catch (ClusterMessageProcessingException var4) {
         this.invokeListeners(var4);
         throw var4;
      }
   }

   public ClusterResponse send(ClusterMessage message, ServerInformation runningServer) throws RemoteException {
      try {
         return this.delegate.send(message, runningServer);
      } catch (RemoteException var4) {
         this.invokeListeners(runningServer.getServerName(), var4);
         throw var4;
      }
   }

   public ClusterResponse send(ClusterMessage message, String serverName) throws RemoteException {
      try {
         return this.delegate.send(message, serverName);
      } catch (RemoteException var4) {
         this.invokeListeners(serverName, var4);
         throw var4;
      }
   }

   private synchronized void invokeListeners(ClusterMessageProcessingException cmpe) {
      if (this.listeners != null && this.listeners.size() != 0) {
         Map failedServers = cmpe.getFailedServers();
         if (failedServers != null && failedServers.size() != 0) {
            Iterator iter = failedServers.keySet().iterator();

            while(iter.hasNext()) {
               ServerInformation info = (ServerInformation)iter.next();
               this.invokeListeners(info.getServerName(), (RemoteException)failedServers.get(info));
            }

         }
      }
   }

   private synchronized void invokeListeners(String serverName, RemoteException re) {
      if (this.listeners != null && this.listeners.size() != 0) {
         MessageDeliveryFailureListener[] listeners = new MessageDeliveryFailureListener[this.listeners.size()];
         this.listeners.toArray(listeners);
         WorkManagerFactory.getInstance().getSystem().schedule(new ListenerInvocationRunnable(listeners, serverName, re));
      }
   }

   private static boolean debugEnabled() {
      return debugClusterMessaging.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[MessageSenderWrapper] " + s);
   }

   private static class ListenerInvocationRunnable implements Runnable {
      private final MessageDeliveryFailureListener[] listeners;
      private final String serverName;
      private final RemoteException re;

      ListenerInvocationRunnable(MessageDeliveryFailureListener[] listeners, String serverName, RemoteException re) {
         this.listeners = listeners;
         this.serverName = serverName;
         this.re = re;
      }

      public void run() {
         for(int count = 0; count < this.listeners.length; ++count) {
            MessageDeliveryFailureListener listener = this.listeners[count];
            if (ClusterMessageSenderWrapper.DEBUG) {
               ClusterMessageSenderWrapper.debug("invoking onMessageDeliveryFailure on " + this.serverName);
            }

            listener.onMessageDeliveryFailure(this.serverName, this.re);
         }

      }
   }
}
