package weblogic.jms.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceLoader;

public abstract class CompressionFactory {
   private static CompressionFactory[] factories;

   public static CompressionFactory getCompressionFactory(String factoryName) {
      if (factoryName == null) {
         factoryName = GZIPCompressionFactoryImpl.class.getName();
      }

      CompressionFactory[] var1 = factories;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         CompressionFactory cf = var1[var3];
         if (cf.getClass().getName().equals(factoryName)) {
            return cf;
         }
      }

      throw new AssertionError("Cannot locate compression factory " + factoryName);
   }

   public static CompressionFactory getCompressionFactory(byte tag) {
      CompressionFactory[] var1 = factories;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         CompressionFactory cf = var1[var3];
         if (cf.getCompressionTag() == tag) {
            return cf;
         }
      }

      throw new AssertionError("Cannot locate compression factory with tag " + tag);
   }

   public abstract OutputStream createCompressionOutputStream(OutputStream var1, Properties var2) throws IOException;

   public abstract InputStream createDecompressionInputStream(InputStream var1, int var2, Properties var3) throws IOException;

   public abstract byte getCompressionTag();

   static {
      ServiceLoader CompressionFactoryLoader = ServiceLoader.load(CompressionFactory.class);
      Iterator cfItr = CompressionFactoryLoader.iterator();
      ArrayList cfArray = new ArrayList();

      boolean hasGZIP;
      CompressionFactory cf;
      for(hasGZIP = false; cfItr.hasNext(); cfArray.add(cf)) {
         cf = (CompressionFactory)cfItr.next();
         if (cf.getClass().getName().equals(GZIPCompressionFactoryImpl.class.getName())) {
            hasGZIP = true;
         }
      }

      if (!hasGZIP) {
         cfArray.add(new GZIPCompressionFactoryImpl());
      }

      factories = (CompressionFactory[])cfArray.toArray(new CompressionFactory[cfArray.size()]);
   }
}
