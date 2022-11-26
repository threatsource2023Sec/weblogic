package weblogic.rmi.extensions;

import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.rmi.extensions.server.ReferenceHelper;

public final class PortableRemoteObject {
   private static AuditableThreadLocal recursive = AuditableThreadLocalFactory.createThreadLocal();

   public static void exportObject(Remote obj) throws RemoteException {
      if (obj == null) {
         throw new NullPointerException("Invalid argument");
      } else {
         if (ReferenceHelper.exists()) {
            ReferenceHelper.getReferenceHelper().exportObject(obj);
         }

      }
   }

   public static Remote toStub(Remote obj) throws NoSuchObjectException {
      if (obj == null) {
         throw new NullPointerException("Invalid argument");
      } else {
         return ReferenceHelper.exists() ? ReferenceHelper.getReferenceHelper().toStub(obj) : obj;
      }
   }

   public static void unexportObject(Remote obj) throws NoSuchObjectException {
      if (obj == null) {
         throw new NullPointerException("Invalid argument");
      } else {
         if (ReferenceHelper.exists()) {
            ReferenceHelper.getReferenceHelper().unexportObject(obj);
         }

      }
   }

   public static Object narrow(Object narrowFrom, Class narrowTo) throws ClassCastException {
      if (narrowFrom != null && narrowTo != null) {
         Object var2;
         if (!ReferenceHelper.exists()) {
            if (narrowTo.isInstance(narrowFrom)) {
               return narrowFrom;
            } else {
               checkAndSetRecursive(narrowFrom, narrowTo);

               try {
                  var2 = javax.rmi.PortableRemoteObject.narrow(narrowFrom, narrowTo);
               } finally {
                  clearRecursive();
               }

               return var2;
            }
         } else {
            checkAndSetRecursive(narrowFrom, narrowTo);

            try {
               var2 = ReferenceHelper.getReferenceHelper().narrow(narrowFrom, narrowTo);
            } catch (NamingException var12) {
               ClassCastException cce = new ClassCastException(String.format("narrowFrom(%s) can not be cast to narrowTo(%s).", narrowFrom, narrowTo));
               cce.initCause(var12);
               throw cce;
            } finally {
               clearRecursive();
            }

            return var2;
         }
      } else {
         throw new NullPointerException("narrowFrom'" + narrowFrom + '\'' + " \tnarrowTo:" + '\'' + narrowTo + "'It is invalid to call narrow with null parameters");
      }
   }

   private static void checkAndSetRecursive(Object narrowFrom, Class narrowTo) {
      if (Boolean.TRUE.equals(recursive.get())) {
         recursive.set((Object)null);
         throw new ClassCastException("narrowFrom'" + narrowFrom + '\'' + " \tnarrowTo:" + '\'' + narrowTo + "'Recursive narrow detected");
      } else {
         recursive.set(Boolean.TRUE);
      }
   }

   private static void clearRecursive() {
      recursive.set((Object)null);
   }
}
