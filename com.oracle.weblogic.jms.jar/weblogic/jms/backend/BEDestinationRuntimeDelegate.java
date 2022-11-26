package weblogic.jms.backend;

import java.util.LinkedList;
import java.util.List;
import javax.jms.JMSException;
import javax.naming.Context;
import weblogic.application.ModuleException;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.DestinationKeyBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSService;
import weblogic.jms.backend.udd.SyntheticDestinationBean;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.EntityName;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.ModuleName;
import weblogic.jms.deployer.BEDeployer;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.utils.GenericBeanListener;

public abstract class BEDestinationRuntimeDelegate implements JMSModuleManagedEntity {
   protected EntityName entityName;
   private Context applicationContext;
   private JMSBean wholeModule;
   protected DestinationBean specificBean;
   protected boolean temporary;
   private ModuleName auxiliaryModuleName;
   private BEQuota beQuota;
   private GenericBeanListener destinationListener;
   private GenericBeanListener localDestinationListener;
   private GenericBeanListener localDeliveryFailureListener;
   private GenericBeanListener thresholdListener;
   private GenericBeanListener deliveryOverridesListener;
   private GenericBeanListener deliveryFailureListener;
   private GenericBeanListener messageLoggingListener;
   private GenericBeanListener subscriptionListener;
   protected Object myLock = new Object();
   protected BackEnd backEnd;
   private BEDestinationImpl managedDestination;
   private BEDestinationRuntimeMBeanImpl runtimeMBean;
   private String savedConfigName = null;

   protected BEDestinationRuntimeDelegate(EntityName paramEntityName, Context paramApplicationContext, BackEnd paramBackEnd, JMSBean paramWholeModule, DestinationBean paramSpecificBean, boolean paramTemporary, ModuleName paramAuxiliaryModuleName) {
      this.entityName = paramEntityName;
      this.applicationContext = paramApplicationContext;
      this.backEnd = paramBackEnd;
      this.wholeModule = paramWholeModule;
      this.specificBean = paramSpecificBean;
      this.temporary = paramTemporary;
      this.auxiliaryModuleName = paramAuxiliaryModuleName;
   }

   BEDestinationRuntimeMBeanImpl getRuntimeMBean() {
      return this.runtimeMBean;
   }

   protected void initialize(int duration) throws ModuleException {
      BEDestinationImpl mymanagedDestination = this.managedDestination;
      if (mymanagedDestination == null) {
         throw new IllegalStateException("ManagedDestination had not been set:" + this.entityName);
      } else {
         BackEnd mybackEnd = this.checkBackEndWithModuleException();
         mymanagedDestination.setDuration(duration);

         try {
            this.initializeListeners();
         } catch (ManagementException var12) {
            throw new ModuleException("Could not initialize the destination " + this.entityName.toString(), var12);
         }

         try {
            mymanagedDestination.valJNDIName(this.specificBean.getJNDIName());
            mymanagedDestination.valLocalJNDIName(this.specificBean.getLocalJNDIName());
         } catch (BeanUpdateRejectedException var13) {
            if (!(this.specificBean instanceof SyntheticDestinationBean) || !((SyntheticDestinationBean)this.specificBean).isJMSResourceDefinition()) {
               throw new ModuleException(var13.getMessage(), var13);
            }
         }

         String[] keyStrings = this.specificBean.getDestinationKeys();
         List destinationKeysList = new LinkedList();
         boolean jmsMsgIdFound = false;

         for(int lcv = 0; lcv < keyStrings.length; ++lcv) {
            DestinationKeyBean destinationKeyBean = this.wholeModule.lookupDestinationKey(keyStrings[lcv]);
            BEDestinationKey destinationKey = new BEDestinationKey(mymanagedDestination, destinationKeyBean);
            destinationKeysList.add(destinationKey);
            if (destinationKeyBean.getProperty().equals("JMSMessageID")) {
               jmsMsgIdFound = true;
            }
         }

         if (!jmsMsgIdFound) {
            destinationKeysList.add(new BEDestinationKey(mymanagedDestination));
         }

         mymanagedDestination.setDestinationKeysList(destinationKeysList);
         DestinationImpl destinationImpl = new DestinationImpl(mymanagedDestination.getDestinationTypeIndicator(), mybackEnd.getName(), mybackEnd.getConfigName(), mybackEnd.getPersistentStore() != null ? mybackEnd.getPersistentStore().getName() : null, mymanagedDestination.getName(), this.entityName.getApplicationName(), this.entityName.getEARModuleName(), mybackEnd.getJMSServerId(), mymanagedDestination.getJMSID(), mymanagedDestination.getCreationTime(), mymanagedDestination.getSAFExportPolicy());
         mymanagedDestination.setDestinationImpl(destinationImpl);

         try {
            this.setQuota(this.specificBean.getQuota(), false);
         } catch (BeanUpdateFailedException var11) {
            throw new ModuleException(var11.getMessage(), var11);
         }

         this.setErrorDestination(this.specificBean.getDeliveryFailureParams().getErrorDestination());

         try {
            mymanagedDestination.open();
         } catch (JMSException var10) {
            throw new ModuleException(var10);
         }
      }
   }

