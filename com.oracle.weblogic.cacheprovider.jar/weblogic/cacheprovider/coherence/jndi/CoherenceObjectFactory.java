package weblogic.cacheprovider.coherence.jndi;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.spi.ObjectFactory;
import weblogic.application.naming.EnvReference;
import weblogic.cacheprovider.coherence.CoherenceClusterManager;

public class CoherenceObjectFactory implements ObjectFactory {
   private volatile Object referent;

   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
      EnvReference ref = (EnvReference)obj;
      String type = ref.getResourceRefBean().getResType();
      String objectName = ref.getJndiName();
      ClassLoader loader = ref.getClassloader();
      if (this.referent == null) {
         try {
            CoherenceClusterManager manager = CoherenceClusterManager.getInstance();
            if (type.equals("com.tangosol.net.NamedCache")) {
               this.referent = manager.ensureCache(objectName, loader);
            } else if (type.equals("com.tangosol.net.Service")) {
               this.referent = manager.ensureService(objectName, loader);
            }
         } catch (Exception var11) {
            NameNotFoundException ne = new NameNotFoundException(var11.getMessage());
            ne.initCause(var11);
            throw ne;
         }
      }

      if (this.referent != null) {
         return this.referent;
      } else {
         throw new NameNotFoundException(this + " not bound");
      }
   }
}
