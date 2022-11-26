package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;
import org.opensaml.xmlsec.signature.DEREncodedKeyValue;

public class DEREncodedKeyValueImpl extends XSBase64BinaryImpl implements DEREncodedKeyValue {
   private String id;

   protected DEREncodedKeyValueImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getID() {
      return this.id;
   }

   public void setID(String newID) {
      String oldID = this.id;
      this.id = this.prepareForAssignment(this.id, newID);
      this.registerOwnID(oldID, this.id);
   }
}
