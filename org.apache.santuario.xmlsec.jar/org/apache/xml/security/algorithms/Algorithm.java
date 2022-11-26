package org.apache.xml.security.algorithms;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Algorithm extends SignatureElementProxy {
   public Algorithm(Document doc, String algorithmURI) {
      super(doc);
      this.setAlgorithmURI(algorithmURI);
   }

   public Algorithm(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public String getAlgorithmURI() {
      return this.getLocalAttribute("Algorithm");
   }

   protected void setAlgorithmURI(String algorithmURI) {
      if (algorithmURI != null) {
         this.setLocalAttribute("Algorithm", algorithmURI);
      }

   }
}
