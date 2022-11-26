package weblogic.management.runtime;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.WebLogicObjectName;
import weblogic.management.provider.RegistrationManager;
import weblogic.utils.Debug;

@Contract
public class RuntimeMBeanDelegate implements RuntimeMBean {
   private transient Map metaData;
   protected RuntimeMBeanDelegate parent;
   private RuntimeMBeanDelegate restParent;
   private static RuntimeMBeanHelper runtimeHelper = null;
   protected boolean registered;
   protected DescriptorBean descriptor;
   protected String type;
   protected String name;
   private PropertyChangeSupport changeSupport;
   private String parentAttribute;
   private HashSet children;

   public String getType() {
      return this.type;
   }

   public String getParentAttribute() {
      if (this.parentAttribute != null) {
         return this.parentAttribute;
      } else if (this.parent == null) {
         return this.type;
      } else {
         Class parentClass = this.parent.getClass();

         try {
            parentClass.getMethod("get" + this.type, (Class[])null);
            this.parentAttribute = this.type;
            return this.type;
         } catch (NoSuchMethodException var6) {
            String possibleParentAttribute = pluralize(this.type);

            try {
               parentClass.getMethod("get" + possibleParentAttribute, (Class[])null);
               this.parentAttribute = possibleParentAttribute;
            } catch (NoSuchMethodException var5) {
               throw new RuntimeException("Unable to determine parent type for " + this.type + " parent " + parentClass);
            }

            return this.parentAttribute;
         }
      }
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      throw new UnsupportedOperationException("Not Supported");
   }

   public WebLogicMBean getParent() {
      return this.parent;
   }

   public WebLogicMBean getRestParent() {
      return (WebLogicMBean)(this.restParent != null ? this.restParent : this.getParent());
   }

   public void setParent(WebLogicMBean parent) {
      this.parent = (RuntimeMBeanDelegate)parent;
   }

   public void setRestParent(WebLogicMBean parent) {
      this.restParent = (RuntimeMBeanDelegate)parent;
   }

   public WebLogicObjectName getObjectName() {
      throw new AssertionError("Not Supported");
   }

   public boolean isRegistered() {
      return this.registered;
   }

   public boolean isCachingDisabled() {
      return true;
   }

   public final void addPropertyChangeListener(PropertyChangeListener listener) {
      this.changeSupport.addPropertyChangeListener(listener);
   }

   public final void removePropertyChangeListener(PropertyChangeListener listener) {
      if (this.changeSupport != null) {
         this.changeSupport.removePropertyChangeListener(listener);
      }

   }

   public final PropertyChangeListener[] getPropertyChangeListeners() {
      return this.changeSupport != null ? this.changeSupport.getPropertyChangeListeners() : null;
   }

   public RuntimeMBeanDelegate() throws ManagementException {
      this(runtimeHelper.getServerName());
   }

   public RuntimeMBeanDelegate(String nameArg) throws ManagementException {
      this(nameArg, runtimeHelper.getDefaultParent());
   }

   public RuntimeMBeanDelegate(String nameArg, RuntimeMBean parentArg) throws ManagementException {
      this(nameArg, parentArg, true);
   }

   public RuntimeMBeanDelegate(String nameArg, boolean registerNow, String parentAttribute) throws ManagementException {
      this(nameArg, runtimeHelper.getDefaultParent(), registerNow, parentAttribute);
   }

   public RuntimeMBeanDelegate(String nameArg, boolean registerNow) throws ManagementException {
      this(nameArg, registerNow, (String)null);
   }

   public RuntimeMBeanDelegate(String nameArg, RuntimeMBean parentArg, boolean registerNow) throws ManagementException {
      this(nameArg, parentArg, registerNow, (DescriptorBean)null, (String)null);
   }

   public RuntimeMBeanDelegate(String nameArg, RuntimeMBean parentArg, boolean registerNow, String parentAttribute) throws ManagementException {
      this(nameArg, parentArg, registerNow, (DescriptorBean)null, parentAttribute);
   }

   public RuntimeMBeanDelegate(String nameArg, RuntimeMBean parentArg, boolean registerNow, DescriptorBean descriptor) throws ManagementException {
      this(nameArg, parentArg, registerNow, descriptor, (String)null);
   }

