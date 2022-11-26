package org.apache.xml.security.c14n.helper;

import org.apache.xml.security.c14n.CanonicalizationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public final class C14nHelper {
   private C14nHelper() {
   }

   public static boolean namespaceIsRelative(Attr namespace) {
      return !namespaceIsAbsolute(namespace);
   }

   public static boolean namespaceIsRelative(String namespaceValue) {
      return !namespaceIsAbsolute(namespaceValue);
   }

   public static boolean namespaceIsAbsolute(Attr namespace) {
      return namespaceIsAbsolute(namespace.getValue());
   }

   public static boolean namespaceIsAbsolute(String namespaceValue) {
      if (namespaceValue.length() == 0) {
         return true;
      } else {
         return namespaceValue.indexOf(58) > 0;
      }
   }

   public static void assertNotRelativeNS(Attr attr) throws CanonicalizationException {
      if (attr != null) {
         String nodeAttrName = attr.getNodeName();
         boolean definesDefaultNS = "xmlns".equals(nodeAttrName);
         boolean definesNonDefaultNS = nodeAttrName.startsWith("xmlns:");
         if ((definesDefaultNS || definesNonDefaultNS) && namespaceIsRelative(attr)) {
            String parentName = attr.getOwnerElement().getTagName();
            String attrValue = attr.getValue();
            Object[] exArgs = new Object[]{parentName, nodeAttrName, attrValue};
            throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", exArgs);
         }
      }
   }

   public static void checkTraversability(Document document) throws CanonicalizationException {
      if (!document.isSupported("Traversal", "2.0")) {
         Object[] exArgs = new Object[]{document.getImplementation().getClass().getName()};
         throw new CanonicalizationException("c14n.Canonicalizer.TraversalNotSupported", exArgs);
      }
   }

   public static void checkForRelativeNamespace(Element ctxNode) throws CanonicalizationException {
      if (ctxNode == null) {
         throw new CanonicalizationException("Called checkForRelativeNamespace() on null");
      } else {
         NamedNodeMap attributes = ctxNode.getAttributes();
         int length = attributes.getLength();

         for(int i = 0; i < length; ++i) {
            assertNotRelativeNS((Attr)attributes.item(i));
         }

      }
   }
}
