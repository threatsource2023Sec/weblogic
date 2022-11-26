package weblogic.security.utils;

import java.security.cert.X509Certificate;
import javax.net.ssl.SSLSocket;

public interface SSLTruster {
   int ERR_NONE = 0;
   int ERR_CERT_CHAIN_INVALID = 1;
   int ERR_CERT_EXPIRED = 2;
   int ERR_CERT_CHAIN_INCOMPLETE = 4;
   int ERR_SIGNATURE_INVALID = 8;
   int ERR_CERT_CHAIN_UNTRUSTED = 16;
   int ERR_VALIDATION_FAILED = 32;
   int ERR_OVERRIDE_NOT_ALLOWED = 64;

   int validationCallback(X509Certificate[] var1, int var2, SSLSocket var3, X509Certificate[] var4);
}
