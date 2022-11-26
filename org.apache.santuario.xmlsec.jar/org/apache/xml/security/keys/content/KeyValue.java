package org.apache.xml.security.keys.content;

import java.security.PublicKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.keyvalues.DSAKeyValue;
import org.apache.xml.security.keys.content.keyvalues.ECKeyValue;
import org.apache.xml.security.keys.content.keyvalues.RSAKeyValue;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class KeyValue extends SignatureElementProxy implements KeyInfoContent {
   public KeyValue(Document doc, DSAKeyValue dsaKeyValue) {
      super(doc);
      this.addReturnToSelf();
      this.appendSelf(dsaKeyValue);
      this.addReturnToSelf();
   }

   public KeyValue(Document doc, RSAKeyValue rsaKeyValue) {
      super(doc);
      this.addReturnToSelf();
      this.appendSelf(rsaKeyValue);
      this.addReturnToSelf();
   }

   public KeyValue(Document doc, Element unknownKeyValue) {
      super(doc);
      this.addReturnToSelf();
      this.appendSelf(unknownKeyValue);
      this.addReturnToSelf();
   }

   public KeyValue(Document doc, PublicKey pk) {
      super(doc);
      this.addReturnToSelf();
      if (pk instanceof DSAPublicKey) {
         DSAKeyValue dsa = new DSAKeyValue(this.getDocument(), pk);
         this.appendSelf(dsa);
         this.addReturnToSelf();
      } else if (pk instanceof RSAPublicKey) {
         RSAKeyValue rsa = new RSAKeyValue(this.getDocument(), pk);
         this.appendSelf(rsa);
         this.addReturnToSelf();
      } else {
         if (!(pk instanceof ECPublicKey)) {
            String error = "The given PublicKey type " + pk + " is not supported. Only DSAPublicKey and RSAPublicKey and ECPublicKey types are currently supported";
            throw new IllegalArgumentException(error);
         }

         ECKeyValue ec = new ECKeyValue(this.getDocument(), pk);
         this.appendSelf(ec);
         this.addReturnToSelf();
      }

   }

   public KeyValue(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public PublicKey getPublicKey() throws XMLSecurityException {
      Element rsa = XMLUtils.selectDsNode(this.getFirstChild(), "RSAKeyValue", 0);
      if (rsa != null) {
         RSAKeyValue kv = new RSAKeyValue(rsa, this.baseURI);
         return kv.getPublicKey();
      } else {
         Element dsa = XMLUtils.selectDsNode(this.getFirstChild(), "DSAKeyValue", 0);
         if (dsa != null) {
            DSAKeyValue kv = new DSAKeyValue(dsa, this.baseURI);
            return kv.getPublicKey();
         } else {
            return null;
         }
      }
   }

   public String getBaseLocalName() {
      return "KeyValue";
   }
}
