package weblogic.security.utils;

import com.rsa.certj.cert.AttributeValueAssertion;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

public final class SSLCertUtility {
   private static final String WILDCARD_PREFIX = "*.";

   public static X509Certificate[] toJavaX5092(Certificate[] certs) throws IOException {
      if (certs != null) {
         if (certs instanceof X509Certificate[]) {
            return (X509Certificate[])((X509Certificate[])certs);
         }

         try {
            X509Certificate[] jCerts = new X509Certificate[certs.length];
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            for(int i = 0; i < certs.length; ++i) {
               jCerts[i] = (X509Certificate)((X509Certificate)(certs[i] instanceof X509Certificate ? certs[i] : cf.generateCertificate(new ByteArrayInputStream(certs[i].getEncoded()))));
            }

            return jCerts;
         } catch (CertificateException var4) {
            SSLSetup.info(var4, "Exception processing certificates: " + var4.getMessage());
         }
      }

      return null;
   }

   public static X509Certificate toX509(Certificate cert) throws CertificateException {
      if (cert == null) {
         return null;
      } else if (cert instanceof X509Certificate) {
         return (X509Certificate)cert;
      } else {
         CertificateFactory cf = CertificateFactory.getInstance("X.509");
         return (X509Certificate)cf.generateCertificate(new ByteArrayInputStream(cert.getEncoded()));
      }
   }

   public static X509Certificate getPeerLeafCert(SSLSocket sslSocket) {
      return getPeerLeafCert(sslSocket.getSession());
   }

   public static X509Certificate getPeerLeafCert(SSLSession session) {
      try {
         Certificate[] certs = session.getPeerCertificates();
         return certs != null && certs.length != 0 ? toX509(certs[0]) : null;
      } catch (CertificateException var2) {
      } catch (SSLPeerUnverifiedException var3) {
      }

      return null;
   }

   public static X509Certificate[] getPeerCertChain(SSLSocket sslSocket) {
      try {
         return toJavaX5092(sslSocket.getSession().getPeerCertificates());
      } catch (SSLPeerUnverifiedException var2) {
      } catch (IOException var3) {
      }

      return null;
   }

   public static com.rsa.certj.cert.X509Certificate toCertJ(X509Certificate cert) throws CertificateEncodingException, com.rsa.certj.cert.CertificateException {
      return new com.rsa.certj.cert.X509Certificate(cert.getEncoded(), 0, 0);
   }

   public static X500Name getSubjectX500Name(X509Certificate cert) throws CertificateEncodingException, com.rsa.certj.cert.CertificateException {
      return toCertJ(cert).getSubjectName();
   }

   public static String getSubjectDNValue(X500Name name, int oid) throws CertificateEncodingException, com.rsa.certj.cert.CertificateException, NameException {
      AttributeValueAssertion ava = name.getAttribute(oid);
      return ava == null ? null : ava.getStringAttribute();
   }

   public static String getSubjectDNValue(X509Certificate cert, int oid) throws CertificateEncodingException, com.rsa.certj.cert.CertificateException, NameException {
      return getSubjectDNValue(getSubjectX500Name(cert), oid);
   }

   public static String getSubjectDNCommonName(X509Certificate cert) throws CertificateEncodingException, com.rsa.certj.cert.CertificateException, NameException {
      return getSubjectDNValue((X509Certificate)cert, 0);
   }

   public static X509Certificate[] inputCertificateChain(SSLContextWrapper sslCtx, InputStream certStream) throws IOException, KeyManagementException {
      InputStreamCloner sc = new InputStreamCloner(certStream);

      try {
         return sslCtx.inputCertChain(sc.cloneStream());
      } catch (IOException var9) {
         throw new KeyManagementException(var9.getMessage());
      } catch (KeyManagementException var10) {
         try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate[] chain = new X509Certificate[]{(X509Certificate)cf.generateCertificate(sc.cloneStream())};
            return chain;
         } catch (CertificateEncodingException var6) {
         } catch (CertificateException var7) {
         } catch (IOException var8) {
         }

         throw var10;
      }
   }

   public static Collection getX509Certificates(KeyStore ks) throws KeyStoreException {
      ArrayList certs = new ArrayList();
      Enumeration aliases = ks.aliases();

      while(aliases.hasMoreElements()) {
         String alias = (String)aliases.nextElement();
         if (ks.isCertificateEntry(alias)) {
            Certificate cert = ks.getCertificate(alias);
            if (cert instanceof X509Certificate) {
               certs.add(cert);
            }
         }
      }

      return certs;
   }

   public static byte[] getFingerprint(Certificate cert) throws CertificateEncodingException, NoSuchAlgorithmException {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(cert.getEncoded());
      return md.digest();
   }

   public static String getCommonName(X509Certificate cert) {
      String cn = null;
      if (cert != null) {
         String dn = cert.getSubjectX500Principal().getName();
         int index = dn.indexOf("CN=");
         if (index >= 0) {
            boolean containsEscapeCharacters = false;
            int start = index + 3;

            int end;
            for(end = dn.indexOf(44, start); end > 0 && dn.charAt(end - 1) == '\\'; end = dn.indexOf(",", end + 1)) {
               containsEscapeCharacters = true;
            }

            if (end < 0) {
               end = dn.length();
            }

            cn = dn.substring(start, end);
            if (containsEscapeCharacters) {
               int cnLength = cn.length();
               StringBuffer buf = new StringBuffer(cnLength);

               for(int i = 0; i < cnLength; ++i) {
                  char c = cn.charAt(i);
                  if (c == '\\') {
                     ++i;
                     if (i == cnLength) {
                        break;
                     }

                     c = cn.charAt(i);
                  }

                  buf.append(c);
               }

               cn = buf.toString();
            }
         }
      }

      return cn;
   }

   public static String getCommonName(SSLSession session) {
      return getCommonName(getPeerLeafCert(session));
   }

   public static Collection getDNSSubjAltNames(SSLSession session) {
      return getDNSSubjAltNames(session, true, true);
   }

   public static Collection getDNSSubjAltNames(SSLSession session, boolean includeWildcardSANDNSNames, boolean includeNonWildcardSANDNSNames) {
      X509Certificate cert = getPeerLeafCert(session);
      Collection sansCollection = null;

      try {
         sansCollection = cert.getSubjectAlternativeNames();
      } catch (CertificateParsingException var12) {
         return sansCollection;
      }

      if (sansCollection == null) {
         return sansCollection;
      } else {
         Vector dnsNames = new Vector();
         Iterator iter = sansCollection.iterator();
         List generalName = null;
         ListIterator li = null;
         Object ob = null;
         Integer index = null;
         String dnsName = null;

         label69:
         while(iter.hasNext()) {
            generalName = (List)iter.next();
            li = generalName.listIterator();

            while(true) {
               do {
                  do {
                     do {
                        do {
                           do {
                              if (!li.hasNext()) {
                                 continue label69;
                              }

                              ob = li.next();
                           } while(!(ob instanceof Integer));

                           index = (Integer)ob;
                        } while(index != 2);
                     } while(!li.hasNext());

                     dnsName = (String)li.next();
                  } while(!includeWildcardSANDNSNames && dnsName != null && dnsName.startsWith("*."));
               } while(!includeNonWildcardSANDNSNames && dnsName != null && !dnsName.startsWith("*."));

               dnsNames.add(dnsName);
            }
         }

         return dnsNames;
      }
   }
}
