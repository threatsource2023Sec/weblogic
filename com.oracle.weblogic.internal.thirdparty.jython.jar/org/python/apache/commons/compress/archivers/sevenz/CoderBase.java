package org.python.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

abstract class CoderBase {
   private final Class[] acceptableOptions;
   private static final byte[] NONE = new byte[0];

   protected CoderBase(Class... acceptableOptions) {
      this.acceptableOptions = acceptableOptions;
   }

   boolean canAcceptOptions(Object opts) {
      Class[] var2 = this.acceptableOptions;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class c = var2[var4];
         if (c.isInstance(opts)) {
            return true;
         }
      }

      return false;
   }

   byte[] getOptionsAsProperties(Object options) throws IOException {
      return NONE;
   }

   Object getOptionsFromCoder(Coder coder, InputStream in) throws IOException {
      return null;
   }

   abstract InputStream decode(String var1, InputStream var2, long var3, Coder var5, byte[] var6) throws IOException;

   OutputStream encode(OutputStream out, Object options) throws IOException {
      throw new UnsupportedOperationException("method doesn't support writing");
   }

   protected static int numberOptionOrDefault(Object options, int defaultValue) {
      return options instanceof Number ? ((Number)options).intValue() : defaultValue;
   }
}
