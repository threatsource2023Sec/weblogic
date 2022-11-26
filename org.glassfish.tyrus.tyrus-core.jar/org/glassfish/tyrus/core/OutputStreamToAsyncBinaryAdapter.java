package org.glassfish.tyrus.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

class OutputStreamToAsyncBinaryAdapter extends OutputStream {
   private final TyrusWebSocket socket;

   public OutputStreamToAsyncBinaryAdapter(TyrusWebSocket socket) {
      this.socket = socket;
   }

   public void write(byte[] b, int off, int len) throws IOException {
      if (b == null) {
         throw new NullPointerException();
      } else if (off >= 0 && off <= b.length && len >= 0 && off + len <= b.length && off + len >= 0) {
         if (len != 0) {
            Future future = this.socket.sendBinary(b, off, len, false);

            try {
               future.get();
            } catch (InterruptedException var6) {
               Thread.currentThread().interrupt();
            } catch (ExecutionException var7) {
               if (var7.getCause() instanceof IOException) {
                  throw (IOException)var7.getCause();
               }

               throw new IOException(var7.getCause());
            }

         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void write(int i) throws IOException {
      byte[] byteArray = new byte[]{(byte)i};
      this.write(byteArray, 0, byteArray.length);
   }

   public void flush() throws IOException {
   }

   public void close() throws IOException {
      this.socket.sendBinary(new byte[0], true);
   }
}
