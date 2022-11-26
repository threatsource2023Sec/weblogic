package org.opensaml.soap.wsfed.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.soap.wsfed.AppliesTo;
import org.opensaml.soap.wsfed.EndPointReference;

public class AppliesToImpl extends AbstractXMLObject implements AppliesTo {
   private EndPointReference endPointReference;

   protected AppliesToImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public EndPointReference getEndPointReference() {
      return this.endPointReference;
   }

   public void setEndPointReference(EndPointReference reference) {
      this.endPointReference = (EndPointReference)this.prepareForAssignment(this.endPointReference, reference);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.add(this.endPointReference);
      return Collections.unmodifiableList(children);
   }
}
