package weblogic.management.jmx.modelmbean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.openmbean.ArrayType;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.jmx.CompositeTypeAny;
import weblogic.management.jmx.CompositeTypeProperties;
import weblogic.management.jmx.CompositeTypeThrowable;
import weblogic.management.jmx.ObjectNameManager;
import weblogic.management.jmx.ObjectSecurityManager;
import weblogic.management.jmx.PrimitiveMapper;
import weblogic.management.jmx.mbeanserver.WLSMBeanServer;

public class WLSModelMBeanContext implements Cloneable {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXCore");
   private boolean recurse = false;
   private boolean filteringEnabled = false;
   private boolean readOnly = false;
   private String version = null;
   private final ObjectNameManager nameManager;
   private final MBeanServer mbeanServer;
   private final ObjectSecurityManager securityManager;
   private String notificationFactoryClassName = PropertyChangeNotificationTranslator.class.getName();

   public WLSModelMBeanContext(MBeanServer server, ObjectNameManager nameManager, ObjectSecurityManager securityManager) {
      this.nameManager = nameManager;
      this.mbeanServer = server;
      this.securityManager = securityManager;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Create wls model mbean context for mbean server " + server);
      }

   }

   public boolean isReadOnly() {
      return this.readOnly;
   }

   public void setReadOnly(boolean readOnly) {
      this.readOnly = readOnly;
   }

   public String getVersion() {
      return this.version;
   }

   public void setVersion(String version) {
      this.version = version;
   }

   public ObjectNameManager getNameManager() {
      return this.nameManager;
   }

   public MBeanServer getMBeanServer() {
      return this.mbeanServer;
   }

   public ObjectSecurityManager getSecurityManager() {
      return this.securityManager;
   }

   public boolean isRecurse() {
      return this.recurse;
   }

   public void setRecurse(boolean recurse) {
      this.recurse = recurse;
   }

   public boolean isFilteringEnabled() {
      return this.filteringEnabled;
   }

   public void setFilteringEnabled(boolean filteringEnabled) {
      this.filteringEnabled = filteringEnabled;
   }

   public String getNotificationFactoryClassName() {
      return this.notificationFactoryClassName;
   }

   public void setNotificationFactoryClassName(String notificationFactoryClassName) {
      this.notificationFactoryClassName = notificationFactoryClassName;
   }

   public Object mapFromJMX(Class jmxArgumentClass, Class beanArgumentClass, Object argument) {
      if (argument == null) {
         return null;
      } else {
         Object result = argument;
         if (ObjectName.class.isAssignableFrom(jmxArgumentClass) && !ObjectName.class.isAssignableFrom(beanArgumentClass)) {
            result = this.nameManager.lookupObject((ObjectName)argument);
            if (result == null) {
               throw new IllegalArgumentException("Object does not exist:  " + ((ObjectName)argument).getCanonicalName());
            }
         } else if (ObjectName[].class.isAssignableFrom(jmxArgumentClass) && !ObjectName[].class.isAssignableFrom(beanArgumentClass)) {
            ObjectName[] argArray = (ObjectName[])((ObjectName[])argument);
            Object[] mappedArgArray = (Object[])((Object[])Array.newInstance(beanArgumentClass.getComponentType(), argArray.length));

            for(int j = 0; j < mappedArgArray.length; ++j) {
               mappedArgArray[j] = this.nameManager.lookupObject(argArray[j]);
            }

            result = mappedArgArray;
         }

         return result;
      }
   }

   public boolean isContainedBean(PropertyDescriptor descriptor) {
      Class classToCheck = descriptor.getPropertyType();
      if (classToCheck.isArray()) {
         classToCheck = classToCheck.getComponentType();
      }

      if (!this.nameManager.isClassMapped(classToCheck)) {
         return false;
      } else {
         String relationship = (String)descriptor.getValue("relationship");
         return relationship != null && relationship.equals("containment");
      }
   }

   public Object mapToJMX(Class responseClass, Object response, OpenType openType) {
      if (response == null) {
         return response;
      } else if (this.nameManager == null) {
         return response;
      } else if (openType != null) {
         if (!(openType instanceof ArrayType)) {
            return openType instanceof CompositeType ? this.createCompositeData(response, (CompositeType)openType) : response;
         } else {
            ArrayType arrayType = (ArrayType)openType;
            OpenType elementOpenType = arrayType.getElementOpenType();
            Object[] responseArray = (Object[])((Object[])response);
            CompositeData[] compositeArray = new CompositeData[responseArray.length];

            for(int i = 0; i < responseArray.length; ++i) {
               Object responseInstance = responseArray[i];
               compositeArray[i] = this.createCompositeData(responseInstance, (CompositeType)elementOpenType);
            }

            return compositeArray;
         }
      } else {
         if (responseClass.isArray()) {
            responseClass = responseClass.getComponentType();
            Class resultClass = response.getClass().getComponentType();
            if (ObjectName.class.isAssignableFrom(responseClass) && !ObjectName.class.isAssignableFrom(resultClass)) {
               Object[] responseArray = (Object[])((Object[])response);
               ArrayList onameList = new ArrayList();

               for(int i = 0; i < responseArray.length; ++i) {
                  Object o = responseArray[i];
                  if (o != null && this.nameManager.isClassMapped(o.getClass())) {
                     if (!(o instanceof AbstractDescriptorBean) || !((AbstractDescriptorBean)o)._isClone()) {
                        onameList.add(this.lookupOrRegister(o));
                     }
                  } else {
                     onameList.add((Object)null);
                  }
               }

               response = onameList.toArray(new ObjectName[onameList.size()]);
            }
         } else if (ObjectName.class.isAssignableFrom(responseClass) && !ObjectName.class.isAssignableFrom(response.getClass()) && this.nameManager.isClassMapped(response.getClass())) {
            response = this.lookupOrRegister(response);
         }

         return response;
      }
   }

   public ObjectName lookupOrRegister(Object bean) {
      ObjectName result = this.nameManager.lookupRegisteredObjectName(bean);
      if (result != null) {
         return result;
      } else {
         WLSModelMBeanFactory.registerWLSModelMBean(bean, this);
         result = this.nameManager.lookupRegisteredObjectName(bean);
         if (result == null) {
            result = this.nameManager.buildObjectName(bean);
         }

         return result;
      }
   }

   public void unregister(Object bean) {
      ObjectName beanName = this.nameManager.unregisterObjectInstance(bean);
      if (beanName != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("WLS Model mbean context - unregister object " + beanName);
         }

         try {
            if (this.mbeanServer instanceof WLSMBeanServer) {
               ((WLSMBeanServer)this.mbeanServer).internalUnregisterMBean(beanName);
            } else {
               this.mbeanServer.unregisterMBean(beanName);
            }

         } catch (InstanceNotFoundException var4) {
            throw new AssertionError(var4);
         } catch (MBeanRegistrationException var5) {
            throw new AssertionError(var5);
         }
      }
   }

   public String toString() {
      return "Context for MBeanServer " + this.mbeanServer + " read-only = " + this.readOnly;
   }

   private CompositeData createCompositeData(Object response, CompositeType openType) {
      Class interfaceClass = null;
      if (openType.getTypeName().equals(CompositeTypeThrowable.THROWABLE_TYPE_NAME)) {
         try {
            return CompositeTypeThrowable.newThrowableInstance((Throwable)response);
         } catch (OpenDataException var15) {
            throw new RuntimeException("Unable to convert value for " + response + " to " + openType);
         }
      } else if (openType.getTypeName().equals(CompositeTypeProperties.TYPE_NAME)) {
         try {
            return CompositeTypeProperties.newPropertiesInstance((Properties)response);
         } catch (OpenDataException var16) {
            throw new RuntimeException("Unable to convert value for " + response + " to " + openType);
         }
      } else {
         try {
            interfaceClass = Class.forName(openType.getTypeName(), false, response.getClass().getClassLoader());
         } catch (ClassNotFoundException var18) {
            throw new RuntimeException("Unable to load interface class for OpenType " + var18);
         }

         Set keys = openType.keySet();
         String[] itemNames = (String[])((String[])keys.toArray(new String[keys.size()]));
         Object[] itemValues = new Object[itemNames.length];

         for(int i = 0; i < itemNames.length; ++i) {
            String itemKey = itemNames[i];

            try {
               String typeName = openType.getType(itemKey).getTypeName();
               Method method;
               if (typeName.equals(Boolean.class.getName())) {
                  method = interfaceClass.getMethod("is" + itemKey);
               } else {
                  method = interfaceClass.getMethod("get" + itemKey);
               }

               Object currentItem = method.invoke(response);
               if (typeName.equals(CompositeTypeAny.ANY_TYPE_NAME)) {
                  if (currentItem != null) {
                     OpenType subType = PrimitiveMapper.lookupOpenType(currentItem.getClass().getName());
                     if (subType != null && subType instanceof CompositeType && subType.getTypeName() != typeName) {
                        this.createCompositeData(currentItem, (CompositeType)subType);
                        continue;
                     }

                     Class instanceClass = currentItem.getClass();
                     if (instanceClass.isArray()) {
                        Class instanceElement = instanceClass.getComponentType();
                        if (PrimitiveMapper.lookupOpenType(instanceElement.getName()) == null) {
                           currentItem = this.mapToJMX(ObjectName[].class, currentItem, (OpenType)null);
                        }
                     } else if (PrimitiveMapper.lookupOpenType(instanceClass.getName()) == null) {
                        currentItem = this.mapToJMX(ObjectName.class, currentItem, (OpenType)null);
                     }
                  }

                  itemValues[i] = CompositeTypeAny.newAnyInstance(currentItem);
               } else if (typeName.equals(CompositeTypeThrowable.THROWABLE_TYPE_NAME)) {
                  itemValues[i] = CompositeTypeThrowable.newThrowableInstance((Throwable)currentItem);
               } else if (typeName.equals(CompositeTypeProperties.TYPE_NAME)) {
                  itemValues[i] = CompositeTypeProperties.newPropertiesInstance((Properties)currentItem);
               } else {
                  itemValues[i] = currentItem;
               }
            } catch (NoSuchMethodException var19) {
               throw new RuntimeException("Unable to acquire getter for " + itemKey, var19);
            } catch (IllegalAccessException var20) {
               throw new RuntimeException("Unable to acquire value for " + itemKey, var20);
            } catch (InvocationTargetException var21) {
               throw new RuntimeException("Unable to acquire value for " + itemKey, var21);
            } catch (OpenDataException var22) {
               throw new RuntimeException("Unable to convert value for " + itemKey, var22);
            }
         }

         try {
            return new CompositeDataSupport(openType, itemNames, itemValues);
         } catch (OpenDataException var17) {
            throw new RuntimeException(var17);
         }
      }
   }

   public WLSModelMBeanContext clone() {
      try {
         WLSModelMBeanContext clone = (WLSModelMBeanContext)super.clone();
         return clone;
      } catch (CloneNotSupportedException var3) {
         throw new RuntimeException(var3);
      }
   }
}
