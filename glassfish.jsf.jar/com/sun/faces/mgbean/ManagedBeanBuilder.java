package com.sun.faces.mgbean;

import com.sun.faces.RIConstants;
import com.sun.faces.util.MessageUtils;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ExpressionFactory;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class ManagedBeanBuilder extends BeanBuilder {
   private List properties;

   public ManagedBeanBuilder(ManagedBeanInfo beanInfo) {
      super(beanInfo);
   }

   void bake() {
      if (!this.isBaked()) {
         super.bake();
         if (this.beanInfo.hasManagedProperties()) {
            this.properties = new ArrayList(this.beanInfo.getManagedProperties().size());
            String propertyName = null;

            try {
               Iterator var2 = this.beanInfo.getManagedProperties().iterator();

               while(var2.hasNext()) {
                  ManagedBeanInfo.ManagedProperty property = (ManagedBeanInfo.ManagedProperty)var2.next();
                  propertyName = property.getPropertyName();
                  switch (this.getPropertyType(property)) {
                     case MAP:
                        this.bakeMapProperty(property);
                        break;
                     case LIST:
                        this.bakeListProperty(property);
                        break;
                     default:
                        this.bakeBeanProperty(property);
                  }
               }
            } catch (Exception var4) {
               if (var4 instanceof ManagedBeanPreProcessingException) {
                  throw (ManagedBeanPreProcessingException)var4;
               }

               String message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_PROPERTY_UNKNOWN_PROCESSING_ERROR", propertyName);
               throw new ManagedBeanPreProcessingException(message, var4, ManagedBeanPreProcessingException.Type.UNCHECKED);
            }
         }

         this.baked();
         Introspector.flushFromCaches(this.getBeanClass());
      }

   }

   protected void buildBean(Object bean, FacesContext context) {
      if (this.properties != null) {
         Iterator var3 = this.properties.iterator();

         while(var3.hasNext()) {
            BakedProperty property = (BakedProperty)var3.next();
            property.set(bean, context);
         }
      }

   }

   private PropertyType getPropertyType(ManagedBeanInfo.ManagedProperty property) {
      String message;
      if (property.hasListEntry()) {
         if (!property.hasMapEntry() && !property.hasPropertyValue()) {
            return ManagedBeanBuilder.PropertyType.LIST;
         } else {
            message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_LIST_PROPERTY_CONFIG_ERROR", property.getPropertyName(), this.beanInfo.getName());
            throw new ManagedBeanPreProcessingException(message);
         }
      } else if (property.hasMapEntry()) {
         if (property.hasPropertyValue()) {
            message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_MAP_PROPERTY_CONFIG_ERROR", property.getPropertyName(), this.beanInfo.getName());
            throw new ManagedBeanPreProcessingException(message);
         } else {
            return ManagedBeanBuilder.PropertyType.MAP;
         }
      } else {
         return ManagedBeanBuilder.PropertyType.BEAN;
      }
   }

   private void bakeMapProperty(ManagedBeanInfo.ManagedProperty property) {
      ManagedBeanInfo.MapEntry rawEntry = property.getMapEntry();
      Map mapEntries = this.getBakedMap(rawEntry.getKeyClass(), rawEntry.getValueClass(), rawEntry.getEntries());
      PropertyDescriptor pd = this.getPropertyDescriptor(property.getPropertyName());
      String message;
      if (pd == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_PROPERTY_DOES_NOT_EXIST_ERROR", property.getPropertyName(), this.beanInfo.getName());
         this.queueMessage(message);
      } else {
         String message;
         if (pd.getWriteMethod() != null) {
            Class[] params = pd.getWriteMethod().getParameterTypes();
            if (!Map.class.isAssignableFrom(params[0])) {
               message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_MAP_PROPERTY_INCORRECT_SETTER_ERROR", property.getPropertyName(), this.beanInfo.getName());
               this.queueMessage(message);
            }
         } else if (pd.getReadMethod() == null) {
            message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_PROPERTY_DOES_NOT_EXIST_ERROR", property.getPropertyName(), this.beanInfo.getName());
            this.queueMessage(message);
         } else {
            Class returnType = pd.getReadMethod().getReturnType();
            if (!Map.class.isAssignableFrom(returnType)) {
               message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_MAP_PROPERTY_INCORRECT_GETTER_ERROR", property.getPropertyName(), this.beanInfo.getName());
               this.queueMessage(message);
            }
         }
      }

      if (!this.hasMessages()) {
         BakedMapProperty baked = new BakedMapProperty(mapEntries, pd);
         this.properties.add(baked);
      }

   }

   private void bakeListProperty(ManagedBeanInfo.ManagedProperty property) {
      ManagedBeanInfo.ListEntry rawEntry = property.getListEntry();
      List listEntry = this.getBakedList(rawEntry.getValueClass(), rawEntry.getValues());
      PropertyDescriptor pd = this.getPropertyDescriptor(property.getPropertyName());
      String message;
      if (pd == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_PROPERTY_DOES_NOT_EXIST_ERROR", property.getPropertyName(), this.beanInfo.getName());
         this.queueMessage(message);
      } else {
         String message;
         if (pd.getReadMethod() == null) {
            if (pd.getWriteMethod() == null) {
               message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_PROPERTY_DOES_NOT_EXIST_ERROR", property.getPropertyName(), this.beanInfo.getName());
               this.queueMessage(message);
            } else {
               Class[] params = pd.getWriteMethod().getParameterTypes();
               if (params.length != 1) {
                  message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_PROPERTY_INCORRECT_ARGS_ERROR", property.getPropertyName(), this.beanInfo.getName());
                  this.queueMessage(message);
               } else if (!params[0].isArray() && !List.class.isAssignableFrom(params[0])) {
                  message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_LIST_SETTER_DOES_NOT_ACCEPT_LIST_OR_ARRAY_ERROR", property.getPropertyName(), this.beanInfo.getName());
                  this.queueMessage(message);
               }
            }
         } else {
            Class retType = pd.getReadMethod().getReturnType();
            if (retType.isArray()) {
               if (pd.getWriteMethod() == null) {
                  message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_LIST_GETTER_ARRAY_NO_SETTER_ERROR", property.getPropertyName(), this.beanInfo.getName());
                  this.queueMessage(message);
               }
            } else if (!List.class.isAssignableFrom(retType)) {
               message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_LIST_SETTER_DOES_NOT_RETURN_LIST_OR_ARRAY_ERROR", property.getPropertyName(), this.beanInfo.getName());
               this.queueMessage(message);
            }
         }
      }

      if (!this.hasMessages()) {
         BakedListProperty baked = new BakedListProperty(listEntry, pd);
         this.properties.add(baked);
      }

   }

   private void bakeBeanProperty(ManagedBeanInfo.ManagedProperty property) {
      String className = property.getPropertyClass();
      PropertyDescriptor pd = this.getPropertyDescriptor(property.getPropertyName());
      String propertyValue;
      if (pd != null && pd.getWriteMethod() != null) {
         Method method = pd.getWriteMethod();
         Class[] param = method.getParameterTypes();
         if (param.length != 1) {
            propertyValue = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_PROPERTY_INCORRECT_ARGS_ERROR", property.getPropertyName(), this.beanInfo.getName());
            this.queueMessage(propertyValue);
         }
      } else if (!UIComponent.class.isAssignableFrom(this.getBeanClass())) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_PROPERTY_DOES_NOT_EXIST_ERROR", property.getPropertyName(), this.beanInfo.getName());
         this.queueMessage(message);
      }

      BeanBuilder.Expression value = null;
      if (pd != null) {
         Class propertyClass;
         if (className != null) {
            propertyClass = this.loadClass(className);
         } else {
            propertyClass = pd.getPropertyType();
         }

         if (className != null && !pd.getWriteMethod().getParameterTypes()[0].isAssignableFrom(propertyClass)) {
            propertyValue = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_DEFINED_PROPERTY_CLASS_NOT_COMPATIBLE_ERROR", property.getPropertyName(), this.beanInfo.getName(), property.getPropertyClass());
            this.queueMessage(propertyValue);
         }

         propertyValue = property.getPropertyValue();
         if (!"null_value".equals(propertyValue)) {
            value = new BeanBuilder.Expression(propertyValue, propertyClass);
         }
      } else {
         String propertyValue = property.getPropertyValue();
         if (!"null_value".equals(propertyValue)) {
            value = new BeanBuilder.Expression(propertyValue, String.class);
         }
      }

      if (!this.hasMessages()) {
         BakedBeanProperty baked = new BakedBeanProperty(property.getPropertyName(), pd, value);
         this.properties.add(baked);
      }

   }

   private PropertyDescriptor getPropertyDescriptor(String propertyName) {
      try {
         BeanInfo beanInfo = Introspector.getBeanInfo(this.getBeanClass());
         PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
         PropertyDescriptor[] var4 = pds;
         int var5 = pds.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor pd = var4[var6];
            if (propertyName.equals(pd.getName())) {
               return pd;
            }
         }

         return null;
      } catch (IntrospectionException var8) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_INTROSPECTION_ERROR", this.beanInfo.getName());
         throw new ManagedBeanPreProcessingException(message);
      }
   }

   private class BakedBeanProperty implements BakedProperty {
      private String propertyName;
      private PropertyDescriptor pd;
      private BeanBuilder.Expression value;

      BakedBeanProperty(String propertyName, PropertyDescriptor pd, BeanBuilder.Expression value) {
         this.propertyName = propertyName;
         this.pd = pd;
         this.value = value;
      }

      public void set(Object bean, FacesContext context) {
         if (this.pd != null) {
            Method writeMethod = this.pd.getWriteMethod();

            try {
               writeMethod.invoke(bean, this.value != null ? this.value.evaluate(context.getELContext()) : null);
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var6) {
               String message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_UNABLE_TO_SET_PROPERTY_ERROR", this.pd.getName(), ManagedBeanBuilder.this.beanInfo.getName());
               throw new ManagedBeanCreationException(message, var6);
            }
         } else {
            ((UIComponent)bean).getAttributes().put(this.propertyName, this.value != null ? this.value.evaluate(context.getELContext()) : "");
         }

      }
   }

   private class BakedListProperty implements BakedProperty {
      private List listEntries;
      private PropertyDescriptor pd;

      BakedListProperty(List listEntries, PropertyDescriptor pd) {
         this.listEntries = listEntries;
         this.pd = pd;
      }

      public void set(Object bean, FacesContext context) {
         Method readMethod = this.pd.getReadMethod();
         Object temp = null;
         if (readMethod != null) {
            try {
               temp = readMethod.invoke(bean, RIConstants.EMPTY_METH_ARGS);
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var16) {
            }
         }

         List target = null;
         if (temp != null) {
            if (temp.getClass().isArray()) {
               int i = 0;

               for(int len = Array.getLength(temp); i < len; ++i) {
                  if (target == null) {
                     target = new ArrayList(len);
                  }

                  ((List)target).add(Array.get(temp, i));
               }
            } else {
               target = (List)temp;
            }
         }

         if (target == null) {
            target = new ArrayList();
         }

         ExpressionFactory expFactory = context.getApplication().getExpressionFactory();
         ManagedBeanBuilder.this.initList(this.listEntries, (List)target, context);
         if (temp == null || temp.getClass().isArray()) {
            String message;
            if (temp != null) {
               Class arrayType = temp.getClass().getComponentType();
               Object resultx = Array.newInstance(arrayType, ((List)target).size());
               int ix = 0;

               for(int lenx = ((List)target).size(); ix < lenx; ++ix) {
                  Array.set(resultx, ix, expFactory.coerceToType(((List)target).get(ix), arrayType));
               }

               try {
                  this.pd.getWriteMethod().invoke(bean, resultx);
               } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var15) {
                  message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_UNABLE_TO_SET_PROPERTY_ERROR", this.pd.getName(), ManagedBeanBuilder.this.beanInfo.getName());
                  throw new ManagedBeanCreationException(message, var15);
               }
            } else {
               Method writeMethod = this.pd.getWriteMethod();
               Class[] param = writeMethod.getParameterTypes();
               if (param[0].isArray()) {
                  Class arrayTypex = param[0].getComponentType();
                  Object result = Array.newInstance(arrayTypex, ((List)target).size());
                  int ixx = 0;

                  for(int lenxx = ((List)target).size(); ixx < lenxx; ++ixx) {
                     Array.set(result, ixx, expFactory.coerceToType(((List)target).get(ixx), arrayTypex));
                  }

                  try {
                     writeMethod.invoke(bean, result);
                  } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var14) {
                     String messagex = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_UNABLE_TO_SET_PROPERTY_ERROR", this.pd.getName(), ManagedBeanBuilder.this.beanInfo.getName());
                     throw new ManagedBeanCreationException(messagex, var14);
                  }
               } else {
                  try {
                     writeMethod.invoke(bean, target);
                  } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var13) {
                     message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_UNABLE_TO_SET_PROPERTY_ERROR", this.pd.getName(), ManagedBeanBuilder.this.beanInfo.getName());
                     throw new ManagedBeanCreationException(message, var13);
                  }
               }
            }

         }
      }
   }

   private class BakedMapProperty implements BakedProperty {
      Map mapEntries;
      PropertyDescriptor pd;

      BakedMapProperty(Map mapEntries, PropertyDescriptor pd) {
         this.mapEntries = mapEntries;
         this.pd = pd;
      }

      public void set(Object bean, FacesContext context) {
         Method readMethod = this.pd.getReadMethod();
         Map target = null;
         boolean mapReturned = false;
         if (readMethod != null) {
            try {
               target = (Map)readMethod.invoke(bean, RIConstants.EMPTY_METH_ARGS);
               mapReturned = target != null;
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var10) {
            }
         }

         if (target == null) {
            target = new HashMap();
         }

         ManagedBeanBuilder.this.initMap(this.mapEntries, (Map)target, context);
         if (!mapReturned) {
            Method writeMethod = this.pd.getWriteMethod();

            try {
               writeMethod.invoke(bean, target);
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var9) {
               String message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_UNABLE_TO_SET_PROPERTY_ERROR", this.pd.getName(), ManagedBeanBuilder.this.beanInfo.getName());
               throw new ManagedBeanPreProcessingException(message, var9);
            }
         }

      }
   }

   private interface BakedProperty {
      void set(Object var1, FacesContext var2);
   }

   private static enum PropertyType {
      MAP,
      LIST,
      BEAN;
   }
}
