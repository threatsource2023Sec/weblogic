package weblogic.jms.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;

public class InflaterDispenser {
   private static byte[] dict = null;
   private static ThreadLocalInflater inflater;

   public static Inflater getInflater() {
      Inflater inf = (Inflater)inflater.get();
      if (dict != null) {
         inf.setDictionary(dict);
      }

      return inf;
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
            throw new AssertionError("Cannot setup Inflater");
         }
      }

      inflater = new ThreadLocalInflater();
   }

   private static class ThreadLocalInflater extends ThreadLocal {
      private ThreadLocalInflater() {
      }

      public Object initialValue() {
         return new Inflater(true);
      }

      // $FF: synthetic method
      ThreadLocalInflater(Object x0) {
         this();
      }
   }
}
