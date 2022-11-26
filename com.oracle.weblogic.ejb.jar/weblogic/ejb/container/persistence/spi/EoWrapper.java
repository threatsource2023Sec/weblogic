package weblogic.ejb.container.persistence.spi;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import weblogic.utils.StackTraceUtilsClient;

public final class EoWrapper {
   private EJBObject eo = null;

   public EoWrapper(EJBObject eo) {
      if (eo == null) {
         throw new IllegalArgumentException();
      } else {
         this.eo = eo;
      }
   }

   public EJBObject getEJBObject() {
      return this.eo;
   }

   public Object getPrimaryKey() {
      try {
         return this.eo.getPrimaryKey();
      } catch (RemoteException var2) {
         throw new IllegalArgumentException(StackTraceUtilsClient.throwable2StackTrace(var2));
      }
   }

   public boolean equals(Object o) {
      if (!(o instanceof EoWrapper)) {
         return false;
      } else {
         EoWrapper other = (EoWrapper)o;
         return this.getPrimaryKey().equals(other.getPrimaryKey());
      }
   }

   public int hashCode() {
      return this.getPrimaryKey().hashCode();
   }
}
