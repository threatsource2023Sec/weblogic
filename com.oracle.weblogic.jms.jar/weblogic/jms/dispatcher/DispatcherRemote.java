package weblogic.jms.dispatcher;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.jms.JMSException;
import weblogic.rmi.extensions.AsyncResult;

public interface DispatcherRemote extends Remote {
   void dispatchAsyncFuture(Request var1, AsyncResult var2) throws RemoteException;

   void dispatchAsyncTranFuture(Request var1, AsyncResult var2) throws RemoteException;

   Response dispatchSyncFuture(Request var1) throws JMSException, RemoteException;

   Response dispatchSyncNoTranFuture(Request var1) throws JMSException, RemoteException;

   Response dispatchSyncTranFuture(Request var1) throws RemoteException, JMSException, DispatcherException;
}
