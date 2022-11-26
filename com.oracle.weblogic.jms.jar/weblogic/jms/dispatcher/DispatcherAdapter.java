package weblogic.jms.dispatcher;

import javax.jms.JMSException;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.DispatcherPeerGoneListener;
import weblogic.messaging.dispatcher.ResourceSetup;
import weblogic.utils.StackTraceUtilsClient;

public class DispatcherAdapter implements JMSDispatcher, ResourceSetup {
   private final Dispatcher delegate;
   private final DispatcherPartition4rmic dispatcherPartition4rmic;

   public DispatcherAdapter(Dispatcher d, DispatcherPartition4rmic dispatcherPartition4rmic) {
      this.delegate = d;
      this.dispatcherPartition4rmic = dispatcherPartition4rmic;
   }

   public void dispatchNoReply(weblogic.messaging.dispatcher.Request request) throws JMSException {
      this.giveRequestResource(request);

      try {
         this.delegate.dispatchNoReply(request);
      } catch (weblogic.messaging.dispatcher.DispatcherException var3) {
         throw convertToJMSExceptionAndThrow(var3);
      }
   }

   public void dispatchNoReplyWithId(weblogic.messaging.dispatcher.Request request, int id) throws JMSException {
      this.giveRequestResource(request);

      try {
         this.delegate.dispatchNoReplyWithId(request, id);
      } catch (weblogic.messaging.dispatcher.DispatcherException var4) {
         throw convertToJMSExceptionAndThrow(var4);
      }
   }

   public weblogic.messaging.dispatcher.Response dispatchSync(weblogic.messaging.dispatcher.Request request) throws JMSException {
      this.giveRequestResource(request);

      try {
         return this.delegate.dispatchSync(request);
      } catch (weblogic.messaging.dispatcher.DispatcherException var3) {
         throw convertToJMSExceptionAndThrow(var3);
      }
   }

   public weblogic.messaging.dispatcher.Response dispatchSyncTran(weblogic.messaging.dispatcher.Request request) throws JMSException {
      this.giveRequestResource(request);

      try {
         return this.delegate.dispatchSyncTran(request);
      } catch (weblogic.messaging.dispatcher.DispatcherException var3) {
         throw convertToJMSExceptionAndThrow(var3);
      }
   }

   public weblogic.messaging.dispatcher.Response dispatchSyncNoTran(weblogic.messaging.dispatcher.Request request) throws JMSException {
      this.giveRequestResource(request);

      try {
         return this.delegate.dispatchSyncNoTran(request);
      } catch (weblogic.messaging.dispatcher.DispatcherException var3) {
         throw convertToJMSExceptionAndThrow(var3);
      }
   }

   public weblogic.messaging.dispatcher.Response dispatchSyncNoTranWithId(weblogic.messaging.dispatcher.Request request, int id) throws JMSException {
      this.giveRequestResource(request);

      try {
         return this.delegate.dispatchSyncNoTranWithId(request, id);
      } catch (weblogic.messaging.dispatcher.DispatcherException var4) {
         throw convertToJMSExceptionAndThrow(var4);
      }
   }

   public DispatcherId getId() {
      return this.delegate.getId();
   }

   public boolean isLocal() {
      return this.delegate.isLocal();
   }

   public void dispatchAsync(weblogic.messaging.dispatcher.Request r) throws weblogic.messaging.dispatcher.DispatcherException {
      this.giveRequestResource(r);
      this.delegate.dispatchAsync(r);
   }

   public void dispatchAsyncWithId(weblogic.messaging.dispatcher.Request r, int id) throws weblogic.messaging.dispatcher.DispatcherException {
      this.giveRequestResource(r);
      this.delegate.dispatchAsyncWithId(r, id);
   }

   public DispatcherPeerGoneListener addDispatcherPeerGoneListener(DispatcherPeerGoneListener listener) {
      return this.delegate.addDispatcherPeerGoneListener(listener);
   }

   public void removeDispatcherPeerGoneListener(DispatcherPeerGoneListener listener) {
      this.delegate.removeDispatcherPeerGoneListener(listener);
   }

   public Dispatcher getDelegate() {
      return this.delegate;
   }

   private static JMSException convertToJMSExceptionAndThrow(weblogic.messaging.dispatcher.DispatcherException de) throws JMSException {
      Throwable cause;
      for(cause = de.getCause(); cause instanceof weblogic.messaging.dispatcher.DispatcherException; cause = cause.getCause()) {
      }

      if (cause instanceof JMSException) {
         return (JMSException)StackTraceUtilsClient.getThrowableWithCause(cause);
      } else if (cause instanceof RuntimeException) {
         throw (RuntimeException)cause;
      } else if (cause instanceof Error) {
         throw (Error)cause;
      } else {
         throw new weblogic.jms.common.JMSException(de);
      }
   }

   public void giveRequestResource(weblogic.messaging.dispatcher.Request request) {
      request.setDispatcherPartition4rmic(this.dispatcherPartition4rmic);
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.dispatcherPartition4rmic;
   }
}
