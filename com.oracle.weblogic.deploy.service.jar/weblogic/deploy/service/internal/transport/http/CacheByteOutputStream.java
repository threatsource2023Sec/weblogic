package weblogic.deploy.service.internal.transport.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class CacheByteOutputStream extends OutputStream {
   static final int DEFAULT_CACHE_LIMIT_SIZE = 1048576;
   private final int limit;
   private int size;
   private byte[] oneByte;
   private ByteArrayOutputStream cache;

   CacheByteOutputStream() {
      this(1048576);
   }

   CacheByteOutputStream(int limit) {
      this.limit = limit;
      if (limit > 0) {
         this.cache = new ByteArrayOutputStream();
      }

   }

   public void write(int b) throws IOException {
      if (this.cache != null) {
         if (this.oneByte == null) {
            this.oneByte = new byte[1];
         }

         this.oneByte[0] = (byte)b;
         this.write(this.oneByte, 0, 1);
      } else {
         ++this.size;
      }

   }

   public void write(byte[] b) throws IOException {
      this.write(b, 0, b.length);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this.size += len;
      if (this.cache != null && this.cache.size() < this.limit - len) {
         this.cache.write(b, off, len);
      } else if (this.cache != null) {
         this.oneByte = null;
         this.cache = null;
      }

   }

   public int size() {
      return this.size;
   }

   public boolean isCached() {
      return this.cache != null;
   }

   public void writeTo(OutputStream out) throws IOException {
      if (this.cache != null) {
         this.cache.writeTo(out);
      }

   }
}
