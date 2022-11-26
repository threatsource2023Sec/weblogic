package org.opensaml.soap.wspolicy.impl;

import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wspolicy.Policy;
import org.opensaml.soap.wssecurity.IdBearing;

public class PolicyImpl extends OperatorContentTypeImpl implements Policy {
   private String id;
   private String name;
   private AttributeMap unknownAttributes = new AttributeMap(this);

   protected PolicyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String newName) {
      this.name = this.prepareForAssignment(this.name, newName);
   }

   public String getWSUId() {
      return this.id;
   }

   public void setWSUId(String newId) {
      String oldId = this.id;
      this.id = this.prepareForAssignment(this.id, newId);
      this.registerOwnID(oldId, this.id);
      this.manageQualifiedAttributeNamespace(IdBearing.WSU_ID_ATTR_NAME, this.id != null);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }
}
