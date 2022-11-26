package org.opensaml.soap.wstrust.impl;

import java.util.List;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wstrust.BinarySecret;

public class BinarySecretImpl extends XSBase64BinaryImpl implements BinarySecret {
   private String type;
   private AttributeMap unknownChildren = new AttributeMap(this);

   public BinarySecretImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getType() {
      return this.type;
   }

   public void setType(String newType) {
      this.type = this.prepareForAssignment(this.type, newType);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownChildren;
   }

   public List getOrderedChildren() {
      return null;
   }
}
