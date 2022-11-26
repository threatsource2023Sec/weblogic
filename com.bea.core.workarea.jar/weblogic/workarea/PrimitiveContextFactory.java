package weblogic.workarea;

import java.io.IOException;
import java.io.Serializable;

public class PrimitiveContextFactory {
   public static String createEncodedKey(String key) {
      int hash = key.hashCode();

      StringBuffer code;
      for(code = new StringBuffer(); hash != 0; hash >>>= 6) {
         code.append((char)(59 + (hash & 63)));
      }

      return code.toString();
   }

   public static WorkContext create(String ctx) {
      return new StringWorkContext(ctx);
   }

   public static WorkContext create(long ctx) {
      return new LongWorkContext(ctx);
   }

   public static WorkContext createASCII(String ctx) {
      return new AsciiWorkContext(ctx);
   }

   public static WorkContext create(Serializable ctx) throws IOException {
      return new SerializableWorkContext(ctx);
   }

   public static WorkContext createMutable(Serializable ctx) throws IOException {
      return new SerializableWorkContext(ctx, true);
   }
}
