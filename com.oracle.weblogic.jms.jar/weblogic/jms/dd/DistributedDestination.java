package weblogic.jms.dd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.application.ApplicationContext;
import weblogic.application.ModuleException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.DistributedDestinationBean;
import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;
import weblogic.j2ee.descriptor.wl.DistributedQueueBean;
import weblogic.j2ee.descriptor.wl.DistributedTopicBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSException;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.jms.module.JMSDeploymentHelper;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.management.ManagementException;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSConstants;
import weblogic.management.utils.BeanListenerCustomizer;
import weblogic.management.utils.GenericBeanListener;
import weblogic.store.common.PartitionNameUtils;

public final class DistributedDestination implements JMSModuleManagedEntity, BeanListenerCustomizer, DDConfig, DDConstants {
   private static final String PHYS_DEST_NAME = "PhysicalDestinationName";
   private int type;
   private HashMap memberName2Listener = new HashMap();
   private HashMap memberName2InteropListener = new HashMap();
   private ArrayList addedMemberNameList;
   private ArrayList removedMemberInformation;
   private String name;
   private String jndiName;
   private String unitOfOrderRouting;
   private boolean isDefaultUnitOfOrder;
   private String loadBalancingPolicy;
   private String forwardingPolicy;
   private int queueForwardDelay;
   private boolean resetDeliveryCount = true;
   private BasicDeploymentMBean deployableApplication;
   private JMSBean module;
   private DistributedDestinationBean specificBean;
   private String EARModuleName;
   private String moduleName;
   private ApplicationContext appCtx;
   private String safExportPolicy;
   private GenericBeanListener distributedDestinationBeanListener;
   private String ddGroupTargetName = null;
   private DDHandler ddHandler;
   private static HashMap groupBeanSignatures = new HashMap();
   private boolean isInterop;

   public DistributedDestination(String paramName, JMSBean paramModule, DistributedDestinationBean paramSpecificBean, BasicDeploymentMBean paramDeployableApplication, String paramEARModuleName, String paramModuleName, ApplicationContext paramAppCtx) throws ModuleException {
      this.name = paramName;
      this.module = paramModule;
      this.specificBean = paramSpecificBean;
      this.deployableApplication = paramDeployableApplication;
      this.EARModuleName = paramEARModuleName;
      this.moduleName = paramModuleName;
      this.appCtx = paramAppCtx;
      this.isInterop = this.moduleName.equals("interop-jms");
      if (this.specificBean instanceof DistributedQueueBean) {
         this.type = 0;
      } else {
         this.type = 1;
      }

      this.ddHandler = new DDHandler(this, true, (Context)null, false);
   }

   private BasicDeploymentMBean findApplication() {
      if (this.deployableApplication != null) {
         return this.deployableApplication;
      } else {
         this.deployableApplication = JMSModuleHelper.findJMSSystemResource(this.moduleName);
         return this.deployableApplication != null ? this.deployableApplication : null;
      }
   }

   private void initializeWithBean() throws ModuleException {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("Initializing distributed destination with Bean " + this.specificBean.getName());
      }

