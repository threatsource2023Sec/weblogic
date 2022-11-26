package weblogic.deployment.jms;

import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.QueueBrowser;
import javax.jms.XAJMSContext;
import javax.transaction.SystemException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.wrapper.Wrapper;

public abstract class WrappedSecondaryContext implements Wrapper {
   protected SecondaryContextHolder secondaryContextHolder;
   protected JMSContext vendorSecondaryContext;
   protected Object vendorObj;
   protected XAJMSContext xaJMSContext;
   protected JMSContext context;
   protected int wrapStyle;
   protected int sessionMode;
   protected int requestedSessionMode = -1;
   protected Set openChildren;
   protected WrappedClassManager wrapperManager;
   protected boolean closed;
   protected boolean started;
   protected boolean autoStart = true;
   protected AbstractSubject subject = null;
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   protected void init(SecondaryContextHolder secondaryContextHolder, WrappedClassManager manager) {
      this.secondaryContextHolder = secondaryContextHolder;
      this.xaJMSContext = secondaryContextHolder.getXAJMSContext();
      this.sessionMode = secondaryContextHolder.getSessionMode();
      this.wrapperManager = manager;
      if (secondaryContextHolder.getPrimaryContextHelper() != null) {
         this.subject = secondaryContextHolder.getPrimaryContextHelper().getSubject();
      }

      this.setVendorObj(secondaryContextHolder.getSecondaryContext());
      this.openChildren = new HashSet(3);
   }

   protected void enlistInTransaction(boolean isProducer) {
   }

   protected void delistFromTransaction(boolean commit) {
   }

   protected void closeChildren() {
      this.pushSubject();

      try {
         Iterator children = this.openChildren.iterator();

         while(children.hasNext()) {
            Object obj = children.next();
            if (obj instanceof WrappedJMSProducer) {
               WrappedJMSProducer producer = (WrappedJMSProducer)obj;
               producer.setClosed();
            } else if (obj instanceof WrappedJMSConsumer) {
               try {
                  WrappedJMSConsumer consumer = (WrappedJMSConsumer)obj;
                  consumer.setClosed();
                  consumer.closeProviderConsumer();
               } catch (JMSRuntimeException var9) {
               }
            } else if (obj instanceof WrappedQueueBrowser) {
               try {
                  ((WrappedQueueBrowser)obj).closeProviderBrowser();
               } catch (JMSException var8) {
               }
            }
         }

         this.openChildren.clear();
      } finally {
         this.popSubject();
      }
   }

   protected void closeProviderSecondaryContext() {
      this.closeChildren();
      this.pushSubject();

      try {
         this.secondaryContextHolder.destroy();
      } finally {
         this.popSubject();
      }

   }

   protected synchronized void setSecondaryContextStarted(boolean started) {
      this.started = started;
      Iterator allChildren = this.openChildren.iterator();

      while(allChildren.hasNext()) {
         Object next = allChildren.next();
         if (next instanceof WrappedJMSConsumer) {
            this.pushSubject();

            try {
               ((WrappedJMSConsumer)next).setSecondaryContextStarted(started);
            } finally {
               this.popSubject();
            }
         }
      }

   }

   private Object createWrappedMessageProducer(String debugString) throws JMSException {
      this.checkClosed();
      JMSProducer producer = null;
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Creating a new JMSProducer, " + debugString);
      }

      this.pushSubject();

      try {
         producer = this.vendorSecondaryContext.createProducer();
      } finally {
         this.popSubject();
      }

