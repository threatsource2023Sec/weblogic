package org.jboss.weld.module.web.servlet;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.enterprise.context.Initialized.Literal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.context.http.HttpConversationContext;
import org.jboss.weld.contexts.AbstractConversationContext;
import org.jboss.weld.event.FastEvent;
import org.jboss.weld.logging.ContextLogger;
import org.jboss.weld.logging.ConversationLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.module.web.context.http.LazyHttpConversationContextImpl;
import org.jboss.weld.module.web.logging.ServletLogger;
import org.jboss.weld.util.reflection.Reflections;

public class ConversationContextActivator {
   private static final String NO_CID = "nocid";
   private static final String CONVERSATION_PROPAGATION = "conversationPropagation";
   private static final String CONVERSATION_PROPAGATION_NONE = "none";
   private static final String CONTEXT_ACTIVATED_IN_REQUEST = ConversationContextActivator.class.getName() + ".contextActivatedInRequest";
   private final BeanManagerImpl beanManager;
   private HttpConversationContext httpConversationContextCache;
   private final FastEvent conversationInitializedEvent;
   private final FastEvent conversationBeforeDestroyedEvent;
   private final FastEvent conversationDestroyedEvent;
   private final Consumer lazyInitializationCallback;
   private final boolean lazy;

   protected ConversationContextActivator(BeanManagerImpl beanManager, boolean lazy) {
      this.beanManager = beanManager;
      this.conversationInitializedEvent = FastEvent.of(HttpServletRequest.class, beanManager, new Annotation[]{Literal.CONVERSATION});
      this.conversationBeforeDestroyedEvent = FastEvent.of(HttpServletRequest.class, beanManager, new Annotation[]{javax.enterprise.context.BeforeDestroyed.Literal.CONVERSATION});
      this.conversationDestroyedEvent = FastEvent.of(HttpServletRequest.class, beanManager, new Annotation[]{javax.enterprise.context.Destroyed.Literal.CONVERSATION});
      this.lazyInitializationCallback = lazy ? this.conversationInitializedEvent::fire : null;
      this.lazy = lazy;
   }

   private HttpConversationContext httpConversationContext() {
      if (this.httpConversationContextCache == null) {
         this.httpConversationContextCache = (HttpConversationContext)this.beanManager.instance().select(HttpConversationContext.class, new Annotation[0]).get();
      }

      return this.httpConversationContextCache;
   }

   public void startConversationContext(HttpServletRequest request) {
      this.associateConversationContext(request);
      this.activateConversationContext(request);
   }

   public void stopConversationContext(HttpServletRequest request) {
      this.deactivateConversationContext(request);
   }

   protected void activateConversationContext(HttpServletRequest request) {
      HttpConversationContext conversationContext = this.httpConversationContext();
      if (!this.isContextActivatedInRequest(request)) {
         this.setContextActivatedInRequest(request);
         this.activate(conversationContext, request);
      } else {
         conversationContext.dissociate(request);
         conversationContext.associate(request);
         this.activate(conversationContext, request);
      }

   }

   private void activate(HttpConversationContext conversationContext, HttpServletRequest request) {
      if (this.lazy) {
         conversationContext.activateLazily(this.lazyInitializationCallback);
      } else {
         String cid = determineConversationId(request, conversationContext.getParameterName());
         conversationContext.activate(cid);
         if (cid == null) {
            this.conversationInitializedEvent.fire(request);
         }
      }

   }

   protected void associateConversationContext(HttpServletRequest request) {
      this.httpConversationContext().associate(request);
   }

   private void setContextActivatedInRequest(HttpServletRequest request) {
      request.setAttribute(CONTEXT_ACTIVATED_IN_REQUEST, true);
   }

   private boolean isContextActivatedInRequest(HttpServletRequest request) {
      Object result = request.getAttribute(CONTEXT_ACTIVATED_IN_REQUEST);
      return result == null ? false : (Boolean)result;
   }

