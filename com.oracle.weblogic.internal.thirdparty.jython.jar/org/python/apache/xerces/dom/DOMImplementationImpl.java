package org.python.apache.xerces.dom;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;

public class DOMImplementationImpl extends CoreDOMImplementationImpl implements DOMImplementation {
   static final DOMImplementationImpl singleton = new DOMImplementationImpl();

   public static DOMImplementation getDOMImplementation() {
      return singleton;
   }

   public boolean hasFeature(String var1, String var2) {
      boolean var3 = super.hasFeature(var1, var2);
      if (var3) {
         return var3;
      } else {
         boolean var4 = var2 == null || var2.length() == 0;
         if (var1.startsWith("+")) {
            var1 = var1.substring(1);
         }

         return var1.equalsIgnoreCase("Events") && (var4 || var2.equals("2.0")) || var1.equalsIgnoreCase("MutationEvents") && (var4 || var2.equals("2.0")) || var1.equalsIgnoreCase("Traversal") && (var4 || var2.equals("2.0")) || var1.equalsIgnoreCase("Range") && (var4 || var2.equals("2.0")) || var1.equalsIgnoreCase("MutationEvents") && (var4 || var2.equals("2.0"));
      }
   }

   protected CoreDocumentImpl createDocument(DocumentType var1) {
      return new DocumentImpl(var1);
   }
}
