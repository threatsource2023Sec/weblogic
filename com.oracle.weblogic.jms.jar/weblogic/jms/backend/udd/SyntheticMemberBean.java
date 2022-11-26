package weblogic.jms.backend.udd;

import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.jms.backend.MemberBeanWithTargeting;
import weblogic.jms.extensions.JMSModuleHelper;

public class SyntheticMemberBean implements MemberBeanWithTargeting, DescriptorBean {
   LinkedList listeners = new LinkedList();
   String jmsServerName;
   UDDEntity udd;

   public SyntheticMemberBean(UDDEntity udd, String jmsServerName) {
      this.udd = udd;
      this.jmsServerName = jmsServerName;
   }

   public String getServerName() {
      return this.jmsServerName;
   }

   public String getMigratableTargetName() {
      return null;
   }

   public String getClusterName() {
      return this.udd.getUDestBean().getSubDeploymentName();
   }

   public int getWeight() {
      return 1;
   }

   public void setWeight(int weight) throws IllegalArgumentException {
      throw new IllegalArgumentException("Cannot set weight on a uniform distributed destination");
   }

   public String getName() {
      return JMSModuleHelper.uddMakeName(this.jmsServerName, this.udd.getUDestBean().getName());
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

   public String getPhysicalDestinationName() {
      return this.getName();
   }

   public void setPhysicalDestinationName(String name) {
      this.setName(name);
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
      return this.udd.getDDBean();
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
}
