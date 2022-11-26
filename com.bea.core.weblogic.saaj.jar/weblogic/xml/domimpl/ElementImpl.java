package weblogic.xml.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

class ElementImpl extends ElementBase implements Element {
   private String name;

   protected ElementImpl(DocumentImpl ownerDocument, String name) throws DOMException {
      super(ownerDocument);
      this.name = name;
   }

   public String getNodeName() {
      return this.name;
   }

   public String getTagName() {
      return this.name;
   }

   public String toString() {
      return this.getTagName();
   }
}
