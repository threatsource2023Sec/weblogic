package weblogic.apache.xerces.impl.xs;

import weblogic.apache.xerces.impl.xs.util.XSObjectListImpl;
import weblogic.apache.xerces.xs.XSAnnotation;
import weblogic.apache.xerces.xs.XSNamespaceItem;
import weblogic.apache.xerces.xs.XSNotationDeclaration;
import weblogic.apache.xerces.xs.XSObjectList;

public class XSNotationDecl implements XSNotationDeclaration {
   public String fName = null;
   public String fTargetNamespace = null;
   public String fPublicId = null;
   public String fSystemId = null;
   public XSObjectList fAnnotations = null;
   private XSNamespaceItem fNamespaceItem = null;

   public short getType() {
      return 11;
   }

   public String getName() {
      return this.fName;
   }

   public String getNamespace() {
      return this.fTargetNamespace;
   }

   public String getSystemId() {
      return this.fSystemId;
   }

   public String getPublicId() {
      return this.fPublicId;
   }

   public XSAnnotation getAnnotation() {
      return this.fAnnotations != null ? (XSAnnotation)this.fAnnotations.item(0) : null;
   }

   public XSObjectList getAnnotations() {
      return (XSObjectList)(this.fAnnotations != null ? this.fAnnotations : XSObjectListImpl.EMPTY_LIST);
   }

   public XSNamespaceItem getNamespaceItem() {
      return this.fNamespaceItem;
   }

   void setNamespaceItem(XSNamespaceItem var1) {
      this.fNamespaceItem = var1;
   }
}
