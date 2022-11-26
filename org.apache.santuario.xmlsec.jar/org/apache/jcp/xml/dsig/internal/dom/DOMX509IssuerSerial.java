package org.apache.jcp.xml.dsig.internal.dom;

import java.math.BigInteger;
import javax.security.auth.x500.X500Principal;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMX509IssuerSerial extends DOMStructure implements X509IssuerSerial {
   private final String issuerName;
   private final BigInteger serialNumber;

   public DOMX509IssuerSerial(String issuerName, BigInteger serialNumber) {
      if (issuerName == null) {
         throw new NullPointerException("issuerName cannot be null");
      } else if (serialNumber == null) {
         throw new NullPointerException("serialNumber cannot be null");
      } else {
         new X500Principal(issuerName);
         this.issuerName = issuerName;
         this.serialNumber = serialNumber;
      }
   }

   public DOMX509IssuerSerial(Element isElem) throws MarshalException {
      Element iNElem = DOMUtils.getFirstChildElement(isElem, "X509IssuerName", "http://www.w3.org/2000/09/xmldsig#");
      Element sNElem = DOMUtils.getNextSiblingElement(iNElem, "X509SerialNumber", "http://www.w3.org/2000/09/xmldsig#");
      this.issuerName = iNElem.getFirstChild().getNodeValue();
      this.serialNumber = new BigInteger(sNElem.getFirstChild().getNodeValue());
   }

   public String getIssuerName() {
      return this.issuerName;
   }

   public BigInteger getSerialNumber() {
      return this.serialNumber;
   }

   public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      Document ownerDoc = DOMUtils.getOwnerDocument(parent);
      Element isElem = DOMUtils.createElement(ownerDoc, "X509IssuerSerial", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      Element inElem = DOMUtils.createElement(ownerDoc, "X509IssuerName", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      Element snElem = DOMUtils.createElement(ownerDoc, "X509SerialNumber", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      inElem.appendChild(ownerDoc.createTextNode(this.issuerName));
      snElem.appendChild(ownerDoc.createTextNode(this.serialNumber.toString()));
      isElem.appendChild(inElem);
      isElem.appendChild(snElem);
      parent.appendChild(isElem);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof X509IssuerSerial)) {
         return false;
      } else {
         X509IssuerSerial ois = (X509IssuerSerial)obj;
         return this.issuerName.equals(ois.getIssuerName()) && this.serialNumber.equals(ois.getSerialNumber());
      }
   }

   public int hashCode() {
      int result = 17;
      result = 31 * result + this.issuerName.hashCode();
      result = 31 * result + this.serialNumber.hashCode();
      return result;
   }
}
