package org.apache.xml.security.keys.content.x509;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.RFC2253Parser;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLX509IssuerSerial extends SignatureElementProxy implements XMLX509DataContent {
   private static final Logger LOG = LoggerFactory.getLogger(XMLX509IssuerSerial.class);

   public XMLX509IssuerSerial(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public XMLX509IssuerSerial(Document doc, String x509IssuerName, BigInteger x509SerialNumber) {
      super(doc);
      this.addReturnToSelf();
      this.addTextElement(x509IssuerName, "X509IssuerName");
      this.addTextElement(x509SerialNumber.toString(), "X509SerialNumber");
   }

   public XMLX509IssuerSerial(Document doc, String x509IssuerName, String x509SerialNumber) {
      this(doc, x509IssuerName, new BigInteger(x509SerialNumber));
   }

   public XMLX509IssuerSerial(Document doc, String x509IssuerName, int x509SerialNumber) {
      this(doc, x509IssuerName, new BigInteger(Integer.toString(x509SerialNumber)));
   }

   public XMLX509IssuerSerial(Document doc, X509Certificate x509certificate) {
      this(doc, x509certificate.getIssuerX500Principal().getName(), x509certificate.getSerialNumber());
   }

   public BigInteger getSerialNumber() {
      String text = this.getTextFromChildElement("X509SerialNumber", "http://www.w3.org/2000/09/xmldsig#");
      LOG.debug("X509SerialNumber text: {}", text);
      return new BigInteger(text);
   }

   public int getSerialNumberInteger() {
      return this.getSerialNumber().intValue();
   }

   public String getIssuerName() {
      return RFC2253Parser.normalize(this.getTextFromChildElement("X509IssuerName", "http://www.w3.org/2000/09/xmldsig#"));
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof XMLX509IssuerSerial)) {
         return false;
      } else {
         XMLX509IssuerSerial other = (XMLX509IssuerSerial)obj;
         return this.getSerialNumber().equals(other.getSerialNumber()) && this.getIssuerName().equals(other.getIssuerName());
      }
   }

   public int hashCode() {
      int result = 17;
      result = 31 * result + this.getSerialNumber().hashCode();
      result = 31 * result + this.getIssuerName().hashCode();
      return result;
   }

   public String getBaseLocalName() {
      return "X509IssuerSerial";
   }
}
