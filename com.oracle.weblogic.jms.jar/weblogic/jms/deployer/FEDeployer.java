package weblogic.jms.deployer;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import javax.jms.JMSException;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.frontend.FEConnectionFactory;
import weblogic.jms.frontend.FrontEnd;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class FEDeployer implements DeployerConstants {
   private Object shutdownLock;
   private boolean initialized;
   private HashMap defaultConnectionFactories;
   private FrontEnd frontEnd;
   private JMSService jmsService;
   private FEConnectionFactory platformConnectionFactory;

   public FEDeployer(JMSService jmsService) throws ManagementException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Constructing JMS FEDeployer");
      }

      this.jmsService = jmsService;
      this.allocate();
   }

   public void initialize(FrontEnd fe) {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Initializing JMS FEDeployer");
      }

      this.frontEnd = fe;
      this.initializeConnectionFactories();
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      if (ManagementService.getRuntimeAccess(kernelId).getServer().isJMSDefaultConnectionFactoriesEnabled()) {
         this.deployDefaultConnectionFactories();
      }

      this.deployPlatformConnectionFactory();
   }

   public void shutdown() {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Shutting down JMS FEDeployer");
      }

      synchronized(this.shutdownLock) {
         try {
            this.undeployDefaultConnectionFactories();
         } catch (JMSException var5) {
         }

         try {
            this.undeployPlatformConnectionFactory();
         } catch (JMSException var4) {
         }

      }
   }

   public void allocate() {
      this.defaultConnectionFactories = new HashMap();
   }

   public FrontEnd getFrontEnd() {
      return this.frontEnd;
   }

   public void setFrontEnd(FrontEnd frontEnd) {
      this.frontEnd = frontEnd;
   }

   public Object getShutdownLock() {
      synchronized(this.shutdownLock) {
         return this.shutdownLock;
      }
   }

   public void setShutdownLock(Object lock) {
      this.shutdownLock = lock;
   }

   public void initializeConnectionFactories() {
      FEConnectionFactory defaultConnectionFactory = new FEConnectionFactory(this.frontEnd, DeployerConstants.DefaultNames.DEFAULT.getCFName(), DeployerConstants.DefaultNames.DEFAULT.getJndiName(), false, false, "All");
      FEConnectionFactory defaultXAConnectionFactory = new FEConnectionFactory(this.frontEnd, DeployerConstants.DefaultNames.XA.getCFName(), DeployerConstants.DefaultNames.XA.getJndiName(), false, true, "All");
      FEConnectionFactory defaultXAConnectionFactory0 = new FEConnectionFactory(this.frontEnd, DeployerConstants.DefaultNames.XA0.getCFName(), DeployerConstants.DefaultNames.XA0.getJndiName(), false, true, "All", false, false);
      FEConnectionFactory defaultXAConnectionFactory1 = new FEConnectionFactory(this.frontEnd, DeployerConstants.DefaultNames.XA1.getCFName(), DeployerConstants.DefaultNames.XA1.getJndiName(), false, true, "All", false, true);
      FEConnectionFactory defaultXAConnectionFactory2 = new FEConnectionFactory(this.frontEnd, DeployerConstants.DefaultNames.XA2.getCFName(), DeployerConstants.DefaultNames.XA2.getJndiName(), false, true, "All", true, false);
      FEConnectionFactory messageDrivenBeanConnectionFactory = new FEConnectionFactory(this.frontEnd, DeployerConstants.DefaultNames.MDB.getCFName(), DeployerConstants.DefaultNames.MDB.getJndiName(), false, true, "Previous");
      FEConnectionFactory queueConnectionFactory = new FEConnectionFactory(this.frontEnd, DeployerConstants.DefaultNames.QUEUE.getCFName(), DeployerConstants.DefaultNames.QUEUE.getJndiName(), true, true, "Previous");
      FEConnectionFactory topicConnectionFactory = new FEConnectionFactory(this.frontEnd, DeployerConstants.DefaultNames.TOPIC.getCFName(), DeployerConstants.DefaultNames.TOPIC.getJndiName(), true, true, "Previous");
      synchronized(this.shutdownLock) {
         if (!this.jmsService.isShutdown()) {
            this.defaultConnectionFactories.put("DefaultConnectionFactory", defaultConnectionFactory);
            this.defaultConnectionFactories.put("DefaultXAConnectionFactory", defaultXAConnectionFactory);
            this.defaultConnectionFactories.put("DefaultXAConnectionFactory0", defaultXAConnectionFactory0);
            this.defaultConnectionFactories.put("DefaultXAConnectionFactory1", defaultXAConnectionFactory1);
            this.defaultConnectionFactories.put("DefaultXAConnectionFactory2", defaultXAConnectionFactory2);
            this.defaultConnectionFactories.put("MessageDrivenBeanConnectionFactory", messageDrivenBeanConnectionFactory);
            this.defaultConnectionFactories.put("QueueConnectionFactory", queueConnectionFactory);
            this.defaultConnectionFactories.put("TopicConnectionFactory", topicConnectionFactory);
         }
      }
   }

   public void deployDefaultConnectionFactories() {
      int numFactories = 0;
      Iterator iterator;
      synchronized(this.shutdownLock) {
         iterator = ((HashMap)this.defaultConnectionFactories.clone()).values().iterator();
      }

      try {
         while(iterator.hasNext()) {
            FEConnectionFactory connectionFactory = (FEConnectionFactory)iterator.next();
            if (this.jmsService.isActive()) {
               connectionFactory.bind();
            }

            this.frontEnd.connectionFactoryAdd(connectionFactory);
            ++numFactories;
         }
      } catch (JMSException var7) {
         JMSLogger.logConnFactoryFailed(this.jmsService.getMbeanName(), var7);
      }

      JMSLogger.logCntDefCFactory(numFactories);
   }

   public void undeployDefaultConnectionFactories() throws JMSException {
      Iterator itr;
      synchronized(this.shutdownLock) {
         itr = ((HashMap)this.defaultConnectionFactories.clone()).values().iterator();
      }

      while(itr.hasNext()) {
         try {
            FEConnectionFactory factory = (FEConnectionFactory)itr.next();
            factory.shutdown();
            synchronized(this.shutdownLock) {
               this.defaultConnectionFactories.remove(factory);
            }

            this.frontEnd.connectionFactoryRemove(factory);
         } catch (Throwable var6) {
         }
      }

      JMSLogger.logCntDefCFactoryUndeployed(this.defaultConnectionFactories.size());
   }

   public FEConnectionFactory[] getDefaultConnectionFactories() {
      synchronized(this.shutdownLock) {
         FEConnectionFactory[] retValue = new FEConnectionFactory[this.defaultConnectionFactories.size()];
         return (FEConnectionFactory[])((FEConnectionFactory[])this.defaultConnectionFactories.values().toArray(retValue));
      }
   }

   public FEConnectionFactory getDefaultConnectionFactory(String name) {
      return (FEConnectionFactory)this.defaultConnectionFactories.get(name);
   }

   private void deployPlatformConnectionFactory() {
      synchronized(this.shutdownLock) {
         this.platformConnectionFactory = new FEConnectionFactory(this.frontEnd, DeployerConstants.DefaultNames.PLATFORM_DEFAULT.getCFName(), DeployerConstants.DefaultNames.PLATFORM_DEFAULT.getJndiName(), false, true, "All");

         try {
            if (this.jmsService.isActive()) {
               this.platformConnectionFactory.bind();
            }

            this.frontEnd.connectionFactoryAdd(this.platformConnectionFactory);
         } catch (JMSException var4) {
            JMSLogger.logConnFactoryFailed(this.jmsService.getMbeanName(), var4);
         }

      }
   }

   private void undeployPlatformConnectionFactory() throws JMSException {
      try {
         this.platformConnectionFactory.shutdown();
         this.frontEnd.connectionFactoryRemove(this.platformConnectionFactory);
      } catch (Throwable var2) {
      }

   }
}
