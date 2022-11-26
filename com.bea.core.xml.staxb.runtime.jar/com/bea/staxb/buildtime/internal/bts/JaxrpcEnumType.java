package com.bea.staxb.buildtime.internal.bts;

import com.bea.xml.XmlException;

public class JaxrpcEnumType extends BindingType {
   private BindingTypeName baseType;
   private MethodName getValueMethod;
   private MethodName fromValueMethod;
   private MethodName fromStringMethod;
   private MethodName toXMLMethod;
   public static final MethodName DEFAULT_GET_VALUE = MethodName.create("getValue");
   public static final String DEFAULT_FROM_VALUE_NAME = "fromValue";
   public static final MethodName DEFAULT_FROM_STRING = MethodName.create("fromString", JavaTypeName.forString("java.lang.String"));
   public static final MethodName DEFAULT_TO_XML = MethodName.create("toXML");
   private static final long serialVersionUID = 1L;

   public JaxrpcEnumType() {
   }

   public JaxrpcEnumType(BindingTypeName btName) {
      super(btName);
   }

   public void accept(BindingTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   public BindingTypeName getBaseTypeName() {
      return this.getBaseType();
   }

   public void setBaseType(BindingType bType) {
      this.setBaseType(bType.getName());
   }

   public MethodName getGetValueMethod() {
      return this.getValueMethod;
   }

   public void setGetValueMethod(MethodName getValueMethod) {
      this.getValueMethod = getValueMethod;
   }

   public MethodName getFromValueMethod() {
      return this.fromValueMethod;
   }

   public void setFromValueMethod(MethodName fromValueMethod) {
      this.fromValueMethod = fromValueMethod;
   }

   public MethodName getFromStringMethod() {
      return this.fromStringMethod;
   }

   public void setFromStringMethod(MethodName fromStringMethod) {
      this.fromStringMethod = fromStringMethod;
   }

   public MethodName getToXMLMethod() {
      return this.toXMLMethod;
   }

   public void setToXMLMethod(MethodName toXMLMethod) {
      this.toXMLMethod = toXMLMethod;
   }

   public BindingTypeName getBaseType() {
      return this.baseType;
   }

   public void setBaseType(BindingTypeName baseType) {
      this.baseType = baseType;
   }
}
