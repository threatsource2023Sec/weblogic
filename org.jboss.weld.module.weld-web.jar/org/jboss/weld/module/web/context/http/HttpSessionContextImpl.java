package org.jboss.weld.module.web.context.http;

import java.lang.annotation.Annotation;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jboss.weld.Container;
import org.jboss.weld.config.ConfigurationKey;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.context.http.HttpConversationContext;
import org.jboss.weld.context.http.HttpSessionContext;
import org.jboss.weld.contexts.AbstractBoundContext;
import org.jboss.weld.contexts.beanstore.AttributeBeanStore;
import org.jboss.weld.contexts.beanstore.BoundBeanStore;
import org.jboss.weld.contexts.beanstore.NamingScheme;
import org.jboss.weld.contexts.beanstore.SimpleBeanIdentifierIndexNamingScheme;
import org.jboss.weld.logging.ContextLogger;
import org.jboss.weld.module.web.context.beanstore.http.EagerSessionBeanStore;
import org.jboss.weld.module.web.context.beanstore.http.LazySessionBeanStore;
import org.jboss.weld.serialization.BeanIdentifierIndex;

public class HttpSessionContextImpl extends AbstractBoundContext implements HttpSessionContext {
   static final String NAMING_SCHEME_PREFIX = "WELD_S";
   static final String KEY_BEAN_ID_INDEX_HASH = "WELD_S_HASH";
   private final NamingScheme namingScheme;
   private final String contextId;

   public HttpSessionContextImpl(String contextId, BeanIdentifierIndex index) {
      super(contextId, true);
      this.namingScheme = new SimpleBeanIdentifierIndexNamingScheme("WELD_S", index);
      this.contextId = contextId;
   }

   public boolean associate(HttpServletRequest request) {
      if (this.getBeanStore() != null) {
         ContextLogger.LOG.beanStoreLeakDuringAssociation(this.getClass().getName(), request);
      }

      this.setBeanStore(new LazySessionBeanStore(request, this.namingScheme, ((WeldConfiguration)this.getServiceRegistry().getRequired(WeldConfiguration.class)).getBooleanProperty(ConfigurationKey.CONTEXT_ATTRIBUTES_LAZY_FETCH), this.getServiceRegistry()));
      this.checkBeanIdentifierIndexConsistency(request);
      return true;
   }

   public boolean destroy(HttpSession session) {
      BoundBeanStore beanStore = this.getBeanStore();
      if (beanStore == null) {
         boolean var4;
         try {
            HttpConversationContext conversationContext = this.getConversationContext();
            this.setBeanStore(new EagerSessionBeanStore(this.namingScheme, session, this.getServiceRegistry()));
            this.activate();
            this.invalidate();
            conversationContext.destroy(session);
            this.deactivate();
            this.setBeanStore((BoundBeanStore)null);
            var4 = true;
         } finally {
            this.cleanup();
         }

         return var4;
      } else {
         this.invalidate();
         if (beanStore instanceof AttributeBeanStore) {
            AttributeBeanStore attributeBeanStore = (AttributeBeanStore)beanStore;
            if (attributeBeanStore.isAttributeLazyFetchingEnabled()) {
               attributeBeanStore.fetchUninitializedAttributes();
            }
         }

         this.getConversationContext().destroy(session);
         return false;
      }
   }

   public Class getScope() {
      return SessionScoped.class;
   }

   protected HttpConversationContext getConversationContext() {
      return (HttpConversationContext)Container.instance(this.contextId).deploymentManager().instance().select(HttpConversationContext.class, new Annotation[0]).get();
   }

   protected Conversation getConversation() {
      return (Conversation)Container.instance(this.contextId).deploymentManager().instance().select(Conversation.class, new Annotation[0]).get();
   }

   private void checkBeanIdentifierIndexConsistency(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      if (session != null) {
         BeanIdentifierIndex index = (BeanIdentifierIndex)this.getServiceRegistry().get(BeanIdentifierIndex.class);
         if (index != null && index.isBuilt()) {
            Object hash = session.getAttribute("WELD_S_HASH");
            if (hash != null) {
               if (!index.getIndexHash().equals(hash)) {
                  throw ContextLogger.LOG.beanIdentifierIndexInconsistencyDetected(hash.toString(), index.getDebugInfo());
               }
            } else {
               session.setAttribute("WELD_S_HASH", index.getIndexHash());
            }
         }
      }

   }
}
