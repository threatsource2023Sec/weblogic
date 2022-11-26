package weblogic.management.internal.mbean;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.management.Admin;
import weblogic.management.MBeanHome;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.server.GlobalServiceLocator;

public abstract class AbstractDynamicMBean extends AbstractDescriptorBean implements ConfigurationMBeanCustomized, DynamicMBean, MBeanRegistration, NotificationBroadcaster {
   private static volatile RuntimeAccess runtimeAccess;

   public AbstractDynamicMBean() {
   }

   public AbstractDynamicMBean(DescriptorBean parent, int parentPropIdx) {
      super(parent, parentPropIdx);
   }

   public void addNotificationListener(NotificationListener l, NotificationFilter f, Object o) throws IllegalArgumentException {
   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      throw new UnsupportedOperationException("AbstractDynamicMBean.getNotificationInfo()");
   }

   public void removeNotificationListener(NotificationListener l) throws ListenerNotFoundException {
   }

   public MBeanInfo getMBeanInfo() {
      throw new UnsupportedOperationException("AbstractDynamicMBean.getMBeanInfo()");
   }

   public Object getAttribute(String name) throws AttributeNotFoundException, MBeanException, ReflectionException {
      return this.getValue(name);
   }

   public AttributeList getAttributes(String[] list) {
      AttributeList result = new AttributeList(list.length);

      for(int i = 0; i < list.length; ++i) {
         String var10000 = list[i];
         result.add(new Attribute(list[i], this.getValue(list[i])));
      }

      return result;
   }

   public Object invoke(String parm0, Object[] parm1, String[] parm2) throws MBeanException, ReflectionException {
      throw new UnsupportedOperationException("AbstractDynamicMBean.invoke()");
   }

   public void setAttribute(Attribute attr) throws AttributeNotFoundException, InvalidAttributeValueException, ReflectionException {
      this.putValue(attr.getName(), attr.getValue());
   }

   public AttributeList setAttributes(AttributeList list) {
      throw new UnsupportedOperationException("AbstractDynamicMBean.setAttributes()");
   }

   public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception {
      return null;
   }

   public void postDeregister() {
   }

   public void postRegister(Boolean b) {
   }

   public void preDeregister() throws Exception {
   }

   public void touch() throws ConfigurationException {
      throw new UnsupportedOperationException("AbstractDynamicMBean.()");
   }

   public void freezeCurrentValue(String attributeName) throws AttributeNotFoundException, MBeanException {
      throw new UnsupportedOperationException("AbstractDynamicMBean.()");
   }

   public void restoreDefaultValue(String attributeName) throws AttributeNotFoundException {
      throw new UnsupportedOperationException("AbstractDynamicMBean.()");
   }

   public ConfigurationMBean getMbean() {
      return (ConfigurationMBean)this;
   }

   public void putValue(String name, Object v) {
      throw new AssertionError("No such field " + name);
   }

   public void putValueNotify(String name, Object v) {
      this.putValue(name, v);
   }

   public Object getValue(String name) {
      throw new AssertionError("No such field " + name);
   }

   public boolean isConfig() {
      return true;
   }

   public boolean isAdmin() {
      return false;
   }

   public boolean isEdit() {
      Descriptor desc = this.getDescriptor();
      if (desc == null) {
         return false;
      } else {
         DescriptorBean bean = desc.getRootBean();
         return bean != null && bean instanceof DomainMBean ? bean.isEditable() : false;
      }
   }

   private static RuntimeAccess getRuntimeAccess() {
      if (runtimeAccess != null) {
         return runtimeAccess;
      } else {
         Class var0 = RuntimeAccess.class;
         synchronized(RuntimeAccess.class) {
            if (runtimeAccess != null) {
               return runtimeAccess;
            }

            runtimeAccess = (RuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
               public RuntimeAccess run() {
                  ServiceLocator locator = GlobalServiceLocator.getServiceLocator();
                  return (RuntimeAccess)locator.getService(RuntimeAccess.class, new Annotation[0]);
               }
            });
         }

         return runtimeAccess;
      }
   }

   public boolean isRuntime() {
      Descriptor desc = this.getDescriptor();
      if (desc == null) {
         return false;
      } else {
         DescriptorBean bean = desc.getRootBean();
         if (bean != null && bean instanceof DomainMBean) {
            DomainMBean domain = getRuntimeAccess().getDomain();
            return domain == bean;
         } else {
            return false;
         }
      }
   }

   public String getType() {
      String type = this.getClass().getName();
      int idx = type.lastIndexOf(".");
      type = type.substring(idx + 1);
      idx = type.lastIndexOf("MBeanImpl");
      if (idx >= 0) {
         type = type.substring(0, idx);
      }

      return type;
   }

   public MBeanHome getMBeanHome() {
      return Admin.getInstance().getMBeanHome();
   }

   public MBeanHome getAdminMBeanHome() {
      return Admin.getInstance().getAdminMBeanHome();
   }

   public void markAttributeModified(String field) {
   }

   public void sendNotification(Notification notification) {
   }

   protected abstract static class Helper extends AbstractDescriptorBeanHelper {
      protected Helper(AbstractDynamicMBean bean) {
         super(bean);
      }
   }
}
