package org.python.apache.xerces.dom;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.python.apache.xerces.impl.dv.ValidatedInfo;
import org.python.apache.xerces.impl.xs.util.StringListImpl;
import org.python.apache.xerces.xs.ElementPSVI;
import org.python.apache.xerces.xs.ShortList;
import org.python.apache.xerces.xs.StringList;
import org.python.apache.xerces.xs.XSComplexTypeDefinition;
import org.python.apache.xerces.xs.XSElementDeclaration;
import org.python.apache.xerces.xs.XSModel;
import org.python.apache.xerces.xs.XSNotationDeclaration;
import org.python.apache.xerces.xs.XSSimpleTypeDefinition;
import org.python.apache.xerces.xs.XSTypeDefinition;
import org.python.apache.xerces.xs.XSValue;

public class PSVIElementNSImpl extends ElementNSImpl implements ElementPSVI {
   static final long serialVersionUID = 6815489624636016068L;
   protected XSElementDeclaration fDeclaration = null;
   protected XSTypeDefinition fTypeDecl = null;
   protected boolean fNil = false;
   protected boolean fSpecified = true;
   protected ValidatedInfo fValue = new ValidatedInfo();
   protected XSNotationDeclaration fNotation = null;
   protected short fValidationAttempted = 0;
   protected short fValidity = 0;
   protected StringList fErrorCodes = null;
   protected StringList fErrorMessages = null;
   protected String fValidationContext = null;
   protected XSModel fSchemaInformation = null;

   public PSVIElementNSImpl(CoreDocumentImpl var1, String var2, String var3, String var4) {
      super(var1, var2, var3, var4);
   }

   public PSVIElementNSImpl(CoreDocumentImpl var1, String var2, String var3) {
      super(var1, var2, var3);
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
      return (StringList)(this.fErrorCodes != null ? this.fErrorCodes : StringListImpl.EMPTY_LIST);
   }

   public StringList getErrorMessages() {
      return (StringList)(this.fErrorMessages != null ? this.fErrorMessages : StringListImpl.EMPTY_LIST);
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

   public XSModel getSchemaInformation() {
      return this.fSchemaInformation;
   }

   public void setPSVI(ElementPSVI var1) {
      this.fDeclaration = var1.getElementDeclaration();
      this.fNotation = var1.getNotation();
      this.fValidationContext = var1.getValidationContext();
      this.fTypeDecl = var1.getTypeDefinition();
      this.fSchemaInformation = var1.getSchemaInformation();
      this.fValidity = var1.getValidity();
      this.fValidationAttempted = var1.getValidationAttempted();
      this.fErrorCodes = var1.getErrorCodes();
      this.fErrorMessages = var1.getErrorMessages();
      if (!(this.fTypeDecl instanceof XSSimpleTypeDefinition) && (!(this.fTypeDecl instanceof XSComplexTypeDefinition) || ((XSComplexTypeDefinition)this.fTypeDecl).getContentType() != 1)) {
         this.fValue.reset();
      } else {
         this.fValue.copyFrom(var1.getSchemaValue());
      }

      this.fSpecified = var1.getIsSchemaSpecified();
      this.fNil = var1.getNil();
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

   private void writeObject(ObjectOutputStream var1) throws IOException {
      throw new NotSerializableException(this.getClass().getName());
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      throw new NotSerializableException(this.getClass().getName());
   }
}
