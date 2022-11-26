package org.jboss.weld.contexts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.BeforeDestroyed.Literal;
import org.jboss.weld.Container;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.config.ConfigurationKey;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.ManagedConversation;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.contexts.beanstore.BoundBeanStore;
import org.jboss.weld.contexts.beanstore.ConversationNamingScheme;
import org.jboss.weld.contexts.beanstore.NamingScheme;
import org.jboss.weld.contexts.conversation.ConversationIdGenerator;
import org.jboss.weld.contexts.conversation.ConversationImpl;
import org.jboss.weld.event.FastEvent;
import org.jboss.weld.logging.ConversationLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.BeanIdentifierIndex;
import org.jboss.weld.util.LazyValueHolder;
import org.jboss.weld.util.reflection.Reflections;

public abstract class AbstractConversationContext extends AbstractBoundContext implements ConversationContext {
   public static final String CONVERSATIONS_ATTRIBUTE_NAME = ConversationContext.class.getName() + ".conversations";
   public static final String DESTRUCTION_QUEUE_ATTRIBUTE_NAME = ConversationContext.class.getName() + ".destructionQueue";
   private static final String CURRENT_CONVERSATION_ATTRIBUTE_NAME = ConversationContext.class.getName() + ".currentConversation";
   private static final String PARAMETER_NAME = "cid";
   private final AtomicReference parameterName = new AtomicReference("cid");
   private final AtomicLong defaultTimeout;
   private final AtomicLong concurrentAccessTimeout;
   private final ThreadLocal associated;
   private final BeanManagerImpl manager;
   private final BeanIdentifierIndex beanIdentifierIndex;
   private final LazyValueHolder conversationBeforeDestroyedEvent = new LazyValueHolder() {
      protected FastEvent computeValue() {
         return FastEvent.of(String.class, AbstractConversationContext.this.manager, AbstractConversationContext.this.manager.getGlobalLenientObserverNotifier(), Literal.CONVERSATION);
      }
   };
   private final LazyValueHolder conversationDestroyedEvent = new LazyValueHolder() {
      protected FastEvent computeValue() {
         return FastEvent.of(String.class, AbstractConversationContext.this.manager, AbstractConversationContext.this.manager.getGlobalLenientObserverNotifier(), javax.enterprise.context.Destroyed.Literal.CONVERSATION);
      }
   };

   public AbstractConversationContext(String contextId, ServiceRegistry services) {
      super(contextId, true);
      WeldConfiguration configuration = (WeldConfiguration)services.get(WeldConfiguration.class);
      this.defaultTimeout = new AtomicLong(configuration.getLongProperty(ConfigurationKey.CONVERSATION_TIMEOUT));
      this.concurrentAccessTimeout = new AtomicLong(configuration.getLongProperty(ConfigurationKey.CONVERSATION_CONCURRENT_ACCESS_TIMEOUT));
      this.associated = new ThreadLocal();
      this.manager = Container.instance(contextId).deploymentManager();
      this.beanIdentifierIndex = (BeanIdentifierIndex)services.get(BeanIdentifierIndex.class);
   }

   public String getParameterName() {
      return (String)this.parameterName.get();
   }

   public void setParameterName(String cid) {
      this.parameterName.set(cid);
   }

   public void setConcurrentAccessTimeout(long timeout) {
      this.concurrentAccessTimeout.set(timeout);
   }

   public long getConcurrentAccessTimeout() {
      return this.concurrentAccessTimeout.get();
   }

   public void setDefaultTimeout(long timeout) {
      this.defaultTimeout.set(timeout);
   }

   public long getDefaultTimeout() {
      return this.defaultTimeout.get();
   }

   public boolean associate(Object request) {
      this.associated.set(request);
      return true;
   }

   public boolean dissociate(Object request) {
      if (this.isAssociated()) {
         boolean var2;
         try {
            this.copyConversationIdGeneratorAndConversationsToSession();
            var2 = true;
         } finally {
            this.associated.set((Object)null);
            this.cleanup();
         }

         return var2;
      } else {
         return false;
      }
   }

