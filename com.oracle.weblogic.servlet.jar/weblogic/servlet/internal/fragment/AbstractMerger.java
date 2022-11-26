package weblogic.servlet.internal.fragment;

import java.lang.reflect.Method;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;

public abstract class AbstractMerger implements Merger {
   public void merge(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
      if (update.getUpdateType() == 1) {
         this.handleChangeEvent(targetBean, sourceBean, proposedBean, update);
      }

      if (update.getUpdateType() == 2) {
         this.handleAddEvent(targetBean, sourceBean, proposedBean, update);
      }

      if (update.getUpdateType() == 3) {
         this.handleRemoveEvent(targetBean, sourceBean, proposedBean, update);
      }

   }

   protected void handleChangeEvent(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
   }

   protected void handleAddEvent(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
   }

   protected void handleRemoveEvent(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
   }

   protected static final String singularizeProperty(String property) {
      if (property.endsWith("ies")) {
         return property.substring(0, property.length() - 3) + "y";
      } else if (property.endsWith("ses")) {
         return property.endsWith("Cases") ? property.substring(0, property.length() - 1) : property.substring(0, property.length() - 2);
      } else {
         return property.endsWith("s") ? property.substring(0, property.length() - 1) : property;
      }
   }

   protected static final void addProperty(DescriptorBean bean, String property, Object value) throws MergeException {
      String methodName = "add" + property;

      try {
         Method adder = bean.getClass().getMethod(methodName, value.getClass());
         adder.invoke(bean, value);
      } catch (Exception var5) {
         throw new MergeException(methodName + " invocation error");
      }
   }

   protected static final void addChildBean(DescriptorBean workingBean, String property, Object object) throws BeanAlreadyExistsException {
      workingBean.createChildCopy(property, (DescriptorBean)object);
   }

   protected static final boolean isPropertySet(DescriptorBean bean, String property) throws MergeException {
      String methodName = "is" + property + "Set";

      try {
         Method isPropertySetMethod = bean.getClass().getMethod(methodName);
         return (Boolean)isPropertySetMethod.invoke(bean);
      } catch (Exception var4) {
         throw new MergeException(methodName + " invocation error");
      }
   }

   protected static final void setProperty(DescriptorBean bean, String property, Object value) throws MergeException {
      String methodName = "set" + property;

      try {
         Class clazz = value.getClass();
         if (value instanceof Boolean) {
            clazz = Boolean.TYPE;
         }

         Method setter = bean.getClass().getMethod(methodName, clazz);
         setter.invoke(bean, value);
      } catch (Exception var6) {
         throw new MergeException(methodName + " invocation error");
      }
   }

   protected static final Object getProperty(DescriptorBean bean, String property) throws MergeException {
      String methodName = "get" + property;

      Method getter;
      try {
         getter = bean.getClass().getMethod(methodName);
      } catch (NoSuchMethodException var8) {
         methodName = "is" + property;

         try {
            getter = bean.getClass().getMethod(methodName);
         } catch (NoSuchMethodException var7) {
            throw new MergeException("cannot find getter method for " + property);
         }
      }

      try {
         return getter.invoke(bean);
      } catch (Exception var6) {
         throw new MergeException("error occurs while executing " + getter.getName());
      }
   }
}
