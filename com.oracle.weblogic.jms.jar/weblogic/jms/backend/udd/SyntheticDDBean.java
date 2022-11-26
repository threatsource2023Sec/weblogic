package weblogic.jms.backend.udd;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.LinkedList;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBean;
import weblogic.j2ee.descriptor.wl.DeliveryParamsOverridesBean;
import weblogic.j2ee.descriptor.wl.MessageLoggingParamsBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.j2ee.descriptor.wl.ThresholdParamsBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedDestinationBean;

public class SyntheticDDBean implements UniformDistributedDestinationBean, DescriptorBean {
   ArrayList members = new ArrayList();
   LinkedList listeners = new LinkedList();
   UDDEntity udd;

   SyntheticDDBean(UDDEntity udd) {
      this.udd = udd;
   }

   private int findMember(String jmsServerName) {
      if (this.members == null) {
         return -1;
      } else {
         for(int i = 0; i < this.members.size(); ++i) {
            SyntheticMemberBean bean = (SyntheticMemberBean)this.members.get(i);
            if (bean.jmsServerName.equals(jmsServerName)) {
               return i;
            }
         }

         return -1;
      }
   }

   SyntheticMemberBean findMemberBean(String jmsServerName) {
      int memberNumber = this.findMember(jmsServerName);
      return memberNumber == -1 ? null : (SyntheticMemberBean)this.members.get(memberNumber);
   }

   void removeMember(String jmsServerName) {
      int member = this.findMember(jmsServerName);
      if (member != -1) {
         this.members.remove(member);
      }
   }

   SyntheticMemberBean addMember(String jmsServerName) {
      int member = this.findMember(jmsServerName);
      if (member != -1) {
         throw new AssertionError("Same server added twice?");
      } else {
         SyntheticMemberBean memberBean = new SyntheticMemberBean(this.udd, jmsServerName);
         this.members.add(memberBean);
         return memberBean;
      }
   }

   public String getName() {
      return this.udd.getEntityName();
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

   public String getJNDIName() {
      return this.udd.getUDestBean().getJNDIName();
   }

   public void setJNDIName(String name) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public String getLocalJNDIName() {
      return this.udd.getUDestBean().getLocalJNDIName();
   }

   public void setLocalJNDIName(String name) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public String getSubDeploymentName() {
      return this.udd.getUDestBean().getSubDeploymentName();
   }

   public void setSubDeploymentName(String name) {
      throw new AssertionError("Don't want to modify fake bean");
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
      throw new AssertionError("Don't want to modify fake bean");
   }

   public DescriptorBean createChildCopy(String propertyName, DescriptorBean beanToCopy) {
      throw new UnsupportedOperationException();
   }

   public DescriptorBean createChildCopyIncludingObsolete(String propertyName, DescriptorBean beanToCopy) {
      throw new UnsupportedOperationException();
   }

   public String getSAFExportPolicy() {
      return null;
   }

   public void setSAFExportPolicy(String safExportPolicy) {
   }

   public boolean isDefaultTargetingEnabled() {
      return this.udd.getUDestBean().isDefaultTargetingEnabled();
   }

   public void setDefautTargetingEnabled(boolean defaultTargetingEnabled) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public void addDestinationKey(String destinationKey) {
      this.udd.getUDestBean().addDestinationKey(destinationKey);
   }

   public String getAttachSender() {
      return this.udd.getUDestBean().getAttachSender();
   }

   public DeliveryFailureParamsBean getDeliveryFailureParams() {
      return this.udd.getUDestBean().getDeliveryFailureParams();
   }

   public DeliveryParamsOverridesBean getDeliveryParamsOverrides() {
      return this.udd.getUDestBean().getDeliveryParamsOverrides();
   }

   public String[] getDestinationKeys() {
      return this.udd.getUDestBean().getDestinationKeys();
   }

   public int getIncompleteWorkExpirationTime() {
      return this.udd.getUDestBean().getIncompleteWorkExpirationTime();
   }

   public String getJMSCreateDestinationIdentifier() {
      return this.udd.getUDestBean().getJMSCreateDestinationIdentifier();
   }

   public int getMaximumMessageSize() {
      return this.udd.getUDestBean().getMaximumMessageSize();
   }

   public MessageLoggingParamsBean getMessageLoggingParams() {
      return this.udd.getUDestBean().getMessageLoggingParams();
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

   public String getUnitOfWorkHandlingPolicy() {
      return this.udd.getUDestBean().getUnitOfWorkHandlingPolicy();
   }

   public boolean isConsumptionPausedAtStartup() {
      return this.udd.getUDestBean().isConsumptionPausedAtStartup();
   }

   public boolean isDefaultUnitOfOrder() {
      return this.udd.getUDestBean().isDefaultUnitOfOrder();
   }

   public boolean isInsertionPausedAtStartup() {
      return this.udd.getUDestBean().isInsertionPausedAtStartup();
   }

   public boolean isProductionPausedAtStartup() {
      return this.udd.getUDestBean().isProductionPausedAtStartup();
   }

   public void removeDestinationKey(String destinationKey) {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setAttachSender(String attachSender) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setConsumptionPausedAtStartup(boolean consumptionPausedAtStartup) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setDefaultUnitOfOrder(boolean defaultUnitOfOrderFlag) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setDestinationKeys(String[] destinationKeyArray) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setIncompleteWorkExpirationTime(int incompleteWorkExpirationTime) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setInsertionPausedAtStartup(boolean insertionPausedAtStartup) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setJMSCreateDestinationIdentifier(String destinationName) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setMaximumMessageSize(int maximumMessageSize) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setMessagingPerformancePreference(int throughputEmphasis) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setProductionPausedAtStartup(boolean productionPausedAtStartup) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setQuota(QuotaBean quota) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setTemplate(TemplateBean template) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setUnitOfWorkHandlingPolicy(String unitOfWorkHandlingPolicy) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }

   public void setDefaultTargetingEnabled(boolean defaultTargetingEnabled) throws IllegalArgumentException {
      throw new AssertionError("Cannot write to fake bean");
   }
}
