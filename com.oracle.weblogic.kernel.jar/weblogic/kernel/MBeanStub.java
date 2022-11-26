package weblogic.kernel;

import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.WebLogicMBean;
import weblogic.management.WebLogicObjectName;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.collections.PropertiesHelper;

public abstract class MBeanStub {
   private static final Class[] STRING_PARAM = new Class[]{String.class};
   private static final Map primitivePromotionMap = new HashMap();
   private boolean isPersisted = true;
   private boolean isDefaulted = false;

   private static Class promote(Class c) {
      Class p = (Class)primitivePromotionMap.get(c);
      return p == null ? c : p;
   }

   protected final void initializeFromSystemProperties(String prefix) {
      if (!KernelEnvironment.getKernelEnvironment().isInitializeFromSystemPropertiesAllowed(prefix)) {
         throw new UnsupportedOperationException("Initialize WebLogic system properties with prefix " + prefix + " is not allowed");
      } else {
         Class cls = this.getClass();
         Method[] methods = cls.getMethods();

         for(int i = 0; i < methods.length; ++i) {
            Method m = methods[i];
            Class[] params = m.getParameterTypes();
            if (params.length == 1) {
               String name = m.getName();
               if (name.startsWith("set") && name.length() != 3) {
                  String attribute = name.substring(3);
                  String key = prefix + attribute;
                  String value = null;

                  try {
                     value = System.getProperty(key);
                     if (value == null) {
                        continue;
                     }
                  } catch (SecurityException var19) {
                     if (Kernel.isApplet()) {
                        return;
                     }

                     KernelLogger.logErrorInitialingFromSystemProperties(cls.getName(), attribute, "", StackTraceUtils.throwable2StackTrace(var19));
                  }

                  if (params[0] == Properties.class) {
                     Properties p = PropertiesHelper.parse(value);

                     try {
                        m.invoke(this, p);
                     } catch (InvocationTargetException var13) {
                        KernelLogger.logErrorInitialingFromSystemProperties(cls.getName(), attribute, value, StackTraceUtils.throwable2StackTrace(var13.getTargetException()));
                     } catch (Exception var14) {
                        KernelLogger.logErrorInitialingFromSystemProperties(cls.getName(), attribute, value, StackTraceUtils.throwable2StackTrace(var14));
                     }
                  } else {
                     Constructor c = null;

                     try {
                        c = promote(params[0]).getConstructor(STRING_PARAM);
                     } catch (NoSuchMethodException var17) {
                        KernelLogger.logNoConstructorWithStringParam(cls.getName(), m.getName(), attribute, value);
                     } catch (Exception var18) {
                        KernelLogger.logErrorInitialingFromSystemProperties(cls.getName(), attribute, value, StackTraceUtils.throwable2StackTrace(var18));
                     }

                     if (c != null) {
                        try {
                           m.invoke(this, c.newInstance(value));
                        } catch (InvocationTargetException var15) {
                           KernelLogger.logErrorInitialingFromSystemProperties(cls.getName(), attribute, value, StackTraceUtils.throwable2StackTrace(var15.getTargetException()));
                        } catch (Exception var16) {
                           KernelLogger.logErrorInitialingFromSystemProperties(cls.getName(), attribute, value, StackTraceUtils.throwable2StackTrace(var16));
                        }
                     }
                  }
               }
            }
         }

      }
   }

   public final Object getKey() {
      return this.getName();
   }

   public final String getAttributeStringValue(String s) {
      return null;
   }

   public final Set getSetFields() {
      return null;
   }

   public final String getNotes() {
      return null;
   }

   public final void setNotes(String s) {
   }

   public final Element getXml(Document document) {
      return null;
   }

   public final Element getXmlConverter(Document document) {
      return null;
   }

   public void freezeCurrentValue(String attributeName) {
   }

   public void restoreDefaultValue(String attributeName) {
   }

   public void addPropertyChangeListener(PropertyChangeListener l) {
      throw new UnsupportedOperationException();
   }

   public void removePropertyChangeListener(PropertyChangeListener l) {
      throw new UnsupportedOperationException();
   }

   public boolean isPersistenceEnabled() {
      return this.isPersisted;
   }

   public void setPersistenceEnabled(boolean persist) {
      this.isPersisted = persist;
   }

   public boolean isDefaultedMBean() {
      return this.isDefaulted;
   }

   public void setDefaultedMBean(boolean defaulted) {
      this.isDefaulted = defaulted;
   }

   public String getComments() {
      return null;
   }

   public void setComments(String comments) {
   }

   public void addLinkMbeanAttribute(String attributeName, ObjectName on) {
   }

   public final void registerConfigMBean(String configMBeanObjectName, MBeanServer server) {
   }

   public final void unRegisterConfigMBean(String configMBeanObjectName) {
   }

   public final void touch() {
   }

   public void linkToRepository(Object n) {
   }

   public String[] getTags() {
      return null;
   }

   public void setTags(String[] tagArray) throws IllegalArgumentException {
   }

   public boolean addTag(String tag) throws IllegalArgumentException {
      return false;
   }

   public boolean removeTag(String tag) throws IllegalArgumentException {
      return false;
   }

   public String getName() {
      return null;
   }

   public final void setName(String s) {
   }

   public final String getType() {
      return null;
   }

   public final WebLogicObjectName getObjectName() {
      return null;
   }

   public final boolean isCachingDisabled() {
      return false;
   }

   public final WebLogicMBean getParent() {
      return null;
   }

   public final void setParent(WebLogicMBean parent) {
   }

   public final boolean isRegistered() {
      return true;
   }

   public final void postDeregister() {
   }

   public final void preDeregister() {
   }

   public final void postRegister(Boolean b) {
   }

   public final ObjectName preRegister(MBeanServer s, ObjectName o) {
      return null;
   }

   public final void addNotificationListener(NotificationListener l, NotificationFilter f, Object o) {
   }

   public final void removeNotificationListener(NotificationListener l) {
   }

   public final MBeanNotificationInfo[] getNotificationInfo() {
      return null;
   }

   public final Object getAttribute(String s) {
      return null;
   }

   public final void setAttribute(Attribute a) {
   }

   public final AttributeList getAttributes(String[] s) {
      return null;
   }

   public final AttributeList setAttributes(AttributeList l) {
      return null;
   }

   public final MBeanInfo getMBeanInfo() {
      return null;
   }

   public final Object invoke(String s, Object[] o, String[] sa) {
      return null;
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

   public DescriptorBean createChildCopy(String propertyName, DescriptorBean beanToCopy) {
      throw new UnsupportedOperationException();
   }

   public DescriptorBean createChildCopyIncludingObsolete(String propertyName, DescriptorBean beanToCopy) {
      throw new UnsupportedOperationException();
   }

   public boolean isInherited(String propertyName) {
      return false;
   }

   public String[] getInheritedProperties(String[] propertyNames) {
      return null;
   }

   public boolean isDynamicallyCreated() {
      return false;
   }

   public long getId() {
      return 0L;
   }

   static {
      primitivePromotionMap.put(Boolean.TYPE, Boolean.class);
      primitivePromotionMap.put(Character.TYPE, Character.class);
      primitivePromotionMap.put(Byte.TYPE, Byte.class);
      primitivePromotionMap.put(Short.TYPE, Short.class);
      primitivePromotionMap.put(Integer.TYPE, Integer.class);
      primitivePromotionMap.put(Long.TYPE, Long.class);
      primitivePromotionMap.put(Float.TYPE, Float.class);
      primitivePromotionMap.put(Double.TYPE, Double.class);
   }
}
