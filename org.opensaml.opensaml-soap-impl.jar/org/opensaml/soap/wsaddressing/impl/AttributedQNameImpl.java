package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.schema.impl.XSQNameImpl;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wsaddressing.AttributedQName;

public class AttributedQNameImpl extends XSQNameImpl implements AttributedQName {
   private AttributeMap unknownAttributes = new AttributeMap(this);

   public AttributedQNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }
}
