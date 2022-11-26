package weblogic.messaging.dispatcher;

import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.rmi.extensions.AsyncResult;

public interface DispatcherRemote extends Remote {
   void dispatchAsyncFuture(Request var1, AsyncResult var2) throws DispatcherException, RemoteException;

   void dispatchAsyncFutureWithId(Request var1, AsyncResult var2, int var3) throws DispatcherException, RemoteException;

   void dispatchAsyncTranFuture(Request var1, AsyncResult var2) throws DispatcherException, RemoteException;

   void dispatchAsyncTranFutureWithId(Request var1, AsyncResult var2, int var3) throws DispatcherException, RemoteException;

   Response dispatchSyncFuture(Request var1) throws DispatcherException, RemoteException;

   Response dispatchSyncNoTranFuture(Request var1) throws DispatcherException, RemoteException;

   Response dispatchSyncTranFuture(Request var1) throws DispatcherException, RemoteException;

   Response dispatchSyncTranFutureWithId(Request var1, int var2) throws DispatcherException, RemoteException;
}
