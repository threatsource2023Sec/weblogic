package org.python.apache.xerces.impl.xs;

import org.python.apache.xerces.impl.dv.ValidatedInfo;
import org.python.apache.xerces.impl.xs.util.StringListImpl;
import org.python.apache.xerces.xs.ElementPSVI;
import org.python.apache.xerces.xs.ShortList;
import org.python.apache.xerces.xs.StringList;
import org.python.apache.xerces.xs.XSElementDeclaration;
import org.python.apache.xerces.xs.XSModel;
import org.python.apache.xerces.xs.XSNotationDeclaration;
import org.python.apache.xerces.xs.XSSimpleTypeDefinition;
import org.python.apache.xerces.xs.XSTypeDefinition;
import org.python.apache.xerces.xs.XSValue;

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
