package weblogic.management.mbeanservers.internal;

import java.io.IOException;
import java.security.AccessController;
import java.util.Set;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistrationException;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import weblogic.management.EditSessionTool;
import weblogic.management.jmx.mbeanserver.WLSMBeanServerInterceptorBase;
import weblogic.management.provider.EditAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class EditSessionContextInterceptor extends WLSMBeanServerInterceptorBase {
   private final EditAccess editAccess;
   private final EditSessionTool editSessionTool;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public EditSessionContextInterceptor(EditAccess editAccess, EditSessionTool editSessionTool) {
      this.editAccess = editAccess;
      this.editSessionTool = editSessionTool;
   }

   public Object instantiate(String className) throws ReflectionException, MBeanException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      Object var2;
      try {
         var2 = super.instantiate(className);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var2;
   }

   public Object instantiate(String className, ObjectName loaderName) throws ReflectionException, MBeanException, InstanceNotFoundException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      Object var3;
      try {
         var3 = super.instantiate(className, loaderName);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var3;
   }

   public Object instantiate(String className, Object[] params, String[] signature) throws ReflectionException, MBeanException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      Object var4;
      try {
         var4 = super.instantiate(className, params, signature);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var4;
   }

   public Object instantiate(String className, ObjectName loaderName, Object[] params, String[] signature) throws ReflectionException, MBeanException, InstanceNotFoundException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      Object var5;
      try {
         var5 = super.instantiate(className, loaderName, params, signature);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var5;
   }

   public ObjectInstance createMBean(String s, ObjectName objectName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      ObjectInstance var3;
      try {
         var3 = super.createMBean(s, objectName);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var3;
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, ObjectName objectName1) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      ObjectInstance var4;
      try {
         var4 = super.createMBean(s, objectName, objectName1);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var4;
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, Object[] objects, String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      ObjectInstance var5;
      try {
         var5 = super.createMBean(s, objectName, objects, strings);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var5;
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, ObjectName objectName1, Object[] objects, String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      ObjectInstance var6;
      try {
         var6 = super.createMBean(s, objectName, objectName1, objects, strings);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var6;
   }

   public void unregisterMBean(ObjectName objectName) throws InstanceNotFoundException, MBeanRegistrationException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      try {
         super.unregisterMBean(objectName);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

   }

   public ObjectInstance getObjectInstance(ObjectName objectName) throws InstanceNotFoundException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      ObjectInstance var2;
      try {
         var2 = super.getObjectInstance(objectName);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var2;
   }

   public Set queryMBeans(ObjectName objectName, QueryExp queryExp) throws IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      Set var3;
      try {
         var3 = super.queryMBeans(objectName, queryExp);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var3;
   }

   public Set queryNames(ObjectName objectName, QueryExp queryExp) throws IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      Set var3;
      try {
         var3 = super.queryNames(objectName, queryExp);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var3;
   }

   public boolean isRegistered(ObjectName objectName) throws IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      boolean var2;
      try {
         var2 = super.isRegistered(objectName);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var2;
   }

   public Object getAttribute(ObjectName objectName, String s) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      Object var3;
      try {
         var3 = super.getAttribute(objectName, s);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var3;
   }

   public AttributeList getAttributes(ObjectName objectName, String[] strings) throws InstanceNotFoundException, ReflectionException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      AttributeList var3;
      try {
         var3 = super.getAttributes(objectName, strings);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var3;
   }

   public void setAttribute(ObjectName objectName, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      try {
         super.setAttribute(objectName, attribute);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

   }

   public AttributeList setAttributes(ObjectName objectName, AttributeList attributeList) throws InstanceNotFoundException, ReflectionException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      AttributeList var3;
      try {
         var3 = super.setAttributes(objectName, attributeList);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var3;
   }

   public Object invoke(ObjectName objectName, String s, Object[] objects, String[] strings) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      Object var5;
      try {
         var5 = super.invoke(objectName, s, objects, strings);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var5;
   }

   public void addNotificationListener(ObjectName objectName, NotificationListener notificationListener, NotificationFilter notificationFilter, Object o) throws InstanceNotFoundException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      try {
         super.addNotificationListener(objectName, notificationListener, notificationFilter, o);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

   }

   public void addNotificationListener(ObjectName objectName, ObjectName objectName1, NotificationFilter notificationFilter, Object o) throws InstanceNotFoundException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      try {
         super.addNotificationListener(objectName, objectName1, notificationFilter, o);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

   }

   public void removeNotificationListener(ObjectName objectName, ObjectName objectName1) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      try {
         super.removeNotificationListener(objectName, objectName1);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

   }

   public void removeNotificationListener(ObjectName objectName, ObjectName objectName1, NotificationFilter notificationFilter, Object o) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      try {
         super.removeNotificationListener(objectName, objectName1, notificationFilter, o);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

   }

   public void removeNotificationListener(ObjectName objectName, NotificationListener notificationListener) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      try {
         super.removeNotificationListener(objectName, notificationListener);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

   }

   public void removeNotificationListener(ObjectName objectName, NotificationListener notificationListener, NotificationFilter notificationFilter, Object o) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      try {
         super.removeNotificationListener(objectName, notificationListener, notificationFilter, o);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

   }

   public MBeanInfo getMBeanInfo(ObjectName objectName) throws InstanceNotFoundException, IntrospectionException, ReflectionException, IOException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      MBeanInfo var2;
      try {
         var2 = super.getMBeanInfo(objectName);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var2;
   }

   public ObjectInstance registerMBean(Object object, ObjectName name) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      ObjectInstance var3;
      try {
         var3 = super.registerMBean(object, name);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var3;
   }

   public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      try {
         super.addNotificationListener(listener, filter, handback);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

   }

   public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      try {
         super.removeNotificationListener(listener);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

   }

   public void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws ListenerNotFoundException {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      try {
         super.removeNotificationListener(listener, filter, handback);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      MBeanNotificationInfo[] var1;
      try {
         var1 = super.getNotificationInfo();
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

      return var1;
   }

   public void sendNotification(Notification notification) {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      try {
         super.sendNotification(notification);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

   }

   protected void handleNotification(NotificationListener listener, Notification notif, Object handback) {
      this.editSessionTool.pushEditContext(KERNEL_ID, this.editAccess);

      try {
         super.handleNotification(listener, notif, handback);
      } finally {
         this.editSessionTool.popEditContext(KERNEL_ID);
      }

   }
}
