package weblogic.management.mbeanservers.domainruntime.internal;

import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Hashtable;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.commo.StandardInterface;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.internal.WLSObjectNameManager;

public class FederatedObjectNameManager extends WLSObjectNameManager {
   MBeanServerConnectionManager connectionManager;
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXDomain");

   public boolean isFiltered(Object instance) {
      return instance instanceof StandardInterface ? true : super.isFiltered(instance);
   }

   public FederatedObjectNameManager(MBeanServerConnectionManager connectionManager, String domainName) {
      super(domainName);
      this.connectionManager = connectionManager;
      this.setAddDomainToReadOnly(true);
      this.connectionManager.addCallback(new MBeanServerConnectionManager.MBeanServerConnectionListener() {
         public void connect(String serverName, MBeanServerConnection connection) {
         }

         public void disconnect(String serverName) {
            Collection allObjectNames = FederatedObjectNameManager.this.getAllObjectNames();
            ObjectName[] objectNames = (ObjectName[])((ObjectName[])allObjectNames.toArray(new ObjectName[allObjectNames.size()]));

            for(int i = 0; i < objectNames.length; ++i) {
               ObjectName objectName = objectNames[i];
               String location = objectName.getKeyProperty("Location");
               if (location != null && location.equals(serverName)) {
                  FederatedObjectNameManager.this.unregisterObject(objectName);
               }
            }

         }
      });
   }

   public ObjectName buildObjectName(Object instance) {
      ObjectName result;
      if (Proxy.isProxyClass(instance.getClass())) {
         MBeanServerInvocationHandler handler = (MBeanServerInvocationHandler)Proxy.getInvocationHandler(instance);
         String serverName = this.connectionManager.lookupServerName(handler._getConnection());
         if (serverName == null) {
            return null;
         }

         ObjectName oname = handler._getObjectName();
         result = this.addLocation(oname, serverName);
      } else {
         result = super.buildObjectName(instance);
      }

      return result;
   }

   public ObjectName lookupObjectName(Object instance) {
      if (Proxy.isProxyClass(instance.getClass())) {
         MBeanServerInvocationHandler handler = (MBeanServerInvocationHandler)Proxy.getInvocationHandler(instance);
         return handler._getObjectName();
      } else {
         return super.lookupObjectName(instance);
      }
   }

   public ObjectName lookupRegisteredObjectName(Object instance) {
      if (Proxy.isProxyClass(instance.getClass())) {
         MBeanServerInvocationHandler handler = (MBeanServerInvocationHandler)Proxy.getInvocationHandler(instance);
         return handler._getObjectName();
      } else {
         return super.lookupRegisteredObjectName(instance);
      }
   }

   public Object lookupObject(ObjectName objectName) {
      Object result = super.lookupObject(objectName);
      if (result == null) {
         String location = objectName.getKeyProperty("Location");
         if (location != null && !location.equals(this.getDomainName())) {
            MBeanServerConnection connection = this.connectionManager.lookupMBeanServerConnection(location);
            Hashtable keys = objectName.getKeyPropertyList();
            keys.remove("Location");

            ObjectName remoteObjectName;
            try {
               remoteObjectName = new ObjectName(objectName.getDomain(), keys);
            } catch (MalformedObjectNameException var9) {
               throw new Error(var9);
            }

            try {
               result = MBeanServerInvocationHandler.newProxyInstance(connection, remoteObjectName);
            } catch (Throwable var8) {
               throw new Error(var8);
            }

            if (debug.isDebugEnabled()) {
               debug.debug("Registering on lookupObject for " + objectName + " to " + remoteObjectName + " of " + result.getClass().getName());
            }

            this.registerObject(objectName, result);
         }
      } else if (debug.isDebugEnabled()) {
         debug.debug("lookupObject: returning a " + result.getClass() + " for " + objectName);
      }

      return result;
   }

   private ObjectName addLocation(ObjectName name, String location) {
      Hashtable table = name.getKeyPropertyList();
      table.put("Location", location);

      try {
         return new ObjectName(name.getDomain(), table);
      } catch (MalformedObjectNameException var5) {
         throw new AssertionError(var5);
      }
   }
}
