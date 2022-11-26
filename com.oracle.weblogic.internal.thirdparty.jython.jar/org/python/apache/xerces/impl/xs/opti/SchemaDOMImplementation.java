package org.python.apache.xerces.impl.xs.opti;

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

final class SchemaDOMImplementation implements DOMImplementation {
   private static final SchemaDOMImplementation singleton = new SchemaDOMImplementation();

   public static DOMImplementation getDOMImplementation() {
      return singleton;
   }

   private SchemaDOMImplementation() {
   }

   public Document createDocument(String var1, String var2, DocumentType var3) throws DOMException {
      throw new DOMException((short)9, "Method not supported");
   }

   public DocumentType createDocumentType(String var1, String var2, String var3) throws DOMException {
      throw new DOMException((short)9, "Method not supported");
   }

   public Object getFeature(String var1, String var2) {
      return singleton.hasFeature(var1, var2) ? singleton : null;
   }

   public boolean hasFeature(String var1, String var2) {
      boolean var3 = var2 == null || var2.length() == 0;
      return (var1.equalsIgnoreCase("Core") || var1.equalsIgnoreCase("XML")) && (var3 || var2.equals("1.0") || var2.equals("2.0") || var2.equals("3.0"));
   }
}
