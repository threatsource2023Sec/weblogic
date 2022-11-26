package org.python.netty.handler.ssl.util;

import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.python.netty.buffer.ByteBufUtil;
import org.python.netty.buffer.Unpooled;
import org.python.netty.util.concurrent.FastThreadLocal;
import org.python.netty.util.internal.EmptyArrays;

public final class FingerprintTrustManagerFactory extends SimpleTrustManagerFactory {
   private static final Pattern FINGERPRINT_PATTERN = Pattern.compile("^[0-9a-fA-F:]+$");
   private static final Pattern FINGERPRINT_STRIP_PATTERN = Pattern.compile(":");
   private static final int SHA1_BYTE_LEN = 20;
   private static final int SHA1_HEX_LEN = 40;
   private static final FastThreadLocal tlmd = new FastThreadLocal() {
      protected MessageDigest initialValue() {
         try {
            return MessageDigest.getInstance("SHA1");
         } catch (NoSuchAlgorithmException var2) {
            throw new Error(var2);
         }
      }
   };
   private final TrustManager tm;
   private final byte[][] fingerprints;

   public FingerprintTrustManagerFactory(Iterable fingerprints) {
      this(toFingerprintArray(fingerprints));
   }

   public FingerprintTrustManagerFactory(String... fingerprints) {
      this(toFingerprintArray(Arrays.asList(fingerprints)));
   }

   public FingerprintTrustManagerFactory(byte[]... fingerprints) {
      this.tm = new X509TrustManager() {
         public void checkClientTrusted(X509Certificate[] chain, String s) throws CertificateException {
            this.checkTrusted("client", chain);
         }

         public void checkServerTrusted(X509Certificate[] chain, String s) throws CertificateException {
            this.checkTrusted("server", chain);
         }

         private void checkTrusted(String type, X509Certificate[] chain) throws CertificateException {
            X509Certificate cert = chain[0];
            byte[] fingerprint = this.fingerprint(cert);
            boolean found = false;
            byte[][] var6 = FingerprintTrustManagerFactory.this.fingerprints;
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               byte[] allowedFingerprint = var6[var8];
               if (Arrays.equals(fingerprint, allowedFingerprint)) {
                  found = true;
                  break;
               }
            }

            if (!found) {
               throw new CertificateException(type + " certificate with unknown fingerprint: " + cert.getSubjectDN());
            }
         }

         private byte[] fingerprint(X509Certificate cert) throws CertificateEncodingException {
            MessageDigest md = (MessageDigest)FingerprintTrustManagerFactory.tlmd.get();
            md.reset();
            return md.digest(cert.getEncoded());
         }

         public X509Certificate[] getAcceptedIssuers() {
            return EmptyArrays.EMPTY_X509_CERTIFICATES;
         }
      };
      if (fingerprints == null) {
         throw new NullPointerException("fingerprints");
      } else {
         List list = new ArrayList(fingerprints.length);
         byte[][] var3 = fingerprints;
         int var4 = fingerprints.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            byte[] f = var3[var5];
            if (f == null) {
               break;
            }

            if (f.length != 20) {
               throw new IllegalArgumentException("malformed fingerprint: " + ByteBufUtil.hexDump(Unpooled.wrappedBuffer(f)) + " (expected: SHA1)");
            }

            list.add(f.clone());
         }

         this.fingerprints = (byte[][])list.toArray(new byte[list.size()][]);
      }
   }

   private static byte[][] toFingerprintArray(Iterable fingerprints) {
      if (fingerprints == null) {
         throw new NullPointerException("fingerprints");
      } else {
         List list = new ArrayList();
         Iterator var2 = fingerprints.iterator();

         while(var2.hasNext()) {
            String f = (String)var2.next();
            if (f == null) {
               break;
            }

            if (!FINGERPRINT_PATTERN.matcher(f).matches()) {
               throw new IllegalArgumentException("malformed fingerprint: " + f);
            }

            f = FINGERPRINT_STRIP_PATTERN.matcher(f).replaceAll("");
            if (f.length() != 40) {
               throw new IllegalArgumentException("malformed fingerprint: " + f + " (expected: SHA1)");
            }

            byte[] farr = new byte[20];

            for(int i = 0; i < farr.length; ++i) {
               int strIdx = i << 1;
               farr[i] = (byte)Integer.parseInt(f.substring(strIdx, strIdx + 2), 16);
            }

            list.add(farr);
         }

         return (byte[][])list.toArray(new byte[list.size()][]);
      }
   }

   protected void engineInit(KeyStore keyStore) throws Exception {
   }

   protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws Exception {
   }

   protected TrustManager[] engineGetTrustManagers() {
      return new TrustManager[]{this.tm};
   }
}
