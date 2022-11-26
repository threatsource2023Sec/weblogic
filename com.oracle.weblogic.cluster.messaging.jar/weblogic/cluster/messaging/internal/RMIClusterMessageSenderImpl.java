package weblogic.cluster.messaging.internal;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import javax.naming.NamingException;
import weblogic.jndi.api.ServerEnvironment;
import weblogic.management.configuration.DatabaseLessLeasingBasisMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.URLManagerService;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.collections.ConcurrentHashMap;

public class RMIClusterMessageSenderImpl implements ClusterMessageSender {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugCategory debugMessageSender = Debug.getCategory("weblogic.cluster.leasing.MessageSender");
   private static final boolean DEBUG = debugEnabled();
   private static final ConcurrentHashMap serverNameToURL = new ConcurrentHashMap();
   private static final Integer OVERRIDE_TIMEOUT = Integer.getInteger("weblogic.cluster.messageDeliveryTimeout");
   private boolean isOneWay;

   private void setOneWay(boolean oneWay) {
      this.isOneWay = oneWay;
   }

   public static RMIClusterMessageSenderImpl getInstance() {
      return RMIClusterMessageSenderImpl.Factory.SEND_RECV;
   }

   public static RMIClusterMessageSenderImpl getOneWay() {
      return RMIClusterMessageSenderImpl.Factory.ONE_WAY;
   }

   public ClusterResponse[] send(ClusterMessage message, ServerInformation[] runningServers) throws ClusterMessageProcessingException {
      ClusterResponse[] responses = new ClusterResponse[runningServers.length];
      HashMap failedServers = new HashMap();

      for(int count = 0; count < runningServers.length; ++count) {
         try {
            responses[count] = this.send(message, runningServers[count]);
         } catch (RemoteException var7) {
            failedServers.put(runningServers[count], var7);
         }
      }

      if (failedServers.size() > 0) {
         throw new ClusterMessageProcessingException(responses, failedServers);
      } else {
         return responses;
      }
   }

   public ClusterResponse send(ClusterMessage message, ServerInformation runningServer) throws RemoteException {
      return this.send(message, runningServer.getServerName());
   }

   public ClusterResponse send(ClusterMessage message, String serverName) throws RemoteException {
      return this.send(message, serverName, -1);
   }

   public ClusterResponse send(ClusterMessage message, String serverName, int timeout) throws RemoteException {
      if (DEBUG) {
         debug("Trying to send message " + message + " to " + serverName + " with timeout " + timeout);
      }

      ServerEnvironment env = (ServerEnvironment)GlobalServiceLocator.getServiceLocator().getService(ServerEnvironment.class, new Annotation[0]);
      ClusterMessageEndPoint messageEndPoint;
      if (timeout < 0) {
         messageEndPoint = this.getRMIMessageEndPoint(env, serverName);
      } else {
         messageEndPoint = this.getRMIMessageEndPoint(env, serverName, timeout);
      }

      if (messageEndPoint == null) {
         throw new ClusterMessageProcessingException("MessageEndPoint not available for " + serverName + ". The server is probably dead");
      } else {
         ClusterResponse var7;
         try {
            if (DEBUG) {
               debug(Thread.currentThread() + ": receiver found for " + serverName);
            }

            RMIEnvironment.getEnvironment().pushIntoThreadLocalMap(env.getProperties());
            ClusterResponse response;
            if (this.isOneWay) {
               messageEndPoint.processOneWay(message);
               response = null;
               return response;
            }

            response = messageEndPoint.process(message);
            if (DEBUG) {
               debug("response received for " + serverName + ". Response = " + response);
            }

            var7 = response;
         } catch (RemoteException var12) {
            debug("exception " + var12 + " processing message for " + serverName);
            if (DEBUG) {
               var12.printStackTrace();
            }

            throw var12;
         } catch (RemoteRuntimeException var13) {
            if (DEBUG) {
               debug("remote runtime exception processing message for " + serverName);
            }

            if (DEBUG) {
               var13.printStackTrace();
            }

            throw new RemoteException("RMI stub threw a sender side exception ", var13);
         } finally {
            RMIEnvironment.getEnvironment().popFromThreadLocalMap();
         }

         return var7;
      }
   }

   private static void debug(String s) {
      System.out.println(new Date() + " " + Thread.currentThread().getName() + " [RMIClusterMessageSender] " + s);
   }

   private ClusterMessageEndPoint getRMIMessageEndPoint(ServerEnvironment env, String serverName) throws RemoteException {
      DatabaseLessLeasingBasisMBean mbean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getDatabaseLessLeasingBasis();
      int timeout = mbean.getMessageDeliveryTimeout();
      if (OVERRIDE_TIMEOUT != null && OVERRIDE_TIMEOUT > 0) {
         timeout = OVERRIDE_TIMEOUT;
      }

      return this.getRMIMessageEndPoint(env, serverName, timeout);
   }

   private ClusterMessageEndPoint getRMIMessageEndPoint(ServerEnvironment env, String serverName, int timeout) throws RemoteException {
      try {
         env.setInitialProperties(new Hashtable());
         if (DEBUG) {
            debug("Setting ResponseReadTimeout " + timeout);
         }

         env.setResponseReadTimeout((long)timeout);
         env.setProviderUrl(getURL(serverName));
         ClusterMessageEndPoint remote = (ClusterMessageEndPoint)PortableRemoteObject.narrow(env.getInitialReference(RMIClusterMessageEndPointImpl.class), ClusterMessageEndPoint.class);
         return remote;
      } catch (UnknownHostException var5) {
         if (DEBUG) {
            var5.printStackTrace();
         }

         throw var5;
      } catch (NamingException var6) {
         if (DEBUG) {
            var6.printStackTrace();
         }

         return null;
      }
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private static String getURL(String serverName) throws UnknownHostException {
      try {
         String url = getURLManagerService().findAdministrationURL(serverName);
         serverNameToURL.put(serverName, url);
         return url;
      } catch (UnknownHostException var3) {
         String url = (String)serverNameToURL.get(serverName);
         if (url != null) {
            return url;
         } else {
            throw var3;
         }
      }
   }

   private static boolean debugEnabled() {
      return debugMessageSender.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }

   private static final class Factory {
      static final RMIClusterMessageSenderImpl SEND_RECV = new RMIClusterMessageSenderImpl();
      static final RMIClusterMessageSenderImpl ONE_WAY = new RMIClusterMessageSenderImpl();

      static {
         ONE_WAY.setOneWay(true);
      }
   }
}