   protected void copyConversationIdGeneratorAndConversationsToSession() {
      Object request = this.getRequest();
      if (request != null) {
         Object conversationIdGenerator = this.getRequestAttribute(request, ConversationIdGenerator.CONVERSATION_ID_GENERATOR_ATTRIBUTE_NAME);
         if (conversationIdGenerator != null && this.getSessionAttribute(request, ConversationIdGenerator.CONVERSATION_ID_GENERATOR_ATTRIBUTE_NAME, false) == null) {
            this.setSessionAttribute(request, ConversationIdGenerator.CONVERSATION_ID_GENERATOR_ATTRIBUTE_NAME, conversationIdGenerator, false);
         }

         Object conversationMap = this.getRequestAttribute(request, CONVERSATIONS_ATTRIBUTE_NAME);
         if (conversationMap != null && this.getSessionAttribute(request, CONVERSATIONS_ATTRIBUTE_NAME, false) == null) {
            this.setSessionAttribute(request, CONVERSATIONS_ATTRIBUTE_NAME, conversationMap, false);
         }

      }
   }

   public void sessionCreated() {
      this.copyConversationIdGeneratorAndConversationsToSession();
   }

   protected void associateRequestWithNewConversation() {
      ManagedConversation conversation = new ConversationImpl(this.manager);
      this.lock(conversation);
      this.setRequestAttribute(this.getRequest(), CURRENT_CONVERSATION_ATTRIBUTE_NAME, conversation);
      NamingScheme namingScheme = new ConversationNamingScheme(this.getNamingSchemePrefix(), "transient", this.beanIdentifierIndex);
      this.setBeanStore(this.createRequestBeanStore(namingScheme, this.getRequest()));
      this.setRequestAttribute(this.getRequest(), ConversationNamingScheme.PARAMETER_NAME, namingScheme);
   }

   protected void associateRequest(ManagedConversation conversation) {
      this.setRequestAttribute(this.getRequest(), CURRENT_CONVERSATION_ATTRIBUTE_NAME, conversation);
      NamingScheme namingScheme = new ConversationNamingScheme(this.getNamingSchemePrefix(), conversation.getId(), this.beanIdentifierIndex);
      this.setBeanStore(this.createRequestBeanStore(namingScheme, this.getRequest()));
      this.getBeanStore().attach();
   }

   public void activate() {
      this.activate((String)null);
   }

   public void activate(String cid) {
      if (!this.isAssociated()) {
         throw ConversationLogger.LOG.mustCallAssociateBeforeActivate();
      } else {
         if (!this.isActive()) {
            super.setActive(true);
         } else {
            ConversationLogger.LOG.contextAlreadyActive(this.getRequest());
         }

         this.initialize(cid);
      }
   }

   protected void initialize(String cid) {
      if (cid != null && !cid.isEmpty()) {
         ManagedConversation conversation = this.getConversation(cid);
         if (conversation == null || isExpired(conversation)) {
            this.associateRequestWithNewConversation();
            throw ConversationLogger.LOG.noConversationFoundToRestore(cid);
         }

         boolean lock = this.lock(conversation);
         if (!lock) {
            this.associateRequestWithNewConversation();
            throw ConversationLogger.LOG.conversationLockTimedout(cid);
         }

         if (conversation.isTransient()) {
            this.associateRequestWithNewConversation();
            throw ConversationLogger.LOG.noConversationFoundToRestore(cid);
         }

         this.associateRequest(conversation);
      } else {
         this.associateRequestWithNewConversation();
      }

   }

   private boolean lock(ManagedConversation conversation) {
      return conversation.lock(this.getConcurrentAccessTimeout());
   }

   public void deactivate() {
      if (this.isActive()) {
         if (!this.isAssociated()) {
            throw ConversationLogger.LOG.mustCallAssociateBeforeDeactivate();
         } else {
            try {
               if (this.getCurrentConversation().isTransient() && this.getRequestAttribute(this.getRequest(), ConversationNamingScheme.PARAMETER_NAME) != null) {
                  this.destroy();
               } else {
                  this.getCurrentConversation().touch();
                  if (!this.getBeanStore().isAttached()) {
                     if (!(this.getRequestAttribute(this.getRequest(), ConversationNamingScheme.PARAMETER_NAME) instanceof ConversationNamingScheme)) {
                        throw ConversationLogger.LOG.conversationNamingSchemeNotFound();
                     }

                     ((ConversationNamingScheme)this.getRequestAttribute(this.getRequest(), ConversationNamingScheme.PARAMETER_NAME)).setCid(this.getCurrentConversation().getId());
                     this.getBeanStore().attach();
                     this.getConversationMap().put(this.getCurrentConversation().getId(), this.getCurrentConversation());
                  }
               }
            } finally {
               this.getCurrentConversation().unlock();
               this.setBeanStore((BoundBeanStore)null);
               this.cleanUpConversationMap();
               this.removeState();
            }

         }
      } else {
         throw ConversationLogger.LOG.contextNotActive();
      }
   }

