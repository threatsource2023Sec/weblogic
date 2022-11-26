package org.python.apache.xerces.impl.xs;

import org.python.apache.xerces.impl.dv.ValidatedInfo;
import org.python.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.python.apache.xerces.xs.ShortList;
import org.python.apache.xerces.xs.XSAttributeDeclaration;
import org.python.apache.xerces.xs.XSAttributeUse;
import org.python.apache.xerces.xs.XSNamespaceItem;
import org.python.apache.xerces.xs.XSObjectList;
import org.python.apache.xerces.xs.XSValue;

public class XSAttributeUseImpl implements XSAttributeUse {
   public XSAttributeDecl fAttrDecl = null;
   public short fUse = 0;
   public short fConstraintType = 0;
   public ValidatedInfo fDefault = null;
   public XSObjectList fAnnotations = null;

   public void reset() {
      this.fDefault = null;
      this.fAttrDecl = null;
      this.fUse = 0;
      this.fConstraintType = 0;
      this.fAnnotations = null;
   }

   public short getType() {
      return 4;
   }

   public String getName() {
      return null;
   }

   public String getNamespace() {
      return null;
   }

   public boolean getRequired() {
      return this.fUse == 1;
   }

   public XSAttributeDeclaration getAttrDeclaration() {
      return this.fAttrDecl;
   }

   public short getConstraintType() {
      return this.fConstraintType;
   }

   public String getConstraintValue() {
      return this.getConstraintType() == 0 ? null : this.fDefault.stringValue();
   }

   public XSNamespaceItem getNamespaceItem() {
      return null;
   }

   public Object getActualVC() {
      return this.getConstraintType() == 0 ? null : this.fDefault.actualValue;
   }

   public short getActualVCType() {
      return this.getConstraintType() == 0 ? 45 : this.fDefault.actualValueType;
   }

   public ShortList getItemValueTypes() {
      return this.getConstraintType() == 0 ? null : this.fDefault.itemValueTypes;
   }

   public XSValue getValueConstraintValue() {
      return this.fDefault;
   }

   public XSObjectList getAnnotations() {
      return (XSObjectList)(this.fAnnotations != null ? this.fAnnotations : XSObjectListImpl.EMPTY_LIST);
   }
}
