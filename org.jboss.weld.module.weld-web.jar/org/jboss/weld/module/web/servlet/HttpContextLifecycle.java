package org.jboss.weld.module.web.servlet;

import java.lang.annotation.Annotation;
import java.util.Collections;
import javax.enterprise.context.Initialized.Literal;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jboss.weld.Container;
import org.jboss.weld.bootstrap.BeanDeploymentModule;
import org.jboss.weld.bootstrap.BeanDeploymentModules;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.context.BoundContext;
import org.jboss.weld.context.ManagedContext;
import org.jboss.weld.context.http.HttpRequestContext;
import org.jboss.weld.context.http.HttpSessionContext;
import org.jboss.weld.contexts.cache.RequestScopedCache;
import org.jboss.weld.event.EventMetadataImpl;
import org.jboss.weld.event.FastEvent;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.module.web.context.http.HttpRequestContextImpl;
import org.jboss.weld.module.web.context.http.HttpSessionDestructionContext;
import org.jboss.weld.module.web.logging.ServletLogger;
import org.jboss.weld.servlet.spi.HttpContextActivationFilter;
import org.jboss.weld.util.reflection.Reflections;

public class HttpContextLifecycle implements Service {
   public static final String ASYNC_STARTED_ATTR_NAME = "org.jboss.weld.context.asyncStarted";
   private static final String HTTP_SESSION = "org.jboss.weld." + HttpSession.class.getName();
   private static final String INCLUDE_HEADER = "javax.servlet.include.request_uri";
   private static final String FORWARD_HEADER = "javax.servlet.forward.request_uri";
   private static final String REQUEST_DESTROYED = HttpContextLifecycle.class.getName() + ".request.destroyed";
   private static final String GUARD_PARAMETER_NAME = "org.jboss.weld.context.ignore.guard.marker";
   private static final Object GUARD_PARAMETER_VALUE = new Object();
   private HttpSessionDestructionContext sessionDestructionContextCache;
   private HttpSessionContext sessionContextCache;
   private HttpRequestContext requestContextCache;
   private volatile Boolean conversationActivationEnabled;
   private final boolean ignoreForwards;
   private final boolean ignoreIncludes;
   private final BeanManagerImpl beanManager;
   private final ConversationContextActivator conversationContextActivator;
   private final HttpContextActivationFilter contextActivationFilter;
   private final FastEvent requestInitializedEvent;
   private final FastEvent requestBeforeDestroyedEvent;
   private final FastEvent requestDestroyedEvent;
   private final FastEvent sessionInitializedEvent;
   private final FastEvent sessionBeforeDestroyedEvent;
   private final FastEvent sessionDestroyedEvent;
   private final ServletApiAbstraction servletApi;
   private final ServletContextService servletContextService;
   private final Container container;
   private final BeanDeploymentModule module;
   private static final ThreadLocal nestedInvocationGuard = new ThreadLocal();
   private final boolean nestedInvocationGuardEnabled;

   public HttpContextLifecycle(BeanManagerImpl beanManager, HttpContextActivationFilter contextActivationFilter, boolean ignoreForwards, boolean ignoreIncludes, boolean lazyConversationContext, boolean nestedInvocationGuardEnabled) {
      this.beanManager = beanManager;
      this.conversationContextActivator = new ConversationContextActivator(beanManager, lazyConversationContext);
      this.conversationActivationEnabled = null;
      this.ignoreForwards = ignoreForwards;
      this.ignoreIncludes = ignoreIncludes;
      this.contextActivationFilter = contextActivationFilter;
      this.requestInitializedEvent = FastEvent.of(HttpServletRequest.class, beanManager, new Annotation[]{Literal.REQUEST});
      this.requestBeforeDestroyedEvent = FastEvent.of(HttpServletRequest.class, beanManager, new Annotation[]{javax.enterprise.context.BeforeDestroyed.Literal.REQUEST});
      this.requestDestroyedEvent = FastEvent.of(HttpServletRequest.class, beanManager, new Annotation[]{javax.enterprise.context.Destroyed.Literal.REQUEST});
      this.sessionInitializedEvent = FastEvent.of(HttpSession.class, beanManager, new Annotation[]{Literal.SESSION});
      this.sessionBeforeDestroyedEvent = FastEvent.of(HttpSession.class, beanManager, new Annotation[]{javax.enterprise.context.BeforeDestroyed.Literal.SESSION});
      this.sessionDestroyedEvent = FastEvent.of(HttpSession.class, beanManager, new Annotation[]{javax.enterprise.context.Destroyed.Literal.SESSION});
      this.servletApi = (ServletApiAbstraction)beanManager.getServices().get(ServletApiAbstraction.class);
      this.servletContextService = (ServletContextService)beanManager.getServices().get(ServletContextService.class);
      this.nestedInvocationGuardEnabled = nestedInvocationGuardEnabled;
      this.container = Container.instance(beanManager);
      BeanDeploymentModules beanDeploymentModules = (BeanDeploymentModules)beanManager.getServices().get(BeanDeploymentModules.class);
      this.module = beanDeploymentModules != null ? beanDeploymentModules.getModule(beanManager) : null;
   }

