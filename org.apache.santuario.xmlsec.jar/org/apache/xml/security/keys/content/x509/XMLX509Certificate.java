package org.apache.xml.security.keys.content.x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLX509Certificate extends SignatureElementProxy implements XMLX509DataContent {
   public static final String JCA_CERT_ID = "X.509";

   public XMLX509Certificate(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public XMLX509Certificate(Document doc, byte[] certificateBytes) {
      super(doc);
      this.addBase64Text(certificateBytes);
   }

   public XMLX509Certificate(Document doc, X509Certificate x509certificate) throws XMLSecurityException {
      super(doc);

      try {
         this.addBase64Text(x509certificate.getEncoded());
      } catch (CertificateEncodingException var4) {
         throw new XMLSecurityException(var4);
      }
   }

   public byte[] getCertificateBytes() throws XMLSecurityException {
      return this.getBytesFromTextChild();
   }

   public X509Certificate getX509Certificate() throws XMLSecurityException {
      byte[] certbytes = this.getCertificateBytes();

      try {
         InputStream is = new ByteArrayInputStream(certbytes);
         Throwable var3 = null;

         X509Certificate var6;
         try {
            CertificateFactory certFact = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate)certFact.generateCertificate(is);
            if (cert == null) {
               var6 = null;
               return var6;
            }

            var6 = cert;
         } catch (Throwable var17) {
            var3 = var17;
            throw var17;
         } finally {
            if (var3 != null) {
               try {
                  is.close();
               } catch (Throwable var16) {
                  var3.addSuppressed(var16);
               }
            } else {
               is.close();
            }

         }

         return var6;
      } catch (IOException | CertificateException var19) {
         throw new XMLSecurityException(var19);
      }
   }

   public PublicKey getPublicKey() throws XMLSecurityException, IOException {
      X509Certificate cert = this.getX509Certificate();
      return cert != null ? cert.getPublicKey() : null;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof XMLX509Certificate)) {
         return false;
      } else {
         XMLX509Certificate other = (XMLX509Certificate)obj;

         try {
            return Arrays.equals(other.getCertificateBytes(), this.getCertificateBytes());
         } catch (XMLSecurityException var4) {
            return false;
         }
      }
   }

   public int hashCode() {
      int result = 17;

      try {
         byte[] bytes = this.getCertificateBytes();

         for(int i = 0; i < bytes.length; ++i) {
            result = 31 * result + bytes[i];
         }
      } catch (XMLSecurityException var4) {
         LOG.debug(var4.getMessage(), var4);
      }

      return result;
   }

   public String getBaseLocalName() {
      return "X509Certificate";
   }
}
