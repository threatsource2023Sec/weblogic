package weblogic.utils.classloaders;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import weblogic.utils.io.UnsyncByteArrayInputStream;

public final class ByteArraySource implements Source {
   private final ByteBuffer byteBuffer;
   private final int size;
   private final URL url;
   private final long lastModified;
   private SoftReference cache;

   public ByteArraySource(byte[] bytes, URL url) {
      this.size = bytes.length;
      this.byteBuffer = ByteBuffer.allocateDirect(this.size);
      this.byteBuffer.put(bytes);
      this.cache = new SoftReference(bytes);
      if (url == null) {
         try {
            url = new URL("file", "", ".class");
         } catch (MalformedURLException var4) {
         }
      }

      this.url = url;
      this.lastModified = System.currentTimeMillis();
   }

   public InputStream getInputStream() {
      return new UnsyncByteArrayInputStream(this.getBytes());
   }

   public URL getURL() {
      return this.url;
   }

   public URL getCodeSourceURL() {
      return this.getURL();
   }

   public byte[] getBytes() {
      byte[] bytes = (byte[])((byte[])this.cache.get());
      if (bytes != null) {
         return bytes;
      } else {
         bytes = new byte[this.size];
         this.byteBuffer.rewind();
         this.byteBuffer.get(bytes, 0, this.size);
         this.cache = new SoftReference(bytes);
         return bytes;
      }
   }

   public long lastModified() {
      return this.lastModified;
   }

   public long length() {
      return (long)this.size;
   }
}
