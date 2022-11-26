package weblogic.jms.deployer;

import java.rmi.NoSuchObjectException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.jms.JMSException;
import javax.naming.NamingException;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BEDestinationImpl;
import weblogic.jms.backend.BETempDestinationFactory;
import weblogic.jms.backend.BackEnd;
import weblogic.jms.backend.BackEndTempDestinationFactory;
import weblogic.jms.common.CDS;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class BEDeployer {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private BETempDestinationFactory beTempDestinationFactory;
   private static final String JMS_TEMP_DESTINATION_FTY_JNDI = "weblogic.jms.TempDestinationFactory";
   private Object shutdownLock;
   private final HashMap backEnds = new HashMap();
   private final HashMap backEndByIds = new HashMap();
   private long backEndsHighCount;
   private long backEndsTotalCount;
   private ArrayList tempDestFactories = new ArrayList();
   private int currentFactoryIndex = -1;
   private JMSService jmsService = null;

   public BEDeployer(JMSService paramJMSService) {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Constructing JMS BEDeployer this = " + this + " partition = " + JMSService.getSafePartitionNameFromThread());
      }

      this.jmsService = paramJMSService;
      this.beTempDestinationFactory = new BETempDestinationFactory(this.jmsService);
      this.shutdownLock = this.backEnds;
   }

   public Object getShutdownLock() {
      synchronized(this.shutdownLock) {
         return this.shutdownLock;
      }
   }

   public long getBackEndsHighCount() {
      synchronized(this.shutdownLock) {
         return this.backEndsHighCount;
      }
   }

   public long getBackEndsTotalCount() {
      synchronized(this.shutdownLock) {
         return this.backEndsTotalCount;
      }
   }

   public void addBackEnd(BackEnd backEnd) throws JMSException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMS BEDeployer this = " + this + " adding backend = " + backEnd.getName() + " partition = " + JMSService.getSafePartitionNameFromThread());
      }

      synchronized(this.shutdownLock) {
         this.jmsService.checkShutdown();
         if (this.backEnds.put(backEnd.getName(), backEnd) == null) {
            this.backEndsHighCount = Math.max((long)this.backEnds.size(), this.backEndsHighCount);
            ++this.backEndsTotalCount;
            this.backEndByIds.put(backEnd.getJMSServerId(), backEnd);
            backEnd.getJmsService().getInvocableManagerDelegate().invocableAdd(14, backEnd);
         }

      }
   }

   public void removeBackEnd(BackEnd backEnd) {
      if (backEnd != null) {
         synchronized(this.shutdownLock) {
            this.backEnds.remove(backEnd.getName());
            this.backEndByIds.remove(backEnd.getJMSServerId());
            backEnd.getJmsService().getInvocableManagerDelegate().invocableRemove(14, backEnd.getJMSID());
         }
      }

   }

   public BackEnd[] getBackEnds() {
      synchronized(this.shutdownLock) {
         BackEnd[] retValue = new BackEnd[this.backEnds.size()];
         return (BackEnd[])((BackEnd[])this.backEnds.values().toArray(retValue));
      }
   }

   public HashMap getBackEndsMap() {
      synchronized(this.shutdownLock) {
         return this.backEnds;
      }
   }

   public BackEnd findBackEnd(String name) {
      synchronized(this.shutdownLock) {
         return (BackEnd)this.backEnds.get(name);
      }
   }

   public BackEnd findBackEnd(JMSServerId id) {
      synchronized(this.shutdownLock) {
         return (BackEnd)this.backEndByIds.get(id);
      }
   }

   public BEDestinationImpl findBEDestination(String name) {
      BEDestinationImpl dest = null;
      BackEnd[] backEnds = this.getBackEnds();

      for(int i = 0; i < backEnds.length; ++i) {
         dest = backEnds[i].findDestination(name);
         if (dest != null) {
            return dest;
         }
      }

      return dest;
   }

   public void postDeploymentsStart() {
      synchronized(this.shutdownLock) {
         CDS.getCDS().postDeploymentsStart();
         BackEnd[] backends = this.getBackEnds();
         if (backends != null) {
            for(int lcv = 0; lcv < backends.length; ++lcv) {
               backends[lcv].postDeploymentsStart();
            }

         }
      }
   }

   public void postDeploymentsStop() {
      synchronized(this.shutdownLock) {
         BackEnd[] backends = this.getBackEnds();
         if (backends != null) {
            for(int lcv = 0; lcv < backends.length; ++lcv) {
               backends[lcv].postDeploymentsStop();
            }

         }
      }
   }

   public BackEndTempDestinationFactory nextFactory(boolean isDefaultConnectionFactory, int moduleType, String moduleName) {
      synchronized(this.shutdownLock) {
         int factorySize;
         if ((factorySize = this.tempDestFactories.size()) <= 0) {
            this.currentFactoryIndex = -1;
            return null;
         } else {
            if (this.currentFactoryIndex < 0 || this.currentFactoryIndex >= factorySize) {
               this.currentFactoryIndex = 0;
            }

            WebLogicMBean deploymentScope = null;
            if (!isDefaultConnectionFactory) {
               deploymentScope = getDeploymentScope(moduleType, moduleName);
            }

            int startindex = this.currentFactoryIndex;

            BackEndTempDestinationFactory retVal;
            do {
               retVal = (BackEndTempDestinationFactory)this.tempDestFactories.get(this.currentFactoryIndex++);
               if (this.currentFactoryIndex >= factorySize) {
                  this.currentFactoryIndex = 0;
               }

               if (deploymentScope != null && !(deploymentScope instanceof DomainMBean) && !JMSModuleHelper.isTargetInDeploymentScope(this.getJMSServer(retVal.getJMSServerName()), deploymentScope)) {
                  retVal = null;
               }
            } while(retVal == null && this.currentFactoryIndex != startindex);

            return retVal;
         }
      }
   }

   public void addTempDestinationFactory(BackEndTempDestinationFactory factory) throws NamingException {
      synchronized(this.shutdownLock) {
         this.tempDestFactories.add(factory);
         if (this.tempDestFactories.size() == 1) {
            PrivilegedActionUtilities.bindAsSU(this.jmsService.getCtx(true), "weblogic.jms.TempDestinationFactory", this.beTempDestinationFactory.getFactoryWrapper(), kernelId);
         }

      }
   }

   public void removeTempDestinationFactory(BackEndTempDestinationFactory factory) throws NamingException {
      synchronized(this.shutdownLock) {
         Iterator removalIterator = this.tempDestFactories.iterator();
         boolean didRemove = false;

         while(removalIterator.hasNext()) {
            BackEndTempDestinationFactory savedFactory = (BackEndTempDestinationFactory)removalIterator.next();
            if (savedFactory == factory) {
               didRemove = true;
               removalIterator.remove();
            }
         }

         if (didRemove) {
            int factorySize = this.tempDestFactories.size();
            if (factorySize <= 0) {
               this.currentFactoryIndex = -1;
               PrivilegedActionUtilities.unbindAsSU(this.jmsService.getCtx(true), "weblogic.jms.TempDestinationFactory", kernelId);

               try {
                  ServerHelper.unexportObject(this.beTempDestinationFactory, true);
               } catch (NoSuchObjectException var8) {
               }
            } else if (this.currentFactoryIndex >= factorySize) {
               this.currentFactoryIndex = 0;
            }
         }

      }
   }

   public BETempDestinationFactory getBETempDestinationFactory() {
      return this.beTempDestinationFactory;
   }

   private JMSServerMBean getJMSServer(String name) {
      return ManagementService.getRuntimeAccess(kernelId).getDomain().lookupJMSServer(name);
   }

   public static WebLogicMBean getDeploymentScope(int moduleType, String moduleName) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      BasicDeploymentMBean basic = null;
      switch (moduleType) {
         case 0:
            basic = domain.lookupAppDeployment(moduleName);
            break;
         case 1:
            basic = domain.lookupJMSSystemResource(moduleName);
            break;
         default:
            return null;
      }

      return JMSModuleHelper.getDeploymentScope((BasicDeploymentMBean)basic);
   }
}
