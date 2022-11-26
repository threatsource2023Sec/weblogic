package weblogic.xml.domimpl;

import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;

class EntityReferenceImpl extends ParentNode implements EntityReference {
   protected String name;
   protected String baseURI;

   public EntityReferenceImpl(DocumentImpl ownerDoc, String name) {
      super(ownerDoc);
      this.name = name;
      this.isReadOnly(true);
   }

   public short getNodeType() {
      return 5;
   }

   public String getNodeName() {
      return this.name;
   }

   protected String getEntityRefValue() {
      String value = "";
      if (this.firstChild != null) {
         if (this.firstChild.getNodeType() == 5) {
            value = ((EntityReferenceImpl)this.firstChild).getEntityRefValue();
         } else {
            if (this.firstChild.getNodeType() != 3) {
               return null;
            }

            value = this.firstChild.getNodeValue();
         }

         if (this.firstChild.nextSibling == null) {
            return value;
         } else {
            StringBuffer buff = new StringBuffer(value);

            for(ChildNode next = this.firstChild.nextSibling; next != null; next = next.nextSibling) {
               if (next.getNodeType() == 5) {
                  value = ((EntityReferenceImpl)next).getEntityRefValue();
               } else {
                  if (next.getNodeType() != 3) {
                     return null;
                  }

                  value = next.getNodeValue();
               }

               buff.append(value);
            }

            return buff.toString();
         }
      } else {
         return "";
      }
   }

   public Node cloneNode(boolean deep) {
      EntityReferenceImpl er = (EntityReferenceImpl)super.cloneNode(deep);
      er.setReadOnly(true, deep);
      return er;
   }
}
