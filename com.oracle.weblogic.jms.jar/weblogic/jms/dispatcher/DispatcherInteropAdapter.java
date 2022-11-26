package weblogic.jms.dispatcher;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.jms.JMSException;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteWrapper;

public final class DispatcherInteropAdapter implements weblogic.messaging.dispatcher.DispatcherRemote, weblogic.messaging.dispatcher.DispatcherOneWay, RemoteWrapper {
   private final DispatcherRemote dispatcherRemote;
   private final DispatcherOneWay dispatcherOneWay;

   DispatcherInteropAdapter(DispatcherRemote d1, DispatcherOneWay d2) {
      this.dispatcherRemote = d1;
      this.dispatcherOneWay = d2;
   }

   public void dispatchAsyncFuture(weblogic.messaging.dispatcher.Request request, AsyncResult asyncResult) throws RemoteException {
      this.dispatcherRemote.dispatchAsyncFuture((Request)request, asyncResult);
   }

   public void dispatchAsyncFutureWithId(weblogic.messaging.dispatcher.Request request, AsyncResult asyncResult, int id) throws RemoteException {
      this.dispatchAsyncFuture(request, asyncResult);
   }

   public void dispatchAsyncTranFuture(weblogic.messaging.dispatcher.Request request, AsyncResult asyncResult) throws RemoteException {
      this.dispatcherRemote.dispatchAsyncTranFuture((Request)request, asyncResult);
   }

   public void dispatchAsyncTranFutureWithId(weblogic.messaging.dispatcher.Request request, AsyncResult asyncResult, int id) throws RemoteException {
      this.dispatchAsyncTranFuture(request, asyncResult);
   }

   public weblogic.messaging.dispatcher.Response dispatchSyncFuture(weblogic.messaging.dispatcher.Request request) throws RemoteException {
      try {
         return this.dispatcherRemote.dispatchSyncFuture((Request)request);
      } catch (JMSException var3) {
         throw new RemoteRuntimeException(var3);
      }
   }

   public weblogic.messaging.dispatcher.Response dispatchSyncNoTranFuture(weblogic.messaging.dispatcher.Request request) throws RemoteException {
      try {
         return this.dispatcherRemote.dispatchSyncNoTranFuture((Request)request);
      } catch (JMSException var3) {
         throw new RemoteRuntimeException(var3);
      }
   }

   public weblogic.messaging.dispatcher.Response dispatchSyncTranFutureWithId(weblogic.messaging.dispatcher.Request request, int id) throws RemoteException, weblogic.messaging.dispatcher.DispatcherException {
      return this.dispatchSyncTranFuture(request);
   }

   public weblogic.messaging.dispatcher.Response dispatchSyncTranFuture(weblogic.messaging.dispatcher.Request request) throws RemoteException, weblogic.messaging.dispatcher.DispatcherException {
      try {
         return this.dispatcherRemote.dispatchSyncTranFuture((Request)request);
      } catch (JMSException var3) {
         throw new RemoteRuntimeException(var3);
      } catch (DispatcherException var4) {
         throw new weblogic.messaging.dispatcher.DispatcherException(var4);
      }
   }

   public void dispatchOneWay(weblogic.messaging.dispatcher.Request request) throws RemoteException {
      this.dispatcherOneWay.dispatchOneWay((Request)request);
   }

   public void dispatchOneWayWithId(weblogic.messaging.dispatcher.Request request, int id) throws RemoteException {
      this.dispatchOneWay(request);
   }

   public Remote getRemoteDelegate() {
      return this.dispatcherRemote;
   }
}