      try {
         this.initializeBeanUpdateListeners();
      } catch (JMSException var2) {
         throw new ModuleException(var2.getMessage(), var2);
      }
   }

   public String getApplicationName() {
      return this.appCtx != null ? this.appCtx.getApplicationId() : null;
   }

   private void prepareMembers() throws ModuleException {
      DistributedDestinationMemberBean[] memberArray = null;
      if (this.type == 0) {
         memberArray = (DistributedDestinationMemberBean[])((DistributedQueueBean)this.specificBean).getDistributedQueueMembers();
      } else if (this.type == 1) {
         memberArray = (DistributedDestinationMemberBean[])((DistributedTopicBean)this.specificBean).getDistributedTopicMembers();
      }

      if (memberArray != null) {
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("DistributedDestination -- " + memberArray.length + " members are found in " + this.name);
         }

         for(int i = 0; i < memberArray.length; ++i) {
            if (JMSDebug.JMSConfig.isDebugEnabled()) {
               JMSDebug.JMSConfig.debug("Preparing distributed destination member " + memberArray[i].getName());
            }

            this.prepareMember(memberArray[i], false);
         }
      }

   }

   private synchronized void prepareMember(DistributedDestinationMemberBean member, boolean isDynamic) throws ModuleException {
      String memberName = JMSDeploymentHelper.getMemberName(this.moduleName, member);
      if (this.isInterop && !member.isSet("PhysicalDestinationName")) {
         if (isDynamic) {
            return;
         }

         memberName = null;
      }

      if (memberName == null) {
         JMSLogger.logJMSDDNullMember(this.name, member.getName());
      }
   }

   private List fullMemberNames() {
      DistributedDestinationMemberBean[] members = this.getMemberBeans();
      ArrayList fullMemberNames = new ArrayList(members.length);

      for(int i = 0; i < members.length; ++i) {
         DistributedDestinationMemberBean memberBean = members[i];
         String fullMemberName = JMSDeploymentHelper.getMemberName(this.moduleName, memberBean);
         fullMemberNames.add(fullMemberName);
      }

      return fullMemberNames;
   }

   private void activateMembers() {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("DD: activateMembers for " + this.name + "[" + this + "]");
      }

      if (this.ddHandler != null) {
         this.ddHandler = DDManager.activateOrUpdate(this.ddHandler);
         if (!this.ddHandler.isActive()) {
            throw new AssertionError("DD: activateMembers(): DDHandler not active for " + this.name + "[" + this + "]");
         } else {
            this.updateMembers();
            DistributedDestinationMemberBean[] members = this.getMemberBeans();

            for(int i = 0; i < members.length; ++i) {
               DistributedDestinationMemberBean memberBean = members[i];
               if (JMSDebug.JMSConfig.isDebugEnabled()) {
                  JMSDebug.JMSConfig.debug("Activating distributed destination member " + memberBean.getName());
               }

               this.activateMember(memberBean);
            }

         }
      }
   }

   private void updateMembers() {
      if (this.ddHandler != null && this.ddHandler.isActive()) {
         this.ddHandler.updateMembers(this.fullMemberNames());
      }
   }

   private synchronized void activateMember(DistributedDestinationMemberBean member) {
      String memberName = JMSDeploymentHelper.getMemberName(this.moduleName, member);
      if (this.isInterop) {
         if (this.memberName2InteropListener.get(member.getName()) == null) {
            InteropMemberHandler imh = new InteropMemberHandler(member);
            this.memberName2InteropListener.put(member.getName(), imh);
         }

         if (!member.isSet("PhysicalDestinationName")) {
            return;
         }
      }

      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("new member " + memberName + " added.");
      }

      if (this.ddHandler != null) {
         DDMember ddMember = this.ddHandler.findMemberByName(memberName);
         GenericBeanListener groupBeanListener = new GenericBeanListener((DescriptorBean)member, ddMember, groupBeanSignatures);
         this.memberName2Listener.put(memberName, groupBeanListener);
         this.ddHandler.setMemberWeight(memberName, member.getWeight());
      }

   }

   private void deactivateMembers() {
      DistributedDestinationMemberBean[] members = this.getMemberBeans();

      for(int i = 0; i < members.length; ++i) {
         this.deactivateMember(members[i]);
      }

   }

   private void deactivateMember(DistributedDestinationMemberBean memberBean) {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("Deactivating distributed destination member " + memberBean.getName());
      }

      InteropMemberHandler imh = (InteropMemberHandler)this.memberName2InteropListener.get(memberBean.getName());
      if (imh != null) {
         imh.close();
      }

      GenericBeanListener groupBeanListener = (GenericBeanListener)this.memberName2Listener.get(memberBean.getName());
      if (groupBeanListener != null) {
         groupBeanListener.close();
      }

   }

   private void unprepareMembers() {
      DistributedDestinationMemberBean[] members = this.getMemberBeans();

      for(int lcv = 0; lcv < members.length; ++lcv) {
         DistributedDestinationMemberBean memberBean = members[lcv];
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("Unpreparing distributed destination member " + memberBean.getName());
         }

         this.unprepareMember(memberBean, false);
      }

   }

   private void unprepareMember(DistributedDestinationMemberBean memberBean, boolean isDynamic) {
      String removedMemberName = memberBean.getName();
      GenericBeanListener groupBeanListener = (GenericBeanListener)this.memberName2Listener.remove(removedMemberName);
      if (groupBeanListener != null) {
         groupBeanListener.close();
      }

   }

   public String getName() {
      return this.name;
   }

   public String getEARModuleName() {
      return this.EARModuleName;
   }

   public String getReferenceName() {
      return null;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public ApplicationContext getApplicationContext() {
      return this.appCtx;
   }

   public String getJNDIName() {
      return this.jndiName;
   }

   public void setJNDIName(String jndiName) throws IllegalArgumentException {
      this.jndiName = JMSServerUtilities.transformJNDIName(jndiName);
      if (this.ddHandler != null) {
         this.ddHandler.setJNDIName(jndiName);
      }

   }

   public static int translateLoadBalancingPolicy(String loadBalancingPolicy) {
      int lbPolicy = 0;
      if (loadBalancingPolicy != null) {
         if (loadBalancingPolicy.equals("Round-Robin")) {
            lbPolicy = 0;
         } else if (loadBalancingPolicy.equals("Random")) {
            lbPolicy = 1;
         }
      }

      return lbPolicy;
   }

   public int getLoadBalancingPolicyAsInt() {
      return translateLoadBalancingPolicy(this.loadBalancingPolicy);
   }

   public String getLoadBalancingPolicy() {
      return this.loadBalancingPolicy;
   }

   public void setLoadBalancingPolicy(String loadBalancingPolicy) {
      this.loadBalancingPolicy = loadBalancingPolicy;
      if (this.ddHandler != null) {
         this.ddHandler.setLoadBalancingPolicyAsInt(translateLoadBalancingPolicy(loadBalancingPolicy));
      }

   }

   public static int translateForwardingPolicy(String forwardingPolicy) {
      int fwdPolicy = 1;
      if (forwardingPolicy != null) {
         if (forwardingPolicy.equals(JMSConstants.FORWARDING_POLICY_REPLICATED)) {
            fwdPolicy = 1;
         } else {
            if (!forwardingPolicy.equals(JMSConstants.FORWARDING_POLICY_PARTITIONED)) {
               throw new IllegalArgumentException("Unrecognized forwarding policy " + forwardingPolicy + ". Allowed valid values=" + JMSConstants.FORWARDING_POLICY_REPLICATED + "," + JMSConstants.FORWARDING_POLICY_PARTITIONED);
            }

            fwdPolicy = 0;
         }
      }

      return fwdPolicy;
   }

   public int getForwardingPolicyAsInt() {
      return translateForwardingPolicy(this.forwardingPolicy);
   }

   public String getForwardingPolicy() {
      return this.forwardingPolicy;
   }

   public void setForwardingPolicy(String forwardingPolicy) {
      this.forwardingPolicy = forwardingPolicy;
      if (this.ddHandler != null) {
         this.ddHandler.setForwardingPolicy(translateForwardingPolicy(forwardingPolicy));
      }

   }

   public int getForwardDelay() {
      return this.queueForwardDelay;
   }

   public void setForwardDelay(int queueForwardDelay) {
      this.queueForwardDelay = queueForwardDelay;
      if (this.ddHandler != null) {
         this.ddHandler.setForwardDelay(queueForwardDelay);
      }

   }

   public boolean getResetDeliveryCountOnForward() {
      return this.resetDeliveryCount;
   }

   public void setResetDeliveryCountOnForward(boolean reset) {
      this.resetDeliveryCount = reset;
      if (this.ddHandler != null) {
         this.ddHandler.setResetDeliveryCountOnForward(reset);
      }

   }

   public int getType() {
      return this.type;
   }

   public DistributedDestinationMemberBean getMemberBean(String memberName) {
      if (this.specificBean == null) {
         return null;
      } else if (this.type == 0) {
         DistributedQueueBean dq = (DistributedQueueBean)this.specificBean;
         return dq.lookupDistributedQueueMember(memberName);
      } else {
         DistributedTopicBean dt = (DistributedTopicBean)this.specificBean;
         return dt.lookupDistributedTopicMember(memberName);
      }
   }

   public DistributedDestinationMemberBean[] getMemberBeans() {
      if (this.specificBean == null) {
         return new DistributedDestinationMemberBean[0];
      } else if (this.type == 0) {
         DistributedQueueBean dq = (DistributedQueueBean)this.specificBean;
         return dq.getDistributedQueueMembers();
      } else {
         DistributedTopicBean dt = (DistributedTopicBean)this.specificBean;
         return dt.getDistributedTopicMembers();
      }
   }

   private String getMemberJndiName(DistributedDestinationMemberBean member) {
      String destinationName = member.getPhysicalDestinationName();
      DestinationBean destinationBean = JMSBeanHelper.findDestinationBean(destinationName, this.module);
      return destinationBean == null ? null : destinationBean.getJNDIName();
   }

   private String getMemberLocalJndiName(DistributedDestinationMemberBean member) {
      String destinationName = member.getPhysicalDestinationName();
      DestinationBean destination = JMSBeanHelper.findDestinationBean(destinationName, this.module);
      return destination == null ? null : destination.getLocalJNDIName();
   }

   public String toString() {
      return "DistributedDestination(name = " + this.name + "; jndiName = " + this.jndiName + "; DDHandler[" + this.ddHandler + "])";
   }

   private final void internalValJndiName(String proposedJNDIName, boolean isLocal) throws BeanUpdateRejectedException {
      proposedJNDIName = JMSServerUtilities.transformJNDIName(proposedJNDIName);
      if (proposedJNDIName != null) {
         if (!this.ddHandler.isActive() || this.jndiName == null || !this.jndiName.equals(proposedJNDIName)) {
            Context context = JMSService.getContextWithBeanUpdateRejectedException(!isLocal);
            Object object = null;

            for(int i = 0; i < 40; ++i) {
               try {
                  object = context.lookup(PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", proposedJNDIName));
               } catch (NameNotFoundException var7) {
                  return;
               } catch (NamingException var8) {
                  throw new BeanUpdateRejectedException(var8.getMessage(), var8);
               }

               try {
                  Thread.sleep(500L);
               } catch (InterruptedException var9) {
                  break;
               }
            }

            throw new BeanUpdateRejectedException("The proposed " + (isLocal ? "Local" : "") + " JNDI name " + proposedJNDIName + " for destination " + this.name + " is already bound by another object of type " + (object == null ? "null" : object.getClass().getName()));
         }
      }
   }

   private final void valJNDIName(String proposedJNDIName) throws BeanUpdateRejectedException {
      this.internalValJndiName(proposedJNDIName, false);
   }

   public void prepare() throws ModuleException {
      this.initializeWithBean();

      try {
         this.valJNDIName(this.jndiName);
      } catch (BeanUpdateRejectedException var2) {
         throw new ModuleException(var2.getMessage(), var2.getCause());
      }

      if (this.type == 0) {
         this.queueForwardDelay = ((DistributedQueueBean)this.specificBean).getForwardDelay();
         this.setResetDeliveryCountOnForward(((DistributedQueueBean)this.specificBean).getResetDeliveryCountOnForward());
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("DistributedDestination.prepare()  queueForwardDelay " + this.queueForwardDelay + " resetDeliveryCount " + this.resetDeliveryCount);
         }
      }

      this.prepareMembers();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Sucessfully prepared distributed destination: " + this.name);
      }

   }

   public void activate(JMSBean paramWholeModule) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Activating distributed destination: " + this.name);
      }

      if (paramWholeModule != null) {
         this.module = paramWholeModule;
         if (this.specificBean instanceof DistributedQueueBean) {
            this.specificBean = this.module.lookupDistributedQueue(this.getEntityName());
         } else {
            this.specificBean = this.module.lookupDistributedTopic(this.getEntityName());
         }

         this.closeBeanUpdateListeners();
      }

      this.openBeanUpdateListeners();
      this.activateMembers();
      JMSLogger.logDDDeployed(this.name);
      DDScheduler.drain();
      Throwable thrown = DDScheduler.waitForComplete();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         if (thrown == null) {
            JMSDebug.JMSModule.debug("Sucessfully activated distributed destination: " + this.name);
         } else {
            JMSDebug.JMSModule.debug("failed to activate distributed destination: " + this.name, thrown);
         }
      }

      if (thrown != null) {
         if (thrown instanceof ModuleException) {
            throw (ModuleException)thrown;
         } else {
            throw new ModuleException(thrown);
         }
      }
   }

   public void deactivate() {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Start deactivate distributed destination: " + this.name);
      }

      this.closeBeanUpdateListeners();
      this.deactivateMembers();
      if (this.ddHandler != null) {
         this.ddHandler.deactivate();
      } else if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("ddHandler is null for " + this.name);
      }

      DDScheduler.drain();
      Throwable thrown = DDScheduler.waitForComplete();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         if (thrown == null) {
            JMSDebug.JMSModule.debug("Sucessfully deactivated distributed destination: " + this.name);
         } else {
            JMSDebug.JMSModule.debug("failed to deactivate distributed destination: " + this.name, thrown);
         }
      } else if (thrown != null) {
         JMSDebug.JMSModule.debug("ddHandler is null for " + this.name);
         thrown.printStackTrace();
      }

      this.ddHandler = null;
   }

   public void unprepare() {
      this.unprepareMembers();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Sucessfully unprepared distributed destination: " + this.name);
      }

   }

   public void remove() {
   }

   public void destroy() {
   }

   public String getEntityName() {
      return this.specificBean.getName();
   }

   public void prepareChangeOfTargets(List targets, DomainMBean proposedDomain) {
   }

   public void activateChangeOfTargets() {
   }

   public void rollbackChangeOfTargets() {
   }

   public String getUnitOfOrderRouting() {
      return this.unitOfOrderRouting;
   }

   public void setUnitOfOrderRouting(String unitOfOrderRouting) {
      this.unitOfOrderRouting = unitOfOrderRouting.intern();
      if (this.ddHandler != null) {
         this.ddHandler.setUnitOfOrderRouting(this.unitOfOrderRouting);
      }

   }

   public boolean isDefaultUnitOfOrder() {
      return this.isDefaultUnitOfOrder;
   }

   public void setDefaultUnitOfOrder(boolean isDefaultUnitOfOrder) {
      this.isDefaultUnitOfOrder = isDefaultUnitOfOrder;
      if (this.ddHandler != null) {
         this.ddHandler.setDefaultUnitOfOrder(this.isDefaultUnitOfOrder);
      }

   }

   private void addMemberBean(DistributedDestinationMemberBean memberBean) throws BeanUpdateRejectedException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Preparing distributed destination member for add " + memberBean.getName());
      }

      try {
         this.prepareMember(memberBean, true);
      } catch (ModuleException var3) {
         throw new BeanUpdateRejectedException(JMSExceptionLogger.logCannotDynamicallyAddDDMemberLoggable(this.name, memberBean.getName()).getMessage(), var3);
      }

      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Member " + memberBean.getName() + " is prepared for add ");
      }

   }

   private void removeMemberBean(DistributedDestinationMemberBean memberBean) {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Deactivating distributed destination member for removal" + memberBean.getName());
      }

      String memberName = JMSDeploymentHelper.getMemberName(this.moduleName, memberBean);
      if (memberName != null) {
         this.deactivateMember(memberBean);
      }

      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Member " + memberBean.getName() + " is deactivated for removal ");
      }

   }

   private void finishAddDistributedDestinationMember(String memberName, DistributedDestinationMemberBean toAdd, boolean isActivate) {
      if (memberName != null) {
         if (isActivate) {
            this.updateMembers();
            this.activateMember(toAdd);
         } else {
            this.deactivateMember(toAdd);
         }

      }
   }

   private void finishRemoveDistributedDestinationMember(String memberName, DistributedDestinationMemberBean toRemove, boolean isActivate) throws BeanUpdateRejectedException {
      if (memberName != null) {
         if (isActivate) {
            this.updateMembers();
            this.unprepareMember(toRemove, true);
         } else {
            try {
               this.prepareMember(toRemove, true);
            } catch (ModuleException var5) {
               throw new BeanUpdateRejectedException(JMSExceptionLogger.logCannotDynamicallyRemoveDDMemberLoggable(this.name, memberName).getMessage(), var5);
            }
         }

      }
   }

   public void startAddDistributedQueueMembers(DistributedDestinationMemberBean toAdd) throws BeanUpdateRejectedException {
      if (toAdd != null) {
         this.addMemberBean(toAdd);
      }
   }

   public void finishAddDistributedQueueMembers(DistributedDestinationMemberBean toAdd, boolean isActivate) {
      this.finishAddDistributedDestinationMember(toAdd.getPhysicalDestinationName(), toAdd, isActivate);
   }

   public void startAddDistributedTopicMembers(DistributedDestinationMemberBean toAdd) throws BeanUpdateRejectedException {
      if (toAdd != null) {
         this.addMemberBean(toAdd);
      }
   }

   public void finishAddDistributedTopicMembers(DistributedDestinationMemberBean toAdd, boolean isActivate) {
      this.finishAddDistributedDestinationMember(toAdd.getPhysicalDestinationName(), toAdd, isActivate);
   }

   public void startRemoveDistributedQueueMembers(DistributedDestinationMemberBean toRemove) {
      if (toRemove != null) {
         this.removeMemberBean(toRemove);
      }
   }

   public void finishRemoveDistributedQueueMembers(DistributedDestinationMemberBean toRemove, boolean isActivate) throws BeanUpdateRejectedException {
      String memberName = JMSDeploymentHelper.getMemberName(this.moduleName, toRemove);
      this.finishRemoveDistributedDestinationMember(memberName, toRemove, isActivate);
   }

   public void startRemoveDistributedTopicMembers(DistributedDestinationMemberBean toRemove) {
      if (toRemove != null) {
         this.removeMemberBean(toRemove);
      }
   }

   public void finishRemoveDistributedTopicMembers(DistributedDestinationMemberBean toRemove, boolean isActivate) throws BeanUpdateRejectedException {
      String memberName = JMSDeploymentHelper.getMemberName(this.moduleName, toRemove);
      this.finishRemoveDistributedDestinationMember(memberName, toRemove, isActivate);
   }

   private void initializeBeanUpdateListeners() throws JMSException {
      DescriptorBean descriptor = (DescriptorBean)this.specificBean;
      if (this.type == 0) {
         this.distributedDestinationBeanListener = new GenericBeanListener(descriptor, this, JMSBeanHelper.distributedQueueBeanSignatures, JMSBeanHelper.distributedQueueAdditionSignatures, false);
      } else {
         this.distributedDestinationBeanListener = new GenericBeanListener(descriptor, this, JMSBeanHelper.distributedTopicBeanSignatures, JMSBeanHelper.distributedTopicAdditionSignatures, false);
      }

      try {
         this.distributedDestinationBeanListener.initialize();
      } catch (ManagementException var3) {
         throw new JMSException(var3);
      }

      this.distributedDestinationBeanListener.setCustomizer(this);
   }

   private void openBeanUpdateListeners() {
      DescriptorBean descriptor = (DescriptorBean)this.specificBean;
      if (this.distributedDestinationBeanListener != null) {
         this.distributedDestinationBeanListener.open();
      } else {
         if (this.type == 0) {
            this.distributedDestinationBeanListener = new GenericBeanListener(descriptor, this, JMSBeanHelper.distributedQueueBeanSignatures, JMSBeanHelper.distributedQueueAdditionSignatures);
         } else {
            this.distributedDestinationBeanListener = new GenericBeanListener(descriptor, this, JMSBeanHelper.distributedTopicBeanSignatures, JMSBeanHelper.distributedTopicAdditionSignatures);
         }

         this.distributedDestinationBeanListener.setCustomizer(this);
      }

   }

   private void closeBeanUpdateListeners() {
      if (this.distributedDestinationBeanListener != null) {
         this.distributedDestinationBeanListener.close();
         this.distributedDestinationBeanListener = null;
      }

   }

   public void activateFinished() {
   }

   public DistributedDestinationMemberBean lookupDistributedQueueMember(String name) {
      return null;
   }

   public DistributedDestinationMemberBean lookupDistributedTopicMember(String name) {
      return null;
   }

   public String getSAFExportPolicy() {
      return this.safExportPolicy;
   }

   public void setSAFExportPolicy(String safExportPolicy) {
      if (safExportPolicy == null) {
         this.safExportPolicy = "All";
      } else {
         this.safExportPolicy = safExportPolicy;
      }

      if (this.ddHandler != null) {
         this.ddHandler.setSAFExportPolicy(this.safExportPolicy);
      }

   }

   static {
      groupBeanSignatures.put("Weight", Integer.TYPE);
   }

   private class InteropMemberHandler implements BeanUpdateListener {
      DistributedDestinationMemberBean memberBean;
      boolean physicalDestinationNameSet;

      InteropMemberHandler(DistributedDestinationMemberBean paramMemberBean) {
         this.memberBean = paramMemberBean;
         this.physicalDestinationNameSet = this.memberBean.isSet("PhysicalDestinationName");
         ((DescriptorBean)this.memberBean).addBeanUpdateListener(this);
      }

      private void close() {
         ((DescriptorBean)this.memberBean).removeBeanUpdateListener(this);
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         DistributedDestinationMemberBean proposedMemberBean = (DistributedDestinationMemberBean)event.getProposedBean();
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

         for(int lcv = 0; lcv < updates.length; ++lcv) {
            BeanUpdateEvent.PropertyUpdate update = updates[lcv];
            String changedParam = update.getPropertyName();
            if ("PhysicalDestinationName".equals(changedParam)) {
               if (this.physicalDestinationNameSet) {
                  throw new BeanUpdateRejectedException("An attempt was made to change the physical destination from " + this.memberBean.getPhysicalDestinationName() + " to " + proposedMemberBean.getPhysicalDestinationName() + ".  This is not allowed.  The physical destination may only be changed from an unset state to a particular physical destination");
               }

               DistributedDestination.this.addMemberBean(proposedMemberBean);
            }
         }

      }

      public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

         for(int lcv = 0; lcv < updates.length; ++lcv) {
            BeanUpdateEvent.PropertyUpdate update = updates[lcv];
            String changedParam = update.getPropertyName();
            if ("PhysicalDestinationName".equals(changedParam)) {
               this.physicalDestinationNameSet = true;
               DistributedDestination.this.finishAddDistributedDestinationMember(this.memberBean.getPhysicalDestinationName(), this.memberBean, true);
            }
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
         DistributedDestinationMemberBean proposedMemberBean = (DistributedDestinationMemberBean)event.getProposedBean();
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

         for(int lcv = 0; lcv < updates.length; ++lcv) {
            BeanUpdateEvent.PropertyUpdate update = updates[lcv];
            String changedParam = update.getPropertyName();
            if ("PhysicalDestinationName".equals(changedParam)) {
               DistributedDestination.this.finishAddDistributedDestinationMember(proposedMemberBean.getPhysicalDestinationName(), proposedMemberBean, false);
            }
         }

      }
   }
}
