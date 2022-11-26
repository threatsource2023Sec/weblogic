package com.oracle.weblogic.diagnostics.utils;

import com.oracle.weblogic.diagnostics.expressions.WLDFI18n;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.ResourceBundle;
import weblogic.diagnostics.debug.DebugLogger;

public class BeanInfoLocalizer {
   public static final String DESCRIPTION_ATTRIBUTE_NAME = "description";
   public static final String PARAM_DEFAULTVALUE_NAME = "defaultValue";
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugExpressionBeanLocalizer");

   public static void localize(BeanInfo beanInfo, Locale l) {
      BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
      Class beanClass = beanDescriptor.getBeanClass();
      WLDFI18n beanI18n = (WLDFI18n)beanClass.getAnnotation(WLDFI18n.class);
      if (beanI18n == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No localization info declared for bean class " + beanClass.getName());
         }

      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Localizing bean info for " + beanClass.getName());
         }

         ResourceBundle classBundle = getResourceBundle(l, beanClass, beanI18n);
         if (classBundle != null) {
            try {
               localizeFeatureDescriptor(beanDescriptor, beanI18n, classBundle);
               PropertyDescriptor[] var6 = beanInfo.getPropertyDescriptors();
               int var7 = var6.length;

               int var8;
               for(var8 = 0; var8 < var7; ++var8) {
                  PropertyDescriptor pd = var6[var8];
                  WLDFI18n fldI18n = findPropertyI18nAnnotation(beanClass, pd);
                  localizeFeatureDescriptor(pd, fldI18n, getResourceBundle(l, fldI18n, classBundle));
               }

               MethodDescriptor[] var12 = beanInfo.getMethodDescriptors();
               var7 = var12.length;

               for(var8 = 0; var8 < var7; ++var8) {
                  MethodDescriptor md = var12[var8];
                  localizeMethodDescriptor(md, l, classBundle);
               }
            } catch (Throwable var11) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Caught exception during BeanInfo localization", var11);
               }
            }
         }

      }
   }

   public static void localize(MethodDescriptor md, Locale l) {
      Method method = md.getMethod();
      Class declaringClass = method.getDeclaringClass();
      WLDFI18n classI18n = (WLDFI18n)declaringClass.getAnnotation(WLDFI18n.class);
      ResourceBundle classBundle = getResourceBundle(l, declaringClass, classI18n);
      localizeMethodDescriptor(md, l, classBundle);
   }

   private static void localizeMethodDescriptor(MethodDescriptor md, Locale l, ResourceBundle defaultBundle) {
      if (md != null) {
         Method method = md.getMethod();
         WLDFI18n methodI18n = (WLDFI18n)method.getAnnotation(WLDFI18n.class);
         if (methodI18n != null) {
            localizeFeatureDescriptor(md, methodI18n, getResourceBundle(l, methodI18n, defaultBundle));
         }

         Annotation[][] allParameterAnnotations = method.getParameterAnnotations();
         ParameterDescriptor[] allParameterDescriptors = md.getParameterDescriptors();
         if (allParameterDescriptors != null) {
            for(int paramIndex = 0; paramIndex < allParameterDescriptors.length; ++paramIndex) {
               Annotation[] paramAnnotations = allParameterAnnotations[paramIndex];
               WLDFI18n paramI18n = findWLDF18n(paramAnnotations);
               if (paramI18n != null) {
                  ParameterDescriptor paramDescriptor = allParameterDescriptors[paramIndex];
                  String paramName = paramI18n.name() == null ? null : paramI18n.name().trim();
                  if (paramName != null && !paramName.isEmpty()) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Found parameter name override for method " + method.getName() + ": " + paramName);
                     }

                     paramDescriptor.setName(paramName);
                  }

                  String defaultValue = paramI18n.defaultValue() == null ? null : paramI18n.defaultValue().trim();
                  if (defaultValue != null && !defaultValue.isEmpty()) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Default value for method " + method.getName() + " param " + paramName + " is " + defaultValue);
                     }

                     paramDescriptor.setValue("defaultValue", defaultValue);
                  }

                  localizeFeatureDescriptor(paramDescriptor, paramI18n, getResourceBundle(l, paramI18n, defaultBundle));
               }
            }
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No parameter descriptors for " + md.getName());
         }

      }
   }

   private static void localizeFeatureDescriptor(FeatureDescriptor fd, WLDFI18n i18nInfo, ResourceBundle rb) {
      if (i18nInfo != null && rb != null) {
         String displayNameKey = i18nInfo.displayName();
         if (displayNameKey != null && !displayNameKey.isEmpty()) {
            fd.setDisplayName(rb.getString(displayNameKey));
         }

         String shortDescriptionKey = i18nInfo.value();
         if (shortDescriptionKey != null && !shortDescriptionKey.isEmpty()) {
            fd.setShortDescription(rb.getString(shortDescriptionKey));
         }

         String fullDescriptionKey = i18nInfo.fullDescription();
         if (fullDescriptionKey != null && !fullDescriptionKey.isEmpty()) {
            fd.setValue("description", rb.getString(fullDescriptionKey));
         }

      }
   }

   private static WLDFI18n findWLDF18n(Annotation[] allAnnotations) {
      WLDFI18n found = null;
      Annotation[] var2 = allAnnotations;
      int var3 = allAnnotations.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Annotation annotation = var2[var4];
         if (annotation.annotationType().equals(WLDFI18n.class)) {
            found = (WLDFI18n)annotation;
         }
      }

      return found;
   }

   private static WLDFI18n findPropertyI18nAnnotation(Class beanClass, PropertyDescriptor pd) {
      WLDFI18n fldI18n = null;
      Field field = findDeclaredField(beanClass, pd.getName());
      if (field != null) {
         fldI18n = (WLDFI18n)field.getAnnotation(WLDFI18n.class);
      }

      if (fldI18n == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No field annotation, checking read accessor for property " + pd.getName());
         }

         fldI18n = pd.getReadMethod() != null ? (WLDFI18n)pd.getReadMethod().getAnnotation(WLDFI18n.class) : null;
         if (fldI18n == null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("No read accessor annotation, checking write accessor for property " + pd.getName());
            }

            fldI18n = pd.getWriteMethod() != null ? (WLDFI18n)pd.getWriteMethod().getAnnotation(WLDFI18n.class) : null;
         }
      }

      return fldI18n;
   }

   private static Field findDeclaredField(Class beanClass, String name) {
      Field field = null;
      if (beanClass != null) {
         try {
            field = beanClass.getDeclaredField(name);
         } catch (SecurityException | NoSuchFieldException var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Error accessing field named " + name, var4);
            }
         }

         if (field == null) {
            field = findDeclaredField(beanClass.getSuperclass(), name);
         }
      }

      return field;
   }

   private static ResourceBundle getResourceBundle(Locale l, Class parentClass, WLDFI18n beanI18n) {
      String bundleName = beanI18n == null ? null : beanI18n.resourceBundle();
      if (bundleName == null || bundleName.isEmpty()) {
         bundleName = parentClass.getName();
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Loading resource bundle for " + bundleName);
      }

      ResourceBundle classBundle = null;

      try {
         classBundle = ResourceBundle.getBundle(bundleName, l, parentClass.getClassLoader());
      } catch (Throwable var6) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Unable to load resource bundle for " + bundleName);
         }
      }

      return classBundle;
   }

   private static ResourceBundle getResourceBundle(Locale l, WLDFI18n beanI18n, ResourceBundle defaultBundle) {
      ResourceBundle bundle = defaultBundle;
      if (beanI18n != null) {
         String bundleName = beanI18n.resourceBundle();
         if (bundleName != null && !bundleName.isEmpty()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Loading resource bundle for " + bundleName);
            }

            try {
               bundle = ResourceBundle.getBundle(bundleName, l);
            } catch (Exception var6) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Unable to load resource bundle for " + bundleName);
               }
            }
         }
      }

      return bundle;
   }
}
