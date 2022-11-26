package weblogic.jms.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import javax.management.InvalidAttributeValueException;
import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.NamedEntityBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.TargetableBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BEDDEntityProvider;
import weblogic.jms.backend.BEUDDEntityProvider;
import weblogic.jms.backend.DestinationEntityProvider;
import weblogic.jms.common.EntityName;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.PartitionUtils;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.jms.foreign.ForeignJMSEntityProvider;
import weblogic.jms.frontend.JmsConnectionFactoryEntityProvider;
import weblogic.jms.module.validators.JMSModuleValidator;
import weblogic.jms.saf.ErrorHandlingProvider;
import weblogic.jms.saf.JMSSAFImportedDestinationsEntityProvider;
import weblogic.jms.saf.RemoteContextProvider;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.provider.internal.PartitionProcessor;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.utils.StackTraceUtils;

public class JMSModule extends ModuleCoordinator {
   private static final JMSModuleManagedEntityProvider DESTINATION_PROVIDER = new DestinationEntityProvider();
   private static final JMSModuleManagedEntityProvider DD_PROVIDER = new BEDDEntityProvider();
   private static final JMSModuleManagedEntityProvider UDD_PROVIDER = new BEUDDEntityProvider();
   private static final JMSModuleManagedEntityProvider SAF_PROVIDER = new JMSSAFImportedDestinationsEntityProvider();
   private static final JMSModuleManagedEntityProvider CONN_PROVIDER = new JmsConnectionFactoryEntityProvider();
   private static final JMSModuleManagedEntityProvider FOREIGN_PROVIDER = new ForeignJMSEntityProvider();
   private static final ErrorHandlingProvider ERROR_HANDLING_PROVIDER = new ErrorHandlingProvider();
   private static final RemoteContextProvider REMOTE_CONTEXT_PROVIDER = new RemoteContextProvider();
   private static final int NONEXISTENT_TYPE = 0;
   private static final int TARGETED_TYPE = 1;
   private static final int UBIQUITOUS_TARGETED_TYPE = 2;
   private static final int UBIQUITOUS_NONTARGETED_TYPE = 3;
   private static final int QUEUE_TYPE = 0;
   private static final int TOPIC_TYPE = 1;
   private static final int DISTRIBUTED_QUEUE_TYPE = 2;
   private static final int DISTRIBUTED_TOPIC_TYPE = 3;
   private static final int UNIFORM_DISTRIBUTED_QUEUE_TYPE = 4;
   private static final int UNIFORM_DISTRIBUTED_TOPIC_TYPE = 5;
   private static final int REMOTE_CONTEXT_TYPE = 6;
   private static final int JMS_SAF_IMPORTED_DESTINATIONS_TYPE = 7;
   private static final int ERROR_HANDLING_TYPE = 8;
   private static final int JMS_CONNECTION_FACTORY_TYPE = 9;
   private static final int FOREIGN_TYPE = 10;
   static final int MAX_TYPE = 11;
   private static final String QUOTA_STRING = "Quotas";
   private static final String QUEUE_STRING = "Queues";
   private static final String TOPIC_STRING = "Topics";
   private static final String FOREIGN_STRING = "ForeignServers";
   private static final String DISTRIBUTED_QUEUE_STRING = "DistributedQueues";
   private static final String DISTRIBUTED_TOPIC_STRING = "DistributedTopics";
   private static final String JMS_CONNECTION_FACTORY_STRING = "ConnectionFactories";
   private static final String SAF_IMPORTED_DESTINATIONS_STRING = "SAFImportedDestinations";
   private static final String SAF_REMOTE_CONTEXT_STRING = "SAFRemoteContexts";
   private static final String SAF_ERROR_HANDLING_STRING = "SAFErrorHandlings";
   private static final String UNIFORM_DISTRIBUTED_QUEUE_STRING = "UniformDistributedQueues";
   private static final String UNIFORM_DISTRIBUTED_TOPIC_STRING = "UniformDistributedTopics";
   private static final String TEMPLATE_STRING = "Templates";
   private static final String DESTINATION_KEY_STRING = "DestinationKeys";
   private static final int INIT_STATE = 0;
   private static final int PREP_STATE = 1;
   private static final int ACTI_STATE = 2;
   private static final int DONE_STATE = 3;
   private static final int DEAD_STATE = 4;
   private static final int CHNG_STATE = 5;
   private LinkedList[] allEntities;
   private JMSBean wholeModule;
   private Context applicationNamingContext;
   private boolean isPendingDestinationToBeHosted;
   private Set pendingUTTEntityToBeHostedSet;
   private final Set singletonDefaultTargetedDestinations;
   private final Set hostedSingletonDefaultTargetedDestinations;
   private WebLogicMBean appDeploymentScope;
   private boolean isDeployedToRGT;
   private JMSModuleListener moduleListener;
   private final HashMap indexInfo;
   private static final Stuff QUEUE_STUFF;
   private static final Stuff TOPIC_STUFF;
   private static final Stuff QUEUE_PARTITION_STUFF;
   private static final Stuff TOPIC_PARTITION_STUFF;
   private static final Stuff FOREIGN_PROVIDER_STUFF;
   private static final Stuff DISTRIBUTED_QUEUE_STUFF;
   private static final Stuff DISTRIBUTED_TOPIC_STUFF;
   private static final Stuff CONN_FACTORY_STUFF;
   private static final Stuff SAF_IMPORTED_DEST_STUFF;
   private static final Stuff REMOTE_CTXT_PROVIDER_STUFF;
   private static final Stuff ERROR_HANDLING_STUFF;
   private static final Stuff UDD_QUEUE_STUFF;
   private static final Stuff UDD_TOPIC_STUFF;
   private static final Stuff NON_EXISTENT_STUFF;
   private static final int[] RP_TYPES;
   private static final String[] RP_TYPE_STRING;

   JMSModule(String paramURI, String paramEARModuleName) {
      super(paramEARModuleName, paramURI);
      this.isPendingDestinationToBeHosted = false;
      this.pendingUTTEntityToBeHostedSet = Collections.synchronizedSet(new HashSet());
      this.singletonDefaultTargetedDestinations = Collections.synchronizedSet(new HashSet());
      this.hostedSingletonDefaultTargetedDestinations = Collections.synchronizedSet(new HashSet());
      this.appDeploymentScope = null;
      this.isDeployedToRGT = false;
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule:constructor paramUri: " + paramURI + " paramModule: " + paramEARModuleName);
      }

      this.allEntities = new LinkedList[11];

      for(int lcv = 0; lcv < 11; ++lcv) {
         this.allEntities[lcv] = new LinkedList();
      }

