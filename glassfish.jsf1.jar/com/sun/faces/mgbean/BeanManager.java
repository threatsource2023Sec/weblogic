package com.sun.faces.mgbean;

import com.sun.faces.el.ELUtils;
import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class BeanManager {
   private static final Logger LOGGER;
   private Map managedBeans;
   private InjectionProvider injectionProvider;
   private boolean configPreprocessed;
   private boolean lazyBeanValidation;

   public BeanManager(InjectionProvider injectionProvider, boolean lazyBeanValidation) {
      this.managedBeans = new HashMap();
      this.injectionProvider = injectionProvider;
      this.lazyBeanValidation = lazyBeanValidation;
   }

   public BeanManager(InjectionProvider injectionProvider, Map managedBeans, boolean lazyBeanValidation) {
      this(injectionProvider, lazyBeanValidation);
      this.managedBeans = managedBeans;
   }

   public void register(ManagedBeanInfo beanInfo) {
      String message;
      if (beanInfo.hasListEntry()) {
         if (!beanInfo.hasMapEntry() && !beanInfo.hasManagedProperties()) {
            this.addBean(beanInfo.getName(), new ManagedListBeanBuilder(beanInfo));
         } else {
            message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_AS_LIST_CONFIG_ERROR", beanInfo.getName());
            this.addBean(beanInfo.getName(), new ErrorBean(beanInfo, message));
         }
      } else if (beanInfo.hasMapEntry()) {
         if (beanInfo.hasManagedProperties()) {
            message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_AS_MAP_CONFIG_ERROR", beanInfo.getName());
            this.addBean(beanInfo.getName(), new ErrorBean(beanInfo, message));
         } else {
            this.addBean(beanInfo.getName(), new ManagedMapBeanBuilder(beanInfo));
         }
      } else {
         this.addBean(beanInfo.getName(), new ManagedBeanBuilder(beanInfo));
      }

   }

   public Map getRegisteredBeans() {
      return this.managedBeans;
   }

   public boolean isManaged(String name) {
      return this.managedBeans != null && this.managedBeans.containsKey(name);
   }

   public BeanBuilder getBuilder(String name) {
      return this.managedBeans != null ? (BeanBuilder)this.managedBeans.get(name) : null;
   }

   public void preProcessesBeans() {
      if (!this.configPreprocessed && !this.lazyBeanValidation) {
         this.configPreprocessed = true;
         Iterator i$ = this.managedBeans.entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            this.preProcessBean((String)entry.getKey(), (BeanBuilder)entry.getValue());
         }
      }

   }

   public boolean isBeanInScope(String name, FacesContext context) {
      ELUtils.Scope scope = this.getBuilder(name).getScope();
      ExternalContext externalContext = context.getExternalContext();
      switch (scope) {
         case REQUEST:
            if (externalContext.getRequestMap().containsKey(name)) {
               return true;
            }
         case SESSION:
            if (externalContext.getSessionMap().containsKey(name)) {
               return true;
            }
         case APPLICATION:
            if (externalContext.getApplicationMap().containsKey(name)) {
               return true;
            }
         default:
            return false;
      }
   }

   public Object getBeanFromScope(String name, BeanBuilder builder, FacesContext context) {
      return BeanManager.ScopeManager.getFromScope(name, builder.getScope(), context);
   }

   public Object getBeanFromScope(String name, FacesContext context) {
      ELUtils.Scope scope = this.getBuilder(name).getScope();
      return BeanManager.ScopeManager.getFromScope(name, scope, context);
   }

   public Object create(String name, BeanBuilder builder, FacesContext facesContext) {
      if (builder != null) {
         if (this.lazyBeanValidation && !builder.isBaked()) {
            this.preProcessBean(name, builder);
         }

         if (builder.hasMessages()) {
            throw new ManagedBeanCreationException(this.buildMessage(name, builder.getMessages(), true));
         } else {
            return this.createAndPush(name, builder, facesContext);
         }
      } else {
         return null;
      }
   }

   public Object create(String name, FacesContext facesContext) {
      BeanBuilder builder = (BeanBuilder)this.managedBeans.get(name);
      if (builder != null) {
         if (this.lazyBeanValidation && !builder.isBaked()) {
            this.preProcessBean(name, builder);
         }

         if (builder.hasMessages()) {
            throw new ManagedBeanCreationException(this.buildMessage(name, builder.getMessages(), true));
         } else {
            ELUtils.Scope scope = builder.getScope();
            Object bean;
            switch (scope) {
               case REQUEST:
               default:
                  bean = this.createAndPush(name, builder, scope, facesContext);
                  break;
               case SESSION:
                  synchronized(facesContext.getExternalContext().getSession(true)) {
                     bean = this.createAndPush(name, builder, scope, facesContext);
                     break;
                  }
               case APPLICATION:
                  synchronized(facesContext.getExternalContext().getContext()) {
                     bean = this.createAndPush(name, builder, scope, facesContext);
                  }
            }

            return bean;
         }
      } else {
         return null;
      }
   }

   public void destroy(String beanName, Object bean) {
      BeanBuilder builder = (BeanBuilder)this.managedBeans.get(beanName);
      if (builder != null) {
         builder.destroy(this.injectionProvider, bean);
      }

   }

   private void addBean(String beanName, BeanBuilder builder) {
      if (this.configPreprocessed) {
         this.preProcessBean(beanName, builder);
      }

      this.managedBeans.put(beanName, builder);
   }

   private void validateReferences(BeanBuilder builder, List references, List messages) {
      List refs = builder.getReferences();
      if (refs != null) {
         Iterator i$ = refs.iterator();

         while(true) {
            while(true) {
               String ref;
               do {
                  if (!i$.hasNext()) {
                     return;
                  }

                  ref = (String)i$.next();
               } while(!this.isManaged(ref));

               if (references.contains(ref)) {
                  StringBuilder sb = new StringBuilder(64);
                  String[] ra = (String[])references.toArray(new String[references.size()]);

                  for(int i = 0; i < ra.length; ++i) {
                     sb.append(ra[i]);
                     sb.append(" -> ");
                  }

                  sb.append(ref);
                  String message = MessageUtils.getExceptionMessageString("com.sun.faces.CYCLIC_REFERENCE_ERROR", ra[0], sb.toString());
                  messages.add(message);
               } else {
                  BeanBuilder b = this.getBuilder(ref);
                  if (b.getReferences() != null) {
                     references.add(ref);
                     this.validateReferences(b, references, messages);
                     references.remove(ref);
                  }
               }
            }
         }
      }
   }

   private synchronized void preProcessBean(String beanName, BeanBuilder builder) {
      if (!builder.isBaked()) {
         try {
            builder.bake();
            List propRefs = builder.getReferences();
            if (propRefs != null) {
               Iterator i$ = propRefs.iterator();

               while(i$.hasNext()) {
                  String reference = (String)i$.next();
                  if (this.isManaged(reference)) {
                     BeanBuilder b = this.getBuilder(reference);
                     this.preProcessBean(reference, b);
                  }
               }
            }

            List refs = new ArrayList();
            refs.add(beanName);
            ArrayList messages = new ArrayList();
            this.validateReferences(builder, refs, messages);
            if (!messages.isEmpty()) {
               builder.queueMessages(messages);
            }

            if (builder.hasMessages() && LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, this.buildMessage(beanName, builder.getMessages(), false));
            }
         } catch (ManagedBeanPreProcessingException var7) {
            if (!ManagedBeanPreProcessingException.Type.CHECKED.equals(var7.getType())) {
               String message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_UNKNOWN_PROCESSING_ERROR", beanName);
               throw new ManagedBeanPreProcessingException(message, var7);
            }

            builder.queueMessage(var7.getMessage());
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, this.buildMessage(beanName, builder.getMessages(), false));
            }
         }
      }

   }

   private Object createAndPush(String name, BeanBuilder builder, ELUtils.Scope scope, FacesContext facesContext) {
      Object bean = builder.build(this.injectionProvider, facesContext);
      BeanManager.ScopeManager.pushToScope(name, bean, scope, facesContext);
      return bean;
   }

   private Object createAndPush(String name, BeanBuilder builder, FacesContext facesContext) {
      Object bean = builder.build(this.injectionProvider, facesContext);
      BeanManager.ScopeManager.pushToScope(name, bean, builder.getScope(), facesContext);
      return bean;
   }

   private String buildMessage(String name, List messages, boolean runtime) {
      StringBuilder sb = new StringBuilder(128);
      if (runtime) {
         sb.append(MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_PROBLEMS_ERROR", name));
      } else {
         sb.append(MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_PROBLEMS_STARTUP_ERROR", name));
      }

      Iterator i$ = messages.iterator();

      while(i$.hasNext()) {
         String message = (String)i$.next();
         sb.append("\n     - ").append(message);
      }

      return sb.toString();
   }

   static {
      LOGGER = FacesLogger.MANAGEDBEAN.getLogger();
   }

   private static class ScopeManager {
      private static final EnumMap handlerMap = new EnumMap(ELUtils.Scope.class);

      static void pushToScope(String name, Object bean, ELUtils.Scope scope, FacesContext context) {
         ScopeHandler handler = (ScopeHandler)handlerMap.get(scope);
         if (handler != null) {
            handler.handle(name, bean, context);
         }

      }

      static Object getFromScope(String name, ELUtils.Scope scope, FacesContext context) {
         ScopeHandler handler = (ScopeHandler)handlerMap.get(scope);
         return handler != null ? handler.getFromScope(name, context) : null;
      }

      static {
         handlerMap.put(ELUtils.Scope.REQUEST, new RequestScopeHandler());
         handlerMap.put(ELUtils.Scope.SESSION, new SessionScopeHandler());
         handlerMap.put(ELUtils.Scope.APPLICATION, new ApplicationScopeHandler());
      }

      private static class ApplicationScopeHandler implements ScopeHandler {
         private ApplicationScopeHandler() {
         }

         public void handle(String name, Object bean, FacesContext context) {
            context.getExternalContext().getApplicationMap().put(name, bean);
         }

         public Object getFromScope(String name, FacesContext context) {
            return context.getExternalContext().getApplicationMap().get(name);
         }

         // $FF: synthetic method
         ApplicationScopeHandler(Object x0) {
            this();
         }
      }

      private static class SessionScopeHandler implements ScopeHandler {
         private SessionScopeHandler() {
         }

         public void handle(String name, Object bean, FacesContext context) {
            context.getExternalContext().getSessionMap().put(name, bean);
         }

         public Object getFromScope(String name, FacesContext context) {
            return context.getExternalContext().getSessionMap().get(name);
         }

         // $FF: synthetic method
         SessionScopeHandler(Object x0) {
            this();
         }
      }

      private static class RequestScopeHandler implements ScopeHandler {
         private RequestScopeHandler() {
         }

         public void handle(String name, Object bean, FacesContext context) {
            context.getExternalContext().getRequestMap().put(name, bean);
         }

         public Object getFromScope(String name, FacesContext context) {
            return context.getExternalContext().getRequestMap().get(name);
         }

         // $FF: synthetic method
         RequestScopeHandler(Object x0) {
            this();
         }
      }

      private interface ScopeHandler {
         void handle(String var1, Object var2, FacesContext var3);

         Object getFromScope(String var1, FacesContext var2);
      }
   }
}
