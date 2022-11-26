package com.sun.faces.mgbean;

import com.sun.faces.el.ELUtils;
import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreDestroyCustomScopeEvent;
import javax.faces.event.ScopeContext;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public class BeanManager implements SystemEventListener {
   private static final Logger LOGGER;
   private Map managedBeans;
   private InjectionProvider injectionProvider;
   private boolean configPreprocessed;
   private boolean lazyBeanValidation;
   private List eagerBeans;

   public BeanManager(InjectionProvider injectionProvider, boolean lazyBeanValidation) {
      this.managedBeans = new HashMap();
      this.eagerBeans = new ArrayList(4);
      this.injectionProvider = injectionProvider;
      this.lazyBeanValidation = lazyBeanValidation;
   }

   public BeanManager(InjectionProvider injectionProvider, Map managedBeans, boolean lazyBeanValidation) {
      this(injectionProvider, lazyBeanValidation);
      this.managedBeans = managedBeans;
   }

   public void processEvent(SystemEvent event) throws AbortProcessingException {
      ScopeContext scopeContext = ((PreDestroyCustomScopeEvent)event).getContext();
      Map scope = scopeContext.getScope();
      Iterator var4 = scope.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry entry = (Map.Entry)var4.next();
         String name = (String)entry.getKey();
         if (this.isManaged(name)) {
            BeanBuilder builder = this.getBuilder(name);
            builder.destroy(this.injectionProvider, entry.getValue());
         }
      }

   }

   public boolean isListenerForSource(Object source) {
      return source instanceof ScopeContext;
   }

   public void register(ManagedBeanInfo beanInfo) {
      Object builder;
      String message;
      if (beanInfo.hasListEntry()) {
         if (!beanInfo.hasMapEntry() && !beanInfo.hasManagedProperties()) {
            builder = new ManagedListBeanBuilder(beanInfo);
         } else {
            message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_AS_LIST_CONFIG_ERROR", beanInfo.getName());
            builder = new ErrorBean(beanInfo, message);
         }
      } else if (beanInfo.hasMapEntry()) {
         if (beanInfo.hasManagedProperties()) {
            message = MessageUtils.getExceptionMessageString("com.sun.faces.MANAGED_BEAN_AS_MAP_CONFIG_ERROR", beanInfo.getName());
            builder = new ErrorBean(beanInfo, message);
         } else {
            builder = new ManagedMapBeanBuilder(beanInfo);
         }
      } else {
         builder = new ManagedBeanBuilder(beanInfo);
      }

      this.addBean(beanInfo.getName(), (BeanBuilder)builder);
      if (beanInfo.isEager()) {
         this.eagerBeans.add(beanInfo.getName());
      }

   }

   public List getEagerBeanNames() {
      return this.eagerBeans;
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
         Iterator var1 = this.managedBeans.entrySet().iterator();

         while(var1.hasNext()) {
            Map.Entry entry = (Map.Entry)var1.next();
            this.preProcessBean((String)entry.getKey(), (BeanBuilder)entry.getValue());
         }
      }

   }

   public boolean isBeanInScope(String name, BeanBuilder builder, FacesContext context) {
      return BeanManager.ScopeManager.isInScope(name, builder.getScope(), context);
   }

   public Object getBeanFromScope(String name, BeanBuilder builder, FacesContext context) {
      return BeanManager.ScopeManager.getFromScope(name, builder.getScope(), context);
   }

   public Object getBeanFromScope(String name, FacesContext context) {
      String scope = this.getBuilder(name).getScope();
      return BeanManager.ScopeManager.getFromScope(name, scope, context);
   }

   public Object create(String name, FacesContext facesContext) {
      return this.create(name, (BeanBuilder)this.managedBeans.get(name), facesContext);
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

      if (LOGGER.isLoggable(Level.WARNING) && this.managedBeans.containsKey(beanName)) {
         LOGGER.log(Level.WARNING, "jsf.managed.bean.duplicate", new Object[]{beanName, ((BeanBuilder)this.managedBeans.get(beanName)).beanInfo.getClassName(), builder.beanInfo.getClassName()});
      }

      this.managedBeans.put(beanName, builder);
   }

   private void validateReferences(BeanBuilder builder, List references, List messages) {
      List refs = builder.getReferences();
      if (refs != null) {
         Iterator var5 = refs.iterator();

         while(true) {
            while(true) {
               String ref;
               do {
                  if (!var5.hasNext()) {
                     return;
                  }

                  ref = (String)var5.next();
               } while(!this.isManaged(ref));

               if (references.contains(ref)) {
                  StringBuilder sb = new StringBuilder(64);
                  String[] ra = (String[])references.toArray(new String[references.size()]);
                  String[] var9 = ra;
                  int var10 = ra.length;

                  for(int var11 = 0; var11 < var10; ++var11) {
                     String reference = var9[var11];
                     sb.append(reference);
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
               Iterator var8 = propRefs.iterator();

               while(var8.hasNext()) {
                  String reference = (String)var8.next();
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

      Iterator var5 = messages.iterator();

      while(var5.hasNext()) {
         String message = (String)var5.next();
         sb.append("\n     - ").append(message);
      }

      return sb.toString();
   }

   static {
      LOGGER = FacesLogger.MANAGEDBEAN.getLogger();
   }

   private static class ScopeManager {
      private static final ConcurrentMap handlerMap = new ConcurrentHashMap(5);

      static void pushToScope(String name, Object bean, String customScope, FacesContext context) {
         ScopeHandler handler = getScopeHandler(customScope, context);
         handler.handle(name, bean, context);
      }

      static boolean isInScope(String name, String customScope, FacesContext context) {
         ScopeHandler handler = getScopeHandler(customScope, context);
         return handler.isInScope(name, context);
      }

      static Object getFromScope(String name, String customScope, FacesContext context) {
         ScopeHandler handler = getScopeHandler(customScope, context);
         return handler.getFromScope(name, context);
      }

      private static ScopeHandler getScopeHandler(String customScope, FacesContext context) {
         ScopeHandler handler = (ScopeHandler)handlerMap.get(customScope);
         if (handler == null) {
            ExpressionFactory factory = context.getApplication().getExpressionFactory();
            ValueExpression ve = factory.createValueExpression(context.getELContext(), customScope, Map.class);
            handler = new CustomScopeHandler(ve);
            handlerMap.putIfAbsent(customScope, handler);
         }

         return (ScopeHandler)handler;
      }

      static {
         handlerMap.put(ELUtils.Scope.REQUEST.toString(), new RequestScopeHandler());
         handlerMap.put(ELUtils.Scope.VIEW.toString(), new ViewScopeHandler());
         handlerMap.put(ELUtils.Scope.SESSION.toString(), new SessionScopeHandler());
         handlerMap.put(ELUtils.Scope.APPLICATION.toString(), new ApplicationScopeHandler());
         handlerMap.put(ELUtils.Scope.NONE.toString(), new NoneScopeHandler());
      }

      private static class CustomScopeHandler implements ScopeHandler {
         private ValueExpression scope;

         CustomScopeHandler(ValueExpression scope) {
            this.scope = scope;
         }

         public void handle(String name, Object bean, FacesContext context) {
            Map scopeMap = (Map)this.scope.getValue(this.getELContext(context));
            if (scopeMap != null) {
               synchronized(this) {
                  scopeMap.put(name, bean);
               }
            } else if (BeanManager.LOGGER.isLoggable(Level.WARNING)) {
               BeanManager.LOGGER.log(Level.WARNING, "jsf.managed.bean.custom.scope.eval.null", new Object[]{this.scope.getExpressionString()});
            }

         }

         public boolean isInScope(String name, FacesContext context) {
            Map scopeMap = (Map)this.scope.getValue(this.getELContext(context));
            if (scopeMap != null) {
               return scopeMap.containsKey(name);
            } else {
               if (BeanManager.LOGGER.isLoggable(Level.WARNING)) {
                  BeanManager.LOGGER.log(Level.WARNING, "jsf.managed.bean.custom.scope.eval.null.existence", new Object[]{this.scope.getExpressionString()});
               }

               return true;
            }
         }

         public Object getFromScope(String name, FacesContext context) {
            Map scopeMap = (Map)this.scope.getValue(this.getELContext(context));
            if (scopeMap != null) {
               return scopeMap.get(name);
            } else {
               if (BeanManager.LOGGER.isLoggable(Level.WARNING)) {
                  BeanManager.LOGGER.log(Level.WARNING, "jsf.managed.bean.custom.scope.eval.null.existence", new Object[]{this.scope.getExpressionString()});
               }

               return null;
            }
         }

         private ELContext getELContext(FacesContext ctx) {
            return new CustomScopeELContext(ctx.getELContext());
         }

         private static final class CustomScopeELContext extends ELContext {
            private ELContext delegate;

            public CustomScopeELContext(ELContext delegate) {
               this.delegate = delegate;
            }

            public void putContext(Class aClass, Object o) {
               this.delegate.putContext(aClass, o);
            }

            public Object getContext(Class aClass) {
               return this.delegate.getContext(aClass);
            }

            public Locale getLocale() {
               return this.delegate.getLocale();
            }

            public void setLocale(Locale locale) {
               this.delegate.setLocale(locale);
            }

            public ELResolver getELResolver() {
               return this.delegate.getELResolver();
            }

            public FunctionMapper getFunctionMapper() {
               return this.delegate.getFunctionMapper();
            }

            public VariableMapper getVariableMapper() {
               return this.delegate.getVariableMapper();
            }
         }
      }

      private static class ApplicationScopeHandler implements ScopeHandler {
         private ApplicationScopeHandler() {
         }

         public void handle(String name, Object bean, FacesContext context) {
            synchronized(context.getExternalContext().getContext()) {
               context.getExternalContext().getApplicationMap().put(name, bean);
            }
         }

         public boolean isInScope(String name, FacesContext context) {
            return context.getExternalContext().getApplicationMap().containsKey(name);
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
            synchronized(context.getExternalContext().getSession(true)) {
               context.getExternalContext().getSessionMap().put(name, bean);
            }
         }

         public boolean isInScope(String name, FacesContext context) {
            return context.getExternalContext().getSessionMap().containsKey(name);
         }

         public Object getFromScope(String name, FacesContext context) {
            return context.getExternalContext().getSessionMap().get(name);
         }

         // $FF: synthetic method
         SessionScopeHandler(Object x0) {
            this();
         }
      }

      private static class ViewScopeHandler implements ScopeHandler {
         private ViewScopeHandler() {
         }

         public void handle(String name, Object bean, FacesContext context) {
            Map viewMap = context.getViewRoot().getViewMap();
            if (viewMap != null) {
               viewMap.put(name, bean);
            }

         }

         public boolean isInScope(String name, FacesContext context) {
            Map viewMap = context.getViewRoot().getViewMap(false);
            return viewMap != null && viewMap.containsKey(name);
         }

         public Object getFromScope(String name, FacesContext context) {
            Map viewMap = context.getViewRoot().getViewMap(false);
            return viewMap != null ? viewMap.get(name) : null;
         }

         // $FF: synthetic method
         ViewScopeHandler(Object x0) {
            this();
         }
      }

      private static class RequestScopeHandler implements ScopeHandler {
         private RequestScopeHandler() {
         }

         public void handle(String name, Object bean, FacesContext context) {
            context.getExternalContext().getRequestMap().put(name, bean);
         }

         public boolean isInScope(String name, FacesContext context) {
            return context.getExternalContext().getRequestMap().containsKey(name);
         }

         public Object getFromScope(String name, FacesContext context) {
            return context.getExternalContext().getRequestMap().get(name);
         }

         // $FF: synthetic method
         RequestScopeHandler(Object x0) {
            this();
         }
      }

      private static class NoneScopeHandler implements ScopeHandler {
         private NoneScopeHandler() {
         }

         public void handle(String name, Object bean, FacesContext context) {
         }

         public boolean isInScope(String name, FacesContext context) {
            return false;
         }

         public Object getFromScope(String name, FacesContext context) {
            return null;
         }

         // $FF: synthetic method
         NoneScopeHandler(Object x0) {
            this();
         }
      }

      private interface ScopeHandler {
         void handle(String var1, Object var2, FacesContext var3);

         boolean isInScope(String var1, FacesContext var2);

         Object getFromScope(String var1, FacesContext var2);
      }
   }
}
