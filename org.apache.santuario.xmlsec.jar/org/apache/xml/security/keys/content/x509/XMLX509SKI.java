package org.apache.xml.security.keys.content.x509;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLX509SKI extends SignatureElementProxy implements XMLX509DataContent {
   private static final Logger LOG = LoggerFactory.getLogger(XMLX509SKI.class);
   public static final String SKI_OID = "2.5.29.14";

   public XMLX509SKI(Document doc, byte[] skiBytes) {
      super(doc);
      this.addBase64Text(skiBytes);
   }

   public XMLX509SKI(Document doc, X509Certificate x509certificate) throws XMLSecurityException {
      super(doc);
      this.addBase64Text(getSKIBytesFromCert(x509certificate));
   }

   public XMLX509SKI(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public byte[] getSKIBytes() throws XMLSecurityException {
      return this.getBytesFromTextChild();
   }

   public static byte[] getSKIBytesFromCert(X509Certificate cert) throws XMLSecurityException {
      if (cert.getVersion() < 3) {
         Object[] exArgs = new Object[]{cert.getVersion()};
         throw new XMLSecurityException("certificate.noSki.lowVersion", exArgs);
      } else {
         byte[] extensionValue = cert.getExtensionValue("2.5.29.14");
         if (extensionValue == null) {
            throw new XMLSecurityException("certificate.noSki.null");
         } else {
            byte[] skidValue = new byte[extensionValue.length - 4];
            System.arraycopy(extensionValue, 4, skidValue, 0, skidValue.length);
            if (LOG.isDebugEnabled()) {
               LOG.debug("Base64 of SKI is " + XMLUtils.encodeToString(skidValue));
            }

            return skidValue;
         }
      }
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof XMLX509SKI)) {
         return false;
      } else {
         XMLX509SKI other = (XMLX509SKI)obj;

         try {
            return Arrays.equals(other.getSKIBytes(), this.getSKIBytes());
         } catch (XMLSecurityException var4) {
            return false;
         }
      }
   }

   public int hashCode() {
      int result = 17;

      try {
         byte[] bytes = this.getSKIBytes();

         for(int i = 0; i < bytes.length; ++i) {
            result = 31 * result + bytes[i];
         }
      } catch (XMLSecurityException var4) {
         LOG.debug(var4.getMessage(), var4);
      }

      return result;
   }

   public String getBaseLocalName() {
      return "X509SKI";
   }
}
