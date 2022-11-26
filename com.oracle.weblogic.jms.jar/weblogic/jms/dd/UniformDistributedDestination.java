package weblogic.jms.dd;

import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import weblogic.application.ApplicationContext;
import weblogic.application.ModuleException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedDestinationBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedQueueBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean;
import weblogic.jms.JMSLogger;
import weblogic.jms.backend.udd.SyntheticDQBean;
import weblogic.jms.backend.udd.SyntheticDTBean;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSException;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.jms.module.JMSDeploymentHelper;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSConstants;
import weblogic.management.utils.BeanListenerCustomizer;
import weblogic.management.utils.GenericBeanListener;

public class UniformDistributedDestination implements JMSModuleManagedEntity, BeanListenerCustomizer, DDConfig, DDConstants {
   private static final String PHYS_DEST_NAME = "PhysicalDestinationName";
   private String forwardingPolicy;
   private int type;
   private GenericBeanListener uddBeanListener;
   private UniformDistributedDestinationBean specificBean;
   private String name;
   private JMSBean module;
   private DDHandler ddHandler;
   private String jndiName;
   private ApplicationContext appCtx;
   private String safExportPolicy;
   private String EARModuleName;
   private String moduleName;
   private Context namingContext;
   private String unitOfOrderRouting;
   private boolean isDefaultUnitOfOrder;
   private String loadBalancingPolicy;
   private int queueForwardDelay;
   private boolean resetDeliveryCount = true;
   private boolean isJMSResourceDefinition;

   public UniformDistributedDestination(String paramName, JMSBean paramModule, UniformDistributedDestinationBean paramSpecificBean, String paramEARModuleName, String paramModuleName, ApplicationContext paramAppCtx, Context namingContext, boolean isJMSResourceDefinition) throws ModuleException {
      this.name = paramName;
      this.module = paramModule;
      this.specificBean = paramSpecificBean;
      this.EARModuleName = paramEARModuleName;
      this.moduleName = paramModuleName;
      this.appCtx = paramAppCtx;
      this.namingContext = namingContext;
      if (this.specificBean instanceof UniformDistributedQueueBean) {
         this.type = 0;
      } else {
         this.type = 1;
      }

      this.isJMSResourceDefinition = isJMSResourceDefinition;
      this.ddHandler = new DDHandler(this, true, namingContext, isJMSResourceDefinition);
   }

   private void initializeBeanUpdateListeners() throws JMSException {
      DescriptorBean descriptor = (DescriptorBean)this.specificBean;
      if (this.type == 0) {
         this.uddBeanListener = new GenericBeanListener(descriptor, this, JMSBeanHelper.uniformDistributedQueueBeanSignatures, false);
      } else {
         this.uddBeanListener = new GenericBeanListener(descriptor, this, JMSBeanHelper.uniformDistributedTopicBeanSignatures, false);
      }

      try {
         this.uddBeanListener.initialize();
      } catch (ManagementException var3) {
         throw new JMSException(var3);
      }

      this.uddBeanListener.setCustomizer(this);
   }

