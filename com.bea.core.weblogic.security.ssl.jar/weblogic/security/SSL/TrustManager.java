package weblogic.security.SSL;

import java.security.cert.X509Certificate;

public interface TrustManager {
   int ERR_NONE = 0;
   int ERR_CERT_CHAIN_INVALID = 1;
   int ERR_CERT_EXPIRED = 2;
   int ERR_CERT_CHAIN_INCOMPLETE = 4;
   int ERR_SIGNATURE_INVALID = 8;
   int ERR_CERT_CHAIN_UNTRUSTED = 16;

   boolean certificateCallback(X509Certificate[] var1, int var2);
}
