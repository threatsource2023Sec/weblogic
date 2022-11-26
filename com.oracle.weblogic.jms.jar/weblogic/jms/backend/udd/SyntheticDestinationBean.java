package weblogic.jms.backend.udd;

import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBean;
import weblogic.j2ee.descriptor.wl.DeliveryParamsOverridesBean;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.MessageLoggingParamsBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.j2ee.descriptor.wl.ThresholdParamsBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean;
import weblogic.j2ee.descriptor.wl.constants.JMSConstants;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.management.ManagementException;

public class SyntheticDestinationBean implements DestinationBean, DescriptorBean, DeliveryFailureParamsBean {
   LinkedList listeners = new LinkedList();
   String jmsServerInstanceName;
   String jmsServerConfigName;
   boolean defaultTargetingEnabled;
   UDDEntity udd;

   public SyntheticDestinationBean(UDDEntity udd, String jmsServerInstanceName, String jmsServerConfigName) {
      this.udd = udd;
      this.jmsServerInstanceName = jmsServerInstanceName;
      this.jmsServerConfigName = jmsServerConfigName;

      try {
         JMSSecurityHelper.getSecurityHelper().mapDestinationName(this.getName(), udd.getUDestBean().getName());
      } catch (ManagementException var5) {
         throw new AssertionError("Cannot find Security Helper");
      }
   }

   public void addDestinationKey(String destinationKey) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public String getAttachSender() {
      return this.udd.getUDestBean().getAttachSender();
   }

   public DeliveryFailureParamsBean getDeliveryFailureParams() {
      return this;
   }

   public MessageLoggingParamsBean getMessageLoggingParams() {
      return this.udd.getUDestBean().getMessageLoggingParams();
   }

   public DeliveryParamsOverridesBean getDeliveryParamsOverrides() {
      return this.udd.getUDestBean().getDeliveryParamsOverrides();
   }

   public String[] getDestinationKeys() {
      return this.udd.getUDestBean().getDestinationKeys();
   }

   public String getJMSCreateDestinationIdentifier() {
      return this.udd.getUDestBean().getJMSCreateDestinationIdentifier();
   }

   public String getJNDIName() {
      return this.udd.getUDestBean().getJNDIName() == null ? null : JMSModuleHelper.uddMakeName(this.jmsServerInstanceName, this.udd.getUDestBean().getJNDIName());
   }

   public String getLocalJNDIName() {
      return this.udd.getUDestBean().getLocalJNDIName() == null ? null : JMSModuleHelper.uddMakeName(this.jmsServerInstanceName, this.udd.getUDestBean().getLocalJNDIName());
   }

   public int getMaximumMessageSize() {
      return this.udd.getUDestBean().getMaximumMessageSize();
   }

   public int getMessagingPerformancePreference() {
      return this.udd.getUDestBean().getMessagingPerformancePreference();
   }

   public QuotaBean getQuota() {
      return this.udd.getUDestBean().getQuota();
   }

   public TemplateBean getTemplate() {
      return this.udd.getUDestBean().getTemplate();
   }

   public ThresholdParamsBean getThresholds() {
      return this.udd.getUDestBean().getThresholds();
   }

   public boolean isConsumptionPausedAtStartup() {
      return this.udd.getUDestBean().isConsumptionPausedAtStartup();
   }

   public boolean isInsertionPausedAtStartup() {
      return this.udd.getUDestBean().isInsertionPausedAtStartup();
   }

   public boolean isProductionPausedAtStartup() {
      return this.udd.getUDestBean().isProductionPausedAtStartup();
   }

   public String getSAFExportPolicy() {
      return this.udd.getUDestBean().getSAFExportPolicy();
   }

   public String getForwardingPolicy() {
      return this.udd.getUDestBean() instanceof UniformDistributedTopicBean ? ((UniformDistributedTopicBean)this.udd.getUDestBean()).getForwardingPolicy() : JMSConstants.FORWARDING_POLICY_REPLICATED;
   }

   public void removeDestinationKey(String destinationKey) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public void setAttachSender(String attachSender) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setSAFExportPolicy(String safExportPolicy) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setConsumptionPausedAtStartup(boolean consumptionPausedAtStartup) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setDestinationKeys(String[] destinationKeyArray) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setJMSCreateDestinationIdentifier(String destinationName) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setInsertionPausedAtStartup(boolean insertionPausedAtStartup) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setJNDIName(String jndiName) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setLocalJNDIName(String localJndiName) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setMaximumMessageSize(int maximumMessageSize) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setMessagingPerformancePreference(int throughputEmphasis) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setProductionPausedAtStartup(boolean productionPausedAtStartup) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setQuota(QuotaBean quota) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setTemplate(TemplateBean template) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public String getSubDeploymentName() {
      return this.jmsServerConfigName;
   }

