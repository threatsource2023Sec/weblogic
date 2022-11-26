package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wssecurity.AttributedURI;
import org.opensaml.soap.wssecurity.IdBearing;

public class AttributedURIImpl extends AbstractWSSecurityObject implements AttributedURI {
   private String value;
   private String id;
   private AttributeMap attributes = new AttributeMap(this);

   public AttributedURIImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String newValue) {
      this.value = this.prepareForAssignment(this.value, newValue);
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
      return this.attributes;
   }
}
