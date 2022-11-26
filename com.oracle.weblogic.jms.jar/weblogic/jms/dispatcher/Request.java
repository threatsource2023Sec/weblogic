package weblogic.jms.dispatcher;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSException;
import weblogic.jms.common.JMSID;
import weblogic.messaging.dispatcher.DispatcherCommon;
import weblogic.messaging.dispatcher.InvocableManager;
import weblogic.rmi.extensions.AsyncResultListener;
import weblogic.utils.StackTraceUtilsClient;

public abstract class Request extends weblogic.messaging.dispatcher.Request implements Runnable, AsyncResultListener, Externalizable {
   static final long serialVersionUID = -3580248041850964617L;

   public Request(JMSID invocableId, int methodId) {
      super(invocableId, methodId, VoidResponse.THE_ONE);
   }

   protected InvocableManager getInvocableManager(String partitionId) throws Exception {
      return InvocableManagerDelegate.getDelegate(partitionId).getInvocableManager();
   }

   protected boolean isPushToClientRequest(int invocabletyp) {
      return InvocableManagerDelegate.isPushToClient(invocabletyp);
   }

   protected int getInvocableTypeMask() {
      return InvocableManagerDelegate.getInvocableTypeMask();
   }

   protected Throwable getAppException(String str, Throwable th) {
      return new JMSException(str, th);
   }

   public synchronized weblogic.messaging.dispatcher.Response getResult() throws javax.jms.JMSException {
      try {
         return super.getResult();
      } catch (Throwable var2) {
         return handleThrowable(var2);
      }
   }

   public synchronized weblogic.messaging.dispatcher.Response useChildResult(Class responseClass) throws javax.jms.JMSException {
      weblogic.messaging.dispatcher.Response response = ((Request)super.getChild()).getResult();
      this.setResult(response);
      this.setState(Integer.MAX_VALUE);
      return response;
   }

   public static weblogic.messaging.dispatcher.Response handleThrowable(Throwable throwable) throws javax.jms.JMSException {
      if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
         JMSDebug.JMSDispatcherVerbose.debug("Handling throwable: " + throwable.getMessage(), throwable);
      }

      if (throwable instanceof RuntimeException) {
         throw (RuntimeException)throwable;
      } else if (throwable instanceof Error) {
         throw (Error)throwable;
      } else if (throwable instanceof javax.jms.JMSException) {
         throw (javax.jms.JMSException)StackTraceUtilsClient.getThrowableWithCause(throwable);
      } else {
         throw new JMSException(throwable.getMessage(), throwable);
      }
   }

   public void dispatchAsync(JMSDispatcher dispatcher, weblogic.messaging.dispatcher.Request request) throws weblogic.messaging.dispatcher.DispatcherException {
      if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
         JMSDebug.JMSDispatcherVerbose.debug("delegateing to super.dispatchAsync; dispatcher: " + dispatcher.getId());
      }

      super.dispatchAsync(dispatcher.getDelegate(), request);
   }

   public void dispatchAsyncWithId(JMSDispatcher dispatcher, weblogic.messaging.dispatcher.Request request, int id) throws weblogic.messaging.dispatcher.DispatcherException {
      if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
         JMSDebug.JMSDispatcherVerbose.debug("delegateing to super.dispatchAsyncWithId; dispatcher: " + dispatcher.getId());
      }

      super.dispatchAsyncWithId(dispatcher.getDelegate(), request, id);
   }

   public void run() {
      try {
         DispatcherPartition4rmic dpc4rmic = this.getDispatcherPartition4rmic();
         if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatcherVerbose.debug("Request.run(): dispatching Async: using dispatcher: " + dpc4rmic);
         }

         DispatcherCommon dispatcherToUse = dpc4rmic.getLocalDispatcher();
         dispatcherToUse.dispatchAsync(this);
      } catch (javax.jms.JMSException | weblogic.messaging.dispatcher.DispatcherException var3) {
         if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatcherVerbose.debug(var3.getMessage(), var3);
         }
      }

   }

   public Request() {
      this.setAppVoidResponse(VoidResponse.THE_ONE);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in, new JMSID());
   }
}
