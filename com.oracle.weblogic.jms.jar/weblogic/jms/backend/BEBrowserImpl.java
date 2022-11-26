package weblogic.jms.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.MessageConsumer;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSBrowserGetEnumerationResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSPushExceptionRequest;
import weblogic.jms.common.JMSSQLExpression;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.common.TimedSecurityParticipant;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.jms.extensions.ConsumerClosedException;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.InvalidExpressionException;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.Queue;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.work.WorkManagerFactory;

final class BEBrowserImpl implements BEBrowser, TimedSecurityParticipant {
   private final JMSID browserId;
   private final BESession session;
   private BEQueueImpl destination;
   private Queue queue;
   private Expression expression;
   private InvocableMonitor invocableMonitor;
   private boolean isClosed;
   private AuthenticatedSubject authenticatedSubject;
   private static final boolean debug = false;
   private final Map enumerations = new HashMap();

   BEBrowserImpl(BESession session, BEQueueImpl destination, Queue queue, String selector) throws JMSException {
      destination.getJMSDestinationSecurity().checkBrowsePermission();
      this.session = session;
      this.browserId = JMSService.getNextId();
      this.destination = destination;
      this.queue = queue;
      this.authenticatedSubject = JMSSecurityHelper.getCurrentSubject();
      if (session == null) {
         this.invocableMonitor = destination.getBackEnd().getInvocableMonitor();
      } else {
         this.invocableMonitor = session.getInvocableMonitor();
      }

      try {
         JMSSQLExpression jmsExpression = new JMSSQLExpression(selector);
         this.expression = queue.getFilter().createExpression(jmsExpression);
      } catch (InvalidExpressionException var6) {
         throw new InvalidSelectorException(var6.toString());
      } catch (KernelException var7) {
         throw new weblogic.jms.common.JMSException(var7);
      }
   }

   public JMSID getJMSID() {
      return this.browserId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.destination.getDispatcherPartition4rmic();
   }

   BESession getSession() {
      return this.session;
   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   public synchronized boolean isClosed() {
      return this.isClosed;
   }

   private int close(Request request) {
      this.close();
      request.setResult(VoidResponse.THE_ONE);
      request.setState(Integer.MAX_VALUE);
      return Integer.MAX_VALUE;
   }

   public void close() {
      ArrayList enumsCopy;
      synchronized(this) {
         if (this.isClosed) {
            return;
         }

         this.isClosed = true;
         enumsCopy = new ArrayList(this.enumerations.values());
         this.enumerations.clear();
      }

      Iterator i = enumsCopy.iterator();

      while(i.hasNext()) {
         ((BEEnumerationImpl)i.next()).close();
      }

      if (this.session == null) {
         this.destination.getBackEnd().getJmsService().getInvocableManagerDelegate().invocableRemove(18, this.browserId);
      } else {
         this.session.browserRemove(this.browserId);
      }

      this.destination.removeBrowser(this.getJMSID());
   }

   private int enumerate(BEBrowserGetEnumerationRequest request) throws JMSException {
      this.checkShutdownOrSuspended("enumerate browser");
      this.checkPermission();
      JMSID enumerationId = JMSService.getNextId();
      BEEnumerationImpl enumeration = new BEEnumerationImpl(this, enumerationId, this.queue, this.expression);
      this.enumerationAdd(enumeration);
      request.setState(Integer.MAX_VALUE);
      request.setResult(new JMSBrowserGetEnumerationResponse(enumerationId));
      return Integer.MAX_VALUE;
   }

   private synchronized void enumerationAdd(BEEnumerationImpl enumeration) throws JMSException {
      this.destination.getBackEnd().getJmsService().getInvocableManagerDelegate().invocableAdd(19, enumeration);
      this.enumerations.put(enumeration.getJMSID(), enumeration);
   }

   synchronized void enumerationRemove(JMSID enumerationId) {
      this.enumerations.remove(enumerationId);
      this.destination.getBackEnd().getJmsService().getInvocableManagerDelegate().invocableRemove(19, enumerationId);
   }

   void checkShutdownOrSuspended(String string) throws JMSException {
      if (this.isClosed()) {
         throw new weblogic.jms.common.JMSException("The browser is closed");
      } else {
         this.destination.checkShutdownOrSuspendedNeedLock(string);
      }
   }

   public void securityLapsed() {
      BESession session = this.getSession();
      this.close();
      if (session != null) {
         BEConnection connection = session.getConnection();
         if (connection != null) {
            try {
               JMSServerUtilities.anonDispatchNoReply(new JMSPushExceptionRequest(11, this.browserId, new ConsumerClosedException((MessageConsumer)null, "ERROR: Security has lapsed for this consumer")), connection.getDispatcher());
            } catch (JMSException var4) {
               System.out.println("ERROR: Could not push security exception to queue browser: " + var4);
            }
         }
      }

   }

   public HashSet getAcceptedDestinations() {
      return null;
   }

   private void checkPermission() throws JMSSecurityException {
      AuthenticatedSubject currentSubject = JMSSecurityHelper.getCurrentSubject();
      if (!this.destination.getBackEnd().getJmsService().isSecurityCheckerStop()) {
         if (this.authenticatedSubject != currentSubject && (this.authenticatedSubject == null || !this.authenticatedSubject.equals(currentSubject))) {
            this.destination.getJMSDestinationSecurity().checkBrowsePermission(currentSubject);
            this.setSubject(currentSubject);
         }
      } else {
         try {
            this.destination.getJMSDestinationSecurity().checkBrowsePermission(this.authenticatedSubject);
         } catch (JMSSecurityException var3) {
            WorkManagerFactory.getInstance().getSystem().schedule(new BrowserCloseThread());
            throw var3;
         }
      }

   }

   private void checkPermission(boolean shouldFail) throws JMSSecurityException {
      this.checkPermission();
      if (shouldFail) {
         WorkManagerFactory.getInstance().getSystem().schedule(new BrowserCloseThread());
         throw new JMSSecurityException("security check simulation negative result");
      }
   }

   public int invoke(Request request) throws JMSException {
      JMSService.checkThreadInJMSServicePartition(this.destination.getBackEnd().getJmsService(), "BEBrowserImpl");
      switch (request.getMethodId()) {
         case 8210:
            return this.close(request);
         case 8722:
            return this.enumerate((BEBrowserGetEnumerationRequest)request);
         default:
            throw new weblogic.jms.common.JMSException("No such method " + this.getClass().getName() + "." + request.getMethodId());
      }
   }

   private synchronized void setSubject(AuthenticatedSubject subject) {
      if (subject != null) {
         this.authenticatedSubject = subject;
      }

   }

   public synchronized AuthenticatedSubject getSubject() {
      return this.authenticatedSubject;
   }

   private class BrowserCloseThread implements Runnable {
      private BrowserCloseThread() {
      }

      public void run() {
         BEBrowserImpl.this.securityLapsed();
      }

      // $FF: synthetic method
      BrowserCloseThread(Object x1) {
         this();
      }
   }
}
