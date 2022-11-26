package weblogic.connector.common;

import java.util.HashSet;
import weblogic.j2ee.dd.xml.JCAConnectionFactoryProvider;
import weblogic.j2ee.descriptor.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.OutboundResourceAdapterBean;
import weblogic.j2ee.descriptor.ResourceAdapterBean;

public class JCAConnectionFactoryRegistry extends JCAConnectionFactoryProvider {
   private HashSet connectionFactorySet = new HashSet();
   private static JCAConnectionFactoryRegistry instance = null;

   private JCAConnectionFactoryRegistry() {
   }

   public static synchronized JCAConnectionFactoryRegistry getInstance() {
      if (instance == null) {
         createInstane();
      }

      return instance;
   }

   public synchronized boolean isAdapterConnectionFactory(String className) {
      return this.connectionFactorySet.contains(className);
   }

   private void registerConnectionFactory(String cf) {
      if (!this.connectionFactorySet.contains(cf)) {
         this.connectionFactorySet.add(cf);
         if (Debug.isDeploymentEnabled()) {
            Debug.deployment("Registering JCA Connection Factory: " + instance);
         }
      }

   }

   public void registerConnectionFactory(ConnectorBean connectorBean) {
      if (connectorBean != null) {
         ResourceAdapterBean resourceAdapter = connectorBean.getResourceAdapter();
         if (resourceAdapter != null) {
            OutboundResourceAdapterBean outbound = resourceAdapter.getOutboundResourceAdapter();
            if (outbound != null) {
               ConnectionDefinitionBean[] connections = outbound.getConnectionDefinitions();
               if (connections != null) {
                  synchronized(this) {
                     ConnectionDefinitionBean[] var6 = connections;
                     int var7 = connections.length;

                     for(int var8 = 0; var8 < var7; ++var8) {
                        ConnectionDefinitionBean connectionDef = var6[var8];
                        String connectionFactoryInterface = connectionDef.getConnectionFactoryInterface();
                        if (connectionFactoryInterface != null) {
                           this.registerConnectionFactory(connectionFactoryInterface.trim());
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private static void createInstane() {
      instance = new JCAConnectionFactoryRegistry();
      if (Debug.isDeploymentEnabled()) {
         Debug.deployment("Registering JCAConnectionFactoryProvider: " + instance);
      }

      JCAConnectionFactoryProvider.set(instance);
   }
}
