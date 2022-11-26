package org.jboss.weld.module.web.context.http;

import java.util.function.Consumer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.logging.ConversationLogger;
import org.jboss.weld.module.web.servlet.ConversationContextActivator;

public class LazyHttpConversationContextImpl extends HttpConversationContextImpl {
   private final ThreadLocal transientConversationInitializationCallback = new ThreadLocal();
   private final ThreadLocal initialized = new ThreadLocal();

   public LazyHttpConversationContextImpl(String contextId, ServiceRegistry services) {
      super(contextId, services);
   }

   public void activateLazily(Consumer transientConversationInitializationCallback) {
      this.activate();
      this.transientConversationInitializationCallback.set(transientConversationInitializationCallback);
   }

   public void activate() {
      if (!this.isAssociated()) {
         throw ConversationLogger.LOG.mustCallAssociateBeforeActivate();
      } else {
         if (!this.isActive()) {
            super.setActive(true);
         } else {
            ConversationLogger.LOG.contextAlreadyActive(this.getRequest());
         }

         this.initialized.set((Object)null);
      }
   }

   public boolean isInitialized() {
      return this.initialized.get() != null;
   }

   protected void initialize(String cid) {
      this.initialized.set(Boolean.TRUE);
      super.initialize(cid);
   }

   public void deactivate() {
      try {
         if (this.isInitialized()) {
            try {
               super.deactivate();
            } finally {
               this.initialized.set((Object)null);
            }
         } else {
            this.removeState();
         }
      } finally {
         this.transientConversationInitializationCallback.set((Object)null);
      }

   }

   public boolean destroy(HttpSession session) {
      if (this.isAssociated()) {
         this.checkContextInitialized();
      }

      return super.destroy(session);
   }

   protected void checkContextInitialized() {
      if (!this.isInitialized()) {
         HttpServletRequest request = (HttpServletRequest)this.getRequest();
         String cid = ConversationContextActivator.determineConversationId(request, this.getParameterName());
         this.initialize(cid);
         if (cid == null) {
            Consumer callback = (Consumer)this.transientConversationInitializationCallback.get();
            if (callback != null) {
               callback.accept(request);
            }
         }
      }

   }
}
