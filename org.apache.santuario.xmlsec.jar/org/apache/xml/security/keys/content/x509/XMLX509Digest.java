package org.apache.xml.security.keys.content.x509;

import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.Signature11ElementProxy;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLX509Digest extends Signature11ElementProxy implements XMLX509DataContent {
   public XMLX509Digest(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public XMLX509Digest(Document doc, byte[] digestBytes, String algorithmURI) {
      super(doc);
      this.addBase64Text(digestBytes);
      this.setLocalAttribute("Algorithm", algorithmURI);
   }

   public XMLX509Digest(Document doc, X509Certificate x509certificate, String algorithmURI) throws XMLSecurityException {
      super(doc);
      this.addBase64Text(getDigestBytesFromCert(x509certificate, algorithmURI));
      this.setLocalAttribute("Algorithm", algorithmURI);
   }

   public Attr getAlgorithmAttr() {
      return this.getElement().getAttributeNodeNS((String)null, "Algorithm");
   }

   public String getAlgorithm() {
      return this.getAlgorithmAttr().getNodeValue();
   }

   public byte[] getDigestBytes() throws XMLSecurityException {
      return this.getBytesFromTextChild();
   }

   public static byte[] getDigestBytesFromCert(X509Certificate cert, String algorithmURI) throws XMLSecurityException {
      String jcaDigestAlgorithm = JCEMapper.translateURItoJCEID(algorithmURI);
      if (jcaDigestAlgorithm == null) {
         Object[] exArgs = new Object[]{algorithmURI};
         throw new XMLSecurityException("XMLX509Digest.UnknownDigestAlgorithm", exArgs);
      } else {
         try {
            MessageDigest md = MessageDigest.getInstance(jcaDigestAlgorithm);
            return md.digest(cert.getEncoded());
         } catch (Exception var5) {
            Object[] exArgs = new Object[]{jcaDigestAlgorithm};
            throw new XMLSecurityException("XMLX509Digest.FailedDigest", exArgs);
         }
      }
   }

   public String getBaseLocalName() {
      return "X509Digest";
   }
}
