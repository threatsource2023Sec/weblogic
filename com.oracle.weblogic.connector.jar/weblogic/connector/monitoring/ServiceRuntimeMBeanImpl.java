package weblogic.connector.monitoring;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RACollectionManager;
import weblogic.connector.common.RAInstanceManager;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ConnectorComponentRuntimeMBean;
import weblogic.management.runtime.ConnectorConnectionPoolRuntimeMBean;
import weblogic.management.runtime.ConnectorInboundRuntimeMBean;
import weblogic.management.runtime.ConnectorServiceRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.utils.ErrorCollectionException;

public final class ServiceRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ConnectorServiceRuntimeMBean {
   Set connRuntimes = new HashSet(10);

   public ServiceRuntimeMBeanImpl() throws ManagementException {
      super("ConnectorService");
   }

   private void suspendOrResume(int action, int type, Properties props) throws ErrorCollectionException {
      Debug.service("About to suspend/resume all resource adapters");
      ErrorCollectionException ece = new ErrorCollectionException();
      boolean hadErrors = false;

      try {
         Iterator var6 = RACollectionManager.getRAs().iterator();

         while(var6.hasNext()) {
            RAInstanceManager ra = (RAInstanceManager)var6.next();

            try {
               if (action == 1) {
                  ra.suspend(type, props);
               } else if (action == 2) {
                  ra.resume(type, props);
               }
            } catch (Throwable var12) {
               ece.addError(var12);
               hadErrors = true;
            }
         }
      } finally {
         if (hadErrors) {
            throw ece;
         }

      }

   }

   public void suspendAll(Properties props) throws ErrorCollectionException {
      this.suspendOrResume(1, 7, props);
   }

   public void resumeAll(Properties props) throws ErrorCollectionException {
      this.suspendOrResume(2, 7, props);
   }

   public void suspend(int type, Properties props) throws ErrorCollectionException {
      this.suspendOrResume(1, type, props);
   }

   public void resume(int type, Properties props) throws ErrorCollectionException {
      this.suspendOrResume(2, type, props);
   }

   public void suspendAll() throws ErrorCollectionException {
      this.suspendOrResume(1, 7, (Properties)null);
   }

   public void suspend(int type) throws ErrorCollectionException {
      this.suspendOrResume(1, type, (Properties)null);
   }

   public void resumeAll() throws ErrorCollectionException {
      this.suspendOrResume(2, 7, (Properties)null);
   }

   public void resume(int type) throws ErrorCollectionException {
      this.suspendOrResume(2, type, (Properties)null);
   }

   public int getActiveRACount() {
      int count = 0;
      Iterator raIterator = this.connRuntimes.iterator();

      while(raIterator.hasNext()) {
         ConnectorComponentRuntimeMBean connRuntime = (ConnectorComponentRuntimeMBean)raIterator.next();
         if (connRuntime.isActiveVersion()) {
            ++count;
         }
      }

      return count;
   }

   public int getRACount() {
      return this.connRuntimes.size();
   }

   public ConnectorComponentRuntimeMBean[] getRAs() {
      return (ConnectorComponentRuntimeMBean[])((ConnectorComponentRuntimeMBean[])this.connRuntimes.toArray(new ConnectorComponentRuntimeMBean[this.connRuntimes.size()]));
   }

   public ConnectorComponentRuntimeMBean[] getActiveRAs() {
      List tmpConnRuntimes = new Vector(10);
      Iterator raIterator = this.connRuntimes.iterator();

      while(raIterator.hasNext()) {
         ConnectorComponentRuntimeMBean connRuntime = (ConnectorComponentRuntimeMBean)raIterator.next();
         if (connRuntime.isActiveVersion()) {
            tmpConnRuntimes.add(connRuntime);
         }
      }

      return (ConnectorComponentRuntimeMBean[])((ConnectorComponentRuntimeMBean[])tmpConnRuntimes.toArray(new ConnectorComponentRuntimeMBean[tmpConnRuntimes.size()]));
   }

   public ConnectorComponentRuntimeMBean[] getInactiveRAs() {
      List tmpConnRuntimes = new Vector(10);
      Iterator raIterator = this.connRuntimes.iterator();

      while(raIterator.hasNext()) {
         ConnectorComponentRuntimeMBean connRuntime = (ConnectorComponentRuntimeMBean)raIterator.next();
         if (!connRuntime.isActiveVersion()) {
            tmpConnRuntimes.add(connRuntime);
         }
      }

      return (ConnectorComponentRuntimeMBean[])((ConnectorComponentRuntimeMBean[])tmpConnRuntimes.toArray(new ConnectorComponentRuntimeMBean[tmpConnRuntimes.size()]));
   }

   public ConnectorComponentRuntimeMBean getRA(String key) {
      ConnectorComponentRuntimeMBean connRuntime = null;
      Iterator raIterator = this.connRuntimes.iterator();
      if (key != null && key.length() > 0) {
         while(raIterator.hasNext()) {
            connRuntime = (ConnectorComponentRuntimeMBean)raIterator.next();
            if (key.equals(connRuntime.getJndiName())) {
               break;
            }

            connRuntime = null;
         }
      }

      return connRuntime;
   }

   /** @deprecated */
   @Deprecated
   public int getConnectionPoolCurrentCount() {
      int count = 0;
      ConnectorComponentRuntimeMBean connRuntime = null;
      Iterator raIterator = this.connRuntimes.iterator();

      while(raIterator.hasNext()) {
         connRuntime = (ConnectorComponentRuntimeMBean)raIterator.next();
         if (connRuntime.isActiveVersion()) {
            ConnectorConnectionPoolRuntimeMBean[] connPoolRuntimeArray = connRuntime.getConnectionPools();
            if (connPoolRuntimeArray != null) {
               count += connPoolRuntimeArray.length;
            }
         }
      }

      return count;
   }

