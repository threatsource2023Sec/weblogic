package weblogic.messaging.path;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.rmi.extensions.AsyncResult;

public interface AsyncMapRemote extends Remote {
   void putIfAbsent(Serializable var1, Serializable var2, AsyncResult var3) throws RemoteException;

   void put(Serializable var1, Serializable var2, AsyncResult var3) throws RemoteException;

   void get(Serializable var1, AsyncResult var2) throws RemoteException;

   void remove(Serializable var1, Serializable var2, AsyncResult var3) throws RemoteException;

   String getJndiName();
}