   void setManagedDestination(BEDestinationImpl md) throws ManagementException {
      BackEnd mybackEnd;
      synchronized(this.myLock) {
         mybackEnd = this.checkBackEndWithManagementException();
         this.managedDestination = md;
      }

      this.runtimeMBean = new BEDestinationRuntimeMBeanImpl(this.entityName.toString(), mybackEnd.getRuntimeMBean(), false, md);
      md.setRuntimeMBean(this.runtimeMBean);
      md.setModuleName(this.entityName.getFullyQualifiedModuleName());
   }

   public BEDestinationImpl getManagedDestination() {
      return this.managedDestination;
   }

   public void prepare() throws ModuleException {
      BackEnd mybackEnd = this.checkBackEndWithModuleException();
      if (!mybackEnd.isStart()) {
         BEDeployer bed = JMSService.getJMSServiceWithModuleException().getBEDeployer();
         mybackEnd = this.backEnd = bed.findBackEnd(mybackEnd.getName());
      }

      ((JMSServerRuntimeMBeanImpl)mybackEnd.getRuntimeMBean()).removeBEDestinationRuntimeDelegate(this);
      this.initialize(this.temporary ? 0 : 1);
      BEDestinationImpl mymanagedDestination = this.managedDestination;
      mybackEnd = this.checkBackEndWithModuleException();
      synchronized(mymanagedDestination) {
         mymanagedDestination.setStateValue(1);
      }

      try {
         mybackEnd.addDestination(mymanagedDestination);
      } catch (JMSException var5) {
         throw new ModuleException("ERROR: Unable to add destination " + this.entityName + " to the back end " + mybackEnd.getName(), var5);
      }

      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("BEDestinationRuntimeDelegate@" + this.hashCode() + ": Destination " + this.entityName + " successfully prepared");
      }

   }

   public void activate(JMSBean paramWholeModule) throws ModuleException {
      BEDestinationImpl mymanagedDestination = this.managedDestination;
      if (mymanagedDestination == null) {
         throw new IllegalStateException("ManagedDestination had not been set:" + this.entityName);
      } else {
         BackEnd mybackEnd = this.checkBackEndWithModuleException();
         if (paramWholeModule != null) {
            this.wholeModule = paramWholeModule;
            if (this.specificBean instanceof QueueBean) {
               this.specificBean = this.wholeModule.lookupQueue(this.getEntityName());
            } else {
               this.specificBean = this.wholeModule.lookupTopic(this.getEntityName());
            }

            this.closeListeners();
         }

         this.openListeners();
         if (this.beQuota != null) {
            QuotaBean quotaBean = this.specificBean.getQuota();
            this.beQuota.setQuotaBean(quotaBean);
         }

         mymanagedDestination.setApplicationJNDIName(this.constructApplicationJNDIName());
         mymanagedDestination.setApplicationContext(this.applicationContext);

         try {
            mymanagedDestination.start();
         } catch (JMSException var5) {
            throw new ModuleException("ERROR: Could not activate " + mymanagedDestination.getName(), var5);
         }

         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("BEDestinationRuntimeDelegate@" + this.hashCode() + ": Destination " + this.entityName + " successfully activated");
         }

      }
   }

   public void deactivate() throws ModuleException {
      BEDestinationImpl mymanagedDestination = this.managedDestination;
      if (mymanagedDestination == null) {
         throw new IllegalStateException("ManagedDestination had not been set:" + this.entityName);
      } else {
         BackEnd mybackEnd = this.checkBackEndWithModuleException();
         mymanagedDestination.unAdvertise();
         if (this.beQuota != null) {
            if (this.beQuota.close()) {
               mybackEnd.removeBEQuota(this.beQuota.getName());
            }

            this.beQuota = null;
         }

         this.closeListeners();
         BEDestinationSecurityImpl.removeJMSResource(((BEDestinationSecurityImpl)mymanagedDestination.getJMSDestinationSecurity()).getJMSResource(), this instanceof BEQueueRuntimeDelegate, false);
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("BEDestinationRuntimeDelegate@" + this.hashCode() + ": Destination " + this.entityName + " successfully deactivated");
         }

      }
   }

   public void unprepare() throws ModuleException {
      BEDestinationImpl mymanagedDestination = this.managedDestination;
      if (mymanagedDestination == null) {
         throw new IllegalStateException("ManagedDestination had not been set:" + this.entityName);
      } else {
         BackEnd mybackEnd = this.checkBackEndWithModuleException();
         mybackEnd.removeDestination(mymanagedDestination);
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("BEDestinationRuntimeDelegate@" + this.hashCode() + ": Destination " + this.entityName + " successfully unprepared");
         }

         ((JMSServerRuntimeMBeanImpl)mybackEnd.getRuntimeMBean()).addBEDestinationRuntimeDelegate(this);
      }
   }

   public void remove() throws ModuleException {
      BEDestinationImpl mymanagedDestination = this.managedDestination;
      if (mymanagedDestination != null) {
         mymanagedDestination.adminDeletion();
      }

      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("BEDestinationRuntimeDelegate@" + this.hashCode() + ": Destination " + this.entityName + " successfully removed");
      }

      BackEnd mybackEnd = this.backEnd;
      if (mybackEnd != null) {
         JMSServerRuntimeMBeanImpl mb = (JMSServerRuntimeMBeanImpl)mybackEnd.getRuntimeMBean();
         if (mb != null) {
            mb.removeBEDestinationRuntimeDelegate(this);
         }
      }

   }

   public void destroy() throws ModuleException {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("BEDestinationRuntimeDelegate@" + this.hashCode() + ": Destination " + this.entityName + " successfully destroyed");
      }

   }

   void backendDestroyed() {
      synchronized(this.myLock) {
         if (this.backEnd != null) {
            this.savedConfigName = this.backEnd.getConfigName();
            this.backEnd = null;
            this.managedDestination = null;
            if (this.runtimeMBean != null) {
               this.runtimeMBean.backendDestroyed();
            }

         }
      }
   }

   public String getEntityName() {
      return this.specificBean.getName();
   }

   public String getJMSServerConfigName() {
      BackEnd mybackEnd = this.backEnd;
      return mybackEnd == null ? this.savedConfigName : mybackEnd.getConfigName();
   }

   public void prepareChangeOfTargets(List targets, DomainMBean proposedDomain) throws ModuleException {
      BackEnd mybackEnd = this.checkBackEndWithModuleException();
      TargetMBean proposedTarget = (TargetMBean)targets.get(0);
      throw new ModuleException(JMSExceptionLogger.logInvalidTargetChangeLoggable(this.entityName.toString(), mybackEnd.getName(), proposedTarget.getName()).getMessage());
   }

   public void activateChangeOfTargets() {
   }

   public void rollbackChangeOfTargets() {
   }

   private void initializeListeners() throws ManagementException {
      BEDestinationImpl mymanagedDestination = this.managedDestination;
      if (mymanagedDestination == null) {
         throw new IllegalStateException("ManagedDestination had not been set:" + this.entityName);
      } else {
         DescriptorBean descriptor = (DescriptorBean)this.specificBean;
         this.destinationListener = new GenericBeanListener(descriptor, mymanagedDestination, JMSBeanHelper.destinationBeanSignatures, false);
         this.destinationListener.initialize();
         descriptor = (DescriptorBean)this.specificBean.getThresholds();
         this.thresholdListener = new GenericBeanListener(descriptor, mymanagedDestination, JMSBeanHelper.thresholdBeanSignatures, false);
         this.thresholdListener.initialize();
         descriptor = (DescriptorBean)this.specificBean.getDeliveryParamsOverrides();
         this.deliveryOverridesListener = new GenericBeanListener(descriptor, mymanagedDestination, JMSBeanHelper.deliveryOverridesSignatures, false);
         this.deliveryOverridesListener.initialize();
         descriptor = (DescriptorBean)this.specificBean.getDeliveryFailureParams();
         this.deliveryFailureListener = new GenericBeanListener(descriptor, mymanagedDestination, JMSBeanHelper.deliveryFailureSignatures, false);
         this.deliveryFailureListener.initialize();
         descriptor = (DescriptorBean)this.specificBean.getMessageLoggingParams();
         this.messageLoggingListener = new GenericBeanListener(descriptor, mymanagedDestination, JMSBeanHelper.messageLoggingSignatures, false);
         this.messageLoggingListener.initialize();
         if (this.specificBean instanceof TopicBean) {
            TopicBean topicBean = (TopicBean)this.specificBean;
            descriptor = (DescriptorBean)topicBean.getTopicSubscriptionParams();
            this.subscriptionListener = new GenericBeanListener(descriptor, (BETopicImpl)mymanagedDestination, JMSBeanHelper.subscriptionSignatures, false);
            this.subscriptionListener.initialize();
         }

      }
   }

   private void openListeners() {
      BEDestinationImpl mymanagedDestination = this.managedDestination;
      if (mymanagedDestination == null) {
         throw new IllegalStateException("ManagedDestination had not been set:" + this.entityName);
      } else {
         DescriptorBean descriptor;
         if (this.destinationListener != null) {
            this.destinationListener.open();
         } else {
            descriptor = (DescriptorBean)this.specificBean;
            this.destinationListener = new GenericBeanListener(descriptor, mymanagedDestination, JMSBeanHelper.destinationBeanSignatures);
         }

         if (this.localDestinationListener != null) {
            this.localDestinationListener.open();
         } else {
            descriptor = (DescriptorBean)this.specificBean;
            this.localDestinationListener = new GenericBeanListener(descriptor, this, JMSBeanHelper.localDestinationBeanSignatures);
         }

         if (this.localDeliveryFailureListener != null) {
            this.localDestinationListener.open();
         } else {
            descriptor = (DescriptorBean)this.specificBean.getDeliveryFailureParams();
            this.localDeliveryFailureListener = new GenericBeanListener(descriptor, this, JMSBeanHelper.localDeliveryFailureSignatures);
         }

         if (this.thresholdListener != null) {
            this.thresholdListener.open();
         } else {
            descriptor = (DescriptorBean)this.specificBean.getThresholds();
            this.thresholdListener = new GenericBeanListener(descriptor, mymanagedDestination, JMSBeanHelper.thresholdBeanSignatures);
         }

         if (this.deliveryOverridesListener != null) {
            this.deliveryOverridesListener.open();
         } else {
            descriptor = (DescriptorBean)this.specificBean.getDeliveryParamsOverrides();
            this.deliveryOverridesListener = new GenericBeanListener(descriptor, mymanagedDestination, JMSBeanHelper.deliveryOverridesSignatures);
         }

         if (this.deliveryFailureListener != null) {
            this.deliveryFailureListener.open();
         } else {
            descriptor = (DescriptorBean)this.specificBean.getDeliveryFailureParams();
            this.deliveryFailureListener = new GenericBeanListener(descriptor, mymanagedDestination, JMSBeanHelper.deliveryFailureSignatures);
         }

         if (this.messageLoggingListener != null) {
            this.messageLoggingListener.open();
         } else {
            descriptor = (DescriptorBean)this.specificBean.getMessageLoggingParams();
            this.messageLoggingListener = new GenericBeanListener(descriptor, mymanagedDestination, JMSBeanHelper.messageLoggingSignatures);
         }

         if (this.specificBean instanceof TopicBean) {
            if (this.subscriptionListener != null) {
               this.subscriptionListener.open();
            } else {
               TopicBean topicBean = (TopicBean)this.specificBean;
               descriptor = (DescriptorBean)topicBean.getTopicSubscriptionParams();
               this.subscriptionListener = new GenericBeanListener(descriptor, (BETopicImpl)mymanagedDestination, JMSBeanHelper.subscriptionSignatures);
            }
         }

      }
   }

   private void closeListeners() {
      if (this.messageLoggingListener != null) {
         this.messageLoggingListener.close();
         this.messageLoggingListener = null;
      }

      if (this.deliveryFailureListener != null) {
         this.deliveryFailureListener.close();
         this.deliveryFailureListener = null;
      }

      if (this.deliveryOverridesListener != null) {
         this.deliveryOverridesListener.close();
         this.deliveryOverridesListener = null;
      }

      if (this.thresholdListener != null) {
         this.thresholdListener.close();
         this.thresholdListener = null;
      }

      if (this.localDeliveryFailureListener != null) {
         this.localDeliveryFailureListener.close();
         this.localDeliveryFailureListener = null;
      }

      if (this.localDestinationListener != null) {
         this.localDestinationListener.close();
         this.localDestinationListener = null;
      }

      if (this.destinationListener != null) {
         this.destinationListener.close();
         this.destinationListener = null;
      }

      if (this.subscriptionListener != null) {
         this.subscriptionListener.close();
         this.subscriptionListener = null;
      }

   }

   public void setErrorDestination(DestinationBean destination) {
      BEDestinationImpl mymanagedDestination = this.managedDestination;
      if (mymanagedDestination == null) {
         throw new IllegalStateException("ManagedDestination had not been set:" + this.entityName);
      } else {
         EntityName errorDestinationName = destination == null ? null : new EntityName(this.auxiliaryModuleName, destination.getName());
         mymanagedDestination.setErrorDestination(errorDestinationName);
      }
   }

   public void setQuota(QuotaBean quota) throws BeanUpdateFailedException {
      this.setQuota(quota, true);
   }

   private void setQuota(QuotaBean quota, boolean doRegister) throws BeanUpdateFailedException {
      BEDestinationImpl mymanagedDestination = this.managedDestination;
      if (mymanagedDestination == null) {
         throw new IllegalStateException("ManagedDestination had not been set:" + this.entityName);
      } else {
         BackEnd mybackEnd = this.checkBackEndWithBeanUpdateFailedException();
         if (this.beQuota != null) {
            if (this.beQuota.close()) {
               mybackEnd.removeBEQuota(this.beQuota.getName());
            }

            this.beQuota = null;
         }

         if (quota == null) {
            mymanagedDestination.setQuota(mybackEnd.getQuota());
         } else {
            String fullQuotaName;
            if (quota.isShared()) {
               fullQuotaName = JMSBeanHelper.getDecoratedName(this.auxiliaryModuleName.getFullyQualifiedModuleName(), quota.getName());
               this.beQuota = mybackEnd.findBEQuota(fullQuotaName);
               if (this.beQuota == null) {
                  this.beQuota = mybackEnd.createBEQuota(fullQuotaName, quota);
               }
            } else {
               fullQuotaName = JMSBeanHelper.getDecoratedName(this.entityName.toString(), quota.getName());
               this.beQuota = mybackEnd.createBEQuota(fullQuotaName, quota);
            }

            mymanagedDestination.setQuota(this.beQuota.getQuota());
            if (doRegister) {
               this.beQuota.setQuotaBean(quota);
            }
         }

      }
   }

   private String constructApplicationJNDIName() {
      return this.entityName.getEARModuleName() != null && this.entityName.getEARModuleName().length() > 0 ? this.entityName.getEARModuleName() + "#" + this.specificBean.getName() : null;
   }

   protected BackEnd checkBackEndWithModuleException() throws ModuleException {
      synchronized(this.myLock) {
         if (this.backEnd == null) {
            throw new ModuleException("BackEnd destroyed for destination " + this.entityName);
         } else {
            return this.backEnd;
         }
      }
   }

   private BackEnd checkBackEndWithManagementException() throws ManagementException {
      synchronized(this.myLock) {
         if (this.backEnd == null) {
            throw new ManagementException("BackEnd destroyed for destination " + this.entityName);
         } else {
            return this.backEnd;
         }
      }
   }

   private BackEnd checkBackEndWithBeanUpdateFailedException() throws BeanUpdateFailedException {
      synchronized(this.myLock) {
         if (this.backEnd == null) {
            throw new BeanUpdateFailedException("BackEnd destroyed for destination " + this.entityName);
         } else {
            return this.backEnd;
         }
      }
   }
}
