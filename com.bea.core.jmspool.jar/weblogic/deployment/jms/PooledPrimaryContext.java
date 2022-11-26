package weblogic.deployment.jms;

import com.oracle.jms.jmspool.PhantomReferenceCloseable;
import com.oracle.jms.jmspool.ReferenceManager;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;

public class PooledPrimaryContext extends WrappedPrimaryContext {
   private PrimaryContextReference phantomRef;
   private Set openSecondaryContexts;
   private boolean started;

   protected void init(PrimaryContextReference phantomRef, int wrapStyle, WrappedClassManager wrapperManager) {
      JMSPoolDebug.logger.debug("Created a new PooledPrimaryContext.");
      super.init(wrapStyle, wrapperManager, phantomRef.helper.getPrimaryContext());
      this.phantomRef = phantomRef;
      this.openSecondaryContexts = new HashSet();
      phantomRef.helper.incrementReferenceCount();
   }

   protected synchronized void secondaryContextClosed(PooledSecondaryContext secondaryContext) {
      this.openSecondaryContexts.remove(secondaryContext);
   }

   private void doClose() {
      this.closed = true;
      this.phantomRef.closePhantomReference();
      JMSRuntimeException savedException = null;
      Iterator secondaryContexts = this.openSecondaryContexts.iterator();

      while(secondaryContexts.hasNext()) {
         JMSPoolDebug.logger.debug("In PooledSecondaryContext.close, returning a secondary JMSContext");
         PooledSecondaryContext secondaryContext = (PooledSecondaryContext)secondaryContexts.next();

         try {
            secondaryContext.doClose();
         } catch (JMSRuntimeException var5) {
            savedException = var5;
         }
      }

      this.openSecondaryContexts.clear();
      if (savedException != null) {
         throw savedException;
      }
   }

   public synchronized void close() {
      if (!this.closed) {
         this.doClose();
      }

   }

   public void setExceptionListener(ExceptionListener listener) {
      this.checkClosed();
      if (this.wrapStyle != 1 && this.wrapStyle != 2) {
         if (listener != null) {
            this.phantomRef.sessionPool.addExceptionListener(listener);
         }

      } else {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logInvalidJ2EEMethodLoggable("setExceptionListener"));
      }
   }

   protected WrappedSecondaryContext doCreateContext(int requestedSessionMode) {
      int actualSessionMode;
      if (requestedSessionMode != 2 && requestedSessionMode != 0) {
         actualSessionMode = requestedSessionMode;
      } else {
         actualSessionMode = 1;
      }

      try {
         PooledSecondaryContext secondaryContext = this.phantomRef.sessionPool.getSecondaryContext(this.wrapStyle, actualSessionMode, this.phantomRef.containerAuth, this.phantomRef.userName, this.phantomRef.password);
         secondaryContext.setParent(this);
         secondaryContext.setRequestedSessionMode(requestedSessionMode);
         synchronized(this) {
            if (this.started) {
               secondaryContext.setSecondaryContextStarted(true);
            }

            this.openSecondaryContexts.add(secondaryContext);
         }

         return secondaryContext;
      } catch (JMSException var7) {
         throw new JMSRuntimeException(var7.getMessage(), var7.getErrorCode(), var7);
      }
   }

   static class PrimaryContextReference extends PhantomReference implements PhantomReferenceCloseable {
      JMSSessionPool sessionPool;
      PrimaryContextHelperService helper;
      private boolean closed;
      boolean containerAuth;
      String userName;
      String password;
      private final ReferenceManager referenceManager;

      PrimaryContextReference(PooledPrimaryContext referent, JMSSessionPool pool, PrimaryContextHelperService helper, String userName, String password, boolean containerAuth, ReferenceQueue refQ, ReferenceManager referenceManager) {
         super(referent, refQ);
         this.helper = helper;
         this.sessionPool = pool;
         this.userName = userName;
         this.password = password;
         this.containerAuth = containerAuth;
         this.referenceManager = referenceManager;
         referenceManager.registerReference(this);
      }

      public synchronized void closePhantomReference() {
         this.referenceManager.unregisterReference(this);
         if (!this.closed) {
            JMSPoolDebug.logger.debug("Closing a PooledPrimaryContext");
            this.helper.decrementReferenceCount();
            this.closed = true;
         }

      }

      public String toString() {
         return "PrimaryContextReference(" + System.identityHashCode(this) + ")";
      }
   }
}
