package weblogic.aspects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;
import weblogic.utils.classloaders.AbstractClassFinder;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.enumerations.EmptyEnumerator;

public class AspectSystemClassFinder extends AbstractClassFinder {
   private final AspectSystem system;

   public AspectSystemClassFinder(AspectSystem system) {
      this.system = system;
   }

   public Source getSource(String name) {
      return null;
   }

   public Enumeration getSources(String name) {
      Vector list = new Vector(0);
      return list.elements();
   }

   public Source getClassSource(String name) {
      byte[] bytes = (byte[])((byte[])this.system.getAllSources().get(name));
      return bytes == null ? null : new AspectSystemSource(bytes);
   }

   public String getClassPath() {
      return "";
   }

   public ClassFinder getManifestFinder() {
      return null;
   }

   public Enumeration entries() {
      return EmptyEnumerator.EMPTY;
   }

   public void close() {
   }

   private static class AspectSystemSource implements Source {
      private final byte[] bytes;
      private final long lastModified = System.currentTimeMillis();

      public AspectSystemSource(byte[] bytes) {
         this.bytes = bytes;
      }

      public InputStream getInputStream() throws IOException {
         return new ByteArrayInputStream(this.bytes);
      }

      public URL getURL() {
         return null;
      }

      public URL getCodeSourceURL() {
         return null;
      }

      public byte[] getBytes() {
         return this.bytes;
      }

      public long lastModified() {
         return this.lastModified;
      }

      public long length() {
         return (long)this.bytes.length;
      }
   }
}
