package weblogic.xml.domimpl;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Text;

class DocumentFragmentImpl extends ParentNode implements DocumentFragment {
   public DocumentFragmentImpl(DocumentImpl owner_document) {
      super(owner_document);
   }

   public short getNodeType() {
      return 11;
   }

   public String getNodeName() {
      return "#document-fragment";
   }

   public void normalize() {
      if (!this.isNormalized()) {
         ChildNode next;
         for(ChildNode kid = this.firstChild; kid != null; kid = next) {
            next = kid.nextSibling;
            if (kid.getNodeType() == 3) {
               if (next != null && next.getNodeType() == 3) {
                  ((Text)kid).appendData(next.getNodeValue());
                  this.removeChild(next);
                  next = kid;
               } else if (kid.getNodeValue().length() == 0) {
                  this.removeChild(kid);
               }
            }

            kid.normalize();
         }

         this.isNormalized(true);
      }
   }
}
