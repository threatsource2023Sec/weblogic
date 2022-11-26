package org.python.apache.wml.dom;

import org.python.apache.wml.WMLDOMImplementation;
import org.python.apache.xerces.dom.CoreDocumentImpl;
import org.python.apache.xerces.dom.DOMImplementationImpl;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;

public class WMLDOMImplementationImpl extends DOMImplementationImpl implements WMLDOMImplementation {
   static final DOMImplementationImpl singleton = new WMLDOMImplementationImpl();

   public static DOMImplementation getDOMImplementation() {
      return singleton;
   }

   protected CoreDocumentImpl createDocument(DocumentType var1) {
      return new WMLDocumentImpl(var1);
   }
}
