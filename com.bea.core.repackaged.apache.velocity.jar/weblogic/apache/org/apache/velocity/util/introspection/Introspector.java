package weblogic.apache.org.apache.velocity.util.introspection;

import java.lang.reflect.Method;
import weblogic.apache.org.apache.velocity.runtime.RuntimeLogger;

public class Introspector extends IntrospectorBase {
   public static final String CACHEDUMP_MSG = "Introspector : detected classloader change. Dumping cache.";
   private RuntimeLogger rlog = null;

   public Introspector(RuntimeLogger logger) {
      this.rlog = logger;
   }

   public Method getMethod(Class c, String name, Object[] params) throws Exception {
      try {
         return super.getMethod(c, name, params);
      } catch (MethodMap.AmbiguousException var7) {
         String msg = "Introspection Error : Ambiguous method invocation " + name + "( ";

         for(int i = 0; i < params.length; ++i) {
            if (i > 0) {
               msg = msg + ", ";
            }

            msg = msg + params[i].getClass().getName();
         }

         msg = msg + ") for class " + c;
         this.rlog.error(msg);
         return null;
      }
   }

   protected void clearCache() {
      super.clearCache();
      this.rlog.info("Introspector : detected classloader change. Dumping cache.");
   }
}
