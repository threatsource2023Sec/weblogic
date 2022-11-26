package weblogic.xml.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public class DOMImplementationImpl implements DOMImplementation {
   private static final DOMImplementationImpl INSTANCE = new DOMImplementationImpl();

   private DOMImplementationImpl() {
   }

   public static DOMImplementation getInstance() {
      return INSTANCE;
   }

   public boolean hasFeature(String feature, String version) {
      boolean anyVersion = version == null || version.length() == 0;
      if (feature.startsWith("+")) {
         feature = feature.substring(1);
      }

      return feature.equalsIgnoreCase("Core") && (anyVersion || version.equals("1.0") || version.equals("2.0")) || feature.equalsIgnoreCase("XML") && (anyVersion || version.equals("1.0") || version.equals("2.0"));
   }

   public DocumentType createDocumentType(String qualifiedName, String publicId, String systemId) throws DOMException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype) throws DOMException {
      if (namespaceURI == null && qualifiedName == null && doctype == null) {
         return new DocumentImpl();
      } else if (doctype != null && doctype.getOwnerDocument() != null) {
         String msg = "WRONG_DOCUMENT_ERR";
         throw new DOMException((short)4, msg);
      } else {
         DocumentImpl doc = new DocumentImpl(doctype);
         Element e = doc.createElementNS(namespaceURI, qualifiedName);
         doc.appendChild(e);
         return doc;
      }
   }

   public Object getFeature(String feature, String version) {
      throw new AssertionError("UNIMPLEMENTED");
   }
}
