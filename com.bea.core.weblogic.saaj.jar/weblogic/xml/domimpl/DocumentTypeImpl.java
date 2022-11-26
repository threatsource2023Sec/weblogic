package weblogic.xml.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DocumentTypeImpl extends ParentNode implements DocumentType {
   static final long serialVersionUID = 7751299192316526485L;
   protected String name;
   protected NamedNodeMapImpl entities;
   protected NamedNodeMapImpl notations;
   protected NamedNodeMapImpl elements;
   protected String publicID;
   protected String systemID;
   protected String internalSubset;

   public DocumentTypeImpl(DocumentImpl ownerDocument, String name) {
      super(ownerDocument);
      this.name = name;
      this.entities = new NamedNodeMapImpl(this);
      this.notations = new NamedNodeMapImpl(this);
      this.elements = new NamedNodeMapImpl(this);
   }

   public DocumentTypeImpl(DocumentImpl ownerDocument, String qualifiedName, String publicID, String systemID) {
      this(ownerDocument, qualifiedName);
      this.publicID = publicID;
      this.systemID = systemID;
   }

   public String getPublicId() {
      return this.publicID;
   }

   public String getSystemId() {
      return this.systemID;
   }

   public void setInternalSubset(String internalSubset) {
      this.internalSubset = internalSubset;
   }

   public String getInternalSubset() {
      return this.internalSubset;
   }

   public short getNodeType() {
      return 10;
   }

   public String getNodeName() {
      return this.name;
   }

   public Node cloneNode(boolean deep) {
      DocumentTypeImpl newnode = (DocumentTypeImpl)super.cloneNode(deep);
      newnode.entities = this.entities.cloneMap(newnode);
      newnode.notations = this.notations.cloneMap(newnode);
      newnode.elements = this.elements.cloneMap(newnode);
      return newnode;
   }

   public String getTextContent() throws DOMException {
      return null;
   }

   public void setTextContent(String textContent) throws DOMException {
   }

   public boolean isEqualNode(Node arg) {
      if (!super.isEqualNode(arg)) {
         return false;
      } else {
         DocumentTypeImpl argDocType = (DocumentTypeImpl)arg;
         if (this.getPublicId() == null && argDocType.getPublicId() != null || this.getPublicId() != null && argDocType.getPublicId() == null || this.getSystemId() == null && argDocType.getSystemId() != null || this.getSystemId() != null && argDocType.getSystemId() == null || this.getInternalSubset() == null && argDocType.getInternalSubset() != null || this.getInternalSubset() != null && argDocType.getInternalSubset() == null) {
            return false;
         } else if (this.getPublicId() != null && !this.getPublicId().equals(argDocType.getPublicId())) {
            return false;
         } else if (this.getSystemId() != null && !this.getSystemId().equals(argDocType.getSystemId())) {
            return false;
         } else if (this.getInternalSubset() != null && !this.getInternalSubset().equals(argDocType.getInternalSubset())) {
            return false;
         } else {
            NamedNodeMapImpl argEntities = argDocType.entities;
            if ((this.entities != null || argEntities == null) && (this.entities == null || argEntities != null)) {
               Node noteNode1;
               if (this.entities != null && argEntities != null) {
                  if (this.entities.getLength() != argEntities.getLength()) {
                     return false;
                  }

                  for(int index = 0; this.entities.item(index) != null; ++index) {
                     Node entNode1 = this.entities.item(index);
                     noteNode1 = argEntities.getNamedItem(entNode1.getNodeName());
                     if (!((NodeImpl)entNode1).isEqualNode((NodeImpl)noteNode1)) {
                        return false;
                     }
                  }
               }

               NamedNodeMapImpl argNotations = argDocType.notations;
               if ((this.notations != null || argNotations == null) && (this.notations == null || argNotations != null)) {
                  if (this.notations != null && argNotations != null) {
                     if (this.notations.getLength() != argNotations.getLength()) {
                        return false;
                     }

                     for(int index = 0; this.notations.item(index) != null; ++index) {
                        noteNode1 = this.notations.item(index);
                        Node noteNode2 = argNotations.getNamedItem(noteNode1.getNodeName());
                        if (!((NodeImpl)noteNode1).isEqualNode((NodeImpl)noteNode2)) {
                           return false;
                        }
                     }
                  }

                  return true;
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }
   }

   void setOwnerDocument(DocumentImpl doc) {
      super.setOwnerDocument(doc);
      this.entities.setOwnerDocument(doc);
      this.notations.setOwnerDocument(doc);
      this.elements.setOwnerDocument(doc);
   }

   public String getName() {
      return this.name;
   }

   public NamedNodeMap getEntities() {
      return this.entities;
   }

   public NamedNodeMap getNotations() {
      return this.notations;
   }

   public void setReadOnly(boolean readOnly, boolean deep) {
      super.setReadOnly(readOnly, deep);
      this.elements.setReadOnly(readOnly, true);
      this.entities.setReadOnly(readOnly, true);
      this.notations.setReadOnly(readOnly, true);
   }

   public NamedNodeMap getElements() {
      return this.elements;
   }
}
