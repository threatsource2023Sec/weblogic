package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wssecurity.Reference;

public class ReferenceImpl extends AbstractWSSecurityObject implements Reference {
   private String uri;
   private String valueType;
   private AttributeMap unknownAttributes = new AttributeMap(this);

   public ReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getURI() {
      return this.uri;
   }

   public void setURI(String newURI) {
      this.uri = this.prepareForAssignment(this.uri, newURI);
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
