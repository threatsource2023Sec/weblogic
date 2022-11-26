package org.glassfish.grizzly.ssl;

import java.io.IOException;

public interface SSLSupport {
   String CIPHER_SUITE_KEY = "javax.servlet.request.cipher_suite";
   String KEY_SIZE_KEY = "javax.servlet.request.key_size";
   String CERTIFICATE_KEY = "javax.servlet.request.X509Certificate";
   String SESSION_ID_KEY = "javax.servlet.request.ssl_session_id";

   String getCipherSuite() throws IOException;

   Object[] getPeerCertificateChain() throws IOException;

   Object[] getPeerCertificateChain(boolean var1) throws IOException;

   Integer getKeySize() throws IOException;

   String getSessionId() throws IOException;

   public static final class CipherData {
      public String phrase = null;
      public int keySize = 0;

      public CipherData(String phrase, int keySize) {
         this.phrase = phrase;
         this.keySize = keySize;
      }
   }
}
