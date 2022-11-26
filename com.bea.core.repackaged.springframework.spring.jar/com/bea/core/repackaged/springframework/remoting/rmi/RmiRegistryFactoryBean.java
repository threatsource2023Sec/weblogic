package com.bea.core.repackaged.springframework.remoting.rmi;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class RmiRegistryFactoryBean implements FactoryBean, InitializingBean, DisposableBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private String host;
   private int port = 1099;
   private RMIClientSocketFactory clientSocketFactory;
   private RMIServerSocketFactory serverSocketFactory;
   private Registry registry;
   private boolean alwaysCreate = false;
   private boolean created = false;

   public void setHost(String host) {
      this.host = host;
   }

   public String getHost() {
      return this.host;
   }

   public void setPort(int port) {
      this.port = port;
   }

   public int getPort() {
      return this.port;
   }

   public void setClientSocketFactory(RMIClientSocketFactory clientSocketFactory) {
      this.clientSocketFactory = clientSocketFactory;
   }

   public void setServerSocketFactory(RMIServerSocketFactory serverSocketFactory) {
      this.serverSocketFactory = serverSocketFactory;
   }

   public void setAlwaysCreate(boolean alwaysCreate) {
      this.alwaysCreate = alwaysCreate;
   }

   public void afterPropertiesSet() throws Exception {
      if (this.clientSocketFactory instanceof RMIServerSocketFactory) {
         this.serverSocketFactory = (RMIServerSocketFactory)this.clientSocketFactory;
      }

      if ((this.clientSocketFactory == null || this.serverSocketFactory != null) && (this.clientSocketFactory != null || this.serverSocketFactory == null)) {
         this.registry = this.getRegistry(this.host, this.port, this.clientSocketFactory, this.serverSocketFactory);
      } else {
         throw new IllegalArgumentException("Both RMIClientSocketFactory and RMIServerSocketFactory or none required");
      }
   }

   protected Registry getRegistry(String registryHost, int registryPort, @Nullable RMIClientSocketFactory clientSocketFactory, @Nullable RMIServerSocketFactory serverSocketFactory) throws RemoteException {
      if (registryHost != null) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Looking for RMI registry at port '" + registryPort + "' of host [" + registryHost + "]");
         }

         Registry reg = LocateRegistry.getRegistry(registryHost, registryPort, clientSocketFactory);
         this.testRegistry(reg);
         return reg;
      } else {
         return this.getRegistry(registryPort, clientSocketFactory, serverSocketFactory);
      }
   }

   protected Registry getRegistry(int registryPort, @Nullable RMIClientSocketFactory clientSocketFactory, @Nullable RMIServerSocketFactory serverSocketFactory) throws RemoteException {
      if (clientSocketFactory != null) {
         if (this.alwaysCreate) {
            this.logger.debug("Creating new RMI registry");
            this.created = true;
            return LocateRegistry.createRegistry(registryPort, clientSocketFactory, serverSocketFactory);
         } else {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Looking for RMI registry at port '" + registryPort + "', using custom socket factory");
            }

            Class var4 = LocateRegistry.class;
            synchronized(LocateRegistry.class) {
               Registry var10000;
               try {
                  Registry reg = LocateRegistry.getRegistry((String)null, registryPort, clientSocketFactory);
                  this.testRegistry(reg);
                  var10000 = reg;
               } catch (RemoteException var7) {
                  this.logger.trace("RMI registry access threw exception", var7);
                  this.logger.debug("Could not detect RMI registry - creating new one");
                  this.created = true;
                  return LocateRegistry.createRegistry(registryPort, clientSocketFactory, serverSocketFactory);
               }

               return var10000;
            }
         }
      } else {
         return this.getRegistry(registryPort);
      }
   }

   protected Registry getRegistry(int registryPort) throws RemoteException {
      if (this.alwaysCreate) {
         this.logger.debug("Creating new RMI registry");
         this.created = true;
         return LocateRegistry.createRegistry(registryPort);
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Looking for RMI registry at port '" + registryPort + "'");
         }

         Class var2 = LocateRegistry.class;
         synchronized(LocateRegistry.class) {
            Registry var10000;
            try {
               Registry reg = LocateRegistry.getRegistry(registryPort);
               this.testRegistry(reg);
               var10000 = reg;
            } catch (RemoteException var5) {
               this.logger.trace("RMI registry access threw exception", var5);
               this.logger.debug("Could not detect RMI registry - creating new one");
               this.created = true;
               return LocateRegistry.createRegistry(registryPort);
            }

            return var10000;
         }
      }
   }

   protected void testRegistry(Registry registry) throws RemoteException {
      registry.list();
   }

   public Registry getObject() throws Exception {
      return this.registry;
   }

   public Class getObjectType() {
      return this.registry != null ? this.registry.getClass() : Registry.class;
   }

   public boolean isSingleton() {
      return true;
   }

   public void destroy() throws RemoteException {
      if (this.created) {
         this.logger.debug("Unexporting RMI registry");
         UnicastRemoteObject.unexportObject(this.registry, true);
      }

   }
}