   protected void deactivateConversationContext(HttpServletRequest request) {
      try {
         ConversationContext conversationContext = this.httpConversationContext();
         if (conversationContext.isActive()) {
            if (conversationContext instanceof LazyHttpConversationContextImpl) {
               LazyHttpConversationContextImpl lazyConversationContext = (LazyHttpConversationContextImpl)conversationContext;
               if (!lazyConversationContext.isInitialized()) {
                  lazyConversationContext.deactivate();
                  this.processDestructionQueue(request);
                  return;
               }
            }

            boolean isTransient = conversationContext.getCurrentConversation().isTransient();
            if (ConversationLogger.LOG.isTraceEnabled()) {
               if (isTransient) {
                  ConversationLogger.LOG.cleaningUpTransientConversation();
               } else {
                  ConversationLogger.LOG.cleaningUpConversation(conversationContext.getCurrentConversation().getId());
               }
            }

            if (isTransient) {
               this.conversationBeforeDestroyedEvent.fire(request);
            }

            conversationContext.invalidate();
            conversationContext.deactivate();
            if (isTransient) {
               this.conversationDestroyedEvent.fire(request);
            }

            this.processDestructionQueue(request);
         }
      } catch (Exception var4) {
         ServletLogger.LOG.unableToDeactivateContext(this.httpConversationContext(), request);
         ServletLogger.LOG.catchingDebug(var4);
      }

   }

   private void processDestructionQueue(HttpServletRequest request) {
      Object contextsAttribute = request.getAttribute(AbstractConversationContext.DESTRUCTION_QUEUE_ATTRIBUTE_NAME);
      if (contextsAttribute instanceof Map) {
         Map contexts = (Map)Reflections.cast(contextsAttribute);
         synchronized(contexts) {
            FastEvent beforeDestroyedEvent = FastEvent.of(String.class, this.beanManager, new Annotation[]{javax.enterprise.context.BeforeDestroyed.Literal.CONVERSATION});
            FastEvent destroyedEvent = FastEvent.of(String.class, this.beanManager, new Annotation[]{javax.enterprise.context.Destroyed.Literal.CONVERSATION});
            Iterator iterator = contexts.entrySet().iterator();

            while(iterator.hasNext()) {
               Map.Entry entry = (Map.Entry)iterator.next();
               beforeDestroyedEvent.fire(entry.getKey());
               Iterator var9 = ((List)entry.getValue()).iterator();

               while(var9.hasNext()) {
                  ContextualInstance contextualInstance = (ContextualInstance)var9.next();
                  this.destroyContextualInstance(contextualInstance);
               }

               destroyedEvent.fire(entry.getKey());
               iterator.remove();
            }
         }
      }

   }

   private void destroyContextualInstance(ContextualInstance instance) {
      instance.getContextual().destroy(instance.getInstance(), instance.getCreationalContext());
      ContextLogger.LOG.contextualInstanceRemoved(instance, this);
   }

   protected void disassociateConversationContext(HttpServletRequest request) {
      try {
         this.httpConversationContext().dissociate(request);
      } catch (Exception var3) {
         ServletLogger.LOG.unableToDissociateContext(this.httpConversationContext(), request);
         ServletLogger.LOG.catchingDebug(var3);
      }

   }

   public void sessionCreated(HttpSession session) {
      HttpConversationContext httpConversationContext = this.httpConversationContext();
      if (httpConversationContext instanceof AbstractConversationContext) {
         AbstractConversationContext abstractConversationContext = (AbstractConversationContext)httpConversationContext;
         abstractConversationContext.sessionCreated();
      }

   }

   public static String determineConversationId(HttpServletRequest request, String parameterName) {
      if (request == null) {
         throw ConversationLogger.LOG.mustCallAssociateBeforeActivate();
      } else if (request.getParameter("nocid") != null) {
         return null;
      } else if ("none".equals(request.getParameter("conversationPropagation"))) {
         return null;
      } else {
         String cid = request.getParameter(parameterName);
         ConversationLogger.LOG.foundConversationFromRequest(cid);
         return cid;
      }
   }
}