   private void cleanUpConversationMap() {
      Map conversations = this.getConversationMap();
      synchronized(conversations) {
         Iterator entryIterator = conversations.entrySet().iterator();
         Object session = this.getSessionFromRequest(this.getRequest(), false);

         while(entryIterator.hasNext()) {
            Map.Entry entry = (Map.Entry)entryIterator.next();
            if (((ManagedConversation)entry.getValue()).isTransient()) {
               this.destroyConversation(session, (String)entry.getKey());
               entryIterator.remove();
            }
         }

      }
   }

   public void conversationPromotedToLongRunning(ConversationImpl conversation) {
      this.getConversationMap().put(conversation.getId(), conversation);
   }

   public void invalidate() {
      ManagedConversation currentConversation = this.getCurrentConversation();
      Map conversations = this.getConversationMap();
      synchronized(conversations) {
         Iterator var4 = conversations.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry stringManagedConversationEntry = (Map.Entry)var4.next();
            ManagedConversation conversation = (ManagedConversation)stringManagedConversationEntry.getValue();
            if (!currentConversation.equals(conversation) && !conversation.isTransient() && isExpired(conversation)) {
               if (!conversation.lock(0L)) {
                  ConversationLogger.LOG.endLockedConversation(conversation.getId());
               }

               conversation.end();
            }
         }

      }
   }

   public boolean destroy(Object session) {
      BoundBeanStore beanStore = this.getBeanStore();
      boolean active = this.isActive();
      if (beanStore != null) {
         beanStore.detach();
      }

      boolean var15;
      try {
         Object conversationMap = this.getSessionAttributeFromSession(session, CONVERSATIONS_ATTRIBUTE_NAME);
         if (conversationMap instanceof Map) {
            Map conversations = (Map)Reflections.cast(conversationMap);
            synchronized(conversations) {
               if (!conversations.isEmpty()) {
                  this.setActive(true);
                  if (beanStore == null) {
                     Iterator var7 = conversations.entrySet().iterator();

                     while(var7.hasNext()) {
                        Map.Entry entry = (Map.Entry)var7.next();
                        this.destroyConversation(session, (String)entry.getKey());
                     }
                  } else {
                     this.setDestructionQueue(conversations, session);
                  }
               }
            }
         }

         var15 = true;
      } finally {
         this.setBeanStore(beanStore);
         this.setActive(active);
         if (beanStore != null) {
            beanStore.attach();
         } else if (!active) {
            this.removeState();
            this.cleanup();
         }

      }

      return var15;
   }

   private void setDestructionQueue(Map conversations, Object session) {
      Map contexts = new HashMap();
      Iterator var4 = conversations.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry entry = (Map.Entry)var4.next();
         ManagedConversation conversation = (ManagedConversation)entry.getValue();
         if (!conversation.isTransient()) {
            conversation.end();
         }

         List contextualInstances = new ArrayList();
         Iterator var8 = (new ConversationNamingScheme(this.getNamingSchemePrefix(), (String)entry.getKey(), this.beanIdentifierIndex)).filterIds(this.getSessionAttributeNames(session)).iterator();

         while(var8.hasNext()) {
            String id = (String)var8.next();
            contextualInstances.add((ContextualInstance)this.getSessionAttributeFromSession(session, id));
         }

         contexts.put(entry.getKey(), contextualInstances);
      }

      this.setRequestAttribute(this.getRequest(), DESTRUCTION_QUEUE_ATTRIBUTE_NAME, Collections.synchronizedMap(contexts));
   }

   protected void destroyConversation(Object session, String id) {
      if (session != null) {
         ((FastEvent)this.conversationBeforeDestroyedEvent.get()).fire(id);
         this.setBeanStore(this.createSessionBeanStore(new ConversationNamingScheme(this.getNamingSchemePrefix(), id, this.beanIdentifierIndex), session));
         this.getBeanStore().attach();
         this.destroy();
         this.getBeanStore().detach();
         this.setBeanStore((BoundBeanStore)null);
         ((FastEvent)this.conversationDestroyedEvent.get()).fire(id);
      }

   }

   public String generateConversationId() {
      ConversationIdGenerator generator = this.getConversationIdGenerator();
      this.checkContextInitialized();
      return generator.call();
   }

   protected ConversationIdGenerator getConversationIdGenerator() {
      Object request = this.associated.get();
      if (request == null) {
         throw ConversationLogger.LOG.mustCallAssociateBeforeGeneratingId();
      } else {
         Object conversationIdGenerator = this.getRequestAttribute(request, ConversationIdGenerator.CONVERSATION_ID_GENERATOR_ATTRIBUTE_NAME);
         if (conversationIdGenerator == null) {
            conversationIdGenerator = this.getSessionAttribute(request, ConversationIdGenerator.CONVERSATION_ID_GENERATOR_ATTRIBUTE_NAME, false);
            if (conversationIdGenerator == null) {
               conversationIdGenerator = new ConversationIdGenerator();
               this.setRequestAttribute(request, ConversationIdGenerator.CONVERSATION_ID_GENERATOR_ATTRIBUTE_NAME, conversationIdGenerator);
               this.setSessionAttribute(request, ConversationIdGenerator.CONVERSATION_ID_GENERATOR_ATTRIBUTE_NAME, conversationIdGenerator, false);
            } else {
               this.setRequestAttribute(request, ConversationIdGenerator.CONVERSATION_ID_GENERATOR_ATTRIBUTE_NAME, conversationIdGenerator);
            }
         }

         if (!(conversationIdGenerator instanceof ConversationIdGenerator)) {
            throw ConversationLogger.LOG.conversationIdGeneratorNotFound();
         } else {
            return (ConversationIdGenerator)conversationIdGenerator;
         }
      }
   }

   private static boolean isExpired(ManagedConversation conversation) {
      return System.currentTimeMillis() > conversation.getLastUsed() + conversation.getTimeout();
   }

   public ManagedConversation getConversation(String id) {
      return (ManagedConversation)this.getConversationMap().get(id);
   }

   public Collection getConversations() {
      Map conversations = this.getConversationMap();
      synchronized(conversations) {
         return new HashSet(conversations.values());
      }
   }

   private void checkIsAssociated() {
      if (!this.isAssociated()) {
         throw ConversationLogger.LOG.mustCallAssociateBeforeLoadingKnownConversations();
      }
   }

   private Map getConversationMap() {
      this.checkIsAssociated();
      this.checkContextInitialized();
      Object request = this.getRequest();
      Object conversationMap = this.getRequestAttribute(request, CONVERSATIONS_ATTRIBUTE_NAME);
      if (conversationMap == null) {
         conversationMap = this.getSessionAttribute(request, CONVERSATIONS_ATTRIBUTE_NAME, false);
         if (conversationMap == null) {
            conversationMap = Collections.synchronizedMap(new HashMap());
            this.setRequestAttribute(request, CONVERSATIONS_ATTRIBUTE_NAME, conversationMap);
            this.setSessionAttribute(request, CONVERSATIONS_ATTRIBUTE_NAME, conversationMap, false);
         } else {
            this.setRequestAttribute(request, CONVERSATIONS_ATTRIBUTE_NAME, conversationMap);
         }
      }

      if (conversationMap != null && conversationMap instanceof Map) {
         return (Map)Reflections.cast(conversationMap);
      } else {
         throw ConversationLogger.LOG.unableToLoadConversations(CONVERSATIONS_ATTRIBUTE_NAME, conversationMap, request);
      }
   }

   public ManagedConversation getCurrentConversation() {
      this.checkIsAssociated();
      this.checkContextInitialized();
      Object request = this.getRequest();
      Object attribute = this.getRequestAttribute(request, CURRENT_CONVERSATION_ATTRIBUTE_NAME);
      if (attribute != null && attribute instanceof ManagedConversation) {
         return (ManagedConversation)attribute;
      } else {
         throw ConversationLogger.LOG.unableToLoadCurrentConversation(CURRENT_CONVERSATION_ATTRIBUTE_NAME, attribute, request);
      }
   }

   public Class getScope() {
      return ConversationScoped.class;
   }

   protected abstract void setSessionAttribute(Object var1, String var2, Object var3, boolean var4);

   protected abstract Object getSessionAttribute(Object var1, String var2, boolean var3);

   protected abstract Object getSessionAttributeFromSession(Object var1, String var2);

   protected abstract void removeRequestAttribute(Object var1, String var2);

   protected abstract void setRequestAttribute(Object var1, String var2, Object var3);

   protected abstract Object getRequestAttribute(Object var1, String var2);

   protected abstract BoundBeanStore createRequestBeanStore(NamingScheme var1, Object var2);

   protected abstract BoundBeanStore createSessionBeanStore(NamingScheme var1, Object var2);

   protected abstract Object getSessionFromRequest(Object var1, boolean var2);

   protected abstract String getNamingSchemePrefix();

   protected boolean isAssociated() {
      return this.associated.get() != null;
   }

   protected Object getRequest() {
      return this.associated.get();
   }

   protected abstract Iterator getSessionAttributeNames(Object var1);
}
