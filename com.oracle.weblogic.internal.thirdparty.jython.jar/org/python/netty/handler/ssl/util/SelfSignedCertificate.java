package org.python.netty.handler.ssl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.Unpooled;
import org.python.netty.handler.codec.base64.Base64;
import org.python.netty.util.CharsetUtil;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class SelfSignedCertificate {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SelfSignedCertificate.class);
   private static final Date DEFAULT_NOT_BEFORE = new Date(SystemPropertyUtil.getLong("org.python.netty.selfSignedCertificate.defaultNotBefore", System.currentTimeMillis() - 31536000000L));
   private static final Date DEFAULT_NOT_AFTER = new Date(SystemPropertyUtil.getLong("org.python.netty.selfSignedCertificate.defaultNotAfter", 253402300799000L));
   private final File certificate;
   private final File privateKey;
   private final X509Certificate cert;
   private final PrivateKey key;

   public SelfSignedCertificate() throws CertificateException {
      this(DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
   }

   public SelfSignedCertificate(Date notBefore, Date notAfter) throws CertificateException {
      this("example.com", notBefore, notAfter);
   }

   public SelfSignedCertificate(String fqdn) throws CertificateException {
      this(fqdn, DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
   }

   public SelfSignedCertificate(String fqdn, Date notBefore, Date notAfter) throws CertificateException {
      this(fqdn, ThreadLocalInsecureRandom.current(), 1024, notBefore, notAfter);
   }

   public SelfSignedCertificate(String fqdn, SecureRandom random, int bits) throws CertificateException {
      this(fqdn, random, bits, DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
   }

   public SelfSignedCertificate(String fqdn, SecureRandom random, int bits, Date notBefore, Date notAfter) throws CertificateException {
      KeyPair keypair;
      try {
         KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
         keyGen.initialize(bits, random);
         keypair = keyGen.generateKeyPair();
      } catch (NoSuchAlgorithmException var23) {
         throw new Error(var23);
      }

      String[] paths;
      try {
         paths = OpenJdkSelfSignedCertGenerator.generate(fqdn, keypair, random, notBefore, notAfter);
      } catch (Throwable var22) {
         logger.debug("Failed to generate a self-signed X.509 certificate using sun.security.x509:", var22);

         try {
            paths = BouncyCastleSelfSignedCertGenerator.generate(fqdn, keypair, random, notBefore, notAfter);
         } catch (Throwable var21) {
            logger.debug("Failed to generate a self-signed X.509 certificate using Bouncy Castle:", var21);
            throw new CertificateException("No provider succeeded to generate a self-signed certificate. See debug log for the root cause.");
         }
      }

      this.certificate = new File(paths[0]);
      this.privateKey = new File(paths[1]);
      this.key = keypair.getPrivate();
      FileInputStream certificateInput = null;

      try {
         certificateInput = new FileInputStream(this.certificate);
         this.cert = (X509Certificate)CertificateFactory.getInstance("X509").generateCertificate(certificateInput);
      } catch (Exception var20) {
         throw new CertificateEncodingException(var20);
      } finally {
         if (certificateInput != null) {
            try {
               certificateInput.close();
            } catch (IOException var19) {
               logger.warn("Failed to close a file: " + this.certificate, (Throwable)var19);
            }
         }

      }

   }

   public File certificate() {
      return this.certificate;
   }

   public File privateKey() {
      return this.privateKey;
   }

   public X509Certificate cert() {
      return this.cert;
   }

   public PrivateKey key() {
      return this.key;
   }

   public void delete() {
      safeDelete(this.certificate);
      safeDelete(this.privateKey);
   }

   static String[] newSelfSignedCertificate(String fqdn, PrivateKey key, X509Certificate cert) throws IOException, CertificateEncodingException {
      ByteBuf wrappedBuf = Unpooled.wrappedBuffer(key.getEncoded());

      ByteBuf encodedBuf;
      String keyText;
      try {
         encodedBuf = Base64.encode(wrappedBuf, true);

         try {
            keyText = "-----BEGIN PRIVATE KEY-----\n" + encodedBuf.toString(CharsetUtil.US_ASCII) + "\n-----END PRIVATE KEY-----\n";
         } finally {
            encodedBuf.release();
         }
      } finally {
         wrappedBuf.release();
      }

      File keyFile = File.createTempFile("keyutil_" + fqdn + '_', ".key");
      keyFile.deleteOnExit();
      OutputStream keyOut = new FileOutputStream(keyFile);

      try {
         keyOut.write(keyText.getBytes(CharsetUtil.US_ASCII));
         keyOut.close();
         keyOut = null;
      } finally {
         if (keyOut != null) {
            safeClose(keyFile, keyOut);
            safeDelete(keyFile);
         }

      }

      wrappedBuf = Unpooled.wrappedBuffer(cert.getEncoded());

      String certText;
      try {
         encodedBuf = Base64.encode(wrappedBuf, true);

         try {
            certText = "-----BEGIN CERTIFICATE-----\n" + encodedBuf.toString(CharsetUtil.US_ASCII) + "\n-----END CERTIFICATE-----\n";
         } finally {
            encodedBuf.release();
         }
      } finally {
         wrappedBuf.release();
      }

      File certFile = File.createTempFile("keyutil_" + fqdn + '_', ".crt");
      certFile.deleteOnExit();
      OutputStream certOut = new FileOutputStream(certFile);

      try {
         certOut.write(certText.getBytes(CharsetUtil.US_ASCII));
         certOut.close();
         certOut = null;
      } finally {
         if (certOut != null) {
            safeClose(certFile, certOut);
            safeDelete(certFile);
            safeDelete(keyFile);
         }

      }

      return new String[]{certFile.getPath(), keyFile.getPath()};
   }

   private static void safeDelete(File certFile) {
      if (!certFile.delete()) {
         logger.warn("Failed to delete a file: " + certFile);
      }

   }

   private static void safeClose(File keyFile, OutputStream keyOut) {
      try {
         keyOut.close();
      } catch (IOException var3) {
         logger.warn("Failed to close a file: " + keyFile, (Throwable)var3);
      }

   }
}
