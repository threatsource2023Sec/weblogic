package weblogic.apache.xerces.impl.xs;

import weblogic.apache.xerces.impl.dv.ValidatedInfo;
import weblogic.apache.xerces.impl.xs.util.StringListImpl;
import weblogic.apache.xerces.xs.ElementPSVI;
import weblogic.apache.xerces.xs.ItemPSVI;
import weblogic.apache.xerces.xs.ShortList;
import weblogic.apache.xerces.xs.StringList;
import weblogic.apache.xerces.xs.XSElementDeclaration;
import weblogic.apache.xerces.xs.XSModel;
import weblogic.apache.xerces.xs.XSNotationDeclaration;
import weblogic.apache.xerces.xs.XSSimpleTypeDefinition;
import weblogic.apache.xerces.xs.XSTypeDefinition;
import weblogic.apache.xerces.xs.XSValue;

public class ElementPSVImpl implements ElementPSVI {
   protected XSElementDeclaration fDeclaration = null;
   protected XSTypeDefinition fTypeDecl = null;
   protected boolean fNil = false;
   protected boolean fSpecified = false;
   protected ValidatedInfo fValue = new ValidatedInfo();
   protected XSNotationDeclaration fNotation = null;
   protected short fValidationAttempted = 0;
   protected short fValidity = 0;
   protected String[] fErrors = null;
   protected String fValidationContext = null;
   protected SchemaGrammar[] fGrammars = null;
   protected XSModel fSchemaInformation = null;
   protected boolean fIsConstant;

   public ElementPSVImpl() {
   }

   public ElementPSVImpl(boolean var1, ElementPSVI var2) {
      this.fDeclaration = var2.getElementDeclaration();
      this.fTypeDecl = var2.getTypeDefinition();
      this.fNil = var2.getNil();
      this.fSpecified = var2.getIsSchemaSpecified();
      this.fValue.copyFrom(var2.getSchemaValue());
      this.fNotation = var2.getNotation();
      this.fValidationAttempted = var2.getValidationAttempted();
      this.fValidity = var2.getValidity();
      this.fValidationContext = var2.getValidationContext();
      if (var2 instanceof ElementPSVImpl) {
         ElementPSVImpl var3 = (ElementPSVImpl)var2;
         this.fErrors = var3.fErrors != null ? (String[])var3.fErrors.clone() : null;
         var3.copySchemaInformationTo(this);
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

         this.fSchemaInformation = var2.getSchemaInformation();
      }

      this.fIsConstant = var1;
   }

   public ItemPSVI constant() {
      return this.isConstant() ? this : new ElementPSVImpl(true, this);
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

   public boolean getNil() {
      return this.fNil;
   }

   public XSNotationDeclaration getNotation() {
      return this.fNotation;
   }

   public XSTypeDefinition getTypeDefinition() {
      return this.fTypeDecl;
   }

   public XSSimpleTypeDefinition getMemberTypeDefinition() {
      return this.fValue.getMemberTypeDefinition();
   }

   public XSElementDeclaration getElementDeclaration() {
      return this.fDeclaration;
   }

   public synchronized XSModel getSchemaInformation() {
      if (this.fSchemaInformation == null && this.fGrammars != null) {
         this.fSchemaInformation = new XSModelImpl(this.fGrammars);
      }

      return this.fSchemaInformation;
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
      this.fDeclaration = null;
      this.fTypeDecl = null;
      this.fNil = false;
      this.fSpecified = false;
      this.fNotation = null;
      this.fValidationAttempted = 0;
      this.fValidity = 0;
      this.fErrors = null;
      this.fValidationContext = null;
      this.fValue.reset();
   }

   public void copySchemaInformationTo(ElementPSVImpl var1) {
      var1.fGrammars = this.fGrammars;
      var1.fSchemaInformation = this.fSchemaInformation;
   }
}
