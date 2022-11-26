package org.apache.xml.security.keys.content;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.w3c.dom.Element;

public class PGPData extends SignatureElementProxy implements KeyInfoContent {
   public PGPData(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public String getBaseLocalName() {
      return "PGPData";
   }
}
