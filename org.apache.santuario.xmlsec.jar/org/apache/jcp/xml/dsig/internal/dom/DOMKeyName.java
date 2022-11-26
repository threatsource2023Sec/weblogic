package org.apache.jcp.xml.dsig.internal.dom;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.keyinfo.KeyName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMKeyName extends DOMStructure implements KeyName {
   private final String name;

   public DOMKeyName(String name) {
      if (name == null) {
         throw new NullPointerException("name cannot be null");
      } else {
         this.name = name;
      }
   }

   public DOMKeyName(Element knElem) {
      this.name = knElem.getFirstChild().getNodeValue();
   }

   public String getName() {
      return this.name;
   }

   public void marshal(Node parent, String dsPrefix, DOMCryptoContext context) throws MarshalException {
      Document ownerDoc = DOMUtils.getOwnerDocument(parent);
      Element knElem = DOMUtils.createElement(ownerDoc, "KeyName", "http://www.w3.org/2000/09/xmldsig#", dsPrefix);
      knElem.appendChild(ownerDoc.createTextNode(this.name));
      parent.appendChild(knElem);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof KeyName)) {
         return false;
      } else {
         KeyName okn = (KeyName)obj;
         return this.name.equals(okn.getName());
      }
   }

   public int hashCode() {
      int result = 17;
      result = 31 * result + this.name.hashCode();
      return result;
   }
}