   public RuntimeMBeanDelegate(String nameArg, RuntimeMBean parentArg, boolean registerNow, DescriptorBean descriptor, String parentAttribute) throws ManagementException {
      this.parent = null;
      this.restParent = null;
      this.registered = false;
      this.descriptor = null;
      this.type = null;
      this.name = null;
      this.changeSupport = new PropertyChangeSupport(this);
      this.children = new HashSet();
      this.descriptor = descriptor;
      this.parentAttribute = parentAttribute;
      Class mbeanInterface = RuntimeMBeanDelegate.Helper.mbeanType(this.getClass());
      this.parent = (RuntimeMBeanDelegate)parentArg;
      this.type = RuntimeMBeanDelegate.Helper.mbeanType(mbeanInterface.getName());
      if (this.parent == null && runtimeHelper != null && runtimeHelper.isParentRequired(this.type)) {
         this.parent = (RuntimeMBeanDelegate)runtimeHelper.getDefaultParent();
      }

      if (nameArg == null) {
         nameArg = RuntimeMBeanDelegate.Helper.assignAutoName(this.type);
      }

      this.name = nameArg;
      if (this.parent != null) {
         this.parent.addChild(this);
      }

      if (registerNow) {
         this.register();
      }

   }

   public RegistrationManager getRegistrationManager() {
      return this.parent != null ? this.parent.getRegistrationManager() : runtimeHelper.getRegistrationManager();
   }

   public void unregister() throws ManagementException {
      if (this.parent != null && this.parent instanceof RuntimeMBeanDelegate) {
         this.parent.removeChild(this);
      }

      if (this.registered) {
         try {
            this.getRegistrationManager().unregisterBeanRelationship(this);
         } catch (Error var3) {
            Throwable cause = var3.getCause();
            if (cause != null && cause instanceof ManagementException) {
               throw (ManagementException)cause;
            }

            throw var3;
         }
      }

      this.registered = false;
      this.unregisterChildren();
   }

   public void register() throws ManagementException {
      if (!this.registered) {
         if (runtimeHelper != null) {
            if (runtimeHelper.isParentRequired((RuntimeMBean)this) && this.parent == null) {
               Debug.say("WARNING: " + this + " is being registered without a parent.");
            }

            try {
               if (this.getRegistrationManager() != null) {
                  this.getRegistrationManager().registerBeanRelationship(this, this.descriptor);
               }
            } catch (Error var3) {
               Throwable cause = var3.getCause();
               if (cause != null && cause instanceof ManagementException) {
                  throw (ManagementException)cause;
               }

               throw var3;
            }

            this.registered = true;
         }
      }
   }

   protected void addChild(RuntimeMBeanDelegate child) {
      synchronized(this.children) {
         this.children.add(child);
      }
   }

   public void removeChild(RuntimeMBeanDelegate child) {
      synchronized(this.children) {
         this.children.remove(child);
      }
   }

   private void unregisterChildren() {
      if (this.children != null) {
         ArrayList stable;
         synchronized(this.children) {
            stable = new ArrayList(this.children);
            this.children.clear();
         }

         Iterator var2 = stable.iterator();

         while(var2.hasNext()) {
            RuntimeMBeanDelegate child = (RuntimeMBeanDelegate)var2.next();

            try {
               child.unregister();
            } catch (ManagementException var5) {
            }
         }

      }
   }

   public boolean isEditable() {
      return true;
   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return null;
   }

   protected void _postSet(String propertyName, Object oldVal, Object newVal) {
      if (!newVal.equals(oldVal)) {
         if (this.changeSupport != null) {
            this.changeSupport.firePropertyChange(propertyName, oldVal, newVal);
         }

      }
   }

   protected void _postSet(String propertyName, int oldVal, int newVal) {
      if (newVal != oldVal) {
         if (this.changeSupport != null) {
            this.changeSupport.firePropertyChange(propertyName, oldVal, newVal);
         }

      }
   }

   protected void _postSet(String propertyName, long oldVal, long newVal) {
      if (newVal != oldVal) {
         if (this.changeSupport != null) {
            this.changeSupport.firePropertyChange(propertyName, new Long(oldVal), new Long(newVal));
         }

      }
   }

