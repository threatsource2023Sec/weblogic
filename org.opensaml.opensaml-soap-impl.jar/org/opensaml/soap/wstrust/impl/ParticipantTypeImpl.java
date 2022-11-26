package org.opensaml.soap.wstrust.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.soap.wstrust.ParticipantType;

public class ParticipantTypeImpl extends AbstractWSTrustObject implements ParticipantType {
   private XMLObject unknownChild;

   public ParticipantTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public XMLObject getUnknownXMLObject() {
      return this.unknownChild;
   }

   public void setUnknownXMLObject(XMLObject unknownObject) {
      this.unknownChild = this.prepareForAssignment(this.unknownChild, unknownObject);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.unknownChild != null) {
         children.add(this.unknownChild);
      }

      return Collections.unmodifiableList(children);
   }
}