   private HttpSessionDestructionContext getSessionDestructionContext() {
      if (this.sessionDestructionContextCache == null) {
         this.sessionDestructionContextCache = (HttpSessionDestructionContext)this.beanManager.instance().select(HttpSessionDestructionContext.class, new Annotation[0]).get();
      }

      return this.sessionDestructionContextCache;
   }

   private HttpSessionContext getSessionContext() {
      if (this.sessionContextCache == null) {
         this.sessionContextCache = (HttpSessionContext)this.beanManager.instance().select(HttpSessionContext.class, new Annotation[0]).get();
      }

      return this.sessionContextCache;
   }

   public HttpRequestContext getRequestContext() {
      if (this.requestContextCache == null) {
         this.requestContextCache = (HttpRequestContext)this.beanManager.instance().select(HttpRequestContext.class, new Annotation[0]).get();
      }

      return this.requestContextCache;
   }

   public void contextInitialized(ServletContext ctx) {
      this.servletContextService.contextInitialized(ctx);
      this.fireEventForApplicationScope(ctx, Literal.APPLICATION);
   }

   public void contextDestroyed(ServletContext ctx) {
      this.fireEventForApplicationScope(ctx, javax.enterprise.context.BeforeDestroyed.Literal.APPLICATION);
      this.fireEventForApplicationScope(ctx, javax.enterprise.context.Destroyed.Literal.APPLICATION);
   }

   private void fireEventForApplicationScope(ServletContext ctx, Annotation qualifier) {
      if (this.module != null) {
         synchronized(this.container) {
            if (this.module.isWebModule()) {
               this.module.fireEvent(ServletContext.class, ctx, new Annotation[]{qualifier});
            } else {
               ServletLogger.LOG.noEeModuleDescriptor(this.beanManager);
               EventMetadata metadata = new EventMetadataImpl(ServletContext.class, (InjectionPoint)null, Collections.singleton(qualifier));
               this.beanManager.getAccessibleLenientObserverNotifier().fireEvent(ServletContext.class, ctx, metadata, new Annotation[]{qualifier});
            }
         }
      }

   }

   public void sessionCreated(HttpSession session) {
      SessionHolder.sessionCreated(session);
      this.conversationContextActivator.sessionCreated(session);
      this.sessionInitializedEvent.fire(session);
   }

   public void sessionDestroyed(HttpSession session) {
      this.deactivateSessionDestructionContext(session);
      boolean destroyed = this.getSessionContext().destroy(session);
      SessionHolder.clear();
      RequestScopedCache.endRequest();
      if (destroyed) {
         this.sessionDestroyedEvent.fire(session);
      } else if (this.getRequestContext() instanceof HttpRequestContextImpl) {
         HttpServletRequest request = ((HttpRequestContextImpl)Reflections.cast(this.getRequestContext())).getHttpServletRequest();
         request.setAttribute(HTTP_SESSION, session);
      }

   }

   private void deactivateSessionDestructionContext(HttpSession session) {
      HttpSessionDestructionContext context = this.getSessionDestructionContext();
      if (context.isActive()) {
         context.deactivate();
         context.dissociate(session);
      }

   }

