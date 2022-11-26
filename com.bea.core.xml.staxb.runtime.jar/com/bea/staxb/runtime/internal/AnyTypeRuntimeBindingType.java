package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.BuiltinBindingType;
import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

class AnyTypeRuntimeBindingType extends RuntimeBindingType {
   private final GenericXmlMarshaller genericXmlMarshaller;
   static final QName ANY_TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "anyType");
   private static final BindingTypeName SOAP_ELEMENT_TYPE_NAME = createSoapElementTypeName();

   private static BindingTypeName createSoapElementTypeName() {
      JavaTypeName java_type = JavaTypeName.forClassName(SOAPElement.class.getName());
      XmlTypeName xml_type = XmlTypeName.forTypeNamed(ANY_TYPE_NAME);
      return BindingTypeName.forPair(java_type, xml_type);
   }

   QName getQName() {
      return ANY_TYPE_NAME;
   }

   AnyTypeRuntimeBindingType(BuiltinBindingType type, TypeConverter converter) throws XmlException {
      super(type, converter, converter);

      assert converter != null;

      this.genericXmlMarshaller = (GenericXmlMarshaller)converter;

      assert this.getQName().equals(type.getName().getXmlName().getQName());

   }

   void accept(RuntimeTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) throws XmlException {
   }

   boolean hasElementChildren() {
      return false;
   }

   protected RuntimeBindingType determineActualRuntimeType(Object property_value, MarshalResult result) throws XmlException {
      RuntimeBindingType rtt = result.determineDefaultRuntimeBindingType(this, property_value);
      if (rtt == null || rtt == this) {
         rtt = super.determineActualRuntimeType(property_value, result);
      }

      if (property_value == null) {
         return rtt;
      } else if (rtt != this) {
         return rtt;
      } else {
         rtt = this.determineSpecialCaseRuntimeType(property_value, result);
         if (rtt != null) {
            return rtt;
         } else {
            result.addError("unknown java type: " + property_value.getClass().getName());
            return this;
         }
      }
   }

   protected boolean needsXsiType(RuntimeBindingType expected) {
      return false;
   }

   protected boolean containsOwnContainingElement(Object instance) {
      return this.isSOAPElement(instance);
   }

   private RuntimeBindingType determineSpecialCaseRuntimeType(Object property_value, MarshalResult result) throws XmlException {
      return this.isSOAPElement(property_value) ? result.createRuntimeBindingType(this.getSoapElementTypeName()) : null;
   }

   BindingTypeName getSoapElementTypeName() {
      return SOAP_ELEMENT_TYPE_NAME;
   }

   private boolean isSOAPElement(Object property_value) {
      return SOAPElement.class.isInstance(property_value);
   }

   void marshalGenericXml(MarshalResult marshalResult, Object value) throws XmlException {
      this.genericXmlMarshaller.marshalGenericXml(marshalResult, value);
   }
}
