package weblogic.deployment.jms;

import com.oracle.jms.jmspool.PhantomReferenceCloseable;
import com.oracle.jms.jmspool.ReferenceManager;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;

public class PooledConnection extends WrappedConnection {
   private ConnectionReference phantomRef;
   private Set openSessions;
   private boolean started;
   private static Set allTemporaryDestinations = new HashSet();

   protected void init(ConnectionReference phantomRef, int wrapStyle, WrappedClassManager wrapperManager) throws JMSException {
      JMSPoolDebug.logger.debug("Created a new PooledConnection.");
      super.init(wrapStyle, wrapperManager, phantomRef.helper.getConnection());
      this.phantomRef = phantomRef;
      this.openSessions = new HashSet();
      phantomRef.helper.incrementReferenceCount();
   }

   protected synchronized void sessionClosed(PooledSession session) throws JMSException {
      this.openSessions.remove(session);
   }

   protected void temporaryDestinationCreated(Destination dest) {
      synchronized(this.phantomRef) {
         this.phantomRef.openTemporaryDestinations.add(dest);
      }

      synchronized(allTemporaryDestinations) {
         allTemporaryDestinations.add(dest);
      }
   }

   protected boolean isValidTemporary(Destination dest) {
      synchronized(allTemporaryDestinations) {
         if (!allTemporaryDestinations.contains(dest)) {
            return true;
         }
      }

      synchronized(this.phantomRef) {
         return this.phantomRef.openTemporaryDestinations.contains(dest);
      }
   }

   private void doClose() throws JMSException {
      this.closed = true;
      JMSException savedException = null;
      Iterator sessions = this.openSessions.iterator();

      while(sessions.hasNext()) {
         JMSPoolDebug.logger.debug("In PooledConnection.close, returning a Session");
         PooledSession session = (PooledSession)sessions.next();

         try {
            session.doClose();
         } catch (JMSException var5) {
            savedException = var5;
         }
      }

      this.openSessions.clear();
      this.phantomRef.closePhantomReference();
      if (savedException != null) {
         throw savedException;
      }
   }

   public synchronized void close() throws JMSException {
      if (!this.closed) {
         this.doClose();
      }

   }

   public void setExceptionListener(ExceptionListener listener) throws JMSException {
      this.checkClosed();
      if (this.wrapStyle != 1 && this.wrapStyle != 2) {
         if (listener != null) {
            this.phantomRef.sessionPool.addExceptionListener(listener);
         }

      } else {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logInvalidJ2EEMethodLoggable("setExceptionListener"));
      }
   }

   public void start() throws JMSException {
      this.checkClosed();
      this.vendorConnection.start();
      synchronized(this) {
         this.started = true;
         Iterator sessions = this.openSessions.iterator();

         while(sessions.hasNext()) {
            PooledSession session = (PooledSession)sessions.next();
            session.setConnectionStarted(true);
         }

      }
   }

   public void stop() throws JMSException {
      this.checkClosed();
      if (this.wrapStyle != 1 && this.wrapStyle != 2) {
         this.vendorConnection.stop();
         synchronized(this) {
            this.started = false;
            Iterator sessions = this.openSessions.iterator();

            while(sessions.hasNext()) {
               PooledSession session = (PooledSession)sessions.next();
               session.setConnectionStarted(false);
            }

         }
      } else {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logInvalidJ2EEMethodLoggable("stop"));
      }
   }

   protected WrappedSession doCreateSession(int sessionType, boolean transacted, int ackMode) throws JMSException {
      PooledSession session;
      if (!transacted && ackMode == 2) {
         session = this.phantomRef.sessionPool.getNonXASession(sessionType, this.wrapStyle, ackMode, this.phantomRef.containerAuth, this.phantomRef.userName, this.phantomRef.password);
      } else {
         session = this.phantomRef.sessionPool.getSession(sessionType, this.wrapStyle, transacted, ackMode, this.phantomRef.containerAuth, this.phantomRef.userName, this.phantomRef.password);
      }

      session.setParent(this);
      synchronized(this) {
         if (this.started) {
            session.setConnectionStarted(true);
         }

         this.openSessions.add(session);
         return session;
      }
   }

   static class ConnectionReference extends PhantomReference implements PhantomReferenceCloseable {
      JMSSessionPool sessionPool;
      JMSConnectionHelperService helper;
      private Set openTemporaryDestinations;
      private boolean closed;
      boolean containerAuth;
      String userName;
      String password;
      private final ReferenceManager referenceManager;

      ConnectionReference(PooledConnection referent, JMSSessionPool pool, JMSConnectionHelperService helper, String userName, String password, boolean containerAuth, ReferenceQueue refQ, ReferenceManager referenceManager) {
         super(referent, refQ);
         this.helper = helper;
         this.sessionPool = pool;
         this.openTemporaryDestinations = new HashSet();
         this.userName = userName;
         this.password = password;
         this.containerAuth = containerAuth;
         this.referenceManager = referenceManager;
         referenceManager.registerReference(this);
      }

      public synchronized void closePhantomReference() {
         this.referenceManager.unregisterReference(this);
         if (!this.closed) {
            JMSPoolDebug.logger.debug("Closing a PooledConnection");
            Iterator temporaries = this.openTemporaryDestinations.iterator();

            while(temporaries.hasNext()) {
               JMSPoolDebug.logger.debug("In PooledConnection.close, deleting a temporary destination");
               Object obj = temporaries.next();

               try {
                  synchronized(PooledConnection.allTemporaryDestinations) {
                     PooledConnection.allTemporaryDestinations.remove(obj);
                  }

                  if (obj instanceof TemporaryQueue) {
                     ((TemporaryQueue)obj).delete();
                  } else if (obj instanceof TemporaryTopic) {
                     ((TemporaryTopic)obj).delete();
                  }
               } catch (JMSException var6) {
               }
            }

            this.openTemporaryDestinations.clear();
            this.helper.decrementReferenceCount();
            this.closed = true;
         }

      }

      public String toString() {
         return "ConnectionReference(" + System.identityHashCode(this) + ")";
      }
   }
}
