package org.python.apache.html.dom;

import org.python.apache.xerces.dom.DOMImplementationImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.html.HTMLDOMImplementation;
import org.w3c.dom.html.HTMLDocument;

public class HTMLDOMImplementationImpl extends DOMImplementationImpl implements HTMLDOMImplementation {
   private static final HTMLDOMImplementation _instance = new HTMLDOMImplementationImpl();

   private HTMLDOMImplementationImpl() {
   }

   public final HTMLDocument createHTMLDocument(String var1) throws DOMException {
      if (var1 == null) {
         throw new NullPointerException("HTM014 Argument 'title' is null.");
      } else {
         HTMLDocumentImpl var2 = new HTMLDocumentImpl();
         var2.setTitle(var1);
         return var2;
      }
   }

   public static HTMLDOMImplementation getHTMLDOMImplementation() {
      return _instance;
   }
}
