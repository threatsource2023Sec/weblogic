package weblogic.apache.xerces.impl.xs;

import weblogic.apache.xerces.impl.dv.ValidatedInfo;
import weblogic.apache.xerces.impl.xs.util.StringListImpl;
import weblogic.apache.xerces.xs.AttributePSVI;
import weblogic.apache.xerces.xs.ItemPSVI;
import weblogic.apache.xerces.xs.ShortList;
import weblogic.apache.xerces.xs.StringList;
import weblogic.apache.xerces.xs.XSAttributeDeclaration;
import weblogic.apache.xerces.xs.XSSimpleTypeDefinition;
import weblogic.apache.xerces.xs.XSTypeDefinition;
import weblogic.apache.xerces.xs.XSValue;

public class AttributePSVImpl implements AttributePSVI {
   protected XSAttributeDeclaration fDeclaration = null;
   protected XSTypeDefinition fTypeDecl = null;
   protected boolean fSpecified = false;
   protected ValidatedInfo fValue = new ValidatedInfo();
   protected short fValidationAttempted = 0;
   protected short fValidity = 0;
   protected String[] fErrors = null;
   protected String fValidationContext = null;
   protected boolean fIsConstant;

   public AttributePSVImpl() {
   }

   public AttributePSVImpl(boolean var1, AttributePSVI var2) {
      this.fDeclaration = var2.getAttributeDeclaration();
      this.fTypeDecl = var2.getTypeDefinition();
      this.fSpecified = var2.getIsSchemaSpecified();
      this.fValue.copyFrom(var2.getSchemaValue());
      this.fValidationAttempted = var2.getValidationAttempted();
      this.fValidity = var2.getValidity();
      if (var2 instanceof AttributePSVImpl) {
         AttributePSVImpl var3 = (AttributePSVImpl)var2;
         this.fErrors = var3.fErrors != null ? (String[])var3.fErrors.clone() : null;
      } else {
         StringList var9 = var2.getErrorCodes();
         int var4 = var9.getLength();
         if (var4 > 0) {
            StringList var5 = var2.getErrorMessages();
            String[] var6 = new String[var4 << 1];
            int var7 = 0;

            for(int var8 = 0; var7 < var4; ++var7) {
               var6[var8++] = var9.item(var7);
               var6[var8++] = var5.item(var7);
            }

            this.fErrors = var6;
         }
      }

      this.fValidationContext = var2.getValidationContext();
      this.fIsConstant = var1;
   }

   public ItemPSVI constant() {
      return this.isConstant() ? this : new AttributePSVImpl(true, this);
   }

   public boolean isConstant() {
      return this.fIsConstant;
   }

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
