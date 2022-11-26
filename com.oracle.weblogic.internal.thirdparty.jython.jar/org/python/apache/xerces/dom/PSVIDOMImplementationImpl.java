package org.python.apache.xerces.dom;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;

public class PSVIDOMImplementationImpl extends DOMImplementationImpl {
   static final PSVIDOMImplementationImpl singleton = new PSVIDOMImplementationImpl();

   public static DOMImplementation getDOMImplementation() {
      return singleton;
   }

   public boolean hasFeature(String var1, String var2) {
      return super.hasFeature(var1, var2) || var1.equalsIgnoreCase("psvi");
   }

   protected CoreDocumentImpl createDocument(DocumentType var1) {
      return new PSVIDocumentImpl(var1);
   }
}
