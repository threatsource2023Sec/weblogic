package weblogic.management.provider.custom;

import java.beans.PropertyChangeListener;
import java.security.AccessController;
import java.util.HashSet;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.WebLogicObjectName;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ConfigurationMBeanBase implements ConfigurationMBean {
   private String name;
   private String type;
   private ConfigurationMBean parent;
   private WebLogicObjectName objectName;
   private String notes;
   private String comments;
   private boolean registered;
   private long id;
   private HashSet tags;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void unregister() {
      if (this.registered) {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         runtimeAccess.unregisterCustomBean(this.objectName);
         this.registered = false;
      }
   }

   public ConfigurationMBeanBase(String name, String type, ConfigurationMBean parent) {
      this.name = name;
      this.type = type;
      this.parent = parent;
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);

      try {
         if (parent != null) {
            this.objectName = new WebLogicObjectName(name, type, runtimeAccess.getDomainName(), parent.getObjectName());
         } else {
            this.objectName = new WebLogicObjectName(name, type, runtimeAccess.getDomainName());
         }
      } catch (MalformedObjectNameException var6) {
         throw new RuntimeException(var6);
      }

      runtimeAccess.registerCustomBean(this.objectName, this);
      this.registered = true;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) throws InvalidAttributeValueException, ManagementException {
      this.name = name;
   }

   public String getType() {
      return this.type;
   }

   public WebLogicObjectName getObjectName() {
      return this.objectName;
   }

   public DescriptorBean getParentBean() {
      return this.parent;
   }

   public WebLogicMBean getParent() {
      return this.parent;
   }

   public void setParent(WebLogicMBean parent) throws ConfigurationException {
      this.parent = (ConfigurationMBean)parent;
   }

   public boolean isRegistered() {
      return this.registered;
   }

   public String getComments() {
      return this.comments;
   }

   public void setComments(String comments) {
      this.comments = comments;
   }

   public String getNotes() {
      return this.notes;
   }

   public void setNotes(String notes) throws InvalidAttributeValueException, DistributedManagementException {
      this.notes = notes;
   }

   public String[] getTags() {
      return this.tags == null ? null : (String[])this.tags.toArray(new String[0]);
   }

   public void setTags(String[] tagArray) {
      if (this.tags == null) {
         this.tags = new HashSet();
      }

      String[] var2 = tagArray;
      int var3 = tagArray.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String t = var2[var4];
         this.tags.add(t);
      }

   }

   public boolean addTag(String tag) {
      if (this.tags == null) {
         this.tags = new HashSet();
      }

      return this.tags.add(tag);
   }

   public boolean removeTag(String tag) {
      boolean isTagRemoved = false;
      if (this.tags != null) {
         isTagRemoved = this.tags.remove(tag);
      }

      return isTagRemoved;
   }

   public Descriptor getDescriptor() {
      return null;
   }

   public boolean isSet(String propertyName) throws IllegalArgumentException {
      return false;
   }

   public void unSet(String propertyName) throws IllegalArgumentException {
   }

   public void addPropertyChangeListener(PropertyChangeListener listener) {
   }

   public void removePropertyChangeListener(PropertyChangeListener listener) {
   }

   public void addBeanUpdateListener(BeanUpdateListener listener) {
   }

   public void removeBeanUpdateListener(BeanUpdateListener listener) {
   }

   public boolean isEditable() {
      return true;
   }

   public DescriptorBean createChildCopy(String propertyName, DescriptorBean beanToCopy) {
      return null;
   }

   public DescriptorBean createChildCopyIncludingObsolete(String propertyName, DescriptorBean beanToCopy) {
      return null;
   }

   public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
      return null;
   }

   public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
   }

   public AttributeList getAttributes(String[] attributes) {
      return null;
   }

   public AttributeList setAttributes(AttributeList attributes) {
      return null;
   }

   public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
      return null;
   }

   public MBeanInfo getMBeanInfo() {
      return null;
   }

   public boolean isCachingDisabled() {
      return false;
   }

   public boolean isPersistenceEnabled() {
      return false;
   }

   public void setPersistenceEnabled(boolean persist) {
   }

   public boolean isDefaultedMBean() {
      return false;
   }

   public void setDefaultedMBean(boolean defaulted) {
   }

   public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception {
      return null;
   }

   public void postRegister(Boolean registrationDone) {
   }

   public void preDeregister() throws Exception {
   }

   public void postDeregister() {
   }

   public void touch() throws ConfigurationException {
   }

   public void freezeCurrentValue(String attributeName) throws AttributeNotFoundException, MBeanException {
   }

   public void restoreDefaultValue(String attributeName) throws AttributeNotFoundException {
   }

   public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {
   }

   public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      return new MBeanNotificationInfo[0];
   }

   public boolean isInherited(String propertyName) throws IllegalArgumentException {
      return false;
   }

   public String[] getInheritedProperties(String[] propertyNames) {
      return null;
   }

   public boolean isDynamicallyCreated() {
      return false;
   }

   public void setId(long idValue) {
      this.id = idValue;
   }

   public long getId() {
      return this.id;
   }
}