      Object wrappedRet = this.wrapperManager.getWrappedInstance(28, producer);
      synchronized(this) {
         ((WrappedJMSProducer)wrappedRet).init(this, producer);
         this.openChildren.add(wrappedRet);
         return wrappedRet;
      }
   }

   public void setVendorObj(Object o) {
      this.vendorObj = o;
      this.vendorSecondaryContext = (JMSContext)o;
      this.context = this.vendorSecondaryContext;
   }

   public Object getVendorObj() {
      return this.vendorObj;
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws Exception {
      return null;
   }

   public void preInvocationHandler(String methodName, Object[] params) throws JMSException {
      if (!methodName.equals("close")) {
         this.checkClosed();
      }

      if (this.wrapStyle != 0 && (methodName.equals("stop") || methodName.equals("setExceptionListener") || methodName.equals("setClientID") || methodName.equals("createContext"))) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logInvalidJ2EEMethodLoggable(methodName));
      }
   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) {
      byte wrapType;
      if (methodName.equals("createConsumer")) {
         wrapType = 29;
      } else if (methodName.equals("createDurableConsumer")) {
         wrapType = 29;
      } else if (methodName.equals("createSharedConsumer")) {
         wrapType = 29;
      } else if (methodName.equals("createSharedDurableConsumer")) {
         wrapType = 29;
      } else if (methodName.equals("createProducer")) {
         wrapType = 28;
      } else {
         if (!methodName.equals("createBrowser")) {
            return ret;
         }

         wrapType = 15;
      }

      Object wrappedRet;
      try {
         wrappedRet = this.wrapperManager.getWrappedInstance(wrapType, ret);
      } catch (JMSException var9) {
         throw new JMSRuntimeException(var9.getMessage(), var9.getErrorCode(), var9);
      }

      synchronized(this) {
         switch (wrapType) {
            case 15:
               ((WrappedQueueBrowser)wrappedRet).init(this, (QueueBrowser)ret);
               break;
            case 29:
               ((WrappedJMSConsumer)wrappedRet).init(this, (JMSConsumer)ret, this.wrapperManager);
               if (this.autoStart) {
                  this.start();
               }

               if (this.started) {
                  ((WrappedJMSConsumer)wrappedRet).setSecondaryContextStarted(true);
               }
         }

         this.openChildren.add(wrappedRet);
         return wrappedRet;
      }
   }

   public JMSProducer createProducer() throws JMSException {
      return (JMSProducer)this.createWrappedMessageProducer("JMSProducer");
   }

   protected void setRequestedSessionMode(int requestedSessionModeArg) {
      this.requestedSessionMode = requestedSessionModeArg;
   }

   public int getSessionMode() {
      ClientTransactionManager tm = TransactionHelper.getTransactionHelper().getTransactionManager();

      try {
         return tm.getTransaction() != null ? this.requestedSessionMode : this.sessionMode;
      } catch (SystemException var3) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSJTARegistrationErrorLoggable(), var3);
      }
   }

   protected synchronized void producerClosed(WrappedJMSConsumer producer) throws JMSException {
      this.openChildren.remove(producer);
   }

   protected synchronized void consumerClosed(WrappedJMSConsumer consumer) {
      this.openChildren.remove(consumer);
      consumer.closeProviderConsumer();
   }

   protected synchronized void browserClosed(WrappedQueueBrowser browser) throws JMSException {
      this.openChildren.remove(browser);
      browser.closeProviderBrowser();
   }

   protected void setWrapStyle(int style) {
      this.wrapStyle = style;
   }

   protected int getWrapStyle() {
      return this.wrapStyle;
   }

   protected SecondaryContextHolder getSecondaryContextHolder() {
      return this.secondaryContextHolder;
   }

   protected AbstractSubject getSubject() {
      return this.subject;
   }

   protected Transaction getEnlistedTransaction() {
      return null;
   }

   protected synchronized void checkClosed() {
      if (this.closed) {
         throw JMSExceptions.getIllegalStateRuntimeException(JMSPoolLogger.logJMSObjectClosedLoggable());
      } else if (!this.secondaryContextHolder.isEnabled()) {
         throw JMSExceptions.getIllegalStateRuntimeException(JMSPoolLogger.logJMSSessionPoolDisabledLoggable());
      }
   }

   public synchronized String toString() {
      return "WrappedSecondaryContext (" + this.vendorSecondaryContext + ")";
   }

   public synchronized void pushSubject() {
      if (this.subject != null) {
         SubjectManager.getSubjectManager().pushSubject(kernelID, this.subject);
      }

   }

   public synchronized void popSubject() {
      if (this.subject != null) {
         SubjectManager.getSubjectManager().popSubject(kernelID);
      }

   }

   public void start() {
      this.checkClosed();
      this.vendorSecondaryContext.start();
      this.setSecondaryContextStarted(true);
   }

   public void stop() {
      this.checkClosed();
      throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logInvalidJ2EEMethodLoggable("stop"));
   }

   public void setAutoStart(boolean autoStart) {
      this.vendorSecondaryContext.setAutoStart(autoStart);
      this.autoStart = autoStart;
   }

   public boolean getAutoStart() {
      return this.autoStart;
   }
}
