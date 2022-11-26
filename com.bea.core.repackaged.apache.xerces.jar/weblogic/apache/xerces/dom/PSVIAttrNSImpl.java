package weblogic.apache.xerces.dom;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import weblogic.apache.xerces.impl.dv.ValidatedInfo;
import weblogic.apache.xerces.impl.xs.AttributePSVImpl;
import weblogic.apache.xerces.impl.xs.util.StringListImpl;
import weblogic.apache.xerces.xs.AttributePSVI;
import weblogic.apache.xerces.xs.ItemPSVI;
import weblogic.apache.xerces.xs.ShortList;
import weblogic.apache.xerces.xs.StringList;
import weblogic.apache.xerces.xs.XSAttributeDeclaration;
import weblogic.apache.xerces.xs.XSSimpleTypeDefinition;
import weblogic.apache.xerces.xs.XSTypeDefinition;
import weblogic.apache.xerces.xs.XSValue;

public class PSVIAttrNSImpl extends AttrNSImpl implements AttributePSVI {
   static final long serialVersionUID = -3241738699421018889L;
   protected XSAttributeDeclaration fDeclaration = null;
   protected XSTypeDefinition fTypeDecl = null;
   protected boolean fSpecified = true;
   protected ValidatedInfo fValue = new ValidatedInfo();
   protected short fValidationAttempted = 0;
   protected short fValidity = 0;
   protected StringList fErrorCodes = null;
   protected StringList fErrorMessages = null;
   protected String fValidationContext = null;

   public PSVIAttrNSImpl(CoreDocumentImpl var1, String var2, String var3, String var4) {
      super(var1, var2, var3, var4);
   }

   public PSVIAttrNSImpl(CoreDocumentImpl var1, String var2, String var3) {
      super(var1, var2, var3);
   }

   public ItemPSVI constant() {
      return new AttributePSVImpl(true, this);
   }

   public boolean isConstant() {
      return false;
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

   public XSTypeDefinition getTypeDefinition() {
      return this.fTypeDecl;
   }

   public XSSimpleTypeDefinition getMemberTypeDefinition() {
      return this.fValue.getMemberTypeDefinition();
   }

   public XSAttributeDeclaration getAttributeDeclaration() {
      return this.fDeclaration;
   }

   public void setPSVI(AttributePSVI var1) {
      this.fDeclaration = var1.getAttributeDeclaration();
      this.fValidationContext = var1.getValidationContext();
      this.fValidity = var1.getValidity();
      this.fValidationAttempted = var1.getValidationAttempted();
      this.fErrorCodes = var1.getErrorCodes();
      this.fErrorMessages = var1.getErrorMessages();
      this.fValue.copyFrom(var1.getSchemaValue());
      this.fTypeDecl = var1.getTypeDefinition();
      this.fSpecified = var1.getIsSchemaSpecified();
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
