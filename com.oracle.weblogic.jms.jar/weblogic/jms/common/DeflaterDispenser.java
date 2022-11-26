package weblogic.jms.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;

public class DeflaterDispenser {
   private static byte[] dict = null;
   private static ThreadLocalDeflater deflater;

   private static int getLevel(String lvl) {
      if (lvl.equals("BEST_COMPRESSION")) {
         return 9;
      } else if (lvl.equals("BEST_SPEED")) {
         return 1;
      } else if (lvl.equals("DEFAULT_COMPRESSION")) {
         return -1;
      } else {
         throw new AssertionError("Unknown Deflater Level");
      }
   }

   public static Deflater getDeflater(String level) {
      Deflater def = (Deflater)deflater.get();
      def.setLevel(getLevel(level));
      if (dict != null) {
         def.setDictionary(dict);
      }

      return def;
   }

   static {
      String dictFName = System.getProperty("sgc.compression.gzip.dict");
      if (dictFName != null) {
         try {
            File f = new File(dictFName);
            InputStream in = new BufferedInputStream(new FileInputStream(f));
            dict = new byte[(int)f.length()];
            in.read(dict, 0, (int)f.length());
         } catch (IOException var3) {
            throw new AssertionError("Cannot setup Deflater");
         }
      }

      deflater = new ThreadLocalDeflater();
   }

   public static class WlsDeflater extends Deflater {
      private int level;

      public WlsDeflater() {
         this.level = -1;
      }

      public WlsDeflater(int level) {
         super(level);
         this.level = level;
      }

      public WlsDeflater(int level, boolean val) {
         super(level, val);
         this.level = level;
      }

      public void setLevel(int level) {
         this.level = level;
         super.setLevel(level);
      }

      public int getLevel() {
         return this.level;
      }
   }

   static class ThreadLocalDeflater extends ThreadLocal {
      public Object initialValue() {
         return new WlsDeflater(-1, true);
      }
   }
}