      this.indexInfo = new HashMap();
      this.indexInfo.put("Queues", QUEUE_STUFF);
      this.indexInfo.put("Topics", TOPIC_STUFF);
      this.indexInfo.put("ForeignServers", FOREIGN_PROVIDER_STUFF);
      this.indexInfo.put("DistributedQueues", DISTRIBUTED_QUEUE_STUFF);
      this.indexInfo.put("DistributedTopics", DISTRIBUTED_TOPIC_STUFF);
      this.indexInfo.put("ConnectionFactories", CONN_FACTORY_STUFF);
      this.indexInfo.put("SAFImportedDestinations", SAF_IMPORTED_DEST_STUFF);
      this.indexInfo.put("SAFRemoteContexts", REMOTE_CTXT_PROVIDER_STUFF);
      this.indexInfo.put("SAFErrorHandlings", ERROR_HANDLING_STUFF);
      this.indexInfo.put("UniformDistributedQueues", UDD_QUEUE_STUFF);
      this.indexInfo.put("UniformDistributedTopics", UDD_TOPIC_STUFF);
      this.indexInfo.put("Templates", NON_EXISTENT_STUFF);
      this.indexInfo.put("DestinationKeys", NON_EXISTENT_STUFF);
      this.indexInfo.put("Quotas", NON_EXISTENT_STUFF);
   }

   JMSModule(String uri) {
      this(uri, (String)null);
   }

   protected DescriptorBean getModuleDescriptor() {
      return (DescriptorBean)this.wholeModule;
   }

   private void validateDestinations(JMSBean bean) throws IllegalArgumentException {
      QueueBean[] var2 = bean.getQueues();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         QueueBean queueBean = var2[var4];
         if (queueBean.isDefaultTargetingEnabled()) {
            throw new IllegalArgumentException(JMSExceptionLogger.logDefaultTargetingNotSupportedLoggable(queueBean.getName()).getMessage());
         }
      }

      TopicBean[] var6 = bean.getTopics();
      var3 = var6.length;

      for(var4 = 0; var4 < var3; ++var4) {
         TopicBean topicBean = var6[var4];
         if (topicBean.isDefaultTargetingEnabled()) {
            throw new IllegalArgumentException(JMSExceptionLogger.logDefaultTargetingNotSupportedLoggable(topicBean.getName()).getMessage());
         }
      }

   }

   protected synchronized void initializeModule(ApplicationContextInternal paramAppCtx, DomainMBean proposedDomain) throws ModuleException {
      BasicDeploymentMBean basic = this.getBasicDeployment(proposedDomain);
      this.wholeModule = JMSParser.createJMSDescriptor(paramAppCtx, basic, this.getId());
      if (basic != null && basic instanceof JMSSystemResourceMBean && !PartitionUtils.isDomain(this.getAppCtx().getPartitionName())) {
         PartitionProcessor.processIfClone((JMSSystemResourceMBean)basic, this.wholeModule);
      }

      AppDeploymentMBean appDeployment = paramAppCtx.getAppDeploymentMBean();
      this.appDeploymentScope = JMSModuleHelper.getDeploymentScope(paramAppCtx);
      if (this.appDeploymentScope instanceof ResourceGroupTemplateMBean) {
         this.isDeployedToRGT = AppDeploymentHelper.isDeployedThroughRGT(basic);
         this.indexInfo.put("Queues", QUEUE_PARTITION_STUFF);
         this.indexInfo.put("Topics", TOPIC_PARTITION_STUFF);
      } else {
         this.validateDestinations(this.wholeModule);
      }

      if (appDeployment != null || this.appDeploymentScope instanceof ResourceGroupTemplateMBean) {
         TargetInfoMBean targetInfoMBean = this.getTargetingBean(proposedDomain);

         try {
            JMSModuleValidator.validateTargeting(this.wholeModule, basic, targetInfoMBean, this.isJMSResourceDefinition);
         } catch (IllegalArgumentException var7) {
            throw new ModuleException(var7.getMessage(), var7);
         }
      }

      this.targeter = new TargetingHelper(this, proposedDomain, this.moduleName.getEARModuleName(), paramAppCtx.getApplicationId());
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("=== JMSModule:internalInit(): from application " + this.getId() + " Tree === ");
         DescriptorUtils.writeAsXML((DescriptorBean)this.wholeModule);
      }

      this.processEntities((JMSBean)null, (UpdateInformation)null, proposedDomain);
   }

   protected void initializeModule(ApplicationContextInternal paramAppCtx, DomainMBean proposedDomain, JMSBean proposedJMSModule, Context context, String proposedJMSServerName, String jndiName, boolean isJMSDestinationDefinition) throws ModuleException {
      this.wholeModule = proposedJMSModule;
      this.isJMSResourceDefinition = true;
      AppDeploymentMBean appDeployment = paramAppCtx.getAppDeploymentMBean();
      if (appDeployment != null) {
         this.appDeploymentScope = JMSModuleHelper.getDeploymentScope(paramAppCtx);
         if (this.appDeploymentScope instanceof ResourceGroupTemplateMBean) {
            this.isDeployedToRGT = AppDeploymentHelper.isDeployedThroughRGT(paramAppCtx.getBasicDeploymentMBean());
         }

         SubDeploymentMBean subdeploymentBean = null;

         try {
            subdeploymentBean = appDeployment.lookupSubDeployment(this.moduleName.getEARModuleName());
            if (subdeploymentBean == null) {
               subdeploymentBean = appDeployment.createSubDeployment(this.moduleName.getEARModuleName());
            }

            if (proposedJMSServerName != null && !"".equals(proposedJMSServerName)) {
               JMSServerMBean candidateJMSServerMBean = this.getJMSServerMBeanUsingName(appDeployment, proposedDomain, this.appDeploymentScope, paramAppCtx, proposedJMSServerName, jndiName, isJMSDestinationDefinition);
               if (JMSDebug.JMSModule.isDebugEnabled()) {
                  JMSDebug.JMSModule.debug("JMSModule:initializeModule : Target the module to the JMS Server");
               }

               SubDeploymentMBean innerSubDeployment = subdeploymentBean.createSubDeployment(proposedJMSServerName);
               innerSubDeployment.setTargets(new TargetMBean[]{candidateJMSServerMBean});
            } else {
               subdeploymentBean.setTargets(appDeployment.getTargets());
            }
         } catch (InvalidAttributeValueException var13) {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("JMSModule:initializeModule : InvalidAttributeValueException " + var13.getMessage());
            }

            throw new ModuleException(var13);
         } catch (ManagementException var14) {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("JMSModule:initializeModule : ManagementException " + var14.getMessage());
            }

            throw new ModuleException(var14);
         }

         try {
            JMSModuleValidator.validateTargeting(this.wholeModule, appDeployment, this.getTargetingBean(proposedDomain), isJMSDestinationDefinition);
         } catch (IllegalArgumentException var12) {
            throw new ModuleException(var12.getMessage(), var12);
         }
      }

      this.targeter = new TargetingHelper(this, proposedDomain, this.moduleName.getEARModuleName(), paramAppCtx.getApplicationId());
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule:initializeModule: JMS Module ");
         DescriptorUtils.writeAsXML((DescriptorBean)this.wholeModule);
      }

      if (context != null) {
         this.applicationNamingContext = context;
      }

      this.processEntities((JMSBean)null, (UpdateInformation)null, proposedDomain);
   }

   public synchronized void prepare(DomainMBean domain) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule:prepare() called in " + this.moduleName);
      }

      LinkedList prepared = new LinkedList();
      boolean success = false;
      boolean var17 = false;

      try {
         var17 = true;
         synchronized(this.allEntities) {
            int lcv = 0;

            while(true) {
               if (lcv >= 11) {
                  break;
               }

               Iterator it = this.allEntities[lcv].iterator();

               while(it.hasNext()) {
                  EntityState es = (EntityState)it.next();
                  es.setState(1);
                  prepared.addFirst(es);
               }

               ++lcv;
            }
         }

         success = true;
         var17 = false;
      } finally {
         if (var17) {
            if (!success) {
               Iterator pi = prepared.iterator();

               while(pi.hasNext()) {
                  EntityState es = (EntityState)pi.next();

                  try {
                     es.setState(0);
                  } catch (ModuleException var18) {
                     JMSLogger.logUnprepareFailedInPrepare(es.getName(), this.moduleName.toString(), var18.toString());
                  }
               }
            }

         }
      }

      if (!success) {
         Iterator pi = prepared.iterator();

         while(pi.hasNext()) {
            EntityState es = (EntityState)pi.next();

            try {
               es.setState(0);
            } catch (ModuleException var19) {
               JMSLogger.logUnprepareFailedInPrepare(es.getName(), this.moduleName.toString(), var19.toString());
            }
         }
      }

   }

   public synchronized void activate(DomainMBean domain) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule:activate() called in " + this.moduleName);
      }

      this.targeter.activate();
      LinkedList activated = new LinkedList();
      boolean success = false;
      boolean var17 = false;

      try {
         var17 = true;
         synchronized(this.allEntities) {
            int lcv = 0;

            while(true) {
               if (lcv >= 11) {
                  break;
               }

               Iterator it = this.allEntities[lcv].iterator();

               while(it.hasNext()) {
                  EntityState es = (EntityState)it.next();
                  es.setState(2);
                  activated.addFirst(es);
               }

               ++lcv;
            }
         }

         this.moduleListener = new JMSModuleListener();
         DescriptorBean db = (DescriptorBean)this.wholeModule;
         db.addBeanUpdateListener(this.moduleListener);
         this.isDestroyed = false;
         success = true;
         var17 = false;
      } finally {
         if (var17) {
            if (!success) {
               Iterator pi = activated.iterator();

               while(pi.hasNext()) {
                  EntityState es = (EntityState)pi.next();

                  try {
                     es.setState(1);
                  } catch (ModuleException var18) {
                     JMSLogger.logDeactivateFailedInActivate(es.getName(), this.moduleName.toString(), var18.toString());
                  }
               }
            }

         }
      }

      if (!success) {
         Iterator pi = activated.iterator();

         while(pi.hasNext()) {
            EntityState es = (EntityState)pi.next();

            try {
               es.setState(1);
            } catch (ModuleException var19) {
               JMSLogger.logDeactivateFailedInActivate(es.getName(), this.moduleName.toString(), var19.toString());
            }
         }
      }

   }

   public void adminToProduction() {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule:adminToProduction() called in " + this.moduleName);
      }

      this.targeter.adminToProduction();
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule:gracefulProductionToAdmin() called in " + this.moduleName);
      }

      this.targeter.productionToAdmin();
   }

   public void forceProductionToAdmin() throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule:forceProductionToAdmin() called in " + this.moduleName);
      }

      this.targeter.productionToAdmin();
   }

   public synchronized void deactivate(DomainMBean domain) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule:deactivate() called in " + this.moduleName);
      }

      this.targeter.deactivate();
      if (this.moduleListener != null) {
         DescriptorBean db = (DescriptorBean)this.wholeModule;
         db.removeBeanUpdateListener(this.moduleListener);
         this.moduleListener = null;
      }

      ModuleException caught = null;
      synchronized(this.allEntities) {
         for(int lcv = 10; lcv >= 0; --lcv) {
            ListIterator it = this.allEntities[lcv].listIterator(this.allEntities[lcv].size());

            while(it.hasPrevious()) {
               EntityState es = (EntityState)it.previous();

               try {
                  es.setState(1);
               } catch (ModuleException var9) {
                  if (caught == null) {
                     caught = var9;
                  }
               }
            }
         }
      }

      if (caught != null) {
         throw caught;
      }
   }

   public synchronized void unprepare(DomainMBean domain) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule:unprepare() called in " + this.moduleName);
      }

      ModuleException caught = null;
      this.targeter.unprepare();
      synchronized(this.allEntities) {
         int lcv = 10;

         while(true) {
            if (lcv < 0) {
               break;
            }

            ListIterator it = this.allEntities[lcv].listIterator(this.allEntities[lcv].size());

            while(it.hasPrevious()) {
               EntityState es = (EntityState)it.previous();

               try {
                  es.setState(0);
               } catch (ModuleException var9) {
                  if (caught == null) {
                     caught = var9;
                  }
               }
            }

            --lcv;
         }
      }

      if (caught != null) {
         throw caught;
      } else {
         this.pendingUTTEntityToBeHostedSet.clear();
      }
   }

   public synchronized void destroy(DomainMBean domain) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule:destroy() called in " + this.moduleName);
      }

      ModuleException caught = null;
      synchronized(this.allEntities) {
         int lcv = 10;

         while(true) {
            if (lcv < 0) {
               break;
            }

            ListIterator it = this.allEntities[lcv].listIterator(this.allEntities[lcv].size());

            while(it.hasPrevious()) {
               EntityState es = (EntityState)it.previous();

               try {
                  es.setState(3);
               } catch (ModuleException var9) {
                  if (caught == null) {
                     caught = var9;
                  }
               }
            }

            --lcv;
         }
      }

      this.pendingUTTEntityToBeHostedSet.clear();
      this.isDestroyed = true;
      if (caught != null) {
         throw caught;
      }
   }

   public void remove(DomainMBean domain) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule:remove() called in " + this.moduleName);
      }

      this.pendingUTTEntityToBeHostedSet.clear();
      ModuleException caught = null;
      synchronized(this.allEntities) {
         int lcv = 10;

         while(true) {
            if (lcv < 0) {
               break;
            }

            ListIterator it = this.allEntities[lcv].listIterator(this.allEntities[lcv].size());

            while(it.hasPrevious()) {
               EntityState es = (EntityState)it.previous();

               try {
                  es.setState(4);
               } catch (ModuleException var9) {
                  if (caught == null) {
                     caught = var9;
                  }
               }
            }

            this.allEntities[lcv].clear();
            --lcv;
         }
      }

      if (caught != null) {
         throw caught;
      }
   }

   protected Object prepareUpdate(DomainMBean proposedDomain) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: prepareUpdate called in " + this.moduleName);
      }

      TargetInfoMBean targetInfo = this.getTargetingBean(proposedDomain);
      TargetMBean[] targets = null;
      if (targetInfo != null) {
         targets = targetInfo.getTargets();
      }

      UpdateInformation retVal = new UpdateInformation(11);
      retVal.setDefaultTargets(targets);
      BasicDeploymentMBean basic = this.getBasicDeployment(proposedDomain);
      JMSBean proposedModule = JMSParser.createJMSDescriptor(this.getAppCtx(), basic, this.getId());
      if (basic != null && basic instanceof JMSSystemResourceMBean && !PartitionUtils.isDomain(this.getAppCtx().getPartitionName())) {
         PartitionProcessor.processIfClone((JMSSystemResourceMBean)basic, proposedModule);
      }

      TargetInfoMBean targetInfoMBean = null;
      if (this.getModuleType() == 1 && !(this.appDeploymentScope instanceof ResourceGroupTemplateMBean)) {
         this.validateDestinations(proposedModule);
      }

      targetInfoMBean = this.getTargetingBean(proposedDomain);
      if (this.getModuleType() == 0 || this.appDeploymentScope instanceof ResourceGroupTemplateMBean) {
         try {
            JMSModuleValidator.validateTargeting(proposedModule, basic, targetInfoMBean, this.isJMSResourceDefinition);
         } catch (IllegalArgumentException var13) {
            throw new ModuleException(var13.getMessage(), var13);
         }
      }

      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("=== Module " + this.getId() + " Tree === ");
         DescriptorUtils.writeAsXML((DescriptorBean)proposedModule);
      }

      Descriptor proposedDescriptor = ((DescriptorBean)proposedModule).getDescriptor();
      this.targeter.prepareUpdate(basic, retVal, this.getAppCtx());
      retVal.setProposedDomain(proposedDomain);
      DescriptorBean db = (DescriptorBean)this.wholeModule;
      Descriptor descriptor = db.getDescriptor();
      this.moduleListener.setInfo(retVal);

      try {
         descriptor.prepareUpdate(proposedDescriptor, false);
      } catch (DescriptorUpdateRejectedException var12) {
         this.moduleListener.setInfo((UpdateInformation)null);
         throw new ModuleException(var12.getMessage(), var12);
      }

      boolean isProcessEntitiesRequired = this.isPendingDestinationToBeProcessed(proposedDomain, this.appDeploymentScope, proposedModule);
      if (isProcessEntitiesRequired && JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: prepareUpdate isProcessEntitiesRequired : " + isProcessEntitiesRequired);
      }

      TargetingHelper var10000 = this.targeter;
      if (TargetingHelper.hasTargetingChanged(retVal) || isProcessEntitiesRequired) {
         this.processEntities(proposedModule, retVal, proposedDomain);
         if (isProcessEntitiesRequired) {
            this.isPendingDestinationToBeHosted = false;
         }
      }

      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: prepareUpdate finished in " + this.moduleName);
      }

      return retVal;
   }

   protected void rollbackUpdate(DomainMBean domain, Object paramInfo) {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: rollbackUpdate called in " + this.moduleName);
      }

      UpdateInformation info = (UpdateInformation)paramInfo;
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: Before calling targeter.rollbackUpdate() in " + this.moduleName);
      }

      this.targeter.rollbackUpdate(info);
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: After calling targeter.rollbackUpdate() in " + this.moduleName);
      }

      DescriptorBean db = (DescriptorBean)this.wholeModule;
      Descriptor descriptor = db.getDescriptor();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: Before calling descriptor.rollbackUpdate() in " + this.moduleName);
      }

      descriptor.rollbackUpdate();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: After calling descriptor.rollbackUpdate() in " + this.moduleName);
      }

      this.moduleListener.setInfo((UpdateInformation)null);
      LinkedList[] addedEntities = info.getAddedEntities();
      LinkedList[] changedEntities = info.getChangedEntities();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: Going to process the following entities in rollbackUpdate()");
         this.printEntitiesList("All Entities", this.allEntities);
         this.printEntitiesList("Added Entities", addedEntities);
         this.printEntitiesList("Changed Entities", changedEntities);
      }

      for(int lcv = 10; lcv >= 0; --lcv) {
         ListIterator it = addedEntities[lcv].listIterator(addedEntities[lcv].size());

         EntityState es;
         while(it.hasPrevious()) {
            es = (EntityState)it.previous();

            try {
               es.takeDown();
            } catch (ModuleException var12) {
               JMSLogger.logDeactivateFailedInRollbackUpdate(es.getName(), this.moduleName.toString(), var12.toString());
            }
         }

         it = changedEntities[lcv].listIterator(changedEntities[lcv].size());

         while(it.hasPrevious()) {
            es = (EntityState)it.previous();

            try {
               es.setState(2, (List)null, (DomainMBean)null, false);
            } catch (ModuleException var13) {
               JMSLogger.logRollbackChangedFailedInRollbackUpdate(es.getName(), this.moduleName.toString(), var13.toString());
            }
         }
      }

      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: Completed processing of the following entities in rollbackUpdate()");
         this.printEntitiesList("All Entities", this.allEntities);
         this.printEntitiesList("Added Entities", addedEntities);
         this.printEntitiesList("Changed Entities", changedEntities);
      }

      info.close();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: rollbackUpdate finished in " + this.moduleName);
      }

   }

   private static void printThrowable(String description, Throwable th) {
      int lcv = 0;

      for(Throwable cause = th; cause != null; cause = cause.getCause()) {
         JMSDebug.JMSModule.debug(description + " level=" + lcv);
         JMSDebug.JMSModule.debug(StackTraceUtils.throwable2StackTrace(cause));
         ++lcv;
      }

   }

   private static void printDUFE(String moduleName, DescriptorUpdateFailedException dufe) {
      printThrowable("ERROR in " + moduleName, dufe);
      Throwable[] causes = dufe.getExceptionList();
      if (causes != null) {
         for(int lcv = 0; lcv < causes.length; ++lcv) {
            printThrowable("ERROR in " + moduleName + " inner cause=" + lcv, causes[lcv]);
         }

      }
   }

   private void printEntitiesList(String listName, LinkedList[] list) {
      JMSDebug.JMSModule.debug("==== INFO: Current status of " + listName + "====");

      for(int lcv = 10; lcv >= 0; --lcv) {
         ListIterator it = list[lcv].listIterator(list[lcv].size());

         while(it.hasPrevious()) {
            EntityState es = (EntityState)it.previous();
            JMSDebug.JMSModule.debug("Entity: Name=" + es.getName() + ", State=" + es.stateToString(es.getState()) + ", doRemove=" + es.isDoRemove());
         }
      }

   }

   protected void activateUpdate(DomainMBean domain, Object paramInfo) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: activateUpdate called in " + this.moduleName);
      }

      ModuleException anException = null;
      UpdateInformation info = (UpdateInformation)paramInfo;
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: Before calling targeter.activateUpdate() in " + this.moduleName);
      }

      this.targeter.activateUpdate(info);
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: After calling targeter.activateUpdate() in " + this.moduleName);
      }

      DescriptorBean db = (DescriptorBean)this.wholeModule;
      Descriptor descriptor = db.getDescriptor();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: Before calling descriptor.activateUpdate() in " + this.moduleName);
      }

      try {
         descriptor.activateUpdate();
      } catch (DescriptorUpdateFailedException var21) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            printDUFE(this.moduleName.toString(), var21);
         }

         anException = new ModuleException(var21.getMessage(), var21);
      }

      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule: After calling descriptor.activateUpdate() in " + this.moduleName + ", anException=" + anException);
      }

      this.moduleListener.setInfo((UpdateInformation)null);
      LinkedList[] addedEntities = info.getAddedEntities();
      LinkedList[] deletedEntities = info.getDeletedEntities();
      LinkedList[] changedEntities = info.getChangedEntities();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: Going to process the following entities in activateUpdate()");
         this.printEntitiesList("All Entities", this.allEntities);
         this.printEntitiesList("Added Entities", addedEntities);
         this.printEntitiesList("Deleted Entities", deletedEntities);
         this.printEntitiesList("Changed Entities", changedEntities);
      }

      synchronized(this.allEntities) {
         int lcv;
         Iterator it;
         EntityState es;
         for(lcv = 0; lcv < 11; ++lcv) {
            it = addedEntities[lcv].iterator();

            while(it.hasNext()) {
               es = (EntityState)it.next();
               es.setState(2);
               this.allEntities[lcv].add(es);
            }

            it = changedEntities[lcv].iterator();

            while(it.hasNext()) {
               es = (EntityState)it.next();
               es.setState(2, (List)null, (DomainMBean)null, true);
            }
         }

         for(lcv = 10; lcv >= 0; --lcv) {
            it = deletedEntities[lcv].iterator();

            while(it.hasNext()) {
               es = (EntityState)it.next();
               String deleteName = es.getName();
               Iterator it2 = this.allEntities[lcv].iterator();

               while(it2.hasNext()) {
                  EntityState es2 = (EntityState)it2.next();
                  if (es2.getName().equals(deleteName)) {
                     es2.setDoRemove(es.isDoRemove());

                     try {
                        es2.takeDown();
                     } catch (ModuleException var19) {
                        if (anException == null) {
                           anException = var19;
                        }

                        JMSLogger.logDeactivateFailedInActivateUpdate(es2.getName(), this.moduleName.toString(), var19.toString());
                     }

                     it2.remove();
                  }
               }
            }

            deletedEntities[lcv].clear();
         }
      }

      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("INFO: Completed processing of the following entities in activateUpdate()");
         this.printEntitiesList("All Entities", this.allEntities);
         this.printEntitiesList("Added Entities", addedEntities);
         this.printEntitiesList("Deleted Entities", deletedEntities);
         this.printEntitiesList("Changed Entities", changedEntities);
      }

      info.close();
      if (anException != null) {
         throw anException;
      }
   }

   private void addEntity(BeanUpdateEvent.PropertyUpdate toAdd, JMSBean proposedWholeModule, UpdateInformation info) throws BeanUpdateRejectedException {
      Object addedObject = toAdd.getAddedObject();
      String propertyName = toAdd.getPropertyName();
      Stuff stuff = (Stuff)this.indexInfo.get(propertyName);
      if (stuff == null) {
         throw new BeanUpdateRejectedException(JMSExceptionLogger.logAddUnknownTypeLoggable(this.moduleName.toString(), propertyName).getMessage());
      } else if (stuff.getType() != 0) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            NamedEntityBean neb = (NamedEntityBean)addedObject;
            JMSDebug.JMSModule.debug("Adding a named entity " + neb.getName() + " of type " + propertyName + " in module " + this.moduleName);
         }

         this.addEntity(propertyName, (NamedEntityBean)addedObject, proposedWholeModule, info, (EntityState)null, info.getProposedDomain());
      }
   }

   private void addEntity(String entityTypeString, NamedEntityBean namedEntity, JMSBean proposedWholeModule, UpdateInformation info, EntityState paramEntityState, DomainMBean proposedDomain) throws BeanUpdateRejectedException {
      JMSModuleManagedEntityProvider entityProvider = null;
      EntityState entityState = paramEntityState;
      LinkedList[] addedEntities = info.getAddedEntities();
      HashMap[] addedEntitiesHash = info.getAddedEntitiesHash();
      Stuff stuff = (Stuff)this.indexInfo.get(entityTypeString);
      if (stuff == null) {
         throw new BeanUpdateRejectedException(JMSExceptionLogger.logAddUnknownTypeLoggable(this.moduleName.toString(), entityTypeString).getMessage());
      } else {
         entityProvider = stuff.getProvider();
         int entityType = stuff.getType();
         if (entityProvider != null && entityType != 0) {
            LinkedList listToUse = addedEntities[stuff.getIndex()];
            HashMap mapToUse = addedEntitiesHash[stuff.getIndex()];
            TargetInfoMBean targetInfo = this.getTargetingBean(proposedDomain);
            if (paramEntityState == null) {
               List targets = null;
               TargetableBean targetable = null;
               switch (entityType) {
                  case 1:
                     targetable = (TargetableBean)namedEntity;
                     targets = this.targeter.getTarget(targetInfo, targetable, targetable.getSubDeploymentName(), info, false);
                     break;
                  case 2:
                     targetable = (TargetableBean)namedEntity;
                     targets = this.targeter.getTarget(targetInfo, targetable, targetable.getSubDeploymentName(), info, true);
               }

               if ((entityType == 1 || entityType == 2) && targetInfo != null && targetInfo instanceof JMSSystemResourceMBean && targetable != null && this.appDeploymentScope instanceof ResourceGroupTemplateMBean && !targetable.isDefaultTargetingEnabled() && !JMSModuleHelper.isSubDeploymentTargeted((JMSSystemResourceMBean)targetInfo, targetable.getSubDeploymentName())) {
                  JMSLogger.logSubdeploymentNotTargeted(targetable.getSubDeploymentName(), entityTypeString, namedEntity.getName(), JMSModuleHelper.getConfigMBeanShortName(this.appDeploymentScope, targetInfo), JMSModuleHelper.getDeploymentScopeAsString(this.getAppCtx()));
                  if (entityType == 2) {
                     this.pendingUTTEntityToBeHostedSet.add(entityTypeString + "/" + namedEntity.getName());
                  }
               }

               if ((entityType == 1 || entityType == 2) && (targets == null || targets.size() <= 0)) {
                  if (JMSDebug.JMSModule.isDebugEnabled()) {
                     JMSDebug.JMSModule.debug("addEntity(): Entity " + namedEntity.getName() + " of type " + entityTypeString + " in module " + this.moduleName + " with subdeployment " + ((TargetableBean)namedEntity).getSubDeploymentName() + " is not targeted locally: " + targets);
                  }

                  if (entityType == 2) {
                     this.pendingUTTEntityToBeHostedSet.add(entityTypeString + "/" + namedEntity.getName());
                  }

                  return;
               }

               if (this.checkForNoCandidateJMSServers(this.appDeploymentScope, stuff.getIndex(), targetable, proposedDomain)) {
                  if (!this.isPendingDestinationToBeHosted) {
                     this.isPendingDestinationToBeHosted = true;
                  }

                  return;
               }

               if (!this.isCurrentServerHostingSingleton(this.appDeploymentScope, stuff.getIndex(), targetable, proposedDomain)) {
                  return;
               }

               JMSModuleManagedEntity entity;
               try {
                  EntityName entityName = new EntityName(this.moduleName, namedEntity.getName());
                  if (entityType == 2 && targetable != null && this.appDeploymentScope instanceof ResourceGroupTemplateMBean && !targetable.isDefaultTargetingEnabled()) {
                     List toRemove = new ArrayList();
                     Iterator var21 = targets.iterator();

                     while(var21.hasNext()) {
                        Object targetToRemove = var21.next();
                        if (targetToRemove instanceof VirtualTargetMBean) {
                           toRemove.add(targetToRemove);
                        }
                     }

                     targets.removeAll(toRemove);
                  }

                  entity = entityProvider.createEntity(this.getAppCtx(), entityName, this.applicationNamingContext, proposedWholeModule, namedEntity, targets, proposedDomain, this.isJMSResourceDefinition);
                  if (this.appDeploymentScope instanceof ResourceGroupTemplateMBean && (stuff.getIndex() == 0 || stuff.getIndex() == 1) && targetable.isDefaultTargetingEnabled()) {
                     this.hostedSingletonDefaultTargetedDestinations.add(targetable.getName());
                  }
               } catch (ModuleException var25) {
                  throw new BeanUpdateRejectedException(JMSExceptionLogger.logErrorAddingTypeLoggable(this.moduleName.toString(), entityTypeString, namedEntity.getName()).getMessage(), var25);
               }

               entityState = new EntityState(entity);
            }

            try {
               entityState.setState(1);
            } catch (ModuleException var24) {
               try {
                  entityState.takeDown();
               } catch (ModuleException var23) {
                  JMSLogger.logDestroyFailedInAdd(entityState.getName(), this.moduleName.toString(), var23.toString());
               }

               throw new BeanUpdateRejectedException(var24.getMessage(), var24);
            }

            listToUse.add(entityState);
            mapToUse.put(entityState.getName(), entityState);
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("Entity " + namedEntity.getName() + " of type " + entityTypeString + " in module " + this.moduleName + " was succesfully added");
            }

         } else {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("Entity " + namedEntity.getName() + " of type " + entityTypeString + " in module " + this.moduleName + " has no associated factory, no need to add");
            }

         }
      }
   }

   private void removeEntity(NamedEntityBean toRemove, String entityTypeString, UpdateInformation info, boolean doRemove) throws BeanUpdateRejectedException {
      String name = toRemove.getName();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Removing a named entity named " + name);
      }

      Stuff stuff = (Stuff)this.indexInfo.get(entityTypeString);
      if (stuff == null) {
         throw new BeanUpdateRejectedException(JMSExceptionLogger.logDeleteUnknownTypeLoggable(this.moduleName.toString(), entityTypeString).getMessage());
      } else {
         int index = stuff.getIndex();
         if (index >= 0) {
            this.singletonDefaultTargetedDestinations.remove(name);
            this.hostedSingletonDefaultTargetedDestinations.remove(name);
            LinkedList[] deletedEntities = info.getDeletedEntities();
            LinkedList listToUse = deletedEntities[index];
            HashMap[] deletedEntitiesHash = info.getDeletedEntitiesHash();
            HashMap hashToUse = deletedEntitiesHash[index];
            if (!hashToUse.containsKey(name)) {
               EntityState es = new EntityState(name);
               es.setDoRemove(doRemove);
               listToUse.add(es);
               hashToUse.put(name, es);
            }
         }
      }
   }

   private void changeEntity(TargetableBean toChange, int entityType, String entityTypeString, int realChangedState, DomainMBean proposedDomain, UpdateInformation info) throws ModuleException {
      String name = toChange.getName();
      Stuff stuff = (Stuff)this.indexInfo.get(entityTypeString);
      if (stuff == null) {
         throw new ModuleException(JMSExceptionLogger.logDeleteUnknownTypeLoggable(this.moduleName.toString(), entityTypeString).getMessage());
      } else {
         int index = stuff.getIndex();
         if (index >= 0) {
            LinkedList runtimeList = this.allEntities[index];
            Iterator rit = runtimeList.iterator();
            EntityState realEntityState = null;

            EntityState es;
            while(rit.hasNext()) {
               es = (EntityState)rit.next();
               if (es.getName().equals(name)) {
                  realEntityState = es;
                  break;
               }
            }

            es = null;
            LinkedList[] changedEntities;
            switch (realChangedState) {
               case 1:
                  if (realEntityState == null) {
                     changedEntities = info.getAddedEntities();
                  } else {
                     changedEntities = info.getChangedEntities();
                  }
                  break;
               case 2:
                  changedEntities = info.getChangedEntities();
                  break;
               case 3:
                  changedEntities = info.getChangedEntities();
                  break;
               default:
                  throw new AssertionError("realChangeState is UNCHANGED for " + toChange.getName() + " type " + entityTypeString + " change state=" + realChangedState);
            }

            boolean useGlobal = entityType == 2;
            List changedTargets = realChangedState == 2 ? null : this.targeter.getTarget(this.getTargetingBean(proposedDomain), toChange, toChange.getSubDeploymentName(), info, useGlobal);
            LinkedList listToUse = changedEntities[index];
            if (realEntityState != null) {
               realEntityState.setState(5, changedTargets, proposedDomain, true);
               listToUse.add(realEntityState);
            } else if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("changeEntity(): Entity " + toChange.getName() + " of type " + entityTypeString + " in module " + this.moduleName + " with subdeployment " + toChange.getSubDeploymentName() + " is not targeted locally: " + changedTargets);
            }

         }
      }
   }

   private void processEntities(JMSBean proposedWholeModule, UpdateInformation info, DomainMBean proposedDomain) throws ModuleException {
      boolean success = false;
      LinkedList[] allInitialized = new LinkedList[11];

      for(int lcv = 0; lcv < 11; ++lcv) {
         allInitialized[lcv] = new LinkedList();
      }

      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("JMSModule:processEntities called in " + this.moduleName + (proposedWholeModule == null ? " for initialization" : " for update"));
      }

      HashMap[] addedEntitiesHash = proposedWholeModule != null ? info.getAddedEntitiesHash() : null;
      HashMap[] deletedEntitiesHash = proposedWholeModule != null ? info.getDeletedEntitiesHash() : null;
      JMSBean moduleToUse;
      if (proposedWholeModule == null) {
         this.initializeNamingContext();
         moduleToUse = this.wholeModule;
      } else {
         moduleToUse = proposedWholeModule;
      }

      NamedEntityBean[] namedEntities = null;
      LinkedList addToMe = null;
      String entityTypeString = null;
      TargetInfoMBean targetInfo = proposedDomain == null ? null : this.getTargetingBean(proposedDomain);
      boolean isBasic = targetInfo == null ? false : targetInfo instanceof BasicDeploymentMBean;
      HashSet alreadyChecked = null;
      boolean var51 = false;

      int lcv;
      try {
         var51 = true;
         lcv = 0;

         while(true) {
            if (lcv >= 11) {
               success = true;
               var51 = false;
               break;
            }

            addToMe = allInitialized[lcv];
            switch (lcv) {
               case 0:
                  namedEntities = moduleToUse.getQueues();
                  entityTypeString = "Queues";
                  break;
               case 1:
                  namedEntities = moduleToUse.getTopics();
                  entityTypeString = "Topics";
                  break;
               case 2:
                  namedEntities = moduleToUse.getDistributedQueues();
                  entityTypeString = "DistributedQueues";
                  break;
               case 3:
                  namedEntities = moduleToUse.getDistributedTopics();
                  entityTypeString = "DistributedTopics";
                  break;
               case 4:
                  namedEntities = moduleToUse.getUniformDistributedQueues();
                  entityTypeString = "UniformDistributedQueues";
                  break;
               case 5:
                  namedEntities = moduleToUse.getUniformDistributedTopics();
                  entityTypeString = "UniformDistributedTopics";
                  break;
               case 6:
                  namedEntities = moduleToUse.getSAFRemoteContexts();
                  entityTypeString = "SAFRemoteContexts";
                  break;
               case 7:
                  namedEntities = moduleToUse.getSAFImportedDestinations();
                  entityTypeString = "SAFImportedDestinations";
                  break;
               case 8:
                  namedEntities = moduleToUse.getSAFErrorHandlings();
                  entityTypeString = "SAFErrorHandlings";
                  break;
               case 9:
                  namedEntities = moduleToUse.getConnectionFactories();
                  entityTypeString = "ConnectionFactories";
                  break;
               case 10:
                  namedEntities = moduleToUse.getForeignServers();
                  entityTypeString = "ForeignServers";
                  break;
               default:
                  throw new AssertionError("ERROR: processEntities in module " + this.moduleName + " got unknown type " + lcv);
            }

            Stuff entityStuff = (Stuff)this.indexInfo.get(entityTypeString);

            for(int lcv = 0; lcv < ((Object[])namedEntities).length; ++lcv) {
               NamedEntityBean namedEntity = ((Object[])namedEntities)[lcv];
               TargetableBean targetable = null;
               String groupName = null;
               boolean pendingUTTEntityToBeHostedSetResetOnFail = false;
               boolean isUTTEntitySkippedEarlier = false;

               try {
                  if (namedEntity instanceof TargetableBean) {
                     targetable = (TargetableBean)namedEntity;
                     groupName = targetable.getSubDeploymentName();
                     if (targetInfo != null && targetInfo instanceof JMSSystemResourceMBean && !targetable.isDefaultTargetingEnabled() && this.appDeploymentScope instanceof ResourceGroupTemplateMBean) {
                        if (!JMSModuleHelper.isSubDeploymentTargeted((JMSSystemResourceMBean)targetInfo, groupName)) {
                           JMSLogger.logSubdeploymentNotTargeted(groupName, entityTypeString, ((NamedEntityBean)namedEntity).getName(), JMSModuleHelper.getConfigMBeanShortName(this.appDeploymentScope, targetInfo), JMSModuleHelper.getDeploymentScopeAsString(this.getAppCtx()));
                        } else if (this.pendingUTTEntityToBeHostedSet.contains(entityTypeString + "/" + ((NamedEntityBean)namedEntity).getName())) {
                           isUTTEntitySkippedEarlier = true;
                        }
                     }
                  }

                  int changeStatus;
                  int realChangeStatus;
                  List targets;
                  int entityType;
                  targets = null;
                  entityType = entityStuff.getType();
                  label1502:
                  switch (entityType) {
                     case 1:
                     case 2:
                        if (targetInfo != null) {
                           if (alreadyChecked == null) {
                              alreadyChecked = new HashSet();
                           }

                           if (!alreadyChecked.contains(groupName)) {
                              alreadyChecked.add(groupName);
                              SubDeploymentMBean mySub;
                              if (isBasic) {
                                 BasicDeploymentMBean bdmb = (BasicDeploymentMBean)targetInfo;
                                 mySub = bdmb.lookupSubDeployment(groupName);
                              } else {
                                 SubDeploymentMBean sdmb = (SubDeploymentMBean)targetInfo;
                                 mySub = sdmb.lookupSubDeployment(groupName);
                              }

                              if (mySub == null && (!entityStuff.isDefaultTargetingEnabled() || !targetable.isDefaultTargetingEnabled())) {
                                 JMSLogger.logInvalidJMSModuleSubDeploymentConfiguration(this.moduleName.toString(), groupName, ((NamedEntityBean)namedEntity).getName(), entityTypeString);
                              }
                           }
                        }

                        HashMap mapToUse;
                        if (entityType == 2) {
                           realChangeStatus = changeStatus = this.targeter.getGroupTargetChangeStatus(targetable, groupName, info, true, false, false);
                           targets = this.targeter.getTarget(targetInfo, targetable, groupName, info, true);
                           if (isUTTEntitySkippedEarlier && realChangeStatus == 3) {
                              realChangeStatus = 1;
                              changeStatus = 1;
                              List toRemove = new ArrayList();
                              Iterator var82 = targets.iterator();

                              while(var82.hasNext()) {
                                 Object targetToRemove = var82.next();
                                 if (targetToRemove instanceof VirtualTargetMBean) {
                                    toRemove.add(targetToRemove);
                                 }
                              }

                              targets.removeAll(toRemove);
                           }

                           if (JMSDebug.JMSModule.isDebugEnabled()) {
                              JMSDebug.JMSModule.debug("INFO: JMSModule.processEntities() for UBIQUITOUS_TARGETED_TYPE: changeStatus=" + changeStatus + ", targetable=" + targetable + ", groupName=" + groupName + ", update info=" + (info != null ? info.toString() : "null") + ", targetInfo.name=" + (targetInfo != null ? targetInfo.getName() : "null") + ", targetInfo.getTargets=" + (targetInfo != null && targetInfo.getTargets() != null ? targetInfo.getTargets().toString() : "null") + ",targets=" + (targets != null ? targets.toString() : "null"));
                           }

                           switch (realChangeStatus) {
                              case 0:
                                 if (proposedWholeModule == null) {
                                    changeStatus = 1;
                                 }
                                 break label1502;
                              case 1:
                                 if (proposedWholeModule != null && !isUTTEntitySkippedEarlier) {
                                    mapToUse = addedEntitiesHash[entityStuff.getIndex()];
                                    if (mapToUse.containsKey(((NamedEntityBean)namedEntity).getName())) {
                                       changeStatus = 0;
                                    } else {
                                       String key = entityTypeString + "/" + ((NamedEntityBean)namedEntity).getName();
                                       if (this.pendingUTTEntityToBeHostedSet.contains(key)) {
                                          changeStatus = 1;
                                          this.pendingUTTEntityToBeHostedSet.remove(key);
                                          pendingUTTEntityToBeHostedSetResetOnFail = true;
                                          if (JMSDebug.JMSModule.isDebugEnabled()) {
                                             JMSDebug.JMSModule.debug("processEntities(): process ADD for pending UBIQUITOUS_TARGETED_TYPE to be hosted: " + key);
                                          }
                                       } else {
                                          changeStatus = 3;
                                       }
                                    }
                                 }
                                 break label1502;
                              case 2:
                                 mapToUse = deletedEntitiesHash[entityStuff.getIndex()];
                                 if (!mapToUse.containsKey(((NamedEntityBean)namedEntity).getName())) {
                                    changeStatus = 3;
                                 }
                                 break label1502;
                              case 3:
                                 mapToUse = addedEntitiesHash[entityStuff.getIndex()];
                                 if (mapToUse.containsKey(((NamedEntityBean)namedEntity).getName())) {
                                    changeStatus = 0;
                                 }
                                 break label1502;
                              default:
                                 throw new AssertionError("Unknown change status: " + realChangeStatus);
                           }
                        } else {
                           if (this.appDeploymentScope instanceof ResourceGroupTemplateMBean && (entityStuff.getIndex() == 0 || entityStuff.getIndex() == 1) && targetable.isDefaultTargetingEnabled()) {
                              realChangeStatus = changeStatus = this.targeter.getGroupTargetChangeStatus(targetable, groupName, info, false, true, false);
                           } else {
                              boolean isConnectionFactoryDefinitionToJMSServer = this.isJMSResourceDefinition && entityType == 1 && !targetable.isDefaultTargetingEnabled();
                              realChangeStatus = changeStatus = this.targeter.getGroupTargetChangeStatus(targetable, groupName, info, false, false, isConnectionFactoryDefinitionToJMSServer);
                           }

                           targets = this.targeter.getTarget(targetInfo, targetable, groupName, info, false);
                           if (JMSDebug.JMSModule.isDebugEnabled()) {
                              JMSDebug.JMSModule.debug("INFO: JMSModule.processEntities() for TARGETED_TYPE: changeStatus=" + changeStatus + ", targetable=" + targetable + ", groupName=" + groupName + ", update info=" + (info != null ? info.toString() : "null") + ", targetInfo.name=" + (targetInfo != null ? targetInfo.getName() : "null") + ", targetInfo.getTargets=" + (targetInfo != null && targetInfo.getTargets() != null ? targetInfo.getTargets().toString() : "null") + ",targets=" + (targets != null ? targets.toString() : "null"));
                           }

                           if (this.isPendingDestinationToBeHosted && (entityStuff.getIndex() == 0 || entityStuff.getIndex() == 1) && targetable.isDefaultTargetingEnabled() && !this.hostedSingletonDefaultTargetedDestinations.contains(targetable.getName())) {
                              changeStatus = 1;
                           }

                           switch (realChangeStatus) {
                              case 1:
                                 if (proposedWholeModule != null) {
                                    mapToUse = addedEntitiesHash[entityStuff.getIndex()];
                                    if (mapToUse.containsKey(((NamedEntityBean)namedEntity).getName())) {
                                       changeStatus = 0;
                                    }
                                 }

                                 if (this.checkForNoCandidateJMSServers(this.appDeploymentScope, entityStuff.getIndex(), targetable, proposedDomain)) {
                                    this.isPendingDestinationToBeHosted = true;
                                    changeStatus = 0;
                                 } else if (!this.isCurrentServerHostingSingleton(this.appDeploymentScope, entityStuff.getIndex(), targetable, proposedDomain)) {
                                    changeStatus = 0;
                                 }
                                 break label1502;
                              case 2:
                                 mapToUse = deletedEntitiesHash[entityStuff.getIndex()];
                                 if (mapToUse.containsKey(((NamedEntityBean)namedEntity).getName())) {
                                    changeStatus = 0;
                                 }
                              default:
                                 break label1502;
                           }
                        }
                     case 3:
                        if (proposedWholeModule != null) {
                           changeStatus = 0;
                           realChangeStatus = 0;
                        } else {
                           changeStatus = 1;
                           realChangeStatus = 1;
                        }
                        break;
                     default:
                        throw new AssertionError("An entity of this type " + entityTypeString + " should not have type " + entityType);
                  }

                  switch (changeStatus) {
                     case 0:
                        break;
                     case 1:
                        JMSModuleManagedEntityProvider entityProvider = entityStuff.getProvider();
                        EntityName entityName = new EntityName(this.moduleName, ((NamedEntityBean)namedEntity).getName());
                        JMSModuleManagedEntity entity = entityProvider.createEntity(this.getAppCtx(), entityName, this.applicationNamingContext, moduleToUse, (NamedEntityBean)namedEntity, targets, proposedDomain, this.isJMSResourceDefinition);
                        EntityState entityState = new EntityState(entity);
                        addToMe.add(entityState);
                        if (proposedWholeModule != null) {
                           try {
                              this.addEntity(entityTypeString, (NamedEntityBean)namedEntity, proposedWholeModule, info, entityState, proposedDomain);
                              pendingUTTEntityToBeHostedSetResetOnFail = false;
                           } catch (BeanUpdateRejectedException var69) {
                              Throwable th = var69.getCause();
                              if (th != null && th instanceof ModuleException) {
                                 throw (ModuleException)th;
                              }

                              throw new ModuleException(JMSExceptionLogger.logAddFailedLoggable(this.moduleName.toString(), entityState.getName()).getMessage(), var69);
                           }

                           HashMap mapToUse = addedEntitiesHash[entityStuff.getIndex()];
                           mapToUse.put(entityState.getName(), entityState);
                        }
                        break;
                     case 2:
                        try {
                           this.removeEntity((NamedEntityBean)namedEntity, entityTypeString, info, false);
                           break;
                        } catch (BeanUpdateRejectedException var68) {
                           throw new ModuleException(JMSExceptionLogger.logRemoveFailedLoggable(this.moduleName.toString(), ((NamedEntityBean)namedEntity).getName()).getMessage(), var68);
                        }
                     case 3:
                        if (!(namedEntity instanceof TargetableBean)) {
                           throw new AssertionError("The named entity " + ((NamedEntityBean)namedEntity).getName() + " of type " + entityTypeString + " must be targeteable to have its targets changed in " + this.moduleName + ".  It is of class " + namedEntity.getClass().getName());
                        }

                        this.changeEntity((TargetableBean)namedEntity, entityType, entityTypeString, realChangeStatus, proposedDomain, info);
                        break;
                     default:
                        throw new AssertionError("ERROR: processEntities in module " + this.moduleName + " got unknown change state " + changeStatus);
                  }
               } finally {
                  if (pendingUTTEntityToBeHostedSetResetOnFail) {
                     this.pendingUTTEntityToBeHostedSet.add(entityTypeString + "/" + ((NamedEntityBean)namedEntity).getName());
                  }

               }
            }

            ++lcv;
         }
      } finally {
         if (var51) {
            if (success) {
               if (proposedWholeModule == null) {
                  synchronized(this.allEntities) {
                     this.allEntities = allInitialized;
                  }
               }
            } else {
               int lcv;
               if (proposedWholeModule != null) {
                  for(lcv = 0; lcv < 11; ++lcv) {
                     addedEntitiesHash[lcv].clear();
                  }

                  LinkedList[] changedGroups = info.getChangedEntities();

                  for(int lcv = 10; lcv >= 0; --lcv) {
                     ListIterator it = changedGroups[lcv].listIterator(changedGroups[lcv].size());

                     while(it.hasPrevious()) {
                        EntityState es = (EntityState)it.previous();

                        try {
                           es.setState(2, (List)null, (DomainMBean)null, false);
                        } catch (ModuleException var63) {
                           JMSLogger.logRollbackChangeFailedInInit(es.getName(), this.moduleName.toString(), var63.toString());
                        }
                     }
                  }
               }

               for(lcv = 10; lcv >= 0; --lcv) {
                  ListIterator it = allInitialized[lcv].listIterator(allInitialized[lcv].size());

                  while(it.hasPrevious()) {
                     EntityState es = (EntityState)it.previous();

                     try {
                        es.takeDown();
                     } catch (ModuleException var62) {
                        JMSLogger.logDeactivateFailedInInit(es.getName(), this.moduleName.toString(), var62.toString());
                     }
                  }
               }
            }

         }
      }

      if (success) {
         if (proposedWholeModule == null) {
            synchronized(this.allEntities) {
               this.allEntities = allInitialized;
            }
         }
      } else {
         if (proposedWholeModule != null) {
            for(lcv = 0; lcv < 11; ++lcv) {
               addedEntitiesHash[lcv].clear();
            }

            LinkedList[] changedGroups = info.getChangedEntities();

            for(int lcv = 10; lcv >= 0; --lcv) {
               ListIterator it = changedGroups[lcv].listIterator(changedGroups[lcv].size());

               while(it.hasPrevious()) {
                  EntityState es = (EntityState)it.previous();

                  try {
                     es.setState(2, (List)null, (DomainMBean)null, false);
                  } catch (ModuleException var66) {
                     JMSLogger.logRollbackChangeFailedInInit(es.getName(), this.moduleName.toString(), var66.toString());
                  }
               }
            }
         }

         for(lcv = 10; lcv >= 0; --lcv) {
            ListIterator it = allInitialized[lcv].listIterator(allInitialized[lcv].size());

            while(it.hasPrevious()) {
               EntityState es = (EntityState)it.previous();

               try {
                  es.takeDown();
               } catch (ModuleException var65) {
                  JMSLogger.logDeactivateFailedInInit(es.getName(), this.moduleName.toString(), var65.toString());
               }
            }
         }
      }

   }

   private void initializeNamingContext() throws ModuleException {
      if (this.applicationNamingContext != null) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("applicationNamingContext is already initialized");
         }

      } else {
         Context initialContext = JMSService.getContextWithModuleException(true);
         Context weblogicContext = null;

         try {
            weblogicContext = (Context)initialContext.lookup("weblogic");
         } catch (NamingException var6) {
            throw new ModuleException("ERROR: Could not lookup the weblogic context in module " + this.moduleName + " of application " + this.getAppCtx().getApplicationId(), var6);
         }

         try {
            this.applicationNamingContext = weblogicContext.createSubcontext(this.getAppCtx().getApplicationId());
         } catch (NameAlreadyBoundException var4) {
         } catch (NamingException var5) {
            throw new ModuleException("ERROR: Could not create the application context in module " + this.moduleName + " of application " + this.getAppCtx().getApplicationId(), var5);
         }

      }
   }

   private JMSServerMBean getJMSServerMBeanUsingName(AppDeploymentMBean appDeployment, DomainMBean proposedDomain, WebLogicMBean appDeploymentScope, ApplicationContextInternal paramAppCtx, String proposedJMSServerName, String jndiName, boolean isJMSDestinationDefinition) throws ModuleException {
      String jmsResourceDefinitionType = isJMSDestinationDefinition ? "JMS Destination Definition" : "JMS Connection Factory Definition";
      String jmsResourceType = isJMSDestinationDefinition ? "JMS Destination" : "JMS Connection Factory";
      JMSServerMBean candidateJMSServerMBean = null;
      String jmsServerMBeanName = null;
      if (appDeploymentScope instanceof ResourceGroupTemplateMBean) {
         String applicationName = JMSModuleHelper.getConfigMBeanShortName(appDeploymentScope, appDeployment);
         JMSServerMBean[] candidateJMSServers = JMSModuleHelper.getCandidateJMSServers(proposedDomain, appDeploymentScope, "", this.isDeployedToRGT);
         JMSServerMBean[] var14 = candidateJMSServers;
         int var15 = candidateJMSServers.length;

         for(int var16 = 0; var16 < var15; ++var16) {
            JMSServerMBean jmsServerMBean = var14[var16];
            jmsServerMBeanName = JMSModuleHelper.getConfigMBeanShortName(appDeploymentScope, jmsServerMBean);
            if (proposedJMSServerName.equals(jmsServerMBeanName)) {
               candidateJMSServerMBean = jmsServerMBean;
               break;
            }
         }

         if (candidateJMSServerMBean == null) {
            throw new ModuleException(JMSExceptionLogger.logInvalidJMSServerForJMSResourceDefinitionLoggable(proposedJMSServerName, jmsResourceDefinitionType, jndiName, applicationName, JMSModuleHelper.getDeploymentScopeAsString(paramAppCtx), jmsResourceType).getMessage());
         }

         if (candidateJMSServerMBean != null && isJMSDestinationDefinition) {
            PersistentStoreMBean store = candidateJMSServerMBean.getPersistentStore();
            if (store == null) {
               throw new AssertionError("ERROR: JMSServer " + jmsServerMBeanName + " has no associated perstent store, which is not allowed to host a destination defined by JMS Destination Definition");
            }

            if (!"Distributed".equals(store.getDistributionPolicy())) {
               throw new ModuleException(JMSExceptionLogger.logInvalidStoreForJMSResourceDefinitionLoggable(proposedJMSServerName, jndiName, applicationName, store.getDistributionPolicy()).getMessage());
            }
         }
      } else {
         candidateJMSServerMBean = proposedDomain.lookupJMSServer(proposedJMSServerName);
         if (candidateJMSServerMBean == null) {
            throw new ModuleException(JMSExceptionLogger.logInvalidJMSServerForJMSResourceDefinitionLoggable(proposedJMSServerName, jmsResourceDefinitionType, jndiName, paramAppCtx.getApplicationId(), JMSModuleHelper.getDeploymentScopeAsString(paramAppCtx), jmsResourceType).getMessage());
         }

         if (isJMSDestinationDefinition) {
            PersistentStoreMBean store = candidateJMSServerMBean.getPersistentStore();
            if (store != null && !"Distributed".equals(store.getDistributionPolicy())) {
               throw new ModuleException(JMSExceptionLogger.logInvalidStoreForJMSResourceDefinitionLoggable(proposedJMSServerName, jndiName, paramAppCtx.getApplicationId(), store.getDistributionPolicy()).getMessage());
            }
         }
      }

      return candidateJMSServerMBean;
   }

   private boolean checkForNoCandidateJMSServers(WebLogicMBean deploymentScope, int destinationType, TargetableBean targetable, DomainMBean proposedDomain) {
      if (deploymentScope instanceof ResourceGroupTemplateMBean && (destinationType == 0 || destinationType == 1) && targetable.isDefaultTargetingEnabled()) {
         this.singletonDefaultTargetedDestinations.add(targetable.getName());
         JMSServerMBean[] singletonJMSServers = JMSModuleHelper.getCandidateJMSServers(proposedDomain, deploymentScope, "Singleton", this.isDeployedToRGT);
         if (singletonJMSServers == null || singletonJMSServers.length == 0) {
            String destinationTypeString = destinationType == 0 ? "Queue" : "Topic";
            String partitionName = this.getAppCtx().getPartitionName();
            String moduleName = PartitionUtils.stripDecoratedPartitionName(partitionName, this.getName());
            JMSLogger.logMatchingJMSServerNotFound("Singleton", destinationTypeString, targetable.getName(), moduleName, JMSModuleHelper.getDeploymentScopeAsString(this.getAppCtx()));
            return true;
         }
      }

      return false;
   }

   private boolean isCurrentServerHostingSingleton(WebLogicMBean deploymentScope, int destinationType, TargetableBean targetable, DomainMBean proposedDomain) {
      if (deploymentScope instanceof ResourceGroupTemplateMBean && (destinationType == 0 || destinationType == 1) && targetable.isDefaultTargetingEnabled()) {
         JMSServerMBean[] allJMSServers = JMSModuleHelper.getCandidateJMSServers(proposedDomain, deploymentScope, "Singleton", this.isDeployedToRGT);
         if (allJMSServers.length > 0) {
            JMSServerMBean jmsServer = allJMSServers[0];
            return this.targeter.isLocallyTargetedClusteredSingletonJMSServer(jmsServer);
         }
      }

      return true;
   }

   private boolean isPendingDestinationToBeProcessed(DomainMBean proposedDomain, WebLogicMBean deploymentScope, JMSBean proposedModule) {
      if (!(deploymentScope instanceof ResourceGroupTemplateMBean)) {
         return false;
      } else {
         JMSServerMBean[] singletonJMSServers = JMSModuleHelper.getCandidateJMSServers(proposedDomain, deploymentScope, "Singleton", this.isDeployedToRGT);
         if (this.isPendingDestinationToBeHosted && singletonJMSServers != null && singletonJMSServers.length > 0) {
            return true;
         } else {
            if (!this.isPendingDestinationToBeHosted) {
               if (this.singletonDefaultTargetedDestinations.isEmpty()) {
                  return false;
               }

               if (singletonJMSServers == null || singletonJMSServers.length == 0) {
                  this.isPendingDestinationToBeHosted = true;
                  return false;
               }

               if (this.descriptorUpdateEvent == null) {
                  return false;
               }

               if (singletonJMSServers.length == 1) {
                  Iterator beanUpdateIterator = this.descriptorUpdateEvent.getDiff().iterator();

                  while(beanUpdateIterator.hasNext()) {
                     BeanUpdateEvent beanUpdateEvent = (BeanUpdateEvent)beanUpdateIterator.next();
                     BeanUpdateEvent.PropertyUpdate[] var7 = beanUpdateEvent.getUpdateList();
                     int var8 = var7.length;

                     for(int var9 = 0; var9 < var8; ++var9) {
                        BeanUpdateEvent.PropertyUpdate propertyUpdate = var7[var9];
                        if (propertyUpdate.getUpdateType() == 2 && propertyUpdate.getAddedObject() instanceof JMSServerMBean) {
                           JMSServerMBean jmsServerMBean = (JMSServerMBean)propertyUpdate.getAddedObject();
                           if (JMSModuleHelper.isTargetInDeploymentScope(jmsServerMBean, deploymentScope) && jmsServerMBean.getPersistentStore() != null && "Singleton".equals(jmsServerMBean.getPersistentStore().getDistributionPolicy()) && singletonJMSServers[0].getName().equals(jmsServerMBean.getName())) {
                              this.isPendingDestinationToBeHosted = true;
                              return true;
                           }
                        }
                     }
                  }
               }
            }

            return false;
         }
      }
   }

   protected void activateForRP(DomainMBean domain, Set jmsservers) throws ModuleException {
      TargetableBean[] beans = null;

      for(int index = 0; index < RP_TYPES.length; ++index) {
         int type = RP_TYPES[index];
         String typeString = RP_TYPE_STRING[index];
         switch (type) {
            case 0:
               beans = this.wholeModule.getQueues();
               break;
            case 1:
               beans = this.wholeModule.getTopics();
         }

         for(int i = 0; i < ((Object[])beans).length; ++i) {
            TargetableBean oneBean = ((Object[])beans)[i];
            String groupName = ((TargetableBean)oneBean).getSubDeploymentName();
            String serviceName = this.getServiceName(domain, groupName);
            if (serviceName != null && jmsservers.contains(serviceName)) {
               EntityState es = this.getEntityState(((TargetableBean)oneBean).getName(), typeString);
               if (es != null) {
                  if (JMSDebug.JMSModule.isDebugEnabled()) {
                     JMSDebug.JMSModule.debug("activateForRP: restart entity " + ((TargetableBean)oneBean).getName());
                  }

                  es.restart();
               }
            }
         }
      }

   }

   private EntityState getEntityState(String name, String entityTypeString) {
      Stuff stuff = (Stuff)this.indexInfo.get(entityTypeString);
      if (stuff == null) {
         return null;
      } else {
         int index = stuff.getIndex();
         if (index < 0) {
            return null;
         } else {
            LinkedList runtimeList = this.allEntities[index];
            Iterator rit = runtimeList.iterator();
            EntityState realEntityState = null;

            EntityState es;
            do {
               if (!rit.hasNext()) {
                  return null;
               }

               es = (EntityState)rit.next();
            } while(!es.getName().equals(name));

            return es;
         }
      }
   }

   private String getServiceName(DomainMBean domain, String groupName) {
      if (domain != null && this.getTargetingBean(domain) != null) {
         TargetInfoMBean targetInfo = this.getTargetingBean(domain);
         SubDeploymentMBean mysub;
         if (targetInfo instanceof BasicDeploymentMBean) {
            BasicDeploymentMBean bdmb = (BasicDeploymentMBean)targetInfo;
            mysub = bdmb.lookupSubDeployment(groupName);
         } else {
            SubDeploymentMBean sdmb = (SubDeploymentMBean)targetInfo;
            mysub = sdmb.lookupSubDeployment(groupName);
         }

         return mysub != null && mysub.getTargets() != null ? mysub.getTargets()[0].getName() : null;
      } else {
         return null;
      }
   }

   static {
      QUEUE_STUFF = new Stuff(DESTINATION_PROVIDER, 0, 1, false);
      QUEUE_PARTITION_STUFF = new Stuff(DESTINATION_PROVIDER, 0, 1, true);
      TOPIC_STUFF = new Stuff(DESTINATION_PROVIDER, 1, 1, false);
      TOPIC_PARTITION_STUFF = new Stuff(DESTINATION_PROVIDER, 1, 1, true);
      FOREIGN_PROVIDER_STUFF = new Stuff(FOREIGN_PROVIDER, 10, 1, true);
      DISTRIBUTED_QUEUE_STUFF = new Stuff(DD_PROVIDER, 2, 3, false);
      DISTRIBUTED_TOPIC_STUFF = new Stuff(DD_PROVIDER, 3, 3, false);
      CONN_FACTORY_STUFF = new Stuff(CONN_PROVIDER, 9, 1, true);
      SAF_IMPORTED_DEST_STUFF = new Stuff(SAF_PROVIDER, 7, 2, true);
      REMOTE_CTXT_PROVIDER_STUFF = new Stuff(REMOTE_CONTEXT_PROVIDER, 6, 3, false);
      ERROR_HANDLING_STUFF = new Stuff(ERROR_HANDLING_PROVIDER, 8, 3, false);
      UDD_QUEUE_STUFF = new Stuff(UDD_PROVIDER, 4, 2, true);
      UDD_TOPIC_STUFF = new Stuff(UDD_PROVIDER, 5, 2, true);
      NON_EXISTENT_STUFF = new Stuff((JMSModuleManagedEntityProvider)null, -1, 0, false);
      RP_TYPES = new int[]{0, 1};
      RP_TYPE_STRING = new String[]{"Queues", "Topics"};
   }

   private class EntityState {
      private JMSModuleManagedEntity entity;
      private String name;
      private int state;
      private boolean doRemove;

      public int getState() {
         return this.state;
      }

      private EntityState(String paramName) {
         this.state = 0;
         this.doRemove = false;
         this.name = paramName;
      }

      private EntityState(JMSModuleManagedEntity paramEntity) {
         this.state = 0;
         this.doRemove = false;
         this.entity = paramEntity;
         this.name = this.entity.getEntityName();
      }

      private String stateToString(int paramState) {
         switch (paramState) {
            case 0:
               return "INITIALIZED";
            case 1:
               return "PREPARED";
            case 2:
               return "ACTIVE";
            case 3:
               return "FINISHED";
            case 4:
               return "DEAD";
            case 5:
               return "CHANGED";
            default:
               return "UNKNOWN STATE: " + paramState;
         }
      }

      private void invalidStateTransition(int fromState, int toState) {
         throw new AssertionError("ERROR: An invalid state transition was requested in module " + JMSModule.this.moduleName + " for entity " + this.name + ".  The transition requested was from " + this.stateToString(fromState) + " to " + this.stateToString(toState));
      }

      private void setState(int paramState) throws ModuleException {
         this.setState(paramState, (List)null, (DomainMBean)null, true);
      }

      private void setState(int paramState, List targets, DomainMBean proposedDomain, boolean isActivate) throws ModuleException {
         boolean forceTransition = false;

         try {
            label54:
            switch (paramState) {
               case 0:
                  switch (this.state) {
                     case 1:
                        forceTransition = true;
                        this.entity.unprepare();
                        break label54;
                     default:
                        this.invalidStateTransition(this.state, paramState);
                        break label54;
                  }
               case 1:
                  switch (this.state) {
                     case 0:
                        this.entity.prepare();
                        break label54;
                     case 2:
                        forceTransition = true;
                        this.entity.deactivate();
                        break label54;
                     default:
                        this.invalidStateTransition(this.state, paramState);
                        break label54;
                  }
               case 2:
                  switch (this.state) {
                     case 1:
                        this.entity.activate(JMSModule.this.wholeModule);
                        break label54;
                     case 5:
                        forceTransition = true;
                        if (isActivate) {
                           this.entity.activateChangeOfTargets();
                        } else {
                           this.entity.rollbackChangeOfTargets();
                        }
                        break label54;
                     default:
                        this.invalidStateTransition(this.state, paramState);
                        break label54;
                  }
               case 3:
                  switch (this.state) {
                     case 0:
                        forceTransition = true;
                        this.entity.destroy();
                        break label54;
                     default:
                        this.invalidStateTransition(this.state, paramState);
                        break label54;
                  }
               case 4:
                  switch (this.state) {
                     case 3:
                        forceTransition = true;
                        this.entity.remove();
                        break label54;
                     default:
                        this.invalidStateTransition(this.state, paramState);
                        break label54;
                  }
               case 5:
                  switch (this.state) {
                     case 2:
                        this.entity.prepareChangeOfTargets(targets, proposedDomain);
                     case 5:
                        break label54;
                     default:
                        this.invalidStateTransition(this.state, paramState);
                        break label54;
                  }
               default:
                  this.invalidStateTransition(this.state, paramState);
            }
         } catch (ModuleException var7) {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("ERROR: Entity " + this.entity.getEntityName() + " in module " + JMSModule.this.moduleName + "failed to go from state " + this.stateToString(this.state) + " to state " + this.stateToString(paramState) + " due to " + var7.getMessage());
               var7.printStackTrace();
            }

            if (forceTransition) {
               this.state = paramState;
            }

            throw var7;
         }

         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: Entity " + this.entity.getEntityName() + " in module " + JMSModule.this.moduleName + " changed from state " + this.stateToString(this.state) + " to state " + this.stateToString(paramState));
         }

         this.state = paramState;
      }

      private void takeDown() throws ModuleException {
         ModuleException gotAnError = null;
         if (this.state == 5) {
            try {
               this.setState(2, (List)null, (DomainMBean)null, false);
            } catch (ModuleException var3) {
               gotAnError = var3;
            }
         }

         if (this.state == 2) {
            try {
               this.setState(1);
            } catch (ModuleException var7) {
               if (gotAnError != null) {
                  gotAnError = var7;
               }
            }
         }

         if (this.state == 1) {
            try {
               this.setState(0);
            } catch (ModuleException var6) {
               if (gotAnError != null) {
                  gotAnError = var6;
               }
            }
         }

         if (this.state == 0) {
            try {
               this.setState(3);
            } catch (ModuleException var5) {
               if (gotAnError != null) {
                  gotAnError = var5;
               }
            }
         }

         if (this.doRemove && this.state == 3) {
            try {
               this.setState(4);
            } catch (ModuleException var4) {
               if (gotAnError != null) {
                  gotAnError = var4;
               }
            }
         }

         if (gotAnError != null) {
            throw gotAnError;
         }
      }

      private String getName() {
         return this.name;
      }

      private void setDoRemove(boolean paramDoRemove) {
         this.doRemove = paramDoRemove;
      }

      private boolean isDoRemove() {
         return this.doRemove;
      }

      private void restart() {
         try {
            this.entity.deactivate();
            this.entity.unprepare();
         } catch (Exception var3) {
            var3.printStackTrace();
         }

         try {
            this.entity.prepare();
            this.entity.activate(JMSModule.this.wholeModule);
         } catch (Exception var2) {
            var2.printStackTrace();
         }

      }

      public int hashCode() {
         return this.name.hashCode();
      }

      public boolean equals(Object obj) {
         if (obj != null && obj instanceof EntityState) {
            EntityState compareTo = (EntityState)obj;
            return this.name.equals(compareTo.name);
         } else {
            return false;
         }
      }

      // $FF: synthetic method
      EntityState(JMSModuleManagedEntity x1, Object x2) {
         this((JMSModuleManagedEntity)x1);
      }

      // $FF: synthetic method
      EntityState(String x1, Object x2) {
         this((String)x1);
      }
   }

   private static class Stuff {
      private JMSModuleManagedEntityProvider myProvider;
      private int myIndex;
      private int myType;
      private boolean myDefaultTargetingEnabled;

      private Stuff(JMSModuleManagedEntityProvider provider, int index, int type, boolean defaultTargetingEnabled) {
         this.myProvider = provider;
         this.myIndex = index;
         this.myType = type;
         this.myDefaultTargetingEnabled = defaultTargetingEnabled;
      }

      private JMSModuleManagedEntityProvider getProvider() {
         return this.myProvider;
      }

      private int getIndex() {
         return this.myIndex;
      }

      private int getType() {
         return this.myType;
      }

      private boolean isDefaultTargetingEnabled() {
         return this.myDefaultTargetingEnabled;
      }

      // $FF: synthetic method
      Stuff(JMSModuleManagedEntityProvider x0, int x1, int x2, boolean x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   private class JMSModuleListener implements BeanUpdateListener {
      private UpdateInformation info;

      private JMSModuleListener() {
      }

      private void setInfo(UpdateInformation paramInfo) {
         this.info = paramInfo;
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         boolean success = false;

         try {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("INFO: Got module listener prepareUpdate in " + JMSModule.this.moduleName);
            }

            if (this.info != null) {
               BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
               if (updates == null) {
                  if (JMSDebug.JMSModule.isDebugEnabled()) {
                     JMSDebug.JMSModule.debug("INFO: module listener prepareUpdate exits with no events in " + JMSModule.this.moduleName);
                  }

                  return;
               }

               JMSBean proposedBean = (JMSBean)event.getProposedBean();

               for(int lcv = 0; lcv < updates.length; ++lcv) {
                  switch (updates[lcv].getUpdateType()) {
                     case 2:
                        JMSModule.this.addEntity(updates[lcv], proposedBean, this.info);
                        break;
                     case 3:
                        JMSModule.this.removeEntity((NamedEntityBean)updates[lcv].getRemovedObject(), updates[lcv].getPropertyName(), this.info, true);
                  }
               }

               if (JMSDebug.JMSModule.isDebugEnabled()) {
                  JMSDebug.JMSModule.debug("INFO: module listener prepareUpdate exits normally in " + JMSModule.this.moduleName);
               }

               success = true;
               return;
            }
         } finally {
            if (!success) {
               this.info = null;
            }

         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
         try {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("INFO: Got module listener rollback event: " + event + " in " + JMSModule.this.moduleName);
            }
         } finally {
            this.info = null;
         }

      }

      public void activateUpdate(BeanUpdateEvent event) {
         this.info = null;
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("INFO: Got module listener activateUpdate, nothing to do in" + JMSModule.this.moduleName);
         }

      }

      // $FF: synthetic method
      JMSModuleListener(Object x1) {
         this();
      }
   }
}
