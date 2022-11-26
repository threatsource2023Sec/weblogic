package org.apache.xml.security.utils;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class EncryptionElementProxy extends ElementProxy {
   public EncryptionElementProxy(Document doc) {
      super(doc);
   }

   public EncryptionElementProxy(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public final String getBaseNamespace() {
      return "http://www.w3.org/2001/04/xmlenc#";
   }
}