   protected void _postSet(String propertyName, double oldVal, double newVal) {
      if (newVal != oldVal) {
         if (this.changeSupport != null) {
            this.changeSupport.firePropertyChange(propertyName, new Double(oldVal), new Double(newVal));
         }

      }
   }

   protected void _postSet(String propertyName, boolean oldVal, boolean newVal) {
      if (newVal != oldVal) {
         if (this.changeSupport != null) {
            this.changeSupport.firePropertyChange(propertyName, oldVal, newVal);
         }

      }
   }

   public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {
      throw new UnsupportedOperationException("RuntimeMBeanDelegate.addNotificationListener()");
   }

   public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
      throw new UnsupportedOperationException("RuntimeMBeanDelegate.removeNotificationListener()");
   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      throw new UnsupportedOperationException("RuntimeMBeanDelegate.getNotificationInfo()");
   }

   public MBeanInfo getMBeanInfo() {
      throw new UnsupportedOperationException("RuntimeMBeanDelegate.getMBeanInfo()");
   }

   public Object getAttribute(String name) throws AttributeNotFoundException, MBeanException, ReflectionException {
      throw new UnsupportedOperationException("RuntimeMBeanDelegate.getAttribute");
   }

   public AttributeList getAttributes(String[] list) {
      throw new UnsupportedOperationException("RuntimeMBeanDelegate.getAttributes");
   }

   public Object invoke(String parm0, Object[] parm1, String[] parm2) throws MBeanException, ReflectionException {
      throw new UnsupportedOperationException("RuntimeMBeanDelegate.invoke()");
   }

   public void setAttribute(Attribute attr) throws AttributeNotFoundException, InvalidAttributeValueException, ReflectionException {
      throw new UnsupportedOperationException("RuntimeMBeanDelegate.seAttribute");
   }

   public AttributeList setAttributes(AttributeList list) {
      throw new UnsupportedOperationException("RuntimeMBeanDelegate.setAttributes()");
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

   public DescriptorBean createChildCopy(String propertyName, DescriptorBean beanToCopy) {
      throw new UnsupportedOperationException();
   }

   public static String pluralize(String name) {
      String result = null;
      if (!name.endsWith("s") && !name.endsWith("ch") && !name.endsWith("x") && !name.endsWith("sh")) {
         if (name.endsWith("y") && !name.endsWith("ay") && !name.endsWith("ey") && !name.endsWith("iy") && !name.endsWith("oy") && !name.endsWith("uy")) {
            result = name.substring(0, name.length() - 1) + "ies";
         } else {
            result = name + "s";
         }
      } else {
         result = name + "es";
      }

      return result;
   }

   public static void setRuntimeMBeanHelper(RuntimeMBeanHelper helper) {
      runtimeHelper = helper;
   }

   public Object getMetaData(Object key) {
      return this.metaData == null ? null : this.metaData.get(key);
   }

   public Object setMetaData(Object key, Object value) {
      if (this.metaData == null) {
         this.metaData = new HashMap();
      }

      return this.metaData.put(key, value);
   }

   private static class Helper {
      private static Map autoNameMap = new HashMap();

      public static Class mbeanType(Class cls) {
         if (cls == null) {
            return null;
         } else {
            Class[] interfaces = cls.getInterfaces();

            for(int i = 0; i < interfaces.length; ++i) {
               if (interfaces[i].getName().indexOf("MBean") > -1) {
                  return interfaces[i];
               }
            }

            return mbeanType(cls.getSuperclass());
         }
      }

      private static String mbeanType(String fullClassName) {
         String result = trimPackage(fullClassName);
         if (result.endsWith("MBean")) {
            result = result.substring(0, result.length() - 5);
         }

         return result;
      }

      private static synchronized String assignAutoName(String type) {
         Integer num = (Integer)autoNameMap.get(type);
         if (num == null) {
            num = new Integer(0);
         } else {
            num = new Integer(num + 1);
         }

         autoNameMap.put(type, num);
         return type + "-" + num;
      }

      private static String trimPackage(String className) {
         int index = className.lastIndexOf(46);
         int len = className.length();
         if (index != -1) {
            className = className.substring(index + 1, len);
         }

         return className;
      }
   }
}
