package weblogic.management.internal;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.management.Attribute;
import javax.management.AttributeChangeNotification;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.ReflectionException;
import javax.management.RuntimeErrorException;
import javax.management.RuntimeMBeanException;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.ModelMBeanInfo;
import weblogic.common.internal.PeerInfo;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.AttributeAddNotification;
import weblogic.management.AttributeRemoveNotification;
import weblogic.management.MBeanHome;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.RemoteMBeanServer;
import weblogic.management.WebLogicMBean;
import weblogic.management.WebLogicObjectName;
import weblogic.management.commo.StandardInterface;
import weblogic.management.configuration.ConfigurationError;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.info.ExtendedInfo;
import weblogic.utils.AssertionError;

public class MBeanProxy implements InvocationHandler, Serializable, NotificationBroadcaster {
   private static final long serialVersionUID = -3989625551485752570L;
   protected MBeanHome mbeanHome;
   protected WebLogicObjectName objectName;
   protected transient MBeanInfo info;
   protected transient Map listeners;
   private final boolean cachingDisabled = true;
   private boolean unregistered = false;
   private DynamicMBean delegate;
   private transient RemoteMBeanServer mbeanServer;
   private static final String[] EMPTY_SIGNATURE = new String[0];
   private static final int INITIAL_CAPACITY_FOR_MBEAN_TYPES = 89;
   private static Map mbeanInfos = new HashMap(89);
   private static Map configMBeanInfos = new HashMap(89);
   private static Map adminMBeanInfos = new HashMap(89);
   private static final String ADMIN_CONFIG_PKG_NAME = "weblogic.management.configuration";

   public MBeanProxy(DynamicMBean delegate) {
      this.delegate = delegate;
   }

   public MBeanProxy(ObjectName name, MBeanHome home) {
      this.mbeanHome = home;
      this.objectName = (WebLogicObjectName)name;

      try {
         this.info = this.getMBeanServer().getMBeanInfo(this.objectName);
      } catch (Exception var4) {
         throw new ConfigurationError(var4);
      }
   }

   public final MBeanHome getMBeanHome() {
      return this.mbeanHome;
   }

   public final boolean isUnregistered() {
      return this.unregistered;
   }

   public WebLogicObjectName getObjectName() {
      return this.objectName;
   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      return null;
   }

