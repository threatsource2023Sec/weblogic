package com.sun.faces.mgbean;

import com.sun.faces.RIConstants;
import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.el.ELUtils;
import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

public abstract class BeanBuilder {
   private static Logger LOGGER;
   private List messages;
   private List references;
   private ELUtils.Scope scope;
   private boolean isInjectible;
   private boolean baked;
   private Class beanClass;
   protected final ManagedBeanInfo beanInfo;

   public BeanBuilder(ManagedBeanInfo beanInfo) {
      this.beanInfo = beanInfo;
      this.initScope();
   }

   public Object build(InjectionProvider injectionProvider, FacesContext context) {
      Object bean = this.newBeanInstance();
      this.injectResources(bean, injectionProvider);
      this.buildBean(bean, context);
      if (ELUtils.Scope.NONE != this.getScope()) {
         this.invokePostConstruct(bean, injectionProvider);
      }

      return bean;
   }

   public void destroy(InjectionProvider injectionProvider, Object bean) {
      if (this.isInjectible) {
         try {
            injectionProvider.invokePreDestroy(bean);
         } catch (InjectionProviderException var4) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, var4.getMessage(), var4);
            }
         }
      }

   }

   public boolean hasMessages() {
      return this.messages != null && !this.messages.isEmpty();
   }

   public List getMessages() {
      return this.messages;
   }

   public ELUtils.Scope getScope() {
      return this.scope;
   }

   public boolean isBaked() {
      return this.baked;
   }

   public Map getDescriptions() {
      return this.beanInfo.getDescriptions();
   }

   public Class getBeanClass() {
      return this.loadBeanClass();
   }

   protected abstract void buildBean(Object var1, FacesContext var2);

   protected void baked() {
      this.baked = true;
   }

   protected Object newBeanInstance() {
      try {
         return this.loadBeanClass().newInstance();
      } catch (Exception var3) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.CANT_INSTANTIATE_CLASS", this.loadBeanClass().getName());
         throw new ManagedBeanCreationException(message, var3);
      }
   }

   protected void injectResources(Object bean, InjectionProvider injectionProvider) {
      if (this.isInjectible) {
         try {
            injectionProvider.inject(bean);
         } catch (InjectionProviderException var5) {
            String message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_INJECTION_ERROR", this.beanInfo.getName());
            throw new ManagedBeanCreationException(message, var5);
         }
      }

   }

   protected void invokePostConstruct(Object bean, InjectionProvider injectionProvider) {
      if (this.isInjectible) {
         try {
            injectionProvider.invokePostConstruct(bean);
         } catch (InjectionProviderException var5) {
            String message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_INJECTION_ERROR", this.beanInfo.getName());
            throw new ManagedBeanCreationException(message, var5);
         }
      }

   }

   protected Class loadClass(String className) {
      Class valueType = String.class;
      if (null != className && 0 < className.length()) {
         if (className.equals(Boolean.TYPE.getName())) {
            valueType = Boolean.TYPE;
         } else if (className.equals(Byte.TYPE.getName())) {
            valueType = Byte.TYPE;
         } else if (className.equals(Double.TYPE.getName())) {
            valueType = Double.TYPE;
         } else if (className.equals(Float.TYPE.getName())) {
            valueType = Float.TYPE;
         } else if (className.equals(Integer.TYPE.getName())) {
            valueType = Integer.TYPE;
         } else if (className.equals(Character.TYPE.getName())) {
            valueType = Character.TYPE;
         } else if (className.equals(Short.TYPE.getName())) {
            valueType = Short.TYPE;
         } else if (className.equals(Long.TYPE.getName())) {
            valueType = Long.TYPE;
         } else {
            String message;
            try {
               valueType = Util.loadClass(className, this);
            } catch (ClassNotFoundException var5) {
               message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_CLASS_NOT_FOUND_ERROR", className, this.beanInfo.getName());
               throw new ManagedBeanPreProcessingException(message, var5);
            } catch (NoClassDefFoundError var6) {
               message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_CLASS_DEPENDENCY_NOT_FOUND_ERROR", className, this.beanInfo.getName(), var6.getMessage());
               throw new ManagedBeanPreProcessingException(message, var6);
            }
         }
      }

      return valueType;
   }

   protected Map getBakedMap(String keyClass, String valueClass, Map mapEntries) {
      if (mapEntries != null && !mapEntries.isEmpty()) {
         Class keyClazz = this.loadClass(keyClass);
         Class valueClazz = this.loadClass(valueClass);
         Map target = new LinkedHashMap(mapEntries.size(), 1.0F);
         Iterator i$ = mapEntries.entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry m = (Map.Entry)i$.next();
            String sk = (String)m.getKey();
            String sv = (String)m.getValue();
            target.put(new Expression(sk, keyClazz), !sv.equals("null_value") ? new Expression(sv, valueClazz) : null);
         }

         return target;
      } else {
         return new LinkedHashMap(4, 1.0F);
      }
   }

   protected List getBakedList(String valueClass, List entries) {
      Class valueClazz = this.loadClass(valueClass);
      List target = new ArrayList(entries.size());
      Iterator i$ = entries.iterator();

      while(i$.hasNext()) {
         String item = (String)i$.next();
         target.add(!"null_value".equals(item) ? new Expression(item, valueClazz) : null);
      }

      return target;
   }

   protected void initMap(Map source, Map target, FacesContext context) {
      Iterator i$ = source.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry entry = (Map.Entry)i$.next();
         Expression k = (Expression)entry.getKey();
         Expression v = (Expression)entry.getValue();
         target.put(k.evaluate(context.getELContext()), v != null ? v.evaluate(context.getELContext()) : null);
      }

   }

   protected void initList(List source, List target, FacesContext context) {
      int i = 0;

      for(int size = source.size(); i < size; ++i) {
         Expression value = (Expression)source.get(i);
         target.add(value != null ? value.evaluate(context.getELContext()) : null);
      }

   }

   void queueMessage(String message) {
      if (this.messages == null) {
         this.messages = new ArrayList(4);
      }

      this.messages.add(message);
   }

   void queueMessages(List messages) {
      if (this.messages == null) {
         this.messages = messages;
      } else {
         this.messages.addAll(messages);
      }

   }

   void bake() {
      this.loadBeanClass();
   }

   List getReferences() {
      return this.references;
   }

   private Class loadBeanClass() {
      if (this.beanClass != null) {
         return this.beanClass;
      } else {
         String className = this.beanInfo.getClassName();
         Class clazz = this.loadClass(className);
         ApplicationAssociate associate = ApplicationAssociate.getCurrentInstance();
         if (!associate.isDevModeEnabled()) {
            this.beanClass = clazz;
         }

         int classModifiers = clazz.getModifiers();
         String message;
         if (!Modifier.isPublic(classModifiers)) {
            message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_CLASS_IS_NOT_PUBLIC_ERROR", className, this.beanInfo.getName());
            this.queueMessage(message);
         }

         if (Modifier.isInterface(classModifiers) || Modifier.isAbstract(classModifiers)) {
            message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_CLASS_IS_ABSTRACT_ERROR", className, this.beanInfo.getName());
            this.queueMessage(message);
         }

         String message;
         try {
            Constructor ctor = clazz.getConstructor(RIConstants.EMPTY_CLASS_ARGS);
            if (!Modifier.isPublic(ctor.getModifiers())) {
               message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_CLASS_NO_PUBLIC_NOARG_CTOR_ERROR", className, this.beanInfo.getName());
               this.queueMessage(message);
            }
         } catch (NoSuchMethodException var7) {
            message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_CLASS_NO_PUBLIC_NOARG_CTOR_ERROR", className, this.beanInfo.getName());
            this.queueMessage(message);
         }

         if (!this.hasMessages()) {
            this.isInjectible = this.scanForAnnotations(clazz);
         }

         return clazz;
      }
   }

   private void initScope() {
      String scope = this.beanInfo.getScope();
      if ("request".equals(scope)) {
         this.scope = ELUtils.Scope.REQUEST;
      } else if ("session".equals(scope)) {
         this.scope = ELUtils.Scope.SESSION;
      } else if ("application".equals(scope)) {
         this.scope = ELUtils.Scope.APPLICATION;
      } else if ("none".equals(scope)) {
         this.scope = ELUtils.Scope.NONE;
      }

   }

   private boolean scanForAnnotations(Class clazz) {
      if (clazz != null) {
         for(; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            int len$;
            if (fields != null) {
               Field[] arr$ = fields;
               int len$ = fields.length;

               for(len$ = 0; len$ < len$; ++len$) {
                  Field field = arr$[len$];
                  if (field.getAnnotations().length > 0) {
                     return true;
                  }
               }
            }

            Method[] methods = clazz.getDeclaredMethods();
            if (methods != null) {
               Method[] arr$ = methods;
               len$ = methods.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  Method method = arr$[i$];
                  if (method.getDeclaredAnnotations().length > 0) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   static {
      LOGGER = FacesLogger.MANAGEDBEAN.getLogger();
   }

   protected class Expression {
      private String expressionString;
      private Class expectedType;
      private ValueExpression ve;
      private boolean validateLifespanRuntime = false;
      private String[] segment = new String[1];

      public Expression(String expressionString, Class expectedType) {
         this.expressionString = expressionString;
         this.expectedType = expectedType;
         if (ELUtils.isExpression(this.expressionString)) {
            List expressions = ELUtils.getExpressionsFromString(this.expressionString);
            if (!expressions.isEmpty()) {
               for(Iterator i$ = expressions.iterator(); i$.hasNext(); this.segment[0] = null) {
                  String expression = (String)i$.next();
                  ELUtils.getScope(expression, this.segment);
                  if (this.segment[0] != null) {
                     if (BeanBuilder.this.references == null) {
                        BeanBuilder.this.references = new ArrayList(4);
                     }

                     if (!BeanBuilder.this.references.contains(this.segment[0])) {
                        BeanBuilder.this.references.add(this.segment[0]);
                     }
                  }
               }
            }

            ELUtils.Scope expressionScope = ELUtils.getNarrowestScopeFromExpression(this.expressionString);
            if (expressionScope != null) {
               this.validateLifespan(expressionScope, this.validateLifespanRuntime);
            } else {
               this.validateLifespanRuntime = true;
            }
         } else if (this.expressionString != null) {
            this.expressionString = "#{\"" + this.expressionString.replaceAll("[\\\\\"]", "\\\\$0") + "\"}";
         }

      }

      public Object evaluate(ELContext context) {
         if (this.expressionString == null) {
            return null;
         } else {
            if (this.validateLifespanRuntime) {
               ELUtils.Scope expScope = ELUtils.getScope(this.expressionString, this.segment);
               this.validateLifespan(expScope, true);
            }

            if (this.ve == null) {
               this.ve = this.expectedType.isPrimitive() ? ELUtils.createValueExpression(this.expressionString, this.expectedType) : ELUtils.createValueExpression(this.expressionString, Object.class);
            }

            if (this.expectedType.isPrimitive()) {
               return this.ve.getValue(context);
            } else {
               Object tmpval = this.ve.getValue(context);
               return tmpval != null ? ELUtils.coerce(tmpval, this.expectedType) : null;
            }
         }
      }

      private void validateLifespan(ELUtils.Scope expressionScope, boolean runtime) {
         if (!ELUtils.hasValidLifespan(expressionScope, BeanBuilder.this.scope)) {
            String message = MessageUtils.getExceptionMessageString("com.sun.faces.INVALID_SCOPE_LIFESPAN", this.expressionString, expressionScope, BeanBuilder.this.beanInfo.getName(), BeanBuilder.this.scope.toString());
            if (runtime) {
               throw new ManagedBeanCreationException(message);
            }

            BeanBuilder.this.queueMessage(message);
         }

      }
   }
}