   /** @deprecated */
   @Deprecated
   public int getConnectionPoolsTotalCount() {
      return RACollectionManager.getConnectionPoolsTotalCount();
   }

   /** @deprecated */
   @Deprecated
   public ConnectorConnectionPoolRuntimeMBean[] getConnectionPools() {
      ConnectorComponentRuntimeMBean connRuntime = null;
      Iterator raIterator = this.connRuntimes.iterator();
      Vector connPoolRuntimeList = new Vector(10);

      while(true) {
         ConnectorConnectionPoolRuntimeMBean[] connPoolRuntimeArray;
         do {
            do {
               if (!raIterator.hasNext()) {
                  return (ConnectorConnectionPoolRuntimeMBean[])((ConnectorConnectionPoolRuntimeMBean[])connPoolRuntimeList.toArray(new ConnectorConnectionPoolRuntimeMBean[connPoolRuntimeList.size()]));
               }

               connRuntime = (ConnectorComponentRuntimeMBean)raIterator.next();
            } while(!connRuntime.isActiveVersion());

            connPoolRuntimeArray = connRuntime.getConnectionPools();
         } while(connPoolRuntimeArray == null);

         for(int i = 0; i < connPoolRuntimeArray.length; ++i) {
            connPoolRuntimeList.add(connPoolRuntimeArray[i]);
         }
      }
   }

   public ConnectorConnectionPoolRuntimeMBean getConnectionPool(String key) {
      Debug.enter(this, "getConnectionPool( " + key + " )");
      ConnectorConnectionPoolRuntimeMBean poolRuntime = null;

      ConnectorConnectionPoolRuntimeMBean var12;
      try {
         ConnectorConnectionPoolRuntimeMBean[] poolRuntimeArray = null;
         ConnectorComponentRuntimeMBean connRuntime = null;
         Iterator raIterator = null;
         boolean objFound = false;
         if (key != null && key.length() > 0) {
            raIterator = this.connRuntimes.iterator();

            label132:
            while(true) {
               do {
                  while(true) {
                     if (!raIterator.hasNext() || objFound) {
                        break label132;
                     }

                     connRuntime = (ConnectorComponentRuntimeMBean)raIterator.next();
                     if (connRuntime.isActiveVersion()) {
                        poolRuntimeArray = connRuntime.getConnectionPools();
                        break;
                     }

                     if (Debug.getVerbose(this)) {
                        Debug.println((Object)this, (String)("getConnectionPool() found inactive conn component, name = " + connRuntime.getName()));
                     }
                  }
               } while(poolRuntimeArray == null);

               String poolKey = null;

               for(int i = 0; i < poolRuntimeArray.length && !objFound; ++i) {
                  poolKey = poolRuntimeArray[i].getKey();
                  Debug.println((Object)this, (String)(".getConnectionPool() pool[" + i + "] = " + poolKey));
                  if (key.equals(poolKey)) {
                     Debug.println((Object)this, (String)".getConnectionPool() Found pool");
                     poolRuntime = poolRuntimeArray[i];
                     objFound = true;
                  }
               }
            }
         }

         var12 = poolRuntime;
      } finally {
         if (Debug.getVerbose(this)) {
            Debug.exit(this, "getConnectionPool( " + key + " ) returned " + poolRuntime);
         }

      }

      return var12;
   }

   public ConnectorInboundRuntimeMBean[] getInboundConnections(String messageListenerType) {
      ConnectorInboundRuntimeMBean[] inboundRuntimeArray = null;
      ConnectorComponentRuntimeMBean connRuntime = null;
      Iterator raIterator = this.connRuntimes.iterator();
      boolean objFound = false;
      List connectorInboundRuntimeMBeanList = new Vector(10);
      if (messageListenerType != null && messageListenerType.length() > 0) {
         while(true) {
            do {
               do {
                  if (!raIterator.hasNext() || objFound) {
                     return (ConnectorInboundRuntimeMBean[])((ConnectorInboundRuntimeMBean[])connectorInboundRuntimeMBeanList.toArray(new ConnectorInboundRuntimeMBean[connectorInboundRuntimeMBeanList.size()]));
                  }

                  connRuntime = (ConnectorComponentRuntimeMBean)raIterator.next();
               } while(!connRuntime.isActiveVersion());

               inboundRuntimeArray = connRuntime.getInboundConnections();
            } while(inboundRuntimeArray == null);

            for(int i = 0; i < inboundRuntimeArray.length; ++i) {
               if (messageListenerType.equals(inboundRuntimeArray[i].getMsgListenerType())) {
                  connectorInboundRuntimeMBeanList.add(inboundRuntimeArray[i]);
               }
            }
         }
      } else {
         return (ConnectorInboundRuntimeMBean[])((ConnectorInboundRuntimeMBean[])connectorInboundRuntimeMBeanList.toArray(new ConnectorInboundRuntimeMBean[connectorInboundRuntimeMBeanList.size()]));
      }
   }

   public boolean addConnectorRuntime(ConnectorComponentRuntimeMBean connRuntime) {
      return this.connRuntimes.add(connRuntime);
   }

   public boolean removeConnectorRuntime(ConnectorComponentRuntimeMBean connRuntime) {
      return this.connRuntimes.remove(connRuntime);
   }
}
