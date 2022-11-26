package weblogic.common.internal;

import java.io.IOException;
import java.io.InputStream;
import weblogic.utils.Debug;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedObjectInputStream;

public class ProxyClassResolvableChunkedObjectInputStream extends ChunkedObjectInputStream {
   private ChunkedObjectInputStream.NestedObjectInputStream objectStream;

   public ProxyClassResolvableChunkedObjectInputStream(Chunk c, int skip) throws IOException {
      super(c, skip);
      this.initNestedStream();
   }

   protected ProxyClassResolvableChunkedObjectInputStream() throws IOException {
      this.objectStream = new ProxyClassResolvableNestedObjectInputStream(this);
   }

   protected void initNestedStream() throws IOException {
      this.objectStream = new ProxyClassResolvableNestedObjectInputStream(this);
   }

   public ChunkedObjectInputStream.NestedObjectInputStream getInputStream() {
      Debug.assertion(this.objectStream != null);
      return this.objectStream;
   }

   protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
      return ProxyClassResolver.resolveProxyClass(interfaces);
   }

   private final class ProxyClassResolvableNestedObjectInputStream extends ChunkedObjectInputStream.NestedObjectInputStream {
      public ProxyClassResolvableNestedObjectInputStream(InputStream in) throws IOException {
         super(ProxyClassResolvableChunkedObjectInputStream.this, in);
      }

      protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
         return ProxyClassResolvableChunkedObjectInputStream.this.resolveProxyClass(interfaces);
      }
   }
}
