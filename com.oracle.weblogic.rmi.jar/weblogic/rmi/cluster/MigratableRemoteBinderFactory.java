package weblogic.rmi.cluster;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.StateFactory;
import weblogic.jndi.internal.WLNamingManager;
import weblogic.utils.AssertionError;

public final class MigratableRemoteBinderFactory implements StateFactory {
   public Object getStateToBind(Object object, Name name, Context ctx, Hashtable env) throws NamingException {
      Object replacement = null;
      if (MigratableRemoteObject.isEOS(object)) {
         try {
            if (object instanceof Remote) {
               replacement = new MigratableRemoteObject((Remote)object);
            }
         } catch (RemoteException var8) {
            NamingException ne = new ConfigurationException("Failed to bind clusterable object");
            ne.setRootCause(var8);
            throw ne;
         }
      }

      return replacement;
   }

   public static void initialize() {
      try {
         WLNamingManager.addStateFactory(new MigratableRemoteBinderFactory());
      } catch (NamingException var1) {
         throw new AssertionError("impossible exception", var1);
      }
   }
}
