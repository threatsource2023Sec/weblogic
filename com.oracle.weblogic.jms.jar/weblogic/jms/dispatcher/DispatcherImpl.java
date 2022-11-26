package weblogic.jms.dispatcher;

import java.io.IOException;
import java.rmi.RemoteException;
import javax.jms.JMSException;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.server.FutureResponse;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.spi.MsgOutput;
import weblogic.utils.StackTraceUtilsClient;

public final class DispatcherImpl implements DispatcherRemote, DispatcherOneWay {
   private final weblogic.messaging.dispatcher.DispatcherImpl delegate;

   public DispatcherImpl(weblogic.messaging.dispatcher.DispatcherImpl localDispatcher) {
      this.delegate = localDispatcher;
   }

   public boolean isPartitionActive() {
      return this.delegate.isPartitionActive();
   }

   DispatcherImpl() throws IOException {
      throw new IOException("unknown partition");
   }

   public void dispatchAsyncFuture(Request request, AsyncResult asyncResult) throws RemoteException {
      throw new AssertionError();
   }

   public void dispatchAsyncFuture(Request request, AsyncResult asyncResult, FutureResponse future) throws RemoteException {
      throw new AssertionError();
   }

   public void dispatchAsyncTranFuture(Request request, AsyncResult asyncResult) throws RemoteException {
      throw new AssertionError();
   }

   public void dispatchAsyncTranFuture(Request request, AsyncResult asyncResult, FutureResponse future) throws RemoteException {
      throw new AssertionError();
   }

   public Response dispatchSyncFuture(Request request) throws JMSException, RemoteException {
      throw new AssertionError("compiler error");
   }

   public void dispatchSyncFuture(Request request, FutureResponse future) throws JMSException, RemoteException {
      this.delegate.dispatchSyncFuture(request, new InteropFutureWrapper(future));
   }

   public Response dispatchSyncNoTranFuture(Request request) throws JMSException, RemoteException {
      throw new AssertionError("compiler error");
   }

   public void dispatchSyncNoTranFuture(Request request, FutureResponse future) throws JMSException, RemoteException {
      this.delegate.dispatchSyncNoTranFuture(request, new InteropFutureWrapper(future));
   }

   public Response dispatchSyncTranFuture(Request request) throws RemoteException, JMSException, DispatcherException {
      throw new AssertionError("compiler error");
   }

   public void dispatchSyncTranFuture(Request request, FutureResponse future) throws RemoteException, JMSException, DispatcherException {
      this.delegate.dispatchSyncTranFuture(request, new InteropFutureWrapper(future));
   }

   public void dispatchOneWay(Request request) throws RemoteException {
      this.delegate.dispatchOneWay(request);
   }

   private static class InteropFutureWrapper implements FutureResponse {
      private final FutureResponse delegate;

      private InteropFutureWrapper(FutureResponse delegate) {
         this.delegate = delegate;
      }

      public MsgOutput getMsgOutput() throws RemoteException {
         return this.delegate.getMsgOutput();
      }

      public void send() throws RemoteException {
         this.delegate.send();
      }

      public void sendThrowable(Throwable problem) {
         if (problem instanceof weblogic.messaging.dispatcher.DispatcherException) {
            problem = problem.getCause();
         }

         if (!RMIEnvironment.getEnvironment().printExceptionStackTrace()) {
            StackTraceUtilsClient.scrubExceptionStackTrace(problem);
         }

         this.delegate.sendThrowable(problem);
      }

      // $FF: synthetic method
      InteropFutureWrapper(FutureResponse x0, Object x1) {
         this(x0);
      }
   }
}
