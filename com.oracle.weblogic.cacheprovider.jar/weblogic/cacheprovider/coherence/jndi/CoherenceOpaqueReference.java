package weblogic.cacheprovider.coherence.jndi;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.cacheprovider.coherence.CoherenceClusterManager;
import weblogic.jndi.OpaqueReference;

public class CoherenceOpaqueReference implements OpaqueReference {
   private String objectName;
   private ClassLoader loader;
   private String type;
   private volatile Object referent;

   public CoherenceOpaqueReference(String objName, String type, ClassLoader loader) {
      this.objectName = objName;
      this.type = type;
      this.loader = loader;
   }

   public Object getReferent(Name name, Context ctx) throws NamingException {
      if (this.referent == null) {
         try {
            CoherenceClusterManager manager = CoherenceClusterManager.getInstance();
            if (this.type.equals("com.tangosol.net.NamedCache")) {
               this.referent = manager.ensureCache(this.objectName, this.loader);
            } else if (this.type.equals("com.tangosol.net.Service")) {
               this.referent = manager.ensureService(this.objectName, this.loader);
            }
         } catch (Exception var5) {
            NameNotFoundException ne = new NameNotFoundException(var5.getMessage());
            ne.initCause(var5);
            throw ne;
         }
      }

      if (this.referent != null) {
         return this.referent;
      } else {
         throw new NameNotFoundException(this + " not bound");
      }
   }

   public String toString() {
      return this.type + "::" + this.objectName;
   }
}
