package weblogic.jndi.internal;

import java.util.Hashtable;
import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.jndi.Environment;
import weblogic.kernel.ThreadLocalStack;

/** @deprecated */
@Deprecated
@Service
public final class ThreadEnvironment implements FastThreadLocalMarker {
   private static final ThreadLocalStack threadEnvironment = new ThreadLocalStack(true);

   /** @deprecated */
   @Deprecated
   public static void push(Environment env) {
      threadEnvironment.push(env);
   }

   /** @deprecated */
   @Deprecated
   public static Environment pop() {
      return (Environment)threadEnvironment.popAndPeek();
   }

   /** @deprecated */
   @Deprecated
   public static Environment get() {
      return (Environment)threadEnvironment.get();
   }

   /** @deprecated */
   @Deprecated
   public static Hashtable getEnvironmentProperties() {
      Environment env = (Environment)threadEnvironment.get();
      return env != null ? env.getProperties() : null;
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }
}
