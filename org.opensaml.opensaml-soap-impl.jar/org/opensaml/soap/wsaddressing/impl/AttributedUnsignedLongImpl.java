package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wsaddressing.AttributedUnsignedLong;

public class AttributedUnsignedLongImpl extends AbstractWSAddressingObject implements AttributedUnsignedLong {
   private Long value;
   private AttributeMap unknownAttributes = new AttributeMap(this);

   public AttributedUnsignedLongImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public Long getValue() {
      return this.value;
   }

   public void setValue(Long newValue) {
      this.value = (Long)this.prepareForAssignment(this.value, newValue);
   }
}
