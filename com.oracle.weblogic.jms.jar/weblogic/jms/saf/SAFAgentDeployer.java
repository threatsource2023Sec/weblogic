package weblogic.jms.saf;

import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSTargetsListener;
import weblogic.jms.module.ModuleCoordinator;
import weblogic.jms.module.TargetingHelper;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSLegalHelper;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.GenericAdminHandler;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.management.utils.MigratableGenericAdminHandler;
import weblogic.messaging.saf.internal.SAFServerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;

public class SAFAgentDeployer implements GenericAdminHandler, MigratableGenericAdminHandler {
   private final HashMap agents = new HashMap();
   private HashSet safAgentListeners = new HashSet();
   private HashMap safAgentBeanListeners = new HashMap();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean migrationInProgress = false;

   public boolean isMigrationInProgress() {
      return this.migrationInProgress;
   }

   public void setMigrationInProgress(boolean migrationInProgress) {
      this.migrationInProgress = migrationInProgress;
   }

   void start() {
      this.initializeSAFAgentListeners();
   }

   void stop() {
      this.removeSAFAgentListeners();
   }

   public void prepare(GenericManagedDeployment safAgentDeployment) throws DeploymentException {
      DeploymentMBean bean = safAgentDeployment.getMBean();
      if (bean instanceof SAFAgentMBean) {
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("Preparing saf agent " + bean.getName());
         }

         SAFAgentMBean mbean = (SAFAgentMBean)bean;
         boolean receivingOnly = "Receiving-only".equals(mbean.getServiceType());
         SAFAgentAdmin admin = new SAFAgentAdmin(receivingOnly);
         admin.prepare(safAgentDeployment);
         this.agents.put(safAgentDeployment.getName(), admin);
         synchronized(this) {
            SAFService.getSAFService().getIDEntityHelper().prepareLocalSAFAgent(safAgentDeployment);

            try {
               this.startAddSAFAgents(mbean);
            } catch (BeanUpdateRejectedException var9) {
               throw new DeploymentException(var9);
            }

         }
      }
   }

   public void activate(GenericManagedDeployment safAgentDeployment) throws DeploymentException {
      DeploymentMBean bean = safAgentDeployment.getMBean();
      if (bean instanceof SAFAgentMBean) {
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("Activating saf agent " + bean.getName());
         }

         SAFAgentMBean mbean = (SAFAgentMBean)bean;
         SAFAgentAdmin admin = (SAFAgentAdmin)this.agents.get(safAgentDeployment.getName());

         try {
            admin.activate(safAgentDeployment);
         } catch (Exception var8) {
            if (var8 instanceof DeploymentException) {
               throw var8;
            }

            throw new DeploymentException(var8);
         }

         synchronized(this) {
            SAFService.getSAFService().getIDEntityHelper().activateLocalSAFAgent(safAgentDeployment);
            this.finishAddSAFAgents(mbean);
         }
      }
   }

   public void deactivate(GenericManagedDeployment safAgentDeployment) throws UndeploymentException {
      DeploymentMBean bean = safAgentDeployment.getMBean();
      if (bean instanceof SAFAgentMBean) {
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("Deactivating saf agent " + bean.getName());
         }

         SAFAgentMBean mbean = (SAFAgentMBean)bean;
         synchronized(this) {
            SAFService.getSAFService().getIDEntityHelper().deactivateLocalSAFAgent(safAgentDeployment);

            try {
               this.startRemoveSAFAgents(mbean);
            } catch (BeanUpdateRejectedException var7) {
               throw new UndeploymentException(var7);
            }
         }

         SAFAgentAdmin admin = (SAFAgentAdmin)this.agents.get(safAgentDeployment.getName());
         if (admin != null) {
            admin.deactivate();
         }

      }
   }

   public void unprepare(GenericManagedDeployment safAgentDeployment) throws UndeploymentException {
      DeploymentMBean bean = safAgentDeployment.getMBean();
      if (bean instanceof SAFAgentMBean) {
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("Unpreparing saf agent " + bean.getName());
         }

         SAFAgentMBean mbean = (SAFAgentMBean)bean;
         synchronized(this) {
            SAFService.getSAFService().getIDEntityHelper().unprepareLocalSAFAgent(safAgentDeployment);
            this.finishRemoveSAFAgents(mbean);
         }

         SAFAgentAdmin admin = (SAFAgentAdmin)this.agents.remove(safAgentDeployment.getName());
         if (admin != null) {
            admin.unprepare();
         }

      }
   }

   public SAFAgentAdmin getAgent(String agentName) {
      return (SAFAgentAdmin)this.agents.get(agentName);
   }

   SAFAgentAdmin[] getSAFAgents() {
      SAFAgentAdmin[] ret = new SAFAgentAdmin[this.agents.size()];
      return (SAFAgentAdmin[])((SAFAgentAdmin[])this.agents.values().toArray(ret));
   }

   private void initializeSAFAgentListeners() {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      SAFAgentMBean[] safAgents = domain.getSAFAgents();

      for(int lcv = 0; lcv < safAgents.length; ++lcv) {
         DescriptorBean db = safAgents[lcv];
         SAFAgentBeanListener beanListener = new SAFAgentBeanListener(safAgents[lcv]);
         db.addBeanUpdateListener(beanListener);
         synchronized(this.safAgentBeanListeners) {
            this.safAgentBeanListeners.put(safAgents[lcv].getName(), new DescriptorAndListener(db, beanListener));
         }

         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("Listening for changes to SAFAgent " + safAgents[lcv].getName());
         }
      }

   }

   private void removeSAFAgentListeners() {
      synchronized(this.safAgentBeanListeners) {
         Iterator it = this.safAgentBeanListeners.keySet().iterator();

         while(it.hasNext()) {
            String key = (String)it.next();
            DescriptorAndListener dal = (DescriptorAndListener)this.safAgentBeanListeners.get(key);
            DescriptorBean db = dal.getDescriptorBean();
            SAFAgentBeanListener safabl = dal.getListener();
            db.removeBeanUpdateListener(safabl);
            safabl.close();
         }

      }
   }

   public void addSAFAgentListener(JMSTargetsListener listener) {
      synchronized(this.safAgentListeners) {
         this.safAgentListeners.add(listener);
      }
   }

   public void removeSAFAgentListener(JMSTargetsListener listener) {
      synchronized(this.safAgentListeners) {
         this.safAgentListeners.remove(listener);
      }
   }

   private void fireListenersPrepare(DomainMBean domainBean, SAFAgentMBean safAgent, int event) throws BeanUpdateRejectedException {
      boolean success = false;
      LinkedList preparedListeners = new LinkedList();
      LinkedList moduleListeners = new LinkedList();
      LinkedList idListeners = new LinkedList();
      synchronized(this.safAgentListeners) {
         Iterator it = this.safAgentListeners.iterator();

         JMSTargetsListener safal;
         while(it.hasNext()) {
            safal = (JMSTargetsListener)it.next();
            if (safal instanceof ModuleCoordinator.JMSTargetsListenerImpl) {
               moduleListeners.add(safal);
            } else if (safal instanceof ImportedDestinationGroup) {
               idListeners.add(safal);
            }
         }

         boolean var16 = false;

         try {
            var16 = true;
            it = moduleListeners.iterator();

            while(it.hasNext()) {
               safal = (JMSTargetsListener)it.next();
               safal.prepareUpdate(domainBean, safAgent, event, this.migrationInProgress);
               preparedListeners.addLast(safal);
            }

            it = idListeners.iterator();

            while(true) {
               if (!it.hasNext()) {
                  success = true;
                  var16 = false;
                  break;
               }

               safal = (JMSTargetsListener)it.next();
               safal.prepareUpdate(domainBean, safAgent, event, this.migrationInProgress);
               preparedListeners.addLast(safal);
            }
         } finally {
            if (var16) {
               if (!success) {
                  it = preparedListeners.iterator();

                  while(it.hasNext()) {
                     JMSTargetsListener safal = (JMSTargetsListener)it.next();
                     safal.rollbackUpdate();
                  }
               }

            }
         }

         if (!success) {
            it = preparedListeners.iterator();

            while(it.hasNext()) {
               safal = (JMSTargetsListener)it.next();
               safal.rollbackUpdate();
            }
         }

      }
   }

   private void fireListenersActivateOrRollback(boolean forActivate) {
      LinkedList moduleListeners = new LinkedList();
      LinkedList idListeners = new LinkedList();
      synchronized(this.safAgentListeners) {
         Iterator it = this.safAgentListeners.iterator();

         JMSTargetsListener safal;
         while(it.hasNext()) {
            safal = (JMSTargetsListener)it.next();
            if (safal instanceof ModuleCoordinator.JMSTargetsListenerImpl) {
               moduleListeners.add(safal);
            } else if (safal instanceof ImportedDestinationGroup) {
               idListeners.add(safal);
            }
         }

         it = moduleListeners.iterator();

         while(it.hasNext()) {
            safal = (JMSTargetsListener)it.next();
            if (forActivate) {
               safal.activateUpdate();
            } else {
               safal.rollbackUpdate();
            }
         }

         it = idListeners.iterator();

         while(it.hasNext()) {
            safal = (JMSTargetsListener)it.next();
            if (forActivate) {
               safal.activateUpdate();
            } else {
               safal.rollbackUpdate();
            }
         }

      }
   }

   public void startAddSAFAgents(SAFAgentMBean toAdd) throws BeanUpdateRejectedException {
      DomainMBean domain;
      try {
         domain = JMSLegalHelper.getDomain(toAdd);
      } catch (IllegalArgumentException var4) {
         throw new BeanUpdateRejectedException(var4.getMessage(), var4);
      }

      this.fireListenersPrepare(domain, toAdd, 1);
   }

   public void finishAddSAFAgents(SAFAgentMBean toAdd) {
      this.fireListenersActivateOrRollback(true);
      SAFAgentBeanListener safabl = new SAFAgentBeanListener(toAdd);
      toAdd.addBeanUpdateListener(safabl);
      synchronized(this.safAgentBeanListeners) {
         this.safAgentBeanListeners.put(toAdd.getName(), new DescriptorAndListener(toAdd, safabl));
      }

      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("Listening for changes to SAFAgent " + toAdd.getName());
      }

   }

   public void startRemoveSAFAgents(SAFAgentMBean toRemove) throws BeanUpdateRejectedException {
      DomainMBean domain = null;

      try {
         domain = JMSLegalHelper.getDomain(toRemove);
      } catch (IllegalArgumentException var4) {
         throw new BeanUpdateRejectedException(var4.getMessage(), var4);
      }

      this.fireListenersPrepare(domain, toRemove, 0);
   }

   public void finishRemoveSAFAgents(SAFAgentMBean toRemove) {
      this.fireListenersActivateOrRollback(true);
      synchronized(this.safAgentBeanListeners) {
         DescriptorAndListener dal = (DescriptorAndListener)this.safAgentBeanListeners.remove(toRemove.getName());
         if (dal == null) {
            return;
         }

         DescriptorBean db = dal.getDescriptorBean();
         SAFAgentBeanListener safabl = dal.getListener();
         db.removeBeanUpdateListener(safabl);
         safabl.close();
      }

      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("Not listening for changes to removed SAFAgent " + toRemove.getName());
      }

   }

   private static class DescriptorAndListener {
      private DescriptorBean db;
      private SAFAgentBeanListener listener;

      private DescriptorAndListener(DescriptorBean paramDB, SAFAgentBeanListener paramListener) {
         this.db = paramDB;
         this.listener = paramListener;
      }

      private DescriptorBean getDescriptorBean() {
         return this.db;
      }

      private SAFAgentBeanListener getListener() {
         return this.listener;
      }

      // $FF: synthetic method
      DescriptorAndListener(DescriptorBean x0, SAFAgentBeanListener x1, Object x2) {
         this(x0, x1);
      }
   }

   private class SAFAgentBeanListener implements BeanUpdateListener {
      private SAFAgentMBean safAgent;
      private SAFAgentMBean proposedSAFAgent;
      private MigratableTargetMBean migratableTarget;
      int numFound;
      boolean safAgentChanged;

      private SAFAgentBeanListener(SAFAgentMBean paramSAFAgent) {
         this.safAgent = paramSAFAgent;
         TargetMBean[] targets = this.safAgent.getTargets();
         if (targets.length == 1) {
            TargetMBean target = targets[0];
            if (target instanceof MigratableTargetMBean) {
               this.migratableTarget = (MigratableTargetMBean)target;
               this.migratableTarget.addBeanUpdateListener(this);
            }

         }
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         if (this.migratableTarget == null) {
            this.safAgentChanged = true;
         } else {
            DescriptorBean db = event.getProposedBean();
            if (db instanceof SAFAgentMBean) {
               this.safAgentChanged = true;
            } else {
               this.safAgentChanged = false;
            }
         }

         boolean localFound = false;
         boolean targetsChanged = false;
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

         for(int lcv = 0; lcv < updates.length; ++lcv) {
            BeanUpdateEvent.PropertyUpdate update = updates[lcv];
            if (this.safAgentChanged && update.getPropertyName().equals("Targets")) {
               targetsChanged = true;
               localFound = true;
               break;
            }

            if (!this.safAgentChanged && update.getPropertyName().equals("UserPreferredServer")) {
               localFound = true;
               break;
            }
         }

         if (localFound) {
            ++this.numFound;
            this.proposedSAFAgent = this.safAgentChanged ? (SAFAgentMBean)event.getProposedBean() : this.safAgent;

            try {
               if (targetsChanged && !SAFServerService.getService().isTargetsChangeAllowed(this.proposedSAFAgent)) {
                  throw new BeanUpdateRejectedException("Cannot retarget SAF agent " + this.safAgent.getName() + " to a MigrableTarget while it is currently targeted to a Server/Cluster or handling WSRM messages");
               }
            } catch (ServiceFailureException var10) {
               throw new BeanUpdateRejectedException(var10.getMessage());
            }

            DomainMBean domainBean;
            try {
               domainBean = this.safAgentChanged ? JMSLegalHelper.getDomain(this.proposedSAFAgent) : JMSLegalHelper.getDomain((WebLogicMBean)event.getProposedBean());
            } catch (IllegalArgumentException var9) {
               throw new BeanUpdateRejectedException(var9.getMessage(), var9);
            }

            synchronized(SAFAgentDeployer.this) {
               if (SAFService.getSAFService().getIDEntityHelper().getLocalSAFAgents().values().contains(this.proposedSAFAgent.getName()) && !this.isLocallyTargeted(this.proposedSAFAgent)) {
                  SAFAgentDeployer.this.fireListenersPrepare(domainBean, this.proposedSAFAgent, 2);
               }

            }
         }
      }

      public void activateUpdate(BeanUpdateEvent event) {
         if (this.numFound > 0) {
            --this.numFound;
            synchronized(SAFAgentDeployer.this) {
               if (SAFService.getSAFService().getIDEntityHelper().getLocalSAFAgents().values().contains(this.proposedSAFAgent.getName()) && !this.isLocallyTargeted(this.proposedSAFAgent)) {
                  SAFAgentDeployer.this.fireListenersActivateOrRollback(true);
               }
            }

            if (this.safAgentChanged) {
               MigratableTargetMBean oldTarget = this.migratableTarget;
               if (oldTarget != null) {
                  oldTarget.removeBeanUpdateListener(this);
               }

               this.migratableTarget = null;
               TargetMBean[] safAgentTargets = this.safAgent.getTargets();
               if (safAgentTargets.length == 1) {
                  TargetMBean safAgentTarget = safAgentTargets[0];
                  if (safAgentTarget instanceof MigratableTargetMBean) {
                     this.migratableTarget = (MigratableTargetMBean)safAgentTarget;
                     this.migratableTarget.addBeanUpdateListener(this);
                  }

               }
            }
         }
      }

      public void rollbackUpdate(BeanUpdateEvent event) {
         if (this.numFound > 0) {
            --this.numFound;
            synchronized(SAFAgentDeployer.this) {
               if (SAFService.getSAFService().getIDEntityHelper().getLocalSAFAgents().values().contains(this.proposedSAFAgent.getName()) && !this.isLocallyTargeted(this.proposedSAFAgent)) {
                  SAFAgentDeployer.this.fireListenersActivateOrRollback(false);
               }

            }
         }
      }

      private void close() {
         if (this.migratableTarget != null) {
            this.migratableTarget.removeBeanUpdateListener(this);
         }
      }

      private boolean isLocallyTargeted(SAFAgentMBean agent) {
         ServerMBean server = ManagementService.getRuntimeAccess(SAFAgentDeployer.kernelId).getServer();
         return server == null ? false : TargetingHelper.isLocallyTargeted(agent, server.getCluster() == null ? null : server.getCluster().getName(), server.getName());
      }

      // $FF: synthetic method
      SAFAgentBeanListener(SAFAgentMBean x1, Object x2) {
         this(x1);
      }
   }
}
