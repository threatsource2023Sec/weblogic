package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ConfigurableAttributeDataTypeConversionException;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Component;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ConfigurableAttribute;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public final class ConfigurableAttributeInjectionResolver implements InjectionResolver {
   private final Provider configurableAttributeValueProvider;
   private final ServiceLocator serviceLocator;

   @Inject
   public ConfigurableAttributeInjectionResolver(Provider configurableAttributeValueProvider, ServiceLocator serviceLocator) {
      Objects.requireNonNull(configurableAttributeValueProvider);
      Objects.requireNonNull(serviceLocator);
      this.configurableAttributeValueProvider = configurableAttributeValueProvider;
      this.serviceLocator = serviceLocator;
   }

   public final Object resolve(Injectee injectee, ServiceHandle rootServiceHandle) {
      String className = ConfigurableAttributeInjectionResolver.class.getName();
      String methodName = "resolve";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "resolve", new Object[]{injectee, rootServiceHandle});
      }

      Objects.requireNonNull(injectee);
      Object returnValue = null;
      AnnotatedElement parent = injectee.getParent();
      if (parent != null) {
         ConfigurableAttribute ca = null;
         int position = injectee.getPosition();
         if (position >= 0) {
            Annotation[] parameterAnnotations;
            if (parent instanceof Method) {
               parameterAnnotations = ((Method)parent).getParameterAnnotations()[position];
            } else {
               if (!(parent instanceof Constructor)) {
                  throw new IllegalStateException("injectee.getPosition() == " + position + " && !(parent instanceof Method) && !(parent instanceof Constructor): " + parent);
               }

               parameterAnnotations = ((Constructor)parent).getParameterAnnotations()[position];
            }

            assert parameterAnnotations != null;

            Annotation[] var11 = parameterAnnotations;
            int var12 = parameterAnnotations.length;

            for(int var13 = 0; var13 < var12; ++var13) {
               Annotation parameterAnnotation = var11[var13];
               if (parameterAnnotation instanceof ConfigurableAttribute) {
                  ca = (ConfigurableAttribute)parameterAnnotation;
                  break;
               }
            }
         } else {
            ca = (ConfigurableAttribute)parent.getAnnotation(ConfigurableAttribute.class);
         }

         if (ca != null) {
            String componentName = null;
            ActiveDescriptor injecteeDescriptor = injectee.getInjecteeDescriptor();
            Class attributeName;
            if (injecteeDescriptor == null) {
               attributeName = injectee.getInjecteeClass();
               if (attributeName != null) {
                  Component component = (Component)attributeName.getAnnotation(Component.class);
                  if (component != null) {
                     componentName = component.value();
                     if (componentName == null) {
                        componentName = "";
                     } else {
                        componentName = componentName.trim();
                     }
                  }
               }
            } else {
               componentName = Components.getComponentName(this.serviceLocator, injecteeDescriptor);
            }

            if (componentName != null) {
               String attributeName = ca.name();
               if (attributeName == null || attributeName.trim().isEmpty()) {
                  if (!(parent instanceof Field)) {
                     attributeName = null;
                     throw new IllegalArgumentException("Unsupported injectee parent: " + parent);
                  }

                  attributeName = ((Field)parent).getName();
               }

               assert attributeName != null;

               String defaultValue = ca.defaultValue();

               assert defaultValue != null;

               assert this.configurableAttributeValueProvider != null;

               ConfigurableAttributeValueProvider configurableAttributeValueProvider = (ConfigurableAttributeValueProvider)this.configurableAttributeValueProvider.get();

               assert configurableAttributeValueProvider != null;

               String value;
               if (configurableAttributeValueProvider.containsConfigurableAttributeValue(componentName, attributeName)) {
                  value = configurableAttributeValueProvider.getConfigurableAttributeValue(componentName, attributeName);
               } else {
                  value = defaultValue;
               }

               Object convertedValue = null;

               try {
                  convertedValue = convertToType(injectee.getRequiredType(), value);
               } catch (IllegalArgumentException var18) {
                  throw new ConfigurableAttributeDataTypeConversionException(componentName, attributeName, value, injectee.getRequiredType(), var18);
               }

               returnValue = convertedValue;
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "resolve", returnValue);
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   public static final Object convertToType(Type targetType, String val, String ignored) {
      return convertToType(targetType, val);
   }

   public static final Object convertToType(Type targetType, String val) {
      String className = ConfigurableAttributeInjectionResolver.class.getName();
      String methodName = "convertToType";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "convertToType", new Object[]{targetType, val});
      }

      if (!(targetType instanceof Class)) {
         throw new IllegalArgumentException("!(targetType instanceof Class): " + targetType);
      } else {
         Object returnValue = null;
         if (!String.class.equals(targetType) && !CharSequence.class.equals(targetType) && !Object.class.equals(targetType)) {
            if (StringBuilder.class.equals(targetType)) {
               returnValue = val == null ? null : new StringBuilder(val);
            } else if (StringBuffer.class.equals(targetType)) {
               returnValue = val == null ? null : new StringBuffer(val);
            } else if (Character.TYPE.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? val.charAt(0) : '\u0000';
            } else if (char[].class.equals(targetType)) {
               returnValue = val == null ? null : val.toCharArray();
            } else if (Character.class.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? val.charAt(0) : null;
            } else if (Character[].class.equals(targetType)) {
               returnValue = val == null ? null : getCharacterArray(val);
            } else if (Byte.TYPE.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? Byte.valueOf(val) : 0;
            } else if (Byte.class.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? Byte.valueOf(val) : null;
            } else if (Boolean.TYPE.equals(targetType)) {
               returnValue = val == null ? Boolean.FALSE : Boolean.valueOf(val);
            } else if (Boolean.class.equals(targetType)) {
               returnValue = val == null ? null : Boolean.valueOf(val);
            } else if (Short.TYPE.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? Short.valueOf(val) : Short.valueOf((short)0);
            } else if (Short.class.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? Short.valueOf(val) : null;
            } else if (Integer.TYPE.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? Integer.valueOf(val) : 0;
            } else if (Integer.class.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? Integer.valueOf(val) : null;
            } else if (Long.TYPE.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? Long.valueOf(val) : 0L;
            } else if (Long.class.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? Long.valueOf(val) : null;
            } else if (Float.TYPE.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? Float.valueOf(val) : 0.0F;
            } else if (Float.class.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? Float.valueOf(val) : null;
            } else if (Double.TYPE.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? Double.valueOf(val) : 0.0;
            } else if (Double.class.equals(targetType)) {
               returnValue = val != null && !val.isEmpty() ? Double.valueOf(val) : null;
            } else {
               if (!(targetType instanceof Class)) {
                  throw new IllegalArgumentException("ConfigurableAttribute not supported for type: " + targetType);
               }

               PropertyEditor propertyEditor = PropertyEditorManager.findEditor((Class)targetType);
               if (propertyEditor == null) {
                  throw new IllegalArgumentException("ConfigurableAttribute not supported for type: " + targetType);
               }

               propertyEditor.setAsText(val);
               returnValue = propertyEditor.getValue();
            }
         } else {
            returnValue = val;
         }

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "convertToType", returnValue);
         }

         return returnValue;
      }
   }

   public boolean isConstructorParameterIndicator() {
      return true;
   }

   public boolean isMethodParameterIndicator() {
      return true;
   }

   /** @deprecated */
   @Deprecated
   private final String getConfigurableAttributeValue(String componentName, String attributeName, String defaultValue) {
      String className = ConfigurableAttributeInjectionResolver.class.getName();
      String methodName = "getConfigurableAttributeValue";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getConfigurableAttributeValue", new Object[]{componentName, attributeName, defaultValue});
      }

      String returnValue = defaultValue;
      if (componentName != null && attributeName != null) {
         assert this.configurableAttributeValueProvider != null;

         ConfigurableAttributeValueProvider configurableAttributeValueProvider = (ConfigurableAttributeValueProvider)this.configurableAttributeValueProvider.get();

         assert configurableAttributeValueProvider != null;

         if (configurableAttributeValueProvider.containsConfigurableAttributeValue(componentName, attributeName)) {
            returnValue = configurableAttributeValueProvider.getConfigurableAttributeValue(componentName, attributeName);
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getConfigurableAttributeValue", returnValue);
      }

      return returnValue;
   }

   private static final Character[] getCharacterArray(String value) {
      Character[] returnValue;
      if (value == null) {
         returnValue = null;
      } else if (value.isEmpty()) {
         returnValue = new Character[0];
      } else {
         char[] valueChars = value.toCharArray();

         assert valueChars != null;

         assert valueChars.length > 0;

         Character[] chars = new Character[valueChars.length];

         for(int i = 0; i < valueChars.length; ++i) {
            chars[i] = valueChars[i];
         }

         returnValue = chars;
      }

      return returnValue;
   }
}
