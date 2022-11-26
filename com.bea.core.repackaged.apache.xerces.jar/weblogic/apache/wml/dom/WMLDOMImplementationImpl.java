package weblogic.apache.wml.dom;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;
import weblogic.apache.wml.WMLDOMImplementation;
import weblogic.apache.xerces.dom.CoreDocumentImpl;
import weblogic.apache.xerces.dom.DOMImplementationImpl;

public class WMLDOMImplementationImpl extends DOMImplementationImpl implements WMLDOMImplementation {
   static final DOMImplementationImpl singleton = new WMLDOMImplementationImpl();

   public static DOMImplementation getDOMImplementation() {
      return singleton;
   }

   protected CoreDocumentImpl createDocument(DocumentType var1) {
      return new WMLDocumentImpl(var1);
   }
}