   public boolean isDefaultTargetingEnabled() {
      return this.defaultTargetingEnabled;
   }

   public void setDefaultTargetingEnabled(boolean defaultTargetingEnabled) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setSubDeploymentName(String groupName) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public String getLoadBalancingPolicy() {
      return this.udd.getUDestBean().getLoadBalancingPolicy();
   }

   public void setLoadBalancingPolicy(String loadBalancingPolicy) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public String getUnitOfOrderRouting() {
      return this.udd.getUDestBean().getUnitOfOrderRouting();
   }

   public void setUnitOfOrderRouting(String unitOfOrderRouting) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public String getName() {
      return JMSModuleHelper.uddMakeName(this.jmsServerInstanceName, this.udd.getUDestBean().getName());
   }

   public void setName(String name) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public String getNotes() {
      return this.udd.getUDestBean().getNotes();
   }

   public void setNotes(String notes) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public long getId() {
      return this.udd.getUDestBean().getId();
   }

   public void addPropertyChangeListener(PropertyChangeListener l) {
      throw new UnsupportedOperationException();
   }

   public void removePropertyChangeListener(PropertyChangeListener l) {
      throw new UnsupportedOperationException();
   }

   public void addBeanUpdateListener(BeanUpdateListener listener) {
      this.listeners.add(listener);
   }

   public Descriptor getDescriptor() {
      return null;
   }

   public DescriptorBean getParentBean() {
      return ((DescriptorBean)this.udd.getUDestBean()).getParentBean();
   }

   public boolean isEditable() {
      return false;
   }

   public boolean isSet(String propertyName) {
      return false;
   }

   public void removeBeanUpdateListener(BeanUpdateListener listener) {
      this.listeners.remove(listener);
   }

   public void unSet(String propertyName) {
   }

   public DescriptorBean createChildCopy(String propertyName, DescriptorBean beanToCopy) {
      throw new UnsupportedOperationException();
   }

   public DescriptorBean createChildCopyIncludingObsolete(String propertyName, DescriptorBean beanToCopy) {
      throw new UnsupportedOperationException();
   }

   public void setDefaultUnitOfOrder(boolean defaultUnitOfOrderFlag) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public boolean isDefaultUnitOfOrder() {
      return this.udd.getUDestBean().isDefaultUnitOfOrder();
   }

   public DestinationBean getErrorDestination() {
      DestinationBean edBean = this.udd.getUDestBean().getDeliveryFailureParams().getErrorDestination();
      return edBean == null ? null : new SyntheticErrorDestinationBean(this.jmsServerInstanceName, edBean.getName());
   }

   public int getRedeliveryLimit() {
      return this.udd.getUDestBean().getDeliveryFailureParams().getRedeliveryLimit();
   }

   public String getExpirationPolicy() {
      return this.udd.getUDestBean().getDeliveryFailureParams().getExpirationPolicy();
   }

   public String getExpirationLoggingPolicy() {
      return this.udd.getUDestBean().getDeliveryFailureParams().getExpirationLoggingPolicy();
   }

   public String findSubDeploymentName() {
      return this.udd.getUDestBean().getDeliveryFailureParams().findSubDeploymentName();
   }

   public TemplateBean getTemplateBean() {
      return this.udd.getUDestBean().getDeliveryFailureParams().getTemplateBean();
   }

   public void setErrorDestination(DestinationBean errorDestination) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setRedeliveryLimit(int redeliveryLimit) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setExpirationPolicy(String expirationPolicy) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setExpirationLoggingPolicy(String expirationLoggingPolicy) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public void setUnitOfWorkHandlingPolicy(String unitOfWorkHandlingPolicy) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public String getUnitOfWorkHandlingPolicy() {
      return this.udd.getUDestBean().getUnitOfWorkHandlingPolicy();
   }

   public int getIncompleteWorkExpirationTime() {
      return this.udd.getUDestBean().getIncompleteWorkExpirationTime();
   }

   public void setIncompleteWorkExpirationTime(int incompleteWorkExpirationTime) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public boolean isJMSResourceDefinition() {
      return this.udd.isJMSResourceDefinition();
   }

   public String getUDDestinationName() {
      return this.udd.getUDestBean().getName();
   }
}
