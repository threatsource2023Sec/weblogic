package weblogic.rmi.server;

import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerCloneException;
import weblogic.rmi.extensions.server.ServerHelper;

public class UnicastRemoteObject extends RemoteServer implements Cloneable {
   protected UnicastRemoteObject() {
   }

   public static Remote exportObject(Remote obj) throws RemoteException {
      return ServerHelper.exportObject(obj);
   }

   public static boolean unexportObject(Object obj, boolean force) throws NoSuchObjectException {
      return ServerHelper.unexportObject(obj, force, false);
   }

   public Object clone() throws CloneNotSupportedException {
      try {
         UnicastRemoteObject o = (UnicastRemoteObject)super.clone();
         exportObject(o);
         return o;
      } catch (Exception var2) {
         throw new ServerCloneException("Clone failed", var2);
      }
   }
}
