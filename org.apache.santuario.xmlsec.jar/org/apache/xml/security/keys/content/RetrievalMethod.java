package org.apache.xml.security.keys.content;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RetrievalMethod extends SignatureElementProxy implements KeyInfoContent {
   public static final String TYPE_DSA = "http://www.w3.org/2000/09/xmldsig#DSAKeyValue";
   public static final String TYPE_RSA = "http://www.w3.org/2000/09/xmldsig#RSAKeyValue";
   public static final String TYPE_PGP = "http://www.w3.org/2000/09/xmldsig#PGPData";
   public static final String TYPE_SPKI = "http://www.w3.org/2000/09/xmldsig#SPKIData";
   public static final String TYPE_MGMT = "http://www.w3.org/2000/09/xmldsig#MgmtData";
   public static final String TYPE_X509 = "http://www.w3.org/2000/09/xmldsig#X509Data";
   public static final String TYPE_RAWX509 = "http://www.w3.org/2000/09/xmldsig#rawX509Certificate";

   public RetrievalMethod(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public RetrievalMethod(Document doc, String URI, Transforms transforms, String Type) {
      super(doc);
      this.setLocalAttribute("URI", URI);
      if (Type != null) {
         this.setLocalAttribute("Type", Type);
      }

      if (transforms != null) {
         this.appendSelf(transforms);
         this.addReturnToSelf();
      }

   }

   public Attr getURIAttr() {
      return this.getElement().getAttributeNodeNS((String)null, "URI");
   }

   public String getURI() {
      return this.getLocalAttribute("URI");
   }

   public String getType() {
      return this.getLocalAttribute("Type");
   }

   public Transforms getTransforms() throws XMLSecurityException {
      try {
         Element transformsElem = XMLUtils.selectDsNode(this.getFirstChild(), "Transforms", 0);
         return transformsElem != null ? new Transforms(transformsElem, this.baseURI) : null;
      } catch (XMLSignatureException var2) {
         throw new XMLSecurityException(var2);
      }
   }

   public String getBaseLocalName() {
      return "RetrievalMethod";
   }
}
