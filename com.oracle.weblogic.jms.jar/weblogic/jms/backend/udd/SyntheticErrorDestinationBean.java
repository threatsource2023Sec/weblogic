package weblogic.jms.backend.udd;

import java.beans.PropertyChangeListener;
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
import weblogic.jms.extensions.JMSModuleHelper;

public class SyntheticErrorDestinationBean implements DestinationBean {
   String name;

   public SyntheticErrorDestinationBean(String serverName, String fakeName) {
      this.name = JMSModuleHelper.uddMakeName(serverName, fakeName);
   }

   public void addDestinationKey(String destinationKey) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public String getAttachSender() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public DeliveryFailureParamsBean getDeliveryFailureParams() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public MessageLoggingParamsBean getMessageLoggingParams() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public DeliveryParamsOverridesBean getDeliveryParamsOverrides() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public String[] getDestinationKeys() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public String getJMSCreateDestinationIdentifier() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public String getJNDIName() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public String getLocalJNDIName() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public int getMaximumMessageSize() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public int getMessagingPerformancePreference() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public QuotaBean getQuota() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public TemplateBean getTemplate() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public ThresholdParamsBean getThresholds() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public String getUnitOfWorkHandlingPolicy() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public boolean isConsumptionPausedAtStartup() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public boolean isInsertionPausedAtStartup() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public boolean isProductionPausedAtStartup() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public String getSAFExportPolicy() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
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
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public void setSubDeploymentName(String groupName) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public boolean isDefaultTargetingEnabled() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public void setDefaultTargetingEnabled(boolean defaultTargetingEnabled) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public String getLoadBalancingPolicy() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public void setLoadBalancingPolicy(String loadBalancingPolicy) {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public String getUnitOfOrderRouting() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public void setUnitOfOrderRouting(String unitOfOrderRouting) {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public String getNotes() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public void setNotes(String notes) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public long getId() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public void addPropertyChangeListener(PropertyChangeListener l) {
      throw new UnsupportedOperationException();
   }

   public void removePropertyChangeListener(PropertyChangeListener l) {
      throw new UnsupportedOperationException();
   }

   public void addBeanUpdateListener(BeanUpdateListener listener) {
      throw new UnsupportedOperationException();
   }

   public Descriptor getDescriptor() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public DescriptorBean getParentBean() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public boolean isEditable() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public boolean isSet(String propertyName) {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public void removeBeanUpdateListener(BeanUpdateListener listener) {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public void unSet(String propertyName) {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
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
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public void setUnitOfWorkHandlingPolicy(String unitOfWorkHandlingPolicy) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }

   public int getIncompleteWorkExpirationTime() {
      throw new AssertionError("The error destination bean does not support any operation except getName()");
   }

   public void setIncompleteWorkExpirationTime(int incompleteWorkExpirationTime) throws IllegalArgumentException {
      throw new IllegalArgumentException("Don't want to modify fake bean");
   }
}
