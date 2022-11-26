package weblogic.rmi.cluster;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.StateFactory;
import weblogic.iiop.IIOPReplacer;
import weblogic.jndi.internal.WLNamingManager;
import weblogic.rmi.extensions.server.RemoteWrapper;
import weblogic.utils.AssertionError;

public final class ClusterableRemoteBinderFactory implements StateFactory {
   public Object getStateToBind(Object object, Name name, Context ctx, Hashtable env) throws NamingException {
      Object replacement = null;
      if (object instanceof ClusterableRemoteObject) {
         return object;
      } else {
         try {
            if (ClusterableRemoteObject.isIDLObject(object)) {
               object = IIOPReplacer.getRemoteIDLStub(object);
            }

            if (ClusterableRemoteObject.isClusterable(object)) {
               if (object instanceof Remote) {
                  replacement = new ClusterableRemoteObject((Remote)object);
               } else if (object instanceof RemoteWrapper) {
                  replacement = new ClusterableRemoteObject((RemoteWrapper)object);
               }
            }

            return replacement;
         } catch (RemoteException var8) {
            NamingException ne = new ConfigurationException("Failed to bind clusterable object: " + object);
            ne.setRootCause(var8);
            throw ne;
         }
      }
   }

   public static void initialize() {
      try {
         WLNamingManager.addStateFactory(new ClusterableRemoteBinderFactory());
      } catch (NamingException var1) {
         throw new AssertionError("impossible exception", var1);
      }
   }
}
