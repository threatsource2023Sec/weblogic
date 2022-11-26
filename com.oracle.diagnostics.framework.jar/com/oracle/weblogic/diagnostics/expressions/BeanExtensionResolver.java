package com.oracle.weblogic.diagnostics.expressions;

import java.beans.BeanInfo;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.inject.Inject;
import weblogic.diagnostics.debug.DebugLogger;

class BeanExtensionResolver extends ELResolver {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsBeanExtensionResolver");
   private Map beanPropertiesCache = new HashMap();
   private Set skipSet = new HashSet();
   @Inject
   private ExpressionExtensionsManager extensionsManager;
   private Map cachedBeansMap = new HashMap();
   private Map namespaceCache = new HashMap();
   private Set namespaces = new HashSet();
   private Annotation[] qualifiers;
   private boolean cacheDisabled;

   BeanExtensionResolver(Annotation... qualifiers) {
      this.qualifiers = qualifiers;
   }

   @PostConstruct
   void postConstruct() {
      String[] nsSet = this.extensionsManager.getNamespaces();
      if (nsSet != null && nsSet.length > 0) {
         this.namespaces.addAll(Arrays.asList(nsSet));
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Active namespaces: " + this.namespaces.toString());
      }

   }

   public Object getValue(ELContext context, Object base, Object property) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Looking up property " + property + (base == null ? "" : " for class " + base.getClass().getName()));
      }

      if (context == null) {
         throw new NullPointerException();
      } else if (property == null) {
         return null;
      } else {
         NamespaceBean namespaceBean;
         String namespace;
         if (base == null) {
            if (property instanceof String && this.namespaces.contains((String)property)) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Found namespace " + property);
               }

               namespaceBean = (NamespaceBean)this.namespaceCache.get(property);
               namespace = (String)property;
               if (namespaceBean == null) {
                  namespaceBean = new NamespaceBean(namespace);
                  this.namespaceCache.put(namespace, namespaceBean);
               }

               context.setPropertyResolved(true);
               return namespaceBean;
            }
         } else {
            Object value;
            if (base instanceof NamespaceBean && property instanceof String) {
               namespaceBean = (NamespaceBean)base;
               namespace = (String)property;
               value = this.getBean(namespaceBean.value(), namespace);
               if (value != null) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Found extension bean " + namespace + " for namespace " + namespaceBean.value());
                  }

                  context.setPropertyResolved(true);
                  return value;
               }

               throw new ExpressionExtensionBeanNotFound(namespaceBean.namespace, namespace);
            }

            PropertyDescriptor pd = this.getPropertyDescriptor(context, base, property);
            if (pd != null) {
               Method method = pd.getReadMethod();
               if (method != null) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Resolved bean extension property " + property + " for base " + base.getClass().getName());
                  }

                  try {
                     value = method.invoke(base);
                     context.setPropertyResolved(true);
                     return value;
                  } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var7) {
                     throw new ExpressionBeanRuntimeException(var7);
                  }
               }
            }
         }

         return null;
      }
   }

   public Class getType(ELContext context, Object base, Object property) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (base == null && property instanceof String) {
            String ns = (String)property;
            if (this.namespaces.contains(ns)) {
               context.setPropertyResolved(true);
               return NamespaceBean.class;
            }
         } else {
            BeanInfo baseBeanInfo;
            if (base instanceof NamespaceBean && property instanceof String) {
               context.setPropertyResolved(true);
               baseBeanInfo = this.extensionsManager.getExpressionBeanInfo(((NamespaceBean)base).value(), ((String)property).trim());
               return baseBeanInfo.getBeanDescriptor().getBeanClass();
            }

            if (base != null && property instanceof String) {
               baseBeanInfo = this.extensionsManager.getBeanInfo(base.getClass());
               if (baseBeanInfo != null) {
                  PropertyDescriptor[] var5 = baseBeanInfo.getPropertyDescriptors();
                  int var6 = var5.length;

                  int var7;
                  for(var7 = 0; var7 < var6; ++var7) {
                     PropertyDescriptor pd = var5[var7];
                     if (pd.getName().equals((String)property)) {
                        context.setPropertyResolved(true);
                        return pd.getPropertyType();
                     }
                  }

                  MethodDescriptor[] var10 = baseBeanInfo.getMethodDescriptors();
                  var6 = var10.length;

                  for(var7 = 0; var7 < var6; ++var7) {
                     MethodDescriptor md = var10[var7];
                     if (md.getName().equals((String)property)) {
                        context.setPropertyResolved(true);
                        return md.getMethod().getReturnType();
                     }
                  }
               }
            }
         }

         return null;
      }
   }

   public void setValue(ELContext context, Object base, Object property, Object value) {
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) {
      return true;
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      return null;
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      return null;
   }

   private Object getBean(String ns, String beanName) {
      if (ns != null) {
         String qualifiedName = ns + "." + beanName;
         Object bean = this.cachedBeansMap.get(qualifiedName);
         if (bean == null) {
            bean = this.extensionsManager.getBeanInstance(ns, beanName, this.qualifiers);
            if (bean != null) {
               this.cachedBeansMap.put(qualifiedName, bean);
            }
         }

         return bean;
      } else {
         return null;
      }
   }

   private PropertyDescriptor getPropertyDescriptor(ELContext context, Object base, Object property) {
      if (base == null) {
         return null;
      } else {
         Class baseClass = base.getClass();
         String propsKey = baseClass.getName();
         if (this.skipSet.contains(propsKey)) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Class " + propsKey + " in skip set, skipping");
            }

            return null;
         } else {
            String propName = (String)property;
            BeanProperties baseProps = (BeanProperties)this.beanPropertiesCache.get(propsKey);
            if (baseProps == null) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Cache miss for property " + propName);
               }

               BeanInfo beanInfo = this.extensionsManager.findExtensionBeanInfo(baseClass);
               if (beanInfo == null) {
                  if (!this.cacheDisabled) {
                     this.skipSet.add(new String(propsKey.toCharArray()));
                  }
               } else {
                  baseProps = new BeanProperties(beanInfo);
                  if (!this.cacheDisabled) {
                     this.beanPropertiesCache.put(propsKey, baseProps);
                  }
               }
            } else if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Cache hit for property " + propName);
            }

            return baseProps != null ? baseProps.getProperty(propName) : null;
         }
      }
   }

   private static class BeanProperties {
      private Map propertiesMap = new HashMap();

      public BeanProperties(BeanInfo beanInfo) {
         PropertyDescriptor[] var2 = beanInfo.getPropertyDescriptors();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PropertyDescriptor pd = var2[var4];
            this.propertiesMap.put(pd.getName(), pd);
         }

      }

      public PropertyDescriptor getProperty(String propName) {
         return (PropertyDescriptor)this.propertiesMap.get(propName);
      }
   }

   private class NamespaceBean {
      private String namespace;

      public NamespaceBean(String ns) {
         this.namespace = ns;
      }

      public String value() {
         return this.namespace;
      }
   }
}
