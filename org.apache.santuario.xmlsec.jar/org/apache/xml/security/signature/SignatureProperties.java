package org.apache.xml.security.signature;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SignatureProperties extends SignatureElementProxy {
   public SignatureProperties(Document doc) {
      super(doc);
      this.addReturnToSelf();
   }

   public SignatureProperties(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
      Attr attr = element.getAttributeNodeNS((String)null, "Id");
      if (attr != null) {
         element.setIdAttributeNode(attr, true);
      }

      int length = this.getLength();

      for(int i = 0; i < length; ++i) {
         Element propertyElem = XMLUtils.selectDsNode(this.getElement(), "SignatureProperty", i);
         Attr propertyAttr = propertyElem.getAttributeNodeNS((String)null, "Id");
         if (propertyAttr != null) {
            propertyElem.setIdAttributeNode(propertyAttr, true);
         }
      }

   }

   public int getLength() {
      Element[] propertyElems = XMLUtils.selectDsNodes(this.getElement(), "SignatureProperty");
      return propertyElems.length;
   }

   public SignatureProperty item(int i) throws XMLSignatureException {
      try {
         Element propertyElem = XMLUtils.selectDsNode(this.getElement(), "SignatureProperty", i);
         return propertyElem == null ? null : new SignatureProperty(propertyElem, this.baseURI);
      } catch (XMLSecurityException var3) {
         throw new XMLSignatureException(var3);
      }
   }

   public void setId(String Id) {
      if (Id != null) {
         this.setLocalIdAttribute("Id", Id);
      }

   }

   public String getId() {
      return this.getLocalAttribute("Id");
   }

   public void addSignatureProperty(SignatureProperty sp) {
      this.appendSelf(sp);
      this.addReturnToSelf();
   }

   public String getBaseLocalName() {
      return "SignatureProperties";
   }
}
