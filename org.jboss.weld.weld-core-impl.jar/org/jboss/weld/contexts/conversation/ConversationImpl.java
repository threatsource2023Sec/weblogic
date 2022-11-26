package org.jboss.weld.contexts.conversation;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import org.jboss.weld.context.ConversationContext;
import org.jboss.weld.context.ManagedConversation;
import org.jboss.weld.contexts.AbstractConversationContext;
import org.jboss.weld.logging.ConversationLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public class ConversationImpl implements ManagedConversation, Serializable {
   private static final long serialVersionUID = -5566903049468084035L;
   private String id;
   private boolean _transient;
   private long timeout;
   private transient ReentrantLock concurrencyLock;
   private long lastUsed;
   private BeanManagerImpl manager;

   @Inject
   public ConversationImpl(BeanManagerImpl manager) {
      this.manager = manager;
      this._transient = true;
      this.timeout = this.isContextActive() ? this.getActiveConversationContext().getDefaultTimeout() : 0L;
      this.concurrencyLock = new ReentrantLock();
      this.touch();
   }

   public void begin() {
      this.verifyConversationContextActive();
      if (!this._transient) {
         throw ConversationLogger.LOG.beginCalledOnLongRunningConversation();
      } else {
         this._transient = false;
         if (this.id == null) {
            this.id = this.getActiveConversationContext().generateConversationId();
         }

         this.notifyConversationContext();
         ConversationLogger.LOG.promotedTransientConversation(this.id);
      }
   }

   public void begin(String id) {
      this.verifyConversationContextActive();
      if (!this._transient) {
         throw ConversationLogger.LOG.beginCalledOnLongRunningConversation();
      } else if (this.getActiveConversationContext().getConversation(id) != null) {
         throw ConversationLogger.LOG.conversationIdAlreadyInUse(id);
      } else {
         this._transient = false;
         this.id = id;
         this.notifyConversationContext();
         ConversationLogger.LOG.promotedTransientConversation(id);
      }
   }

   private void notifyConversationContext() {
      ConversationContext context = this.getActiveConversationContext();
      if (context instanceof AbstractConversationContext) {
         AbstractConversationContext abstractConversationContext = (AbstractConversationContext)context;
         abstractConversationContext.conversationPromotedToLongRunning(this);
      }

   }

   public void end() {
      this.verifyConversationContextActive();
      if (this._transient) {
         throw ConversationLogger.LOG.endCalledOnTransientConversation();
      } else {
         ConversationLogger.LOG.demotedLongRunningConversation(this.id);
         this._transient = true;
      }
   }

   public String getId() {
      this.verifyConversationContextActive();
      return !this._transient ? this.id : null;
   }

   public long getTimeout() {
      this.verifyConversationContextActive();
      return this.timeout;
   }

   public void setTimeout(long timeout) {
      this.verifyConversationContextActive();
      this.timeout = timeout;
   }

   public String toString() {
      return this._transient ? "Transient conversation" : "Conversation with id: " + this.id;
   }

   public boolean isTransient() {
      this.verifyConversationContextActive();
      return this._transient;
   }

   public long getLastUsed() {
      this.verifyConversationContextActive();
      return this.lastUsed;
   }

   public void touch() {
      this.verifyConversationContextActive();
      this.lastUsed = System.currentTimeMillis();
   }

   public boolean lock(long timeout) {
      this.verifyConversationContextActive();

      boolean success;
      try {
         success = this.concurrencyLock.tryLock(timeout, TimeUnit.MILLISECONDS);
      } catch (InterruptedException var5) {
         Thread.currentThread().interrupt();
         success = false;
      }

      if (success) {
         ConversationLogger.LOG.conversationLocked(this);
      } else {
         ConversationLogger.LOG.conversationUnavailable(timeout, this);
      }

      return success;
   }

   public boolean unlock() {
      this.verifyConversationContextActive();
      if (!this.concurrencyLock.isLocked()) {
         return true;
      } else {
         if (this.concurrencyLock.isHeldByCurrentThread()) {
            this.concurrencyLock.unlock();
            ConversationLogger.LOG.conversationUnlocked(this);
         } else {
            ConversationLogger.LOG.illegalConversationUnlockAttempt(this, "not owner");
         }

         return !this.concurrencyLock.isLocked();
      }
   }

   private void verifyConversationContextActive() {
      if (!this.isContextActive()) {
         throw new ContextNotActiveException("Conversation Context not active when method called on conversation " + this);
      }
   }

   public boolean isContextActive() {
      return this.manager.isContextActive(ConversationScoped.class);
   }

   private ConversationContext getActiveConversationContext() {
      return (ConversationContext)this.manager.getUnwrappedContext(ConversationScoped.class);
   }

   private Object readResolve() throws ObjectStreamException {
      this.concurrencyLock = new ReentrantLock();
      return this;
   }
}
