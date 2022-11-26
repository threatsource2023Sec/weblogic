package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wstrust.BinaryExchange;

public class BinaryExchangeImpl extends XSStringImpl implements BinaryExchange {
   private String valueType;
   private String encodingType;
   private AttributeMap unknownAttributes = new AttributeMap(this);

   public BinaryExchangeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getEncodingType() {
      return this.encodingType;
   }

   public void setEncodingType(String newEncodingType) {
      this.encodingType = this.prepareForAssignment(this.encodingType, newEncodingType);
   }

   public String getValueType() {
      return this.valueType;
   }

   public void setValueType(String newValueType) {
      this.valueType = this.prepareForAssignment(this.valueType, newValueType);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }
}
