package weblogic.jms.saf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.application.ApplicationContext;
import weblogic.application.ModuleException;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.SAFDestinationBean;
import weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBean;
import weblogic.j2ee.descriptor.wl.SAFQueueBean;
import weblogic.j2ee.descriptor.wl.SAFRemoteContextBean;
import weblogic.j2ee.descriptor.wl.SAFTopicBean;
import weblogic.j2ee.descriptor.wl.constants.JMSConstants;
import weblogic.jms.JMSService;
import weblogic.jms.common.EntityName;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSTargetsListener;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.utils.GenericBeanListener;
import weblogic.messaging.saf.SAFLogger;

public class ImportedDestinationGroup implements JMSModuleManagedEntity, JMSTargetsListener {
   private final String name;
   private SAFImportedDestinationsBean idGroupBean;
   private GenericBeanListener groupBeanListener;
   private GenericBeanListener messageLoggingParamsBeanListener;
   private static HashMap groupBeanAddRemoveSignatures = new HashMap();
   private static HashMap groupBeanSignatures = new HashMap();
   private static HashMap messageLoggingSignatures;
   private final Map importedDestinations = new HashMap();
   private final ApplicationContext appCtx;
   private List localTargets;
   private List preparedTargets;
   private EntityName entityName;
   private boolean defaultTargetingEnabled;
   private final String partitionName;
   private final IDEntityHelper idEntityHelper;
   private String exactlyOnceLoadBalancingPolicy;
   private boolean exactlyOnceLBModulePolicyInvalidLogged = false;
   private boolean exactlyOnceLBServerPolicyInvalidLogged = false;
   private boolean exactlyOnceLBPolicyValidLogged = false;
   private String messageLoggingFormat;
   private boolean messageLoggingEnabled = false;

   ImportedDestinationGroup(String name, ApplicationContext appCtx, EntityName entityName, SAFImportedDestinationsBean idGroupBean, List localTargets, DomainMBean domain) throws ModuleException {
      this.name = name;
      this.idGroupBean = idGroupBean;
      this.entityName = entityName;
      this.appCtx = appCtx;
      this.localTargets = localTargets;
      this.defaultTargetingEnabled = idGroupBean.isDefaultTargetingEnabled();
      SAFService safService = SAFService.getSAFService();
      this.partitionName = safService.getPartitionName();
      this.idEntityHelper = safService.getIDEntityHelper();
      String appName = entityName.getApplicationName();
      String earModuleName = entityName.getEARModuleName();
      String moduleName = earModuleName == null ? appName : JMSBeanHelper.getDecoratedName(appName, earModuleName);
      String sysProp = JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY + "." + moduleName;
      String policyFromProperty = null;

      try {
         policyFromProperty = System.getProperty(sysProp);
      } catch (Exception var17) {
      }

      if (policyFromProperty == null || !this.validPolicySetting(policyFromProperty)) {
         if (policyFromProperty != null && !this.exactlyOnceLBModulePolicyInvalidLogged) {
            SAFLogger.logInvalidExactlyOnceLBPolicyProperty(name, moduleName, sysProp, policyFromProperty);
            this.exactlyOnceLBModulePolicyInvalidLogged = true;
         }

         sysProp = JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY;

         try {
            policyFromProperty = System.getProperty(sysProp);
         } catch (Exception var16) {
         }
      }

      if (policyFromProperty != null) {
         if (this.validPolicySetting(policyFromProperty)) {
            if (!this.exactlyOnceLBPolicyValidLogged) {
               SAFLogger.logEffectiveExactlyOnceLBPolicyProperty(name, moduleName, sysProp, policyFromProperty);
               this.exactlyOnceLBPolicyValidLogged = true;
            }
         } else {
            if (!this.exactlyOnceLBServerPolicyInvalidLogged) {
               SAFLogger.logInvalidExactlyOnceLBPolicyProperty(name, moduleName, sysProp, policyFromProperty);
               this.exactlyOnceLBServerPolicyInvalidLogged = true;
            }

            policyFromProperty = null;
         }
      }

      this.exactlyOnceLoadBalancingPolicy = policyFromProperty;
      if (this.exactlyOnceLoadBalancingPolicy == null) {
         this.exactlyOnceLoadBalancingPolicy = idGroupBean.getExactlyOnceLoadBalancingPolicy();
         if (this.exactlyOnceLoadBalancingPolicy == null) {
            this.exactlyOnceLoadBalancingPolicy = JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_MEMBER;
         }
      }

      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDG " + name + " effective exactlyonce LB policy = " + this.exactlyOnceLoadBalancingPolicy);
      }

