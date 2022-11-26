package org.glassfish.grizzly.ssl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;

public class SSLSupportImpl implements SSLSupport {
   private static final Logger logger = Grizzly.logger(SSLSupportImpl.class);
   private static final SSLSupport.CipherData[] ciphers = new SSLSupport.CipherData[]{new SSLSupport.CipherData("_WITH_NULL_", 0), new SSLSupport.CipherData("_WITH_IDEA_CBC_", 128), new SSLSupport.CipherData("_WITH_RC2_CBC_40_", 40), new SSLSupport.CipherData("_WITH_RC4_40_", 40), new SSLSupport.CipherData("_WITH_RC4_128_", 128), new SSLSupport.CipherData("_WITH_DES40_CBC_", 40), new SSLSupport.CipherData("_WITH_DES_CBC_", 56), new SSLSupport.CipherData("_WITH_3DES_EDE_CBC_", 168), new SSLSupport.CipherData("_WITH_AES_128_", 128), new SSLSupport.CipherData("_WITH_AES_256_", 256)};
   public static final String KEY_SIZE_KEY = "SSL_KEY_SIZE";
   private final SSLEngine engine;
   private volatile SSLSession session;

   public SSLSupportImpl(Connection connection) {
      this.engine = SSLUtils.getSSLEngine(connection);
      if (this.engine == null) {
         throw new IllegalStateException("SSLEngine is null");
      } else {
         this.session = this.engine.getSession();
      }
   }

   public String getCipherSuite() throws IOException {
      return this.session == null ? null : this.session.getCipherSuite();
   }

   public Object[] getPeerCertificateChain() throws IOException {
      return this.getPeerCertificateChain(false);
   }

   protected X509Certificate[] getX509Certificates(SSLSession session) throws IOException {
      javax.security.cert.X509Certificate[] jsseCerts = null;

      try {
         jsseCerts = session.getPeerCertificateChain();
      } catch (Throwable var8) {
      }

      if (jsseCerts == null) {
         jsseCerts = new javax.security.cert.X509Certificate[0];
      }

      X509Certificate[] x509Certs = new X509Certificate[jsseCerts.length];

      for(int i = 0; i < x509Certs.length; ++i) {
         try {
            byte[] buffer = jsseCerts[i].getEncoded();
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream stream = new ByteArrayInputStream(buffer);
            x509Certs[i] = (X509Certificate)cf.generateCertificate(stream);
            if (logger.isLoggable(Level.FINE)) {
               logger.log(Level.FINE, "Cert #" + i + " = " + x509Certs[i]);
            }
         } catch (Exception var9) {
            logger.log(Level.INFO, "Error translating " + jsseCerts[i], var9);
            return null;
         }
      }

      if (x509Certs.length < 1) {
         return null;
      } else {
         return x509Certs;
      }
   }

   public Object[] getPeerCertificateChain(boolean force) throws IOException {
      if (this.session == null) {
         return null;
      } else {
         javax.security.cert.X509Certificate[] jsseCerts = null;

         try {
            jsseCerts = this.session.getPeerCertificateChain();
         } catch (Exception var4) {
         }

         if (jsseCerts == null) {
            jsseCerts = new javax.security.cert.X509Certificate[0];
         }

         if (jsseCerts.length <= 0 && force) {
            this.session.invalidate();
            this.session = this.engine.getSession();
         }

         return this.getX509Certificates(this.session);
      }
   }

   public Integer getKeySize() throws IOException {
      SSLSupport.CipherData[] c_aux = ciphers;
      if (this.session == null) {
         return null;
      } else {
         Integer keySize = (Integer)this.session.getValue("SSL_KEY_SIZE");
         if (keySize == null) {
            int size = 0;
            String cipherSuite = this.session.getCipherSuite();

            for(int i = 0; i < c_aux.length; ++i) {
               if (cipherSuite.contains(c_aux[i].phrase)) {
                  size = c_aux[i].keySize;
                  break;
               }
            }

            keySize = size;
            this.session.putValue("SSL_KEY_SIZE", keySize);
         }

         return keySize;
      }
   }

   public String getSessionId() throws IOException {
      if (this.session == null) {
         return null;
      } else {
         byte[] ssl_session = this.session.getId();
         if (ssl_session == null) {
            return null;
         } else {
            StringBuilder buf = new StringBuilder("");

            for(int x = 0; x < ssl_session.length; ++x) {
               String digit = Integer.toHexString(ssl_session[x]);
               if (digit.length() < 2) {
                  buf.append('0');
               }

               if (digit.length() > 2) {
                  digit = digit.substring(digit.length() - 2);
               }

               buf.append(digit);
            }

            return buf.toString();
         }
      }
   }
}
