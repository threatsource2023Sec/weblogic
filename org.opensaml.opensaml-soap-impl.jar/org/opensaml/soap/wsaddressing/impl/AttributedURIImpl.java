package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.schema.impl.XSURIImpl;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wsaddressing.AttributedURI;

public class AttributedURIImpl extends XSURIImpl implements AttributedURI {
   private AttributeMap unknownAttributes = new AttributeMap(this);

   public AttributedURIImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }
}