   public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      try {
         String methodName = method.getName();
         if (method.getDeclaringClass() != Object.class && method.getDeclaringClass() != NotificationBroadcaster.class) {
            if (methodName.equals("isRegistered") && (null == args || args.length == 0) && method.getReturnType() == Boolean.TYPE) {
               return this.isUnregistered() ? Boolean.FALSE : Boolean.TRUE;
            } else if (!methodName.startsWith("get") && !methodName.startsWith("is") || args != null && args.length != 0) {
               if (methodName.equals("setAttributes") && method.getDeclaringClass() == DynamicMBean.class) {
                  return this.setAttributes(args[0]);
               } else if (methodName.equals("setAttribute") && method.getDeclaringClass() == DynamicMBean.class) {
                  return this.setOneAttribute(args[0]);
               } else if (methodName.equals("getAttributes") && method.getDeclaringClass() == DynamicMBean.class) {
                  return this.getAttributes(args[0]);
               } else if (methodName.equals("getAttribute") && method.getDeclaringClass() == DynamicMBean.class) {
                  return this.getOneAttribute(args[0]);
               } else if (methodName.startsWith("set") && args != null && args.length == 1) {
                  return this.setAttribute(methodName, args[0]);
               } else {
                  return methodName.equals("invoke") && method.getDeclaringClass() == DynamicMBean.class ? this.dynamicInvoke(args) : this.invoke(methodName, args);
               }
            } else {
               return this.getAttribute(methodName);
            }
         } else {
            return method.invoke(this, args);
         }
      } catch (MBeanException var5) {
         throw var5.getTargetException();
      } catch (RuntimeMBeanException var6) {
         throw var6.getTargetException();
      } catch (ConfigurationError var7) {
         throw var7;
      } catch (Exception var8) {
         throw var8;
      }
   }

   public final void registerConfigMBean(String configMBeanObjectName, MBeanServer server) {
      if (!WebLogicObjectName.isAdmin(this.objectName)) {
         throw new AssertionError("registerConfigMBean should only be called on AdminMBean, and this is: " + this.objectName);
      } else {
         try {
            this.invokeForCachingStub("registerConfigMBean", new Object[]{configMBeanObjectName, server});
         } catch (Throwable var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            } else {
               throw new RuntimeException(var4);
            }
         }
      }
   }

   public final void unRegisterConfigMBean(String configMBeanObjectName) {
      if (!WebLogicObjectName.isAdmin(this.objectName)) {
         throw new AssertionError("unRegisterConfigMBean should only be called on AdminMBean, and this is: " + this.objectName);
      } else {
         try {
            this.invokeForCachingStub("unRegisterConfigMBean", new Object[]{configMBeanObjectName});
         } catch (Throwable var3) {
            if (var3 instanceof RuntimeException) {
               throw (RuntimeException)var3;
            } else {
               throw new RuntimeException(var3);
            }
         }
      }
   }

   public final void addNotificationListener(NotificationListener listenerArg, NotificationFilter filterArg, Object handbackArg) throws IllegalArgumentException {
      if (this.listeners == null) {
         this.listeners = new HashMap();
      }

      BaseNotificationListener relay = (BaseNotificationListener)this.listeners.get(listenerArg);
      if (relay == null) {
         if (this.objectName instanceof WebLogicObjectName && "LogBroadcasterRuntime".equals(this.objectName.getType())) {
            relay = new OnewayNotificationListenerImpl(this, listenerArg);
         } else {
            relay = new RelayNotificationListenerImpl(this, listenerArg);
         }

         if (null != relay) {
            this.listeners.put(listenerArg, relay);
         }
      }

      ((BaseNotificationListener)relay).addFilterAndHandback(filterArg, handbackArg);
   }

   public final void removeNotificationListener(NotificationListener listenerArg) throws ListenerNotFoundException {
      if (this.listeners == null) {
         throw new ListenerNotFoundException("listener: " + listenerArg);
      } else {
         BaseNotificationListener relay = (BaseNotificationListener)this.listeners.get(listenerArg);
         if (relay == null) {
            throw new ListenerNotFoundException("listener: " + listenerArg);
         } else {
            relay.remove();
            this.listeners.remove(listenerArg);
         }
      }
   }

   public final void sendNotification(NotificationListener listenerArg, Notification notificationArg, Object handbackArg) {
      if (!this.isUnregistered()) {
         Notification notification = null;

         try {
            notification = this.wrapNotification(notificationArg);
         } catch (ManagementException var6) {
            ManagementLogger.logExceptionInMBeanProxy(var6);
            return;
         }

         listenerArg.handleNotification(notification, handbackArg);
      }
   }

   public final Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      return this;
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (other == this) {
         return true;
      } else {
         return other instanceof MBeanProxy && this.equalsMBeanProxy((MBeanProxy)other);
      }
   }

   public int hashCode() {
      int hash = this.objectName.hashCode();
      if (this.mbeanHome != null) {
         hash ^= this.mbeanHome.hashCode();
      }

      return hash;
   }

   public String toString() {
      return "Proxy for " + this.objectName;
   }

   protected final Object invokeForCachingStub(String methodName, Object[] args) throws Throwable {
      try {
         if (methodName.equals("isRegistered")) {
            return this.isUnregistered() ? Boolean.FALSE : Boolean.TRUE;
         } else if (methodName.equals("getObjectName")) {
            return this.objectName;
         } else if (methodName.equals("getName")) {
            return this.objectName.getName();
         } else if (methodName.equals("getType")) {
            return this.objectName.getType();
         } else if (methodName.equals("getMBeanInfo")) {
            return this.info;
         } else if (methodName.equals("isCachingDisabled")) {
            return Boolean.TRUE;
         } else if (!methodName.startsWith("get") && !methodName.startsWith("is") || args != null && args.length != 0) {
            if (methodName.equals("addNotificationListener")) {
               System.out.println("addNotificationListener in invokeForCachingStub");
               this.addNotificationListener((NotificationListener)args[0], (NotificationFilter)args[1], args[2]);
               return null;
            } else if (methodName.equals("removeNotificationListener")) {
               this.removeNotificationListener((NotificationListener)args[0]);
               return null;
            } else if (methodName.equals("getNotificationInfo")) {
               return this.getNotificationInfo();
            } else if (methodName.equals("setAttributes")) {
               return this.setAttributes(args[0]);
            } else if (methodName.equals("setAttribute")) {
               return this.setOneAttribute(args[0]);
            } else if (methodName.equals("getAttributes")) {
               return this.getAttributes(args[0]);
            } else if (methodName.equals("getAttribute")) {
               return this.getOneAttribute(args[0]);
            } else if (methodName.startsWith("set") && args != null && args.length == 1) {
               return this.setAttribute(methodName, args[0]);
            } else {
               return methodName.equals("invoke") ? this.dynamicInvoke(args) : this.invoke(methodName, args);
            }
         } else {
            return this.getAttribute(methodName);
         }
      } catch (MBeanException var4) {
         throw var4.getTargetException();
      } catch (RuntimeMBeanException var5) {
         throw var5.getTargetException();
      } catch (ConfigurationError var6) {
         throw var6;
      } catch (Exception var7) {
         throw var7;
      }
   }

   protected void readObjectForCachingStubs(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      this.objectName = (WebLogicObjectName)stream.readObject();
      this.mbeanHome = (MBeanHome)stream.readObject();
   }

   protected void writeObjectForCachingStubs(ObjectOutputStream stream) throws IOException {
      stream.writeObject(this.objectName);
      stream.writeObject(this.mbeanHome);
   }

   void unregister() {
      if (!this.isUnregistered()) {
         this.unregistered = true;
         if (this.listeners != null) {
            Iterator it = (new HashSet(this.listeners.keySet())).iterator();

            while(it.hasNext()) {
               try {
                  this.removeNotificationListener((NotificationListener)it.next());
               } catch (ListenerNotFoundException var3) {
               }
            }
         }

         this.info = null;
         this.mbeanHome = null;
         this.info = null;
      }
   }

   private RemoteMBeanServer getMBeanServer() {
      return this.mbeanServer != null ? this.mbeanServer : (this.mbeanServer = this.mbeanHome.getMBeanServer());
   }

   private Object getAttribute(String methodName) throws AttributeNotFoundException, InstanceNotFoundException, ReflectionException, MBeanException {
      String attributeName = null;
      if (methodName.charAt(0) == 'g') {
         attributeName = methodName.substring(3);
      } else {
         attributeName = methodName.substring(2);
      }

      if (this.delegate != null) {
         try {
            return this.wrap(attributeName, this.delegate.getAttribute(attributeName));
         } catch (ManagementException var6) {
            throw new MBeanException(var6);
         }
      } else if (this.mbeanHome == null) {
         throw new InstanceNotFoundException(this.objectName.toString());
      } else {
         RemoteMBeanServer server = this.getMBeanServer();
         Object value = server.getAttribute(this.objectName, attributeName);
         if ((this.unregistered || value == null) && this.mbeanHome.getMBean(this.objectName) == null) {
            throw new InstanceNotFoundException("MBean with " + this.objectName + " has been deleted or unregistered from the server");
         } else {
            try {
               value = this.wrap(attributeName, value);
               return value;
            } catch (ManagementException var7) {
               throw new MBeanException(var7);
            }
         }
      }
   }

   private Object getOneAttribute(Object args) throws AttributeNotFoundException, InstanceNotFoundException, ReflectionException, MBeanException {
      if (this.delegate != null) {
         return this.delegate.getAttribute((String)args);
      } else {
         RemoteMBeanServer server = this.getMBeanServer();
         Object value = server.getAttribute(this.objectName, (String)args);
         return value;
      }
   }

   private Object getAttributes(Object args) throws InstanceNotFoundException, ReflectionException {
      if (this.delegate != null) {
         return this.delegate.getAttributes((String[])((String[])args));
      } else {
         RemoteMBeanServer server = this.getMBeanServer();
         Object value = server.getAttributes(this.objectName, (String[])((String[])args));
         return value;
      }
   }

   private MBeanAttributeInfo getAttributeInfo(String attributeName) throws AttributeNotFoundException {
      if (this.info != null) {
         MBeanAttributeInfo[] attrInfos = this.info.getAttributes();

         for(int i = 0; i < attrInfos.length; ++i) {
            if (attrInfos[i].getName().equals(attributeName)) {
               return attrInfos[i];
            }
         }
      }

      throw new AttributeNotFoundException(attributeName);
   }

   private MBeanOperationInfo getOperationInfo(String methodName, Object[] args) throws OperationsException, ClassNotFoundException {
      MBeanOperationInfo[] opInfos = this.info.getOperations();

      for(int i = 0; i < opInfos.length; ++i) {
         if (opInfos[i].getName().equals(methodName) && isAssignableFrom(args, opInfos[i].getSignature())) {
            return opInfos[i];
         }
      }

      throw new OperationsException("no such operation: " + methodName);
   }

   private final Object setAttribute(String methodName, Object value) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      String attributeName = methodName.substring(3);
      if (this.delegate != null) {
         Attribute attribute = new WebLogicAttribute(attributeName, value);
         this.delegate.setAttribute(attribute);
      } else {
         Object setValue = this.unwrap(value);
         Attribute attribute = new WebLogicAttribute(attributeName, setValue);
         RemoteMBeanServer mbeanServer = this.getMBeanServer();

         try {
            mbeanServer.setAttribute(this.objectName, attribute);
            if (attribute.getName().equals("Parent")) {
               this.setParent((WebLogicMBean)value);
            }
         } catch (RuntimeErrorException var9) {
            ManagementLogger.logMBeanProxySetAttributeError(this.objectName, methodName, value, setValue, var9.getTargetError());
            throw var9;
         } catch (RuntimeOperationsException var10) {
            Exception target = var10.getTargetException();
            ManagementLogger.logExceptionInMBeanProxy(target);
         }
      }

      return null;
   }

   private final Object setOneAttribute(Object args) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      if (this.delegate != null) {
         this.delegate.setAttribute((Attribute)args);
      } else {
         Attribute attribute = (Attribute)args;
         RemoteMBeanServer mbeanServer = this.getMBeanServer();
         mbeanServer.setAttribute(this.objectName, this.unwrap(attribute));
         if (attribute.getName().equals("Parent")) {
            if (this.unwrap(attribute) instanceof WebLogicMBean) {
               this.setParent((WebLogicMBean)this.unwrap(attribute.getValue()));
            } else {
               try {
                  this.objectName = new WebLogicObjectName(this.objectName, (WebLogicObjectName)attribute.getValue());
               } catch (MalformedObjectNameException var5) {
                  throw new ConfigurationError(var5);
               }
            }
         }
      }

      return null;
   }

   private final Object setAttributes(Object args) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      WebLogicMBean parent = null;
      AttributeList retList = null;
      if (this.delegate != null) {
         retList = this.delegate.setAttributes((AttributeList)args);
      } else {
         RemoteMBeanServer mbeanServer = this.getMBeanServer();
         retList = mbeanServer.setAttributes(this.objectName, this.unwrap((AttributeList)args));
      }

      if (parent != null) {
         this.setParent((WebLogicMBean)parent);
      }

      return retList;
   }

   private final void setParent(WebLogicMBean parentArg) {
      try {
         this.objectName = new WebLogicObjectName(this.objectName, parentArg.getObjectName());
      } catch (MalformedObjectNameException var3) {
         throw new ConfigurationError(var3);
      }
   }

   private final boolean equalsMBeanProxy(MBeanProxy proxy) {
      boolean var10000;
      label25: {
         if (this.objectName.equals(proxy.objectName)) {
            if (this.mbeanHome == null) {
               if (proxy.mbeanHome == null) {
                  break label25;
               }
            } else if (this.mbeanHome.equals(proxy.mbeanHome)) {
               break label25;
            }
         }

         var10000 = false;
         return var10000;
      }

      var10000 = true;
      return var10000;
   }

   private Object invoke(String methodName, Object[] args) throws InstanceNotFoundException, MBeanException, ReflectionException, ClassNotFoundException, OperationsException {
      RemoteMBeanServer mbeanServer = this.getMBeanServer();
      MBeanOperationInfo info = this.getOperationInfo(methodName, args);
      Class returnType = findClass(info.getReturnType());
      Object[] unwrapped = this.unwrap(args);

      try {
         String[] signature;
         Object result;
         if (this.delegate != null) {
            signature = getSignature(args);
            result = this.delegate.invoke(methodName, unwrapped, signature);
         } else {
            MBeanParameterInfo[] params = info.getSignature();
            signature = new String[params.length];

            for(int i = 0; i < signature.length; ++i) {
               signature[i] = params[i].getType();
            }

            result = mbeanServer.invoke(this.objectName, methodName, unwrapped, signature);
         }

         return this.wrap(returnType, result);
      } catch (RuntimeErrorException var11) {
         throw var11.getTargetError();
      }
   }

   private Object dynamicInvoke(Object[] args) throws InstanceNotFoundException, MBeanException, ReflectionException, ClassNotFoundException, OperationsException {
      RemoteMBeanServer mbeanServer = this.getMBeanServer();
      MBeanOperationInfo info = this.getOperationInfo((String)args[0], this.wrap((String[])((String[])args[2]), (Object[])((Object[])args[1])));
      Class returnType = findClass(info.getReturnType());
      if (this.delegate != null) {
         return this.wrap(returnType, this.delegate.invoke((String)args[0], this.unwrap((Object[])((Object[])args[1])), (String[])((String[])args[2])));
      } else {
         try {
            return this.wrap(returnType, mbeanServer.invoke(this.objectName, (String)args[0], this.unwrap((Object[])((Object[])args[1])), (String[])((String[])args[2])));
         } catch (RuntimeErrorException var6) {
            throw var6.getTargetError();
         }
      }
   }

   private AttributeList unwrap(AttributeList objects) {
      if (objects == null) {
         return null;
      } else {
         AttributeList result = new AttributeList();
         Iterator it = objects.iterator();

         while(it.hasNext()) {
            Attribute unwrapped = this.unwrap((Attribute)it.next());
            if (null != unwrapped) {
               result.add(unwrapped);
            }
         }

         return result;
      }
   }

   private Attribute unwrap(Attribute object) {
      if (null == object) {
         return null;
      } else {
         String name = object.getName();
         Object value = this.unwrap(object.getValue());
         return null == name ? null : new WebLogicAttribute(name, value);
      }
   }

   private Object[] unwrap(Object[] objects) {
      if (objects == null) {
         return null;
      } else {
         Object[] result = new Object[objects.length];

         for(int i = 0; i < objects.length; ++i) {
            result[i] = this.unwrap(objects[i]);
         }

         return result;
      }
   }

   private Object unwrap(Object object) {
      if (object instanceof WebLogicMBean) {
         return ((WebLogicMBean)object).getObjectName();
      } else {
         int i;
         if (object instanceof WebLogicMBean[]) {
            WebLogicMBean[] values = (WebLogicMBean[])((WebLogicMBean[])object);
            ObjectName[] result = new WebLogicObjectName[values.length];

            for(i = 0; i < result.length; ++i) {
               result[i] = values[i].getObjectName();
            }

            return result;
         } else if (object instanceof StandardInterface) {
            return ((StandardInterface)object).wls_getObjectName();
         } else if (!(object instanceof StandardInterface[])) {
            return object;
         } else {
            StandardInterface[] values = (StandardInterface[])((StandardInterface[])object);
            ObjectName[] result = new ObjectName[values.length];

            for(i = 0; i < result.length; ++i) {
               result[i] = values[i].wls_getObjectName();
            }

            return result;
         }
      }
   }

   private Object[] wrap(String[] signature, Object[] objects) {
      if (objects != null && (objects.length != 1 || objects[0] != WebLogicAttribute.NULL_VALUE)) {
         Object[] result = new Object[objects.length];

         for(int i = 0; i < objects.length; ++i) {
            try {
               Class type = findClass(signature[i]);
               result[i] = this.wrap(type, objects[i]);
            } catch (ClassNotFoundException var7) {
               result[i] = objects[i];
            }
         }

         return result;
      } else {
         return null;
      }
   }

   private Object wrap(Class type, Object value) {
      if (value != null && value != WebLogicAttribute.NULL_VALUE) {
         if (type == Void.class) {
            return null;
         } else if (ObjectName.class.isAssignableFrom(type)) {
            return value;
         } else {
            try {
               if (!(value instanceof ObjectName)) {
                  if (!(value instanceof ObjectName[])) {
                     return value;
                  }

                  Class componentClass = type.getComponentType();
                  Object[] values = (Object[])((Object[])value);
                  int len = values.length;
                  Object[] result = (Object[])((Object[])Array.newInstance(componentClass, len));

                  for(int i = 0; i < values.length; ++i) {
                     Object obj = this.wrap(componentClass, values[i]);
                     result[i] = obj;
                  }

                  return result;
               }

               ObjectName objectName = (ObjectName)value;
               if (objectName instanceof WebLogicObjectName) {
                  Object result = this.mbeanHome.getMBean((ObjectName)value);
                  return result;
               }
            } catch (InstanceNotFoundException var9) {
               return null;
            } catch (Throwable var10) {
               ManagementLogger.logExceptionInMBeanProxy(var10);
            }

            return value;
         }
      } else {
         return null;
      }
   }

   private Object wrap(String attributeName, Object value) throws ManagementException {
      if (value == WebLogicAttribute.NULL_VALUE) {
         return null;
      } else {
         try {
            if (attributeName.equals("ObjectName")) {
               return value;
            } else {
               MBeanAttributeInfo info = this.getAttributeInfo(attributeName);
               Class cls = findClass(info.getType());
               return this.wrap(cls, value);
            }
         } catch (ClassNotFoundException var5) {
            throw new ManagementException("error wrapping " + attributeName + ", value=" + value, var5);
         } catch (AttributeNotFoundException var6) {
            throw new ManagementException("error wrapping " + attributeName + ", value=" + value, var6);
         }
      }
   }

   private Notification wrapNotification(Notification notif) throws ManagementException {
      String attributeType = null;
      String attributeName = null;
      Object result;
      Object oldValue;
      if (notif instanceof AttributeAddNotification) {
         AttributeAddNotification addNotif = (AttributeAddNotification)notif;
         attributeName = addNotif.getAttributeName();
         attributeType = addNotif.getAttributeType();
         oldValue = addNotif.getAddedValue();
         result = new AttributeAddNotification(this.objectName, attributeName, attributeType, this.wrap(attributeName, oldValue));
      } else if (notif instanceof AttributeRemoveNotification) {
         AttributeRemoveNotification remNotif = (AttributeRemoveNotification)notif;
         attributeName = remNotif.getAttributeName();
         attributeType = remNotif.getAttributeType();
         oldValue = remNotif.getRemovedValue();
         result = new AttributeRemoveNotification(this.objectName, attributeName, attributeType, this.wrap(attributeName, oldValue));
      } else if (notif instanceof AttributeChangeNotification) {
         AttributeChangeNotification chNotif = (AttributeChangeNotification)notif;
         attributeName = chNotif.getAttributeName();
         attributeType = chNotif.getAttributeType();
         if (attributeName.equals("ObjectName")) {
            return notif;
         }

         oldValue = chNotif.getOldValue();
         Object newValue = chNotif.getNewValue();
         result = new WebLogicAttributeChangeNotification(this.objectName, attributeName, attributeType, this.wrap(attributeName, oldValue), this.wrap(attributeName, newValue));
      } else {
         result = notif;
      }

      return (Notification)result;
   }

   private void writeObject(ObjectOutputStream stream) throws IOException {
      try {
         stream.defaultWriteObject();
         stream.writeObject(((ModelMBeanInfo)this.info).getMBeanDescriptor().getFieldValue("interfaceclassname"));
         stream.writeBoolean(this.objectName.isAdmin() || this.objectName.isConfig());
      } catch (MBeanException var3) {
         throw new IOException(var3.toString());
      }
   }

   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.info = findExtendedInfo(stream);
   }

   public Descriptor getDescriptor() {
      return null;
   }

   public DescriptorBean getParentBean() {
      return null;
   }

   public boolean isSet(String propertyName) {
      return false;
   }

   public void unSet(String propertyName) {
   }

   public void addBeanUpdateListener(BeanUpdateListener listener) {
   }

   public void removeBeanUpdateListener(BeanUpdateListener listener) {
   }

   public boolean isEditable() {
      return false;
   }

   public Object clone() {
      return null;
   }

   private static boolean isAssignableFrom(Object[] args, MBeanParameterInfo[] sig) throws ClassNotFoundException {
      if (args == null) {
         return sig.length == 0;
      } else if (args.length != sig.length) {
         return false;
      } else {
         Class[] argClasses = new Class[args.length];
         Class[] sigClasses = new Class[sig.length];

         int i;
         for(i = 0; i < args.length; ++i) {
            argClasses[i] = args[i] == null ? null : args[i].getClass();
         }

         for(i = 0; i < args.length; ++i) {
            String type = sig[i].getType();
            sigClasses[i] = type == null ? Void.class : wrapClass(findClass(type));
         }

         for(i = 0; i < argClasses.length; ++i) {
            if (argClasses[i] != null && !sigClasses[i].isAssignableFrom(argClasses[i])) {
               return false;
            }
         }

         return true;
      }
   }

   private static String[] getSignature(Object[] args) {
      String[] signature = null;
      if (args != null) {
         signature = new String[args.length];

         for(int i = 0; i < signature.length; ++i) {
            if (args[i] != null) {
               if (WebLogicMBean.class.isAssignableFrom(args[i].getClass())) {
                  signature[i] = "javax.management.ObjectName";
               } else {
                  signature[i] = args[i].getClass().getName();
               }
            } else {
               signature[i] = "void";
            }
         }
      } else {
         signature = EMPTY_SIGNATURE;
      }

      return signature;
   }

   private static ExtendedInfo findExtendedInfo(ObjectInput in) throws IOException, ClassNotFoundException {
      String n = (String)in.readObject();
      boolean isAdminOrConfig = in.readBoolean();
      if (n == null) {
         return null;
      } else {
         try {
            return isAdminOrConfig && isAdminOrConfigMBeanClass(n) ? getAdminOrConfigMBeanInfo(mbeanType(n)) : getMBeanInfo(findClass(n));
         } catch (ConfigurationException var5) {
            Throwable t = var5.getNestedException();
            if (t instanceof ClassNotFoundException) {
               throw (ClassNotFoundException)t;
            } else if (t instanceof IOException) {
               throw (IOException)t;
            } else {
               throw new IOException(t.toString());
            }
         }
      }
   }

   private static Class findClass(String name) throws ClassNotFoundException {
      if (name.equals(Long.class.getName())) {
         return Long.TYPE;
      } else if (name.equals(Double.class.getName())) {
         return Double.TYPE;
      } else if (name.equals(Float.class.getName())) {
         return Float.TYPE;
      } else if (name.equals(Integer.class.getName())) {
         return Integer.TYPE;
      } else if (name.equals(Character.class.getName())) {
         return Character.TYPE;
      } else if (name.equals(Short.class.getName())) {
         return Short.TYPE;
      } else if (name.equals(Byte.class.getName())) {
         return Byte.TYPE;
      } else if (name.equals(Boolean.class.getName())) {
         return Boolean.TYPE;
      } else if (name.equals(Void.class.getName())) {
         return Void.TYPE;
      } else if (name.equals("long")) {
         return Long.TYPE;
      } else if (name.equals("double")) {
         return Double.TYPE;
      } else if (name.equals("float")) {
         return Float.TYPE;
      } else if (name.equals("int")) {
         return Integer.TYPE;
      } else if (name.equals("char")) {
         return Character.TYPE;
      } else if (name.equals("short")) {
         return Short.TYPE;
      } else if (name.equals("byte")) {
         return Byte.TYPE;
      } else if (name.equals("boolean")) {
         return Boolean.TYPE;
      } else if (name.equals("void")) {
         return Void.TYPE;
      } else if (name.endsWith("[]")) {
         Class componentClass = findClass(name.substring(0, name.length() - 2));
         return Array.newInstance(componentClass, 0).getClass();
      } else {
         return Class.forName(name);
      }
   }

   private static Class wrapClass(Class type) {
      if (type == Long.TYPE) {
         return Long.class;
      } else if (type == Double.TYPE) {
         return Double.class;
      } else if (type == Float.TYPE) {
         return Float.class;
      } else if (type == Integer.TYPE) {
         return Integer.class;
      } else if (type == Character.TYPE) {
         return Character.class;
      } else if (type == Short.TYPE) {
         return Short.class;
      } else if (type == Byte.TYPE) {
         return Byte.class;
      } else {
         return type == Boolean.TYPE ? Boolean.class : type;
      }
   }

   private static ExtendedInfo getAdminOrConfigMBeanInfo(String type) {
      try {
         ExtendedInfo result = null;
         boolean isConfig = type.endsWith("Config");
         if (isConfig) {
            type = getAdminType(type);
            result = (ExtendedInfo)configMBeanInfos.get(type);
         } else {
            result = (ExtendedInfo)adminMBeanInfos.get(type);
         }

         if (result != null) {
            return result;
         } else {
            Class cls = findClass("weblogic.management.configuration." + type + "MBean");
            result = getMBeanInfo(cls);
            if (isConfig) {
               configMBeanInfos.put(type, result);
            } else {
               adminMBeanInfos.put(type, result);
            }

            return result;
         }
      } catch (ClassNotFoundException var4) {
         throw new ConfigurationError(var4);
      } catch (ConfigurationException var5) {
         throw new ConfigurationError(var5);
      }
   }

   private static ExtendedInfo getMBeanInfo(Class mbeanInterface) throws ConfigurationException {
      ExtendedInfo result = (ExtendedInfo)mbeanInfos.get(mbeanInterface);
      if (result == null) {
         StringBuffer name = new StringBuffer(mbeanInterface.getName());

         for(int i = 0; i < name.length(); ++i) {
            if (name.charAt(i) == '.') {
               name.setCharAt(i, '/');
            }
         }

         name.append(".mbi");
         ClassLoader loader = mbeanInterface.getClassLoader();
         InputStream in = null;
         if (loader != null) {
            in = loader.getResourceAsStream(name.toString());
         } else {
            in = ClassLoader.getSystemResourceAsStream(name.toString());
         }

         if (in == null) {
            return null;
         }

         try {
            InputStream in = new BufferedInputStream(in);
            ObjectInputStream input = new ObjectInputStream(in);
            result = (ExtendedInfo)input.readObject();
            input.close();
         } catch (ClassNotFoundException var6) {
            throw new ConfigurationException(var6);
         } catch (IOException var7) {
            throw new ConfigurationException(var7);
         }

         if (result == null) {
            throw new ConfigurationException("no info found for " + mbeanInterface);
         }

         mbeanInfos.put(mbeanInterface, result);
      }

      return result;
   }

   private static String mbeanType(String fullClassName) {
      String result = trimPackage(fullClassName);
      if (result.endsWith("MBean")) {
         result = result.substring(0, result.length() - 5);
      }

      return result;
   }

   private static String trimPackage(String className) {
      int index = className.lastIndexOf(46);
      int len = className.length();
      if (index != -1) {
         className = className.substring(index + 1, len);
      }

      return className;
   }

   private static boolean isAdminOrConfigMBeanClass(String className) {
      try {
         Class.forName(className);
         int i = className.lastIndexOf(46);
         if (i != -1) {
            className = className.substring(0, i);
            return className.equals("weblogic.management.configuration");
         } else {
            return false;
         }
      } catch (ClassNotFoundException var2) {
         return false;
      }
   }

   private static String getAdminType(String type) {
      if (type.endsWith("Config")) {
         type = type.substring(0, type.length() - "Config".length());
      }

      return type;
   }
}