   public void activate(JMSBean paramWholeModule) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Activating uniform distributed destination: " + this.name);
      }

      if (this.ddHandler == null) {
         this.ddHandler = new DDHandler(this, true, this.namingContext, this.isJMSResourceDefinition);
      }

      if (paramWholeModule != null) {
         this.module = paramWholeModule;
         if (this.specificBean instanceof UniformDistributedQueueBean) {
            this.specificBean = this.module.lookupUniformDistributedQueue(this.getEntityName());
         } else {
            this.specificBean = this.module.lookupUniformDistributedTopic(this.getEntityName());
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
            JMSDebug.JMSModule.debug("Sucessfully activated uniform distributed destination: " + this.name);
         } else {
            JMSDebug.JMSModule.debug("failed to activate uniform distributed destination: " + this.name, thrown);
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

   private void closeBeanUpdateListeners() {
      if (this.uddBeanListener != null) {
         this.uddBeanListener.close();
         this.uddBeanListener = null;
      }

   }

   private void openBeanUpdateListeners() {
      DescriptorBean descriptor = (DescriptorBean)this.specificBean;
      if (this.uddBeanListener != null) {
         this.uddBeanListener.open();
      } else {
         if (this.type == 0) {
            this.uddBeanListener = new GenericBeanListener(descriptor, this, JMSBeanHelper.uniformDistributedQueueBeanSignatures);
         } else {
            this.uddBeanListener = new GenericBeanListener(descriptor, this, JMSBeanHelper.uniformDistributedTopicBeanSignatures);
         }

         this.uddBeanListener.setCustomizer(this);
      }

   }

   private void activateMembers() {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("UDD: activateMembers for " + this.name + "[" + this + "]");
      }

      if (this.ddHandler != null) {
         this.ddHandler = DDManager.activateOrUpdate(this.ddHandler);
         if (!this.ddHandler.isActive()) {
            throw new AssertionError("UDD: activateMembers(): DDHandler not active for " + this.name + "[" + this + "]");
         } else {
            this.updateMembers();
         }
      }
   }

   private void updateMembers() {
      if (this.ddHandler != null && this.ddHandler.isActive()) {
         this.ddHandler.updateMembers(this.fullMemberNames());
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

   public DistributedDestinationMemberBean[] getMemberBeans() {
      if (this.specificBean == null) {
         return new DistributedDestinationMemberBean[0];
      } else if (this.type == 0) {
         UniformDistributedQueueBean dq = (UniformDistributedQueueBean)this.specificBean;
         return ((SyntheticDQBean)dq).getDistributedQueueMembers();
      } else {
         UniformDistributedTopicBean dt = (UniformDistributedTopicBean)this.specificBean;
         return ((SyntheticDTBean)dt).getDistributedTopicMembers();
      }
   }

   public void deactivate() {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Start deactivate uniform distributed destination: " + this.name);
      }

      this.closeBeanUpdateListeners();
      if (this.ddHandler != null) {
         this.ddHandler.deactivate();
      } else if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("ddHandler is null for " + this.name);
      }

      DDScheduler.drain();
      Throwable thrown = DDScheduler.waitForComplete();
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         if (thrown == null) {
            JMSDebug.JMSModule.debug("Sucessfully deactivated uniform distributed destination: " + this.name);
         } else {
            JMSDebug.JMSModule.debug("failed to deactivate uniform distributed destination: " + this.name, thrown);
         }
      } else if (thrown != null) {
         JMSDebug.JMSModule.debug("ddHandler is null for " + this.name);
         thrown.printStackTrace();
      }

      this.ddHandler = null;
   }

   public void prepare() throws ModuleException {
      this.initializeWithBean();
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("UDD.prepare() is called: jndiName=" + this.jndiName);
      }

      if (this.type == 0) {
         this.queueForwardDelay = ((UniformDistributedQueueBean)this.specificBean).getForwardDelay();
         this.setResetDeliveryCountOnForward(((UniformDistributedQueueBean)this.specificBean).getResetDeliveryCountOnForward());
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("UniformDistributedDestination.prepare()  queueForwardDelay " + this.queueForwardDelay + " resetDeliveryCount " + this.resetDeliveryCount);
         }
      }

      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Sucessfully prepared uniform distributed destination: " + this.name);
      }

   }

   public DDHandler getDDHandler() {
      return this.ddHandler;
   }

   private void initializeWithBean() throws ModuleException {
      if (JMSDebug.JMSConfig.isDebugEnabled()) {
         JMSDebug.JMSConfig.debug("Initializing uniform distributed destination with Bean " + this.specificBean.getName());
      }

      try {
         this.initializeBeanUpdateListeners();
      } catch (JMSException var2) {
         throw new ModuleException(var2.getMessage(), var2);
      }
   }

   public void unprepare() {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Sucessfully unprepared uniform distributed destination: " + this.name);
      }

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

   public String getForwardingPolicy() {
      return this.forwardingPolicy;
   }

   public void setForwardingPolicy(String forwardingPolicy) {
      this.forwardingPolicy = forwardingPolicy;
      if (this.ddHandler != null) {
         this.ddHandler.setForwardingPolicy(translateForwardingPolicy(forwardingPolicy));
      }

   }

   public int getForwardingPolicyAsInt() {
      return translateForwardingPolicy(this.forwardingPolicy);
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

   public boolean getResetDeliveryCountOnForward() {
      return this.resetDeliveryCount;
   }

   public void setResetDeliveryCountOnForward(boolean reset) {
      this.resetDeliveryCount = reset;
      if (this.ddHandler != null) {
         this.ddHandler.setResetDeliveryCountOnForward(reset);
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

   public void destroy() {
   }

   public void remove() {
   }

   public void finishAddDistributedQueueMembers(DistributedDestinationMemberBean toAdd, boolean isActivate) {
      this.finishAddDistributedDestinationMember(toAdd.getPhysicalDestinationName(), toAdd, isActivate);
   }

   private void finishAddDistributedDestinationMember(String memberName, DistributedDestinationMemberBean toAdd, boolean isActivate) {
      if (memberName != null) {
         if (isActivate) {
            this.updateMembers();
         }

      }
   }

   public void finishAddDistributedTopicMembers(DistributedDestinationMemberBean toAdd, boolean isActivate) {
      this.finishAddDistributedDestinationMember(toAdd.getPhysicalDestinationName(), toAdd, isActivate);
   }

   public void finishRemoveDistributedQueueMembers(DistributedDestinationMemberBean toRemove, boolean isActivate) throws BeanUpdateRejectedException {
      String memberName = JMSDeploymentHelper.getMemberName(this.moduleName, toRemove);
      this.finishRemoveDistributedDestinationMember(memberName, toRemove, isActivate);
   }

   private void finishRemoveDistributedDestinationMember(String memberName, DistributedDestinationMemberBean toRemove, boolean isActivate) throws BeanUpdateRejectedException {
      if (memberName != null) {
         if (isActivate) {
            if (this.ddHandler != null) {
               this.ddHandler.shutdownForwarders();
            }

            this.updateMembers();
         }

      }
   }

   public void finishRemoveDistributedTopicMembers(DistributedDestinationMemberBean toRemove, boolean isActivate) throws BeanUpdateRejectedException {
      String memberName = JMSDeploymentHelper.getMemberName(this.moduleName, toRemove);
      this.finishRemoveDistributedDestinationMember(memberName, toRemove, isActivate);
   }

   public void activateFinished() {
   }

   public String getEntityName() {
      return this.specificBean.getName();
   }

   public int getType() {
      return this.type;
   }

   public boolean isDefaultUnitOfOrder() {
      return this.isDefaultUnitOfOrder;
   }

   public String getApplicationName() {
      return this.appCtx != null ? this.appCtx.getApplicationId() : null;
   }

   public void rollbackChangeOfTargets() {
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

   public String getName() {
      return this.name;
   }

   public void prepareChangeOfTargets(List targets, DomainMBean proposedDomain) {
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

   public String getUnitOfOrderRouting() {
      return this.unitOfOrderRouting;
   }

   public void setUnitOfOrderRouting(String unitOfOrderRouting) {
      this.unitOfOrderRouting = unitOfOrderRouting.intern();
      if (this.ddHandler != null) {
         this.ddHandler.setUnitOfOrderRouting(this.unitOfOrderRouting);
      }

   }

   public String getEARModuleName() {
      return this.EARModuleName;
   }

   public void activateChangeOfTargets() {
   }

   public String getReferenceName() {
      return null;
   }

   public String toString() {
      return "UDD(name=" + this.name + ", jndiName=" + this.jndiName + ", DDHandler[" + this.ddHandler + "])";
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
            if ("PhysicalDestinationName".equals(changedParam) && this.physicalDestinationNameSet) {
               throw new BeanUpdateRejectedException("An attempt was made to change the physical destination from " + this.memberBean.getPhysicalDestinationName() + " to " + proposedMemberBean.getPhysicalDestinationName() + ".  This is not allowed.  The physical destination may only be changed from an unset state to a particular physical destination");
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
               UniformDistributedDestination.this.finishAddDistributedDestinationMember(this.memberBean.getPhysicalDestinationName(), this.memberBean, true);
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
               UniformDistributedDestination.this.finishAddDistributedDestinationMember(proposedMemberBean.getPhysicalDestinationName(), proposedMemberBean, false);
            }
         }

      }
   }
}
