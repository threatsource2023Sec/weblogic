package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.schema.impl.XSURIImpl;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wsaddressing.RelatesTo;

public class RelatesToImpl extends XSURIImpl implements RelatesTo {
   private String relationshipType;
   private AttributeMap unknownAttributes = new AttributeMap(this);

   public RelatesToImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
      this.setRelationshipType("http://www.w3.org/2005/08/addressing/reply");
   }

   public String getRelationshipType() {
      return this.relationshipType;
   }

   public void setRelationshipType(String newRelationshipType) {
      this.relationshipType = this.prepareForAssignment(this.relationshipType, newRelationshipType);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }
}
