package weblogic.rmi.extensions;

import java.rmi.RemoteException;
import org.omg.CORBA.SystemException;

public final class RemoteSystemException extends RemoteException {
   private static final long serialVersionUID = -332533197666898703L;
   private SystemException nested;

   public RemoteSystemException() {
   }

   public RemoteSystemException(SystemException nestedException) {
      this.nested = nestedException;
   }

   public SystemException getNested() {
      return this.nested;
   }
}
