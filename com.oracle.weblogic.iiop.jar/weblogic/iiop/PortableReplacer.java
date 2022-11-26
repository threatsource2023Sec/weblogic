package weblogic.iiop;

import java.io.IOException;
import java.io.Serializable;

public abstract class PortableReplacer {
   private static final PortableReplacer NULL_PORTABLE_REPLACER = new NullPortableReplacer();
   private static PortableReplacer replacer;

   public static PortableReplacer getReplacer() {
      return replacer;
   }

   public static void setReplacer(PortableReplacer replacer) {
      PortableReplacer.replacer = replacer;
   }

   public abstract Serializable replaceObject(Serializable var1) throws IOException;

   static {
      replacer = NULL_PORTABLE_REPLACER;
   }

   static class NullPortableReplacer extends PortableReplacer {
      public Serializable replaceObject(Serializable o) throws IOException {
         return o;
      }
   }
}
