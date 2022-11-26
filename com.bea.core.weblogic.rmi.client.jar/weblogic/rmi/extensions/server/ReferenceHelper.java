package weblogic.rmi.extensions.server;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.naming.NamingException;

public abstract class ReferenceHelper {
   private static ReferenceHelper singleton;

   public static ReferenceHelper getReferenceHelper() {
      if (singleton == null) {
         throw new IllegalArgumentException("ReferenceHelper not set");
      } else {
         return singleton;
      }
   }

   public static void setReferenceHelper(ReferenceHelper helper) {
      singleton = helper;
   }

   public abstract Object narrow(Object var1, Class var2) throws NamingException;

   public abstract Object replaceObject(Object var1) throws IOException;

   public abstract void exportObject(Remote var1) throws RemoteException;

   public abstract void unexportObject(Remote var1) throws NoSuchObjectException;

   public abstract Remote toStub(Remote var1) throws NoSuchObjectException;

   public static boolean exists() {
      return singleton != null;
   }
}
