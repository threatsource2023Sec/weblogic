package org.python.bouncycastle.est.jcajce;

import java.util.Set;
import javax.net.ssl.SSLSocketFactory;
import org.python.bouncycastle.est.ESTClient;
import org.python.bouncycastle.est.ESTClientProvider;
import org.python.bouncycastle.est.ESTException;

class DefaultESTHttpClientProvider implements ESTClientProvider {
   private final JsseHostnameAuthorizer hostNameAuthorizer;
   private final SSLSocketFactoryCreator socketFactoryCreator;
   private final int timeout;
   private final ChannelBindingProvider bindingProvider;
   private final Set cipherSuites;
   private final Long absoluteLimit;
   private final boolean filterCipherSuites;

   public DefaultESTHttpClientProvider(JsseHostnameAuthorizer var1, SSLSocketFactoryCreator var2, int var3, ChannelBindingProvider var4, Set var5, Long var6, boolean var7) {
      this.hostNameAuthorizer = var1;
      this.socketFactoryCreator = var2;
      this.timeout = var3;
      this.bindingProvider = var4;
      this.cipherSuites = var5;
      this.absoluteLimit = var6;
      this.filterCipherSuites = var7;
   }

   public ESTClient makeClient() throws ESTException {
      try {
         SSLSocketFactory var1 = this.socketFactoryCreator.createFactory();
         return new DefaultESTClient(new DefaultESTClientSourceProvider(var1, this.hostNameAuthorizer, this.timeout, this.bindingProvider, this.cipherSuites, this.absoluteLimit, this.filterCipherSuites));
      } catch (Exception var2) {
         throw new ESTException(var2.getMessage(), var2.getCause());
      }
   }

   public boolean isTrusted() {
      return this.socketFactoryCreator.isTrusted();
   }
}