      this.messageLoggingEnabled = idGroupBean.getMessageLoggingParams().isMessageLoggingEnabled();
      this.messageLoggingFormat = idGroupBean.getMessageLoggingParams().getMessageLoggingFormat();
      SAFQueueBean[] safQueues = idGroupBean.getSAFQueues();

      for(int i = 0; i < safQueues.length; ++i) {
         this.importedDestinations.put(safQueues[i].getName(), new IDBeanHandler(this, appCtx, entityName, safQueues[i], localTargets, domain));
         this.idEntityHelper.addIDEntity((IDBeanHandler)this.importedDestinations.get(safQueues[i].getName()));
      }

      SAFTopicBean[] safTopics = idGroupBean.getSAFTopics();

      for(int i = 0; i < safTopics.length; ++i) {
         this.importedDestinations.put(safTopics[i].getName(), new IDBeanHandler(this, appCtx, entityName, safTopics[i], localTargets, domain));
         this.idEntityHelper.addIDEntity((IDBeanHandler)this.importedDestinations.get(safTopics[i].getName()));
      }

   }

   private boolean validPolicySetting(String policy) {
      return JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_MEMBER.equals(policy) || JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_JVM.equals(policy);
   }

   String getName() {
      return this.name;
   }

   public void prepare() throws ModuleException {
      Iterator i = this.importedDestinations.values().iterator();

      while(i.hasNext()) {
         IDBeanHandler id = (IDBeanHandler)i.next();
         id.prepare();
      }

   }

   SAFRemoteContextBean getRemoteContextBean() {
      return this.idGroupBean.getSAFRemoteContext();
   }

   String getRemoteSAFContextFullyQualifiedName() {
      SAFRemoteContextBean safRemoteContextBean = this.getRemoteContextBean();
      return safRemoteContextBean != null ? JMSBeanHelper.getDecoratedName(this.entityName.getFullyQualifiedModuleName(), safRemoteContextBean.getName()) : JMSBeanHelper.getDecoratedName(this.entityName.getFullyQualifiedModuleName(), "#LOCAL SERVER CONTEXT#");
   }

   String getRemoteSAFContextNameWithoutPartition() {
      SAFRemoteContextBean safRemoteContextBean = this.getRemoteContextBean();
      String appName = this.entityName.getApplicationName();
      String partitionName = JMSService.getSafePartitionNameFromThread();
      if (partitionName != null && !partitionName.equals("DOMAIN") && appName.endsWith("$" + partitionName)) {
         appName = appName.substring(0, appName.lastIndexOf("$" + partitionName));
      }

      String earModuleName = this.entityName.getEARModuleName();
      String moduleName = earModuleName == null ? appName : JMSBeanHelper.getDecoratedName(appName, earModuleName);
      return safRemoteContextBean != null ? JMSBeanHelper.getDecoratedName(moduleName, safRemoteContextBean.getName()) : JMSBeanHelper.getDecoratedName(moduleName, "#LOCAL SERVER CONTEXT#");
   }

   SAFImportedDestinationsBean getBean() {
      return this.idGroupBean;
   }

   public void activate(JMSBean paramWholeModule) throws ModuleException {
      this.idGroupBean = paramWholeModule.lookupSAFImportedDestinations(this.getEntityName());
      SAFService safService = SAFService.getSAFServiceWithModuleException(this.partitionName);
      this.unregisterBeanUpdateListeners(safService);
      this.registerBeanUpdateListeners(safService);
      SAFQueueBean[] safQueues = this.idGroupBean.getSAFQueues();

      for(int i = 0; i < safQueues.length; ++i) {
         IDBeanHandler id = (IDBeanHandler)this.importedDestinations.get(safQueues[i].getName());
         id.activate(safQueues[i]);
      }

      SAFTopicBean[] safTopics = this.idGroupBean.getSAFTopics();

      for(int i = 0; i < safTopics.length; ++i) {
         IDBeanHandler id = (IDBeanHandler)this.importedDestinations.get(safTopics[i].getName());
         id.activate(safTopics[i]);
      }

   }

   public void deactivate() throws ModuleException {
      SAFService safService = SAFService.getSAFServiceWithModuleException(this.partitionName);
      this.unregisterBeanUpdateListeners(safService);
      Iterator i = this.importedDestinations.values().iterator();

      while(i.hasNext()) {
         IDBeanHandler id = (IDBeanHandler)i.next();
         id.deactivate();
      }

   }

   public void unprepare() throws ModuleException {
      Iterator i = this.importedDestinations.values().iterator();

      while(i.hasNext()) {
         IDBeanHandler id = (IDBeanHandler)i.next();
         id.unprepare();
      }

      this.importedDestinations.clear();
      this.idEntityHelper.removeAllIDEntities();
   }

   public void destroy() throws ModuleException {
      Iterator i = this.importedDestinations.values().iterator();

      while(i.hasNext()) {
         IDBeanHandler id = (IDBeanHandler)i.next();
         id.destroy();
      }

   }

   public void remove() throws ModuleException {
      Iterator i = this.importedDestinations.values().iterator();

      while(i.hasNext()) {
         IDBeanHandler id = (IDBeanHandler)i.next();
         id.remove();
      }

   }

   public String getEntityName() {
      return this.idGroupBean.getName();
   }

   public void prepareChangeOfTargets(List targets, DomainMBean proposedDomain) throws ModuleException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDG.prepareChangeOfTargets: targets=" + targets);
      }

      this.preparedTargets = targets;
      Iterator i = this.importedDestinations.values().iterator();

      while(i.hasNext()) {
         IDBeanHandler id = (IDBeanHandler)i.next();
         id.prepareChangeOfTargets(targets, proposedDomain);
      }

   }

   public void activateChangeOfTargets() throws ModuleException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDG.activateChangeOfTargets ");
      }

      this.localTargets = this.preparedTargets;
      Iterator i = this.importedDestinations.values().iterator();

      while(i.hasNext()) {
         IDBeanHandler id = (IDBeanHandler)i.next();
         id.activateChangeOfTargets();
      }

   }

   public void rollbackChangeOfTargets() {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDG.rollbackChangeOfTargets ");
      }

      Iterator i = this.importedDestinations.values().iterator();

      while(i.hasNext()) {
         IDBeanHandler id = (IDBeanHandler)i.next();
         id.rollbackChangeOfTargets();
      }

   }

   public void prepareUpdate(DomainMBean domain, TargetMBean targetMBean, int action, boolean migrationInProgress) throws BeanUpdateRejectedException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDG.prepareUpdate: target = " + targetMBean + " action = " + action);
      }

      Iterator i = this.importedDestinations.values().iterator();

      while(i.hasNext()) {
         IDBeanHandler id = (IDBeanHandler)i.next();
         id.prepareUpdate(domain, action);
      }

   }

   public void rollbackUpdate() {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDG.rollbackUpdate ");
      }

      Iterator i = this.importedDestinations.values().iterator();

      while(i.hasNext()) {
         IDBeanHandler id = (IDBeanHandler)i.next();
         id.rollbackUpdate();
      }

   }

   public void activateUpdate() {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDG.activateUpdate ");
      }

      Iterator i = this.importedDestinations.values().iterator();

      while(i.hasNext()) {
         IDBeanHandler id = (IDBeanHandler)i.next();
         id.activateUpdate();
      }

   }

   private void startAddDest(SAFDestinationBean toAdd) throws BeanUpdateRejectedException {
      if (toAdd != null) {
         try {
            IDBeanHandler idBeanHandler = new IDBeanHandler(this, this.appCtx, this.entityName, toAdd, this.localTargets, (DomainMBean)null);
            idBeanHandler.prepare();
            this.importedDestinations.put(toAdd.getName(), idBeanHandler);
            this.idEntityHelper.addIDEntity(idBeanHandler);
         } catch (ModuleException var3) {
            throw new BeanUpdateRejectedException("Adding destination", var3);
         }
      }
   }

   private void finishAddDest(SAFDestinationBean toAdd, boolean isActivate) throws BeanUpdateRejectedException {
      IDBeanHandler id;
      if (!isActivate) {
         id = (IDBeanHandler)this.importedDestinations.remove(toAdd.getName());
         this.idEntityHelper.addIDEntity(id);
      } else {
         id = (IDBeanHandler)this.importedDestinations.get(toAdd.getName());

         try {
            id.activate(toAdd);
         } catch (ModuleException var5) {
            throw new BeanUpdateRejectedException("Adding destination", var5);
         }
      }

   }

   public void startAddSAFQueues(SAFQueueBean toAdd) throws BeanUpdateRejectedException {
      this.startAddDest(toAdd);
   }

   public void finishAddSAFQueues(SAFQueueBean toAdd, boolean isActivate) throws BeanUpdateRejectedException {
      this.finishAddDest(toAdd, isActivate);
   }

   public void startAddSAFTopics(SAFTopicBean toAdd) throws BeanUpdateRejectedException {
      this.startAddDest(toAdd);
   }

   public void finishAddSAFTopics(SAFTopicBean toAdd, boolean isActivate) throws BeanUpdateRejectedException {
      this.finishAddDest(toAdd, isActivate);
   }

   public void startRemoveDest(SAFDestinationBean toRemove) {
   }

   public void finishRemoveDest(SAFDestinationBean toRemove, boolean isActivate) {
      if (isActivate) {
         IDBeanHandler id = (IDBeanHandler)this.importedDestinations.remove(toRemove.getName());
         this.idEntityHelper.removeIDEntity(id);

         try {
            id.deactivate();
            id.unprepare();
         } catch (ModuleException var5) {
         }

      }
   }

   public void startRemoveSAFQueues(SAFQueueBean toRemove) {
      this.startRemoveDest(toRemove);
   }

   public void finishRemoveSAFQueues(SAFQueueBean toRemove, boolean isActivate) {
      this.finishRemoveDest(toRemove, isActivate);
   }

   public void startRemoveSAFTopics(SAFTopicBean toRemove) {
      this.startRemoveDest(toRemove);
   }

   public void finishRemoveSAFTopics(SAFTopicBean toRemove, boolean isActivate) {
      this.finishRemoveDest(toRemove, isActivate);
   }

   private void registerBeanUpdateListeners(SAFService safService) {
      safService.getDeployer().addSAFAgentListener(this);
      this.groupBeanListener = new GenericBeanListener((DescriptorBean)this.idGroupBean, this, groupBeanSignatures, groupBeanAddRemoveSignatures);
      this.messageLoggingParamsBeanListener = new GenericBeanListener((DescriptorBean)this.idGroupBean.getMessageLoggingParams(), this, messageLoggingSignatures, (Map)null);
   }

   private void unregisterBeanUpdateListeners(SAFService safService) {
      safService.getDeployer().removeSAFAgentListener(this);
      if (this.groupBeanListener != null) {
         this.groupBeanListener.close();
         this.groupBeanListener = null;
      }

      if (this.messageLoggingParamsBeanListener != null) {
         this.messageLoggingParamsBeanListener.close();
         this.messageLoggingParamsBeanListener = null;
      }

   }

   public void setJNDIPrefix(String JNDIPrefix) {
      Iterator i = this.importedDestinations.values().iterator();

      while(i.hasNext()) {
         IDBeanHandler id = (IDBeanHandler)i.next();
         id.setJNDIPrefix(JNDIPrefix);
      }

   }

   public void setSAFRemoteContext(SAFRemoteContextBean safRemoteContextBean) {
      Iterator i = this.importedDestinations.values().iterator();

      while(i.hasNext()) {
         IDBeanHandler id = (IDBeanHandler)i.next();
         id.remoteContextChanged();
      }

   }

   public void setDefaultTargetingEnabled(boolean defaultTargetingEnabled) {
      this.defaultTargetingEnabled = defaultTargetingEnabled;
   }

   public boolean isDefaultTargetingEnabled() {
      return this.defaultTargetingEnabled;
   }

   public void setExactlyOnceLoadBalancingPolicy(String exactlyOnceLoadBalancingPolicy) {
      this.exactlyOnceLoadBalancingPolicy = exactlyOnceLoadBalancingPolicy;
   }

   public String getExactlyOnceLoadBalancingPolicy() {
      return this.exactlyOnceLoadBalancingPolicy;
   }

   public Map getImportedDestinations() {
      return this.importedDestinations;
   }

   IDEntityHelper getIDEntityHelper() {
      return this.idEntityHelper;
   }

   String getPartitionName() {
      return this.partitionName;
   }

   public synchronized void setMessageLoggingEnabled(boolean value) {
      Iterator iter = this.importedDestinations.values().iterator();

      while(iter.hasNext()) {
         IDBeanHandler idb = (IDBeanHandler)iter.next();
         if (!idb.isMessageLoggingParamsCustomized()) {
            idb.updateMessageLoggingEnabled(value);
         }
      }

      this.messageLoggingEnabled = value;
   }

   synchronized boolean isMessageLoggingEnabled() {
      return this.messageLoggingEnabled;
   }

   public synchronized void setMessageLoggingFormat(String value) {
      Iterator iter = this.importedDestinations.values().iterator();

      while(iter.hasNext()) {
         IDBeanHandler idb = (IDBeanHandler)iter.next();
         if (!idb.isMessageLoggingParamsCustomized()) {
            idb.updateMessageLoggingFormat(value);
         }
      }

      this.messageLoggingFormat = value;
   }

   synchronized String getMessageLoggingFormat() {
      return this.messageLoggingFormat;
   }

   static {
      groupBeanAddRemoveSignatures.put("SAFQueues", SAFQueueBean.class);
      groupBeanAddRemoveSignatures.put("SAFTopics", SAFTopicBean.class);
      groupBeanSignatures.put("JNDIPrefix", String.class);
      groupBeanSignatures.put("SAFRemoteContext", SAFRemoteContextBean.class);
      groupBeanSignatures.put("DefaultTargetingEnabled", Boolean.TYPE);
      messageLoggingSignatures = new HashMap();
      messageLoggingSignatures.put("MessageLoggingFormat", String.class);
      messageLoggingSignatures.put("MessageLoggingEnabled", Boolean.TYPE);
   }
}
