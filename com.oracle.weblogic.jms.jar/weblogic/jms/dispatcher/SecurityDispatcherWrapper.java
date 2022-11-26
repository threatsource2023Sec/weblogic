package weblogic.jms.dispatcher;

import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.jms.JMSException;
import javax.security.auth.Subject;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.DispatcherCommon;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.DispatcherPeerGoneListener;
import weblogic.messaging.dispatcher.ResourceSetup;
import weblogic.security.Security;

public class SecurityDispatcherWrapper implements JMSDispatcher, DispatcherCommon, ResourceSetup {
   private JMSDispatcher adapter;
   private Subject subject;
   private final DispatcherPartition4rmic dispatcherPartition4rmic;

   public SecurityDispatcherWrapper(Subject subject, JMSDispatcher adapter, DispatcherPartition4rmic dispatcherPartition4rmic) {
      this.subject = subject;
      this.adapter = adapter;
      this.dispatcherPartition4rmic = dispatcherPartition4rmic;
   }

   public DispatcherId getId() {
      return this.adapter.getId();
   }

   public boolean isLocal() {
      return this.adapter.isLocal();
   }

   public void dispatchAsync(final weblogic.messaging.dispatcher.Request r) throws weblogic.messaging.dispatcher.DispatcherException {
      r.setDispatcherPartition4rmic(this.dispatcherPartition4rmic);

      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws weblogic.messaging.dispatcher.DispatcherException {
               SecurityDispatcherWrapper.this.adapter.dispatchAsync(r);
               return null;
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e = var4.getException();
         if (e instanceof weblogic.messaging.dispatcher.DispatcherException) {
            throw (weblogic.messaging.dispatcher.DispatcherException)e;
         } else {
            throw new weblogic.messaging.dispatcher.DispatcherException(e.getMessage());
         }
      }
   }

   public void dispatchAsyncWithId(final weblogic.messaging.dispatcher.Request r, final int id) throws weblogic.messaging.dispatcher.DispatcherException {
      r.setDispatcherPartition4rmic(this.dispatcherPartition4rmic);

      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws weblogic.messaging.dispatcher.DispatcherException {
               SecurityDispatcherWrapper.this.adapter.dispatchAsyncWithId(r, id);
               return null;
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e = var5.getException();
         if (e instanceof weblogic.messaging.dispatcher.DispatcherException) {
            throw (weblogic.messaging.dispatcher.DispatcherException)e;
         } else {
            throw new weblogic.messaging.dispatcher.DispatcherException(e.getMessage());
         }
      }
   }

   public DispatcherPeerGoneListener addDispatcherPeerGoneListener(DispatcherPeerGoneListener listener) {
      return this.adapter.addDispatcherPeerGoneListener(listener);
   }

   public void removeDispatcherPeerGoneListener(DispatcherPeerGoneListener listener) {
      this.adapter.removeDispatcherPeerGoneListener(listener);
   }

   public void dispatchNoReply(final weblogic.messaging.dispatcher.Request r) throws JMSException {
      r.setDispatcherPartition4rmic(this.dispatcherPartition4rmic);

      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               SecurityDispatcherWrapper.this.adapter.dispatchNoReply(r);
               return null;
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e = var4.getException();
         if (e instanceof JMSException) {
            throw (JMSException)e;
         } else {
            throw new JMSException(e.getMessage());
         }
      }
   }

   public void dispatchNoReplyWithId(final weblogic.messaging.dispatcher.Request r, final int id) throws JMSException {
      r.setDispatcherPartition4rmic(this.dispatcherPartition4rmic);

      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               SecurityDispatcherWrapper.this.adapter.dispatchNoReplyWithId(r, id);
               return null;
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e = var5.getException();
         if (e instanceof JMSException) {
            throw (JMSException)e;
         } else {
            throw new JMSException(e.getMessage());
         }
      }
   }

   public weblogic.messaging.dispatcher.Response dispatchSync(final weblogic.messaging.dispatcher.Request r) throws JMSException {
      r.setDispatcherPartition4rmic(this.dispatcherPartition4rmic);

      try {
         return (weblogic.messaging.dispatcher.Response)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               return SecurityDispatcherWrapper.this.adapter.dispatchSync(r);
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e = var4.getException();
         if (e instanceof JMSException) {
            throw (JMSException)e;
         } else {
            throw new JMSException(e.getMessage());
         }
      }
   }

   public weblogic.messaging.dispatcher.Response dispatchSyncTran(final weblogic.messaging.dispatcher.Request r) throws JMSException {
      r.setDispatcherPartition4rmic(this.dispatcherPartition4rmic);

      try {
         return (weblogic.messaging.dispatcher.Response)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               return SecurityDispatcherWrapper.this.adapter.dispatchSyncTran(r);
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e = var4.getException();
         if (e instanceof JMSException) {
            throw (JMSException)e;
         } else {
            throw new JMSException(e.getMessage());
         }
      }
   }

   public weblogic.messaging.dispatcher.Response dispatchSyncNoTran(final weblogic.messaging.dispatcher.Request r) throws JMSException {
      r.setDispatcherPartition4rmic(this.dispatcherPartition4rmic);

      try {
         return (weblogic.messaging.dispatcher.Response)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               return SecurityDispatcherWrapper.this.adapter.dispatchSyncNoTran(r);
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e = var4.getException();
         if (e instanceof JMSException) {
            throw (JMSException)e;
         } else {
            throw new JMSException(e.getMessage());
         }
      }
   }

   public weblogic.messaging.dispatcher.Response dispatchSyncNoTranWithId(final weblogic.messaging.dispatcher.Request r, final int id) throws JMSException {
      r.setDispatcherPartition4rmic(this.dispatcherPartition4rmic);

      try {
         return (weblogic.messaging.dispatcher.Response)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               return SecurityDispatcherWrapper.this.adapter.dispatchSyncNoTranWithId(r, id);
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e = var5.getException();
         if (e instanceof JMSException) {
            throw (JMSException)e;
         } else {
            throw new JMSException(e.getMessage());
         }
      }
   }

   public Dispatcher getDelegate() {
      return this.adapter.getDelegate();
   }

   public void giveRequestResource(weblogic.messaging.dispatcher.Request request) {
      request.setDispatcherPartition4rmic(this.dispatcherPartition4rmic);
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.dispatcherPartition4rmic;
   }
}
