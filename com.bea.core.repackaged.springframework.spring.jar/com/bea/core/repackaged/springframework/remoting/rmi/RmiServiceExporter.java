package com.bea.core.repackaged.springframework.remoting.rmi;

import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class RmiServiceExporter extends RmiBasedExporter implements InitializingBean, DisposableBean {
   private String serviceName;
   private int servicePort = 0;
   private RMIClientSocketFactory clientSocketFactory;
   private RMIServerSocketFactory serverSocketFactory;
   private Registry registry;
   private String registryHost;
   private int registryPort = 1099;
   private RMIClientSocketFactory registryClientSocketFactory;
   private RMIServerSocketFactory registryServerSocketFactory;
   private boolean alwaysCreateRegistry = false;
   private boolean replaceExistingBinding = true;
   private Remote exportedObject;
   private boolean createdRegistry = false;

   public void setServiceName(String serviceName) {
      this.serviceName = serviceName;
   }

   public void setServicePort(int servicePort) {
      this.servicePort = servicePort;
   }

   public void setClientSocketFactory(RMIClientSocketFactory clientSocketFactory) {
      this.clientSocketFactory = clientSocketFactory;
   }

   public void setServerSocketFactory(RMIServerSocketFactory serverSocketFactory) {
      this.serverSocketFactory = serverSocketFactory;
   }

   public void setRegistry(Registry registry) {
      this.registry = registry;
   }

   public void setRegistryHost(String registryHost) {
      this.registryHost = registryHost;
   }

   public void setRegistryPort(int registryPort) {
      this.registryPort = registryPort;
   }

   public void setRegistryClientSocketFactory(RMIClientSocketFactory registryClientSocketFactory) {
      this.registryClientSocketFactory = registryClientSocketFactory;
   }

   public void setRegistryServerSocketFactory(RMIServerSocketFactory registryServerSocketFactory) {
      this.registryServerSocketFactory = registryServerSocketFactory;
   }

   public void setAlwaysCreateRegistry(boolean alwaysCreateRegistry) {
      this.alwaysCreateRegistry = alwaysCreateRegistry;
   }

   public void setReplaceExistingBinding(boolean replaceExistingBinding) {
      this.replaceExistingBinding = replaceExistingBinding;
   }

   public void afterPropertiesSet() throws RemoteException {
      this.prepare();
   }

   public void prepare() throws RemoteException {
      this.checkService();
      if (this.serviceName == null) {
         throw new IllegalArgumentException("Property 'serviceName' is required");
      } else {
         if (this.clientSocketFactory instanceof RMIServerSocketFactory) {
            this.serverSocketFactory = (RMIServerSocketFactory)this.clientSocketFactory;
         }

         if (this.clientSocketFactory != null && this.serverSocketFactory == null || this.clientSocketFactory == null && this.serverSocketFactory != null) {
            throw new IllegalArgumentException("Both RMIClientSocketFactory and RMIServerSocketFactory or none required");
         } else {
            if (this.registryClientSocketFactory instanceof RMIServerSocketFactory) {
               this.registryServerSocketFactory = (RMIServerSocketFactory)this.registryClientSocketFactory;
            }

            if (this.registryClientSocketFactory == null && this.registryServerSocketFactory != null) {
               throw new IllegalArgumentException("RMIServerSocketFactory without RMIClientSocketFactory for registry not supported");
            } else {
               this.createdRegistry = false;
               if (this.registry == null) {
                  this.registry = this.getRegistry(this.registryHost, this.registryPort, this.registryClientSocketFactory, this.registryServerSocketFactory);
                  this.createdRegistry = true;
               }

               this.exportedObject = this.getObjectToExport();
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Binding service '" + this.serviceName + "' to RMI registry: " + this.registry);
               }

               if (this.clientSocketFactory != null) {
                  UnicastRemoteObject.exportObject(this.exportedObject, this.servicePort, this.clientSocketFactory, this.serverSocketFactory);
               } else {
                  UnicastRemoteObject.exportObject(this.exportedObject, this.servicePort);
               }

               try {
                  if (this.replaceExistingBinding) {
                     this.registry.rebind(this.serviceName, this.exportedObject);
                  } else {
                     this.registry.bind(this.serviceName, this.exportedObject);
                  }

               } catch (AlreadyBoundException var2) {
                  this.unexportObjectSilently();
                  throw new IllegalStateException("Already an RMI object bound for name '" + this.serviceName + "': " + var2.toString());
               } catch (RemoteException var3) {
                  this.unexportObjectSilently();
                  throw var3;
               }
            }
         }
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
         if (this.alwaysCreateRegistry) {
            this.logger.debug("Creating new RMI registry");
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
      if (this.alwaysCreateRegistry) {
         this.logger.debug("Creating new RMI registry");
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
               return LocateRegistry.createRegistry(registryPort);
            }

            return var10000;
         }
      }
   }

   protected void testRegistry(Registry registry) throws RemoteException {
      registry.list();
   }

   public void destroy() throws RemoteException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Unbinding RMI service '" + this.serviceName + "' from registry" + (this.createdRegistry ? " at port '" + this.registryPort + "'" : ""));
      }

      try {
         this.registry.unbind(this.serviceName);
      } catch (NotBoundException var5) {
         if (this.logger.isInfoEnabled()) {
            this.logger.info("RMI service '" + this.serviceName + "' is not bound to registry" + (this.createdRegistry ? " at port '" + this.registryPort + "' anymore" : ""), var5);
         }
      } finally {
         this.unexportObjectSilently();
      }

   }

   private void unexportObjectSilently() {
      try {
         UnicastRemoteObject.unexportObject(this.exportedObject, true);
      } catch (NoSuchObjectException var2) {
         if (this.logger.isInfoEnabled()) {
            this.logger.info("RMI object for service '" + this.serviceName + "' is not exported anymore", var2);
         }
      }

   }
}
