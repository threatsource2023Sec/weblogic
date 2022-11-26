package weblogic.security.pki.revocation.common;

import java.net.URI;
import java.security.cert.X509Certificate;

abstract class CrlDpFetcher {
   public static CrlDpFetcher getInstance() {
      return new DefaultCrlDpFetcher();
   }

   abstract boolean updateCrls(X509Certificate var1, CrlCacheAccessor var2, URI var3, AbstractCertRevocConstants.AttributeUsage var4, long var5, long var7, LogListener var9) throws Exception;
}
