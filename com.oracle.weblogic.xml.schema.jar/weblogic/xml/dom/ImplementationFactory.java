package weblogic.xml.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

public final class ImplementationFactory implements DOMImplementation {
   private static ImplementationFactory singleton = new ImplementationFactory();

   private ImplementationFactory() {
   }

   public static DOMImplementation newImplementation() {
      return singleton;
   }

   public Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype) {
      if (doctype != null) {
         throw new DOMException((short)15, "This DOM implementation does not support DocumentType objects");
      } else {
         return new DocumentImpl(namespaceURI, qualifiedName);
      }
   }

   public DocumentType createDocumentType(String qualifiedName, String publicId, String systemId) {
      throw new DOMException((short)15, "This DOM implementation does not support DocumentType objects");
   }

   public boolean hasFeature(String feature, String version) {
      return false;
   }

   public Object getFeature(String s, String s1) throws DOMException {
      throw new UnsupportedOperationException("This class does not support JDK1.5");
   }
}
