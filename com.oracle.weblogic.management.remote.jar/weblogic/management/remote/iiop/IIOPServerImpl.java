package weblogic.management.remote.iiop;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.server.ServerNotActiveException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.management.remote.rmi.RMIConnection;
import javax.management.remote.rmi.RMIConnectionImpl;
import javax.management.remote.rmi.RMIServerImpl;
import javax.security.auth.Subject;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jndi.CrossPartitionAware;
import weblogic.jndi.ThreadLocalMap;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.spi.EndPoint;

public class IIOPServerImpl extends RMIServerImpl implements CrossPartitionAware {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXCoreConcise");
   private final Map env;
   private Map disconnectListeners = new ConcurrentHashMap();

   public IIOPServerImpl(Map map) {
      super(map);
      this.env = map == null ? Collections.EMPTY_MAP : map;
   }

   public boolean isAccessAllowed() {
      return true;
   }

   protected void export() throws IOException {
      PortableRemoteObject.exportObject(this);
   }

   public Remote toStub() throws IOException {
      return PortableRemoteObject.toStub(this);
   }

   protected RMIConnection makeClient(String connectionId, Subject subject) throws IOException {
      if (connectionId == null) {
         throw new NullPointerException("Null connectionId");
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Make client for " + connectionId + " subject " + subject);
         }

         boolean newThreadEnvSet = false;
         Hashtable origThreadInstance = null;

         RMIConnectionImpl client;
         try {
            origThreadInstance = ThreadLocalMap.pop();
            newThreadEnvSet = this.setEnvOnThreadContext();
            client = new RMIConnectionImpl(this, connectionId, this.getDefaultClassLoader(), subject, this.env);
            PortableRemoteObject.exportObject(client);

            try {
               EndPoint clientEndpoint = ServerHelper.getClientEndPoint();
               if (!clientEndpoint.getHostID().isLocal()) {
                  DisconnectListener dcl = new ConnectorListener(client, clientEndpoint);
                  clientEndpoint.addDisconnectListener((Remote)null, dcl);
                  this.disconnectListeners.put(client, dcl);
               }
            } catch (ServerNotActiveException var11) {
            }
         } finally {
            if (newThreadEnvSet) {
               ThreadLocalMap.pop();
               if (origThreadInstance != null) {
                  ThreadLocalMap.push(origThreadInstance);
               }
            }

         }

         return client;
      }
   }

   private boolean setEnvOnThreadContext() {
      Long readResponseTimeout = (Long)this.env.get("weblogic.jndi.responseReadTimeout");
      Long connectionTimeout = (Long)this.env.get("weblogic.jndi.connectTimeout");
      String providerUrl = (String)this.env.get("java.naming.provider.url");
      Hashtable ht = new Hashtable();
      if (readResponseTimeout != null && readResponseTimeout > 0L) {
         ht.put("weblogic.jndi.responseReadTimeout", readResponseTimeout);
      }

      if (connectionTimeout != null && connectionTimeout > 0L) {
         ht.put("weblogic.jndi.connectTimeout", connectionTimeout);
      }

      if (providerUrl != null) {
         ht.put("java.naming.provider.url", providerUrl);
      }

      if (ht.size() > 0) {
         ThreadLocalMap.push(ht);
         return true;
      } else {
         return false;
      }
   }

   protected void closeClient(RMIConnection client) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("IIOPServerImpl close client " + client);
      }

      DisconnectListener dcl = (DisconnectListener)this.disconnectListeners.remove(client);
      if (dcl != null) {
         dcl.onDisconnect((DisconnectEvent)null);
      }

      unexportObjectSafely(client);
   }

   private static void unexportObjectSafely(Remote client) throws NoSuchObjectException {
      try {
         PortableRemoteObject.unexportObject(client);
      } catch (NoSuchObjectException var2) {
         var2.printStackTrace();
      }

   }

   protected String getProtocol() {
      return "iiop";
   }

   protected void closeServer() throws IOException {
      unexportObjectSafely(this);
   }

   public RMIConnection newClient(Object credentials) throws IOException {
      return super.newClient(credentials);
   }

   private class ConnectorListener implements DisconnectListener {
      RMIConnection toMonitor;
      private EndPoint endPoint;

      ConnectorListener(RMIConnection client, EndPoint ep) {
         this.toMonitor = client;
         this.endPoint = ep;
      }

      public void onDisconnect(DisconnectEvent event) {
         try {
            if (IIOPServerImpl.debugLogger.isDebugEnabled()) {
               IIOPServerImpl.debugLogger.debug("IIOPServerImpl onDisconnect called");
            }

            try {
               this.endPoint.removeDisconnectListener((Remote)null, this);
            } catch (Exception var3) {
               var3.printStackTrace();
            }

            this.toMonitor.close();
         } catch (IOException var4) {
            throw new AssertionError(var4);
         }
      }
   }
}
