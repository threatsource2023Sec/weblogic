package weblogic.utils.io;

import java.io.IOException;
import java.io.ObjectStreamClass;

public final class ClassLoaderResolver implements Resolver {
   private final ClassLoader loader;

   public ClassLoaderResolver(ClassLoader l) {
      this.loader = l;
   }

   public Class resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
      try {
         return this.loader != null ? Class.forName(osc.getName(), false, this.loader) : Class.forName(osc.getName());
      } catch (ClassNotFoundException var3) {
         return Resolver.NOT_FOUND;
      }
   }
}