   public void requestInitialized(HttpServletRequest request, ServletContext ctx) {
      if (this.nestedInvocationGuardEnabled) {
         Counter counter = (Counter)nestedInvocationGuard.get();
         Object marker = request.getAttribute("org.jboss.weld.context.ignore.guard.marker");
         if (counter != null && marker != null) {
            counter.value++;
            return;
         }

         if (counter != null && marker == null) {
            ServletLogger.LOG.guardLeak(counter.value);
         }

         nestedInvocationGuard.set(new Counter());
         request.setAttribute("org.jboss.weld.context.ignore.guard.marker", GUARD_PARAMETER_VALUE);
      }

      if (!this.ignoreForwards || !this.isForwardedRequest(request)) {
         if (!this.ignoreIncludes || !this.isIncludedRequest(request)) {
            if (this.contextActivationFilter.accepts(request)) {
               ServletLogger.LOG.requestInitialized(request);
               SessionHolder.requestInitialized(request);
               this.getRequestContext().associate(request);
               this.getSessionContext().associate(request);
               if (this.conversationActivationEnabled) {
                  this.conversationContextActivator.associateConversationContext(request);
               }

               this.getRequestContext().activate();
               this.getSessionContext().activate();

               try {
                  if (this.conversationActivationEnabled) {
                     this.conversationContextActivator.activateConversationContext(request);
                  }

                  this.requestInitializedEvent.fire(request);
               } catch (RuntimeException var6) {
                  try {
                     this.requestDestroyed(request);
                  } catch (Exception var5) {
                  }

                  request.setAttribute(REQUEST_DESTROYED, Boolean.TRUE);
                  throw var6;
               }
            }
         }
      }
   }

   public void requestDestroyed(HttpServletRequest request) {
      if (!this.isRequestDestroyed(request)) {
         if (this.nestedInvocationGuardEnabled) {
            Counter counter = (Counter)nestedInvocationGuard.get();
            if (counter == null) {
               ServletLogger.LOG.guardNotSet();
               return;
            }

            counter.value--;
            if (counter.value > 0) {
               return;
            }

            nestedInvocationGuard.remove();
            request.removeAttribute("org.jboss.weld.context.ignore.guard.marker");
         }

         if (!this.ignoreForwards || !this.isForwardedRequest(request)) {
            if (!this.ignoreIncludes || !this.isIncludedRequest(request)) {
               if (this.contextActivationFilter.accepts(request)) {
                  ServletLogger.LOG.requestDestroyed(request);

                  try {
                     this.conversationContextActivator.deactivateConversationContext(request);
                     if (this.servletApi.isAsyncSupported() && this.servletApi.isAsyncStarted(request)) {
                        request.setAttribute("org.jboss.weld.context.asyncStarted", true);
                     } else {
                        this.getRequestContext().invalidate();
                     }

                     this.requestBeforeDestroyedEvent.fire(request);
                     this.safelyDeactivate(this.getRequestContext(), request);
                     this.requestDestroyedEvent.fire(request);
                     Object destroyedHttpSession = request.getAttribute(HTTP_SESSION);
                     if (destroyedHttpSession != null) {
                        this.sessionBeforeDestroyedEvent.fire((HttpSession)destroyedHttpSession);
                     }

                     this.safelyDeactivate(this.getSessionContext(), request);
                     if (destroyedHttpSession != null) {
                        this.sessionDestroyedEvent.fire((HttpSession)destroyedHttpSession);
                     }
                  } finally {
                     this.safelyDissociate(this.getRequestContext(), request);
                     this.safelyDissociate(this.getSessionContext(), request);
                     this.conversationContextActivator.disassociateConversationContext(request);
                     SessionHolder.clear();
                  }

               }
            }
         }
      }
   }

   public boolean isConversationActivationSet() {
      return this.conversationActivationEnabled != null;
   }

   public void setConversationActivationEnabled(boolean conversationActivationEnabled) {
      this.conversationActivationEnabled = conversationActivationEnabled;
   }

   public void cleanup() {
   }

   private boolean isIncludedRequest(HttpServletRequest request) {
      return request.getAttribute("javax.servlet.include.request_uri") != null;
   }

   private boolean isForwardedRequest(HttpServletRequest request) {
      return request.getAttribute("javax.servlet.forward.request_uri") != null;
   }

   private boolean isRequestDestroyed(HttpServletRequest request) {
      return request.getAttribute(REQUEST_DESTROYED) != null;
   }

   private void safelyDissociate(BoundContext context, Object storage) {
      try {
         context.dissociate(storage);
      } catch (Exception var4) {
         ServletLogger.LOG.unableToDissociateContext(context, storage);
         ServletLogger.LOG.catchingDebug(var4);
      }

   }

   private void safelyDeactivate(ManagedContext context, HttpServletRequest request) {
      try {
         context.deactivate();
      } catch (Exception var4) {
         ServletLogger.LOG.unableToDeactivateContext(context, request);
         ServletLogger.LOG.catchingDebug(var4);
      }

   }

   private static class Counter {
      private int value;

      private Counter() {
         this.value = 1;
      }

      // $FF: synthetic method
      Counter(Object x0) {
         this();
      }
   }
}
