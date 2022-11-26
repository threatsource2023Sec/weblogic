package org.python.bouncycastle.est.jcajce;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.python.bouncycastle.est.LimitedSource;
import org.python.bouncycastle.est.Source;
import org.python.bouncycastle.est.TLSUniqueProvider;

class LimitedSSLSocketSource implements Source, TLSUniqueProvider, LimitedSource {
   protected final SSLSocket socket;
   private final ChannelBindingProvider bindingProvider;
   private final Long absoluteReadLimit;

   public LimitedSSLSocketSource(SSLSocket var1, ChannelBindingProvider var2, Long var3) {
      this.socket = var1;
      this.bindingProvider = var2;
      this.absoluteReadLimit = var3;
   }

   public InputStream getInputStream() throws IOException {
      return this.socket.getInputStream();
   }

   public OutputStream getOutputStream() throws IOException {
      return this.socket.getOutputStream();
   }

   public SSLSession getSession() {
      return this.socket.getSession();
   }

   public byte[] getTLSUnique() {
      if (this.isTLSUniqueAvailable()) {
         return this.bindingProvider.getChannelBinding(this.socket, "tls-unique");
      } else {
         throw new IllegalStateException("No binding provider.");
      }
   }

   public boolean isTLSUniqueAvailable() {
      return this.bindingProvider.canAccessChannelBinding(this.socket);
   }

   public void close() throws IOException {
      this.socket.close();
   }

   public Long getAbsoluteReadLimit() {
      return this.absoluteReadLimit;
   }
}
