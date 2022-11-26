package weblogic.apache.xerces.impl.xs;

import weblogic.apache.xerces.impl.xs.util.XSObjectListImpl;
import weblogic.apache.xerces.xs.XSAnnotation;
import weblogic.apache.xerces.xs.XSModelGroup;
import weblogic.apache.xerces.xs.XSModelGroupDefinition;
import weblogic.apache.xerces.xs.XSNamespaceItem;
import weblogic.apache.xerces.xs.XSObjectList;

public class XSGroupDecl implements XSModelGroupDefinition {
   public String fName = null;
   public String fTargetNamespace = null;
   public XSModelGroupImpl fModelGroup = null;
   public XSObjectList fAnnotations = null;
   private XSNamespaceItem fNamespaceItem = null;

   public short getType() {
      return 6;
   }

   public String getName() {
      return this.fName;
   }

   public String getNamespace() {
      return this.fTargetNamespace;
   }

   public XSModelGroup getModelGroup() {
      return this.fModelGroup;
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
