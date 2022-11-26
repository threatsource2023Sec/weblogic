package weblogic.jms.frontend;

import java.util.HashMap;
import java.util.Iterator;
import javax.jms.JMSException;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BEBrowserCloseRequest;
import weblogic.jms.backend.BEBrowserGetEnumerationRequest;
import weblogic.jms.common.JMSBrowserGetEnumerationResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSPushExceptionRequest;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;

public final class FEBrowser implements Invocable {
   private final JMSID browserId;
   private final JMSDispatcher dispatcher;
   private final FEConnection connection;
   private final FESession session;
   private final InvocableMonitor invocableMonitor;
   private final HashMap enumerations = new HashMap();

   public FEBrowser(FEConnection connection, FESession session, JMSID browserId, JMSDispatcher dispatcher) {
      this.connection = connection;
      this.session = session;
      this.browserId = browserId;
      this.dispatcher = dispatcher;
      this.invocableMonitor = session.getInvocableMonitor();
   }

   public JMSID getJMSID() {
      return this.browserId;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.connection.getDispatcherPartition4rmic();
   }

   FEConnection getConnection() {
      return this.connection;
   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   private int close(FEBrowserCloseRequest request) throws JMSException {
      JMSException savedException = null;
      switch (request.getState()) {
         case 0:
            BEBrowserCloseRequest child = new BEBrowserCloseRequest(this.browserId);
            synchronized(request) {
               request.rememberChild(child);
               request.setState(1);
            }

            try {
               request.dispatchAsync(this.dispatcher, child);
            } catch (DispatcherException var9) {
               throw new weblogic.jms.common.JMSException("Error closing browser", var9);
            }

            return request.getState();
         default:
            try {
               request.useChildResult(VoidResponse.class);
            } catch (JMSException var13) {
               if (savedException == null) {
                  savedException = var13;
               }
            }

            synchronized(this) {
               Iterator iterator = ((HashMap)this.enumerations.clone()).values().iterator();

               while(true) {
                  if (!iterator.hasNext()) {
                     break;
                  }

                  FEEnumeration enumeration = (FEEnumeration)iterator.next();

                  try {
                     this.enumerationRemove(enumeration.getJMSID());
                  } catch (JMSException var11) {
                     if (savedException == null) {
                        savedException = var11;
                     }
                  }
               }
            }

            if (this.session == null) {
               this.connection.browserRemove(this.browserId);
            } else {
               this.session.browserRemove(this.browserId);
            }

            if (savedException != null) {
               throw savedException;
            } else {
               return Integer.MAX_VALUE;
            }
      }
   }

   private synchronized void enumerationAdd(FEEnumeration enumeration) throws JMSException {
      if (this.enumerations.put(enumeration.getJMSID(), enumeration) == null) {
         this.connection.getFrontEnd().getService().getInvocableManagerDelegate().invocableAdd(12, enumeration);
      }

   }

   synchronized void enumerationRemove(JMSID enumerationId) throws JMSException {
      if (this.enumerations.remove(enumerationId) == null) {
         throw new weblogic.jms.common.JMSException("Enumeration not found, " + enumerationId);
      } else {
         this.connection.getFrontEnd().getService().getInvocableManagerDelegate().invocableRemove(12, enumerationId);
      }
   }

   private int enumerate(Request invocableRequest) throws JMSException {
      FEBrowserGetEnumerationRequest request = (FEBrowserGetEnumerationRequest)invocableRequest;
      switch (request.getState()) {
         case 0:
            BEBrowserGetEnumerationRequest child = new BEBrowserGetEnumerationRequest(this.browserId);
            synchronized(request) {
               request.rememberChild(child);
               request.setState(1);
            }

            try {
               request.dispatchAsync(this.dispatcher, child);
            } catch (DispatcherException var6) {
               throw new weblogic.jms.common.JMSException("Error getting enumeration", var6);
            }

            return request.getState();
         case 1:
         default:
            JMSBrowserGetEnumerationResponse response = (JMSBrowserGetEnumerationResponse)request.useChildResult(JMSBrowserGetEnumerationResponse.class);
            this.enumerationAdd(new FEEnumeration(this, response.getEnumerationId(), this.dispatcher));
            return Integer.MAX_VALUE;
      }
   }

   private int pushException(Request invocableRequest) throws JMSException {
      JMSPushExceptionRequest request = (JMSPushExceptionRequest)invocableRequest;
      JMSPushExceptionRequest childRequest = new JMSPushExceptionRequest(22, this.browserId, request.getException());
      if (this.session != null) {
         JMSServerUtilities.anonDispatchNoReply(childRequest, this.session.getConnection().getClientDispatcher());
      } else if (this.connection != null) {
         JMSServerUtilities.anonDispatchNoReply(childRequest, this.connection.getClientDispatcher());
      }

      synchronized(this) {
         Iterator iterator = ((HashMap)this.enumerations.clone()).values().iterator();

         while(true) {
            if (!iterator.hasNext()) {
               break;
            }

            FEEnumeration enumeration = (FEEnumeration)iterator.next();
            this.enumerationRemove(enumeration.getJMSID());
         }
      }

      if (this.session == null) {
         this.connection.browserRemove(this.browserId);
      } else {
         this.session.browserRemove(this.browserId);
      }

      request.setState(Integer.MAX_VALUE);
      return request.getState();
   }

   public int invoke(Request request) throws JMSException {
      JMSService.checkThreadInJMSServicePartition(this.connection.getFrontEnd().getService(), "FEBrowser");
      switch (request.getMethodId()) {
         case 267:
            return this.close((FEBrowserCloseRequest)request);
         case 779:
            return this.enumerate(request);
         case 15371:
            return this.pushException(request);
         default:
            throw new weblogic.jms.common.JMSException("No such method " + this.getClass().getName() + "." + request.getMethodId());
      }
   }
}
