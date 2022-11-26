package org.python.apache.xerces.impl.xs;

import org.python.apache.xerces.impl.dv.ValidatedInfo;
import org.python.apache.xerces.impl.xs.util.StringListImpl;
import org.python.apache.xerces.xs.AttributePSVI;
import org.python.apache.xerces.xs.ShortList;
import org.python.apache.xerces.xs.StringList;
import org.python.apache.xerces.xs.XSAttributeDeclaration;
import org.python.apache.xerces.xs.XSSimpleTypeDefinition;
import org.python.apache.xerces.xs.XSTypeDefinition;
import org.python.apache.xerces.xs.XSValue;

public class AttributePSVImpl implements AttributePSVI {
   protected XSAttributeDeclaration fDeclaration = null;
   protected XSTypeDefinition fTypeDecl = null;
   protected boolean fSpecified = false;
   protected ValidatedInfo fValue = new ValidatedInfo();
   protected short fValidationAttempted = 0;
   protected short fValidity = 0;
   protected String[] fErrors = null;
   protected String fValidationContext = null;

   public String getSchemaDefault() {
      return this.fDeclaration == null ? null : this.fDeclaration.getConstraintValue();
   }

   public String getSchemaNormalizedValue() {
      return this.fValue.getNormalizedValue();
   }

   public boolean getIsSchemaSpecified() {
      return this.fSpecified;
   }

   public short getValidationAttempted() {
      return this.fValidationAttempted;
   }

   public short getValidity() {
      return this.fValidity;
   }

   public StringList getErrorCodes() {
      return (StringList)(this.fErrors != null && this.fErrors.length != 0 ? new PSVIErrorList(this.fErrors, true) : StringListImpl.EMPTY_LIST);
   }

   public StringList getErrorMessages() {
      return (StringList)(this.fErrors != null && this.fErrors.length != 0 ? new PSVIErrorList(this.fErrors, false) : StringListImpl.EMPTY_LIST);
   }

   public String getValidationContext() {
      return this.fValidationContext;
   }

   public XSTypeDefinition getTypeDefinition() {
      return this.fTypeDecl;
   }

   public XSSimpleTypeDefinition getMemberTypeDefinition() {
      return this.fValue.getMemberTypeDefinition();
   }

   public XSAttributeDeclaration getAttributeDeclaration() {
      return this.fDeclaration;
   }

   public Object getActualNormalizedValue() {
      return this.fValue.getActualValue();
   }

   public short getActualNormalizedValueType() {
      return this.fValue.getActualValueType();
   }

   public ShortList getItemValueTypes() {
      return this.fValue.getListValueTypes();
   }

   public XSValue getSchemaValue() {
      return this.fValue;
   }

   public void reset() {
      this.fValue.reset();
      this.fDeclaration = null;
      this.fTypeDecl = null;
      this.fSpecified = false;
      this.fValidationAttempted = 0;
      this.fValidity = 0;
      this.fErrors = null;
      this.fValidationContext = null;
   }
}
