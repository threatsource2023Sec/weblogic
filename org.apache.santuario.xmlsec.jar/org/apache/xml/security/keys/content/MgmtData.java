package org.apache.xml.security.keys.content;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MgmtData extends SignatureElementProxy implements KeyInfoContent {
   public MgmtData(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public MgmtData(Document doc, String mgmtData) {
      super(doc);
      this.addText(mgmtData);
   }

   public String getMgmtData() {
      return this.getTextFromTextChild();
   }

   public String getBaseLocalName() {
      return "MgmtData";
   }
}
