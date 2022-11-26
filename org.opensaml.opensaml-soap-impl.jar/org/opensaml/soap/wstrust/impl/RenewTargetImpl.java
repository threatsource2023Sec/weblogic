package org.opensaml.soap.wstrust.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.soap.wstrust.RenewTarget;

public class RenewTargetImpl extends AbstractWSTrustObject implements RenewTarget {
   private XMLObject unknownChild;

   public RenewTargetImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public XMLObject getUnknownXMLObject() {
      return this.unknownChild;
   }

   public void setUnknownXMLObject(XMLObject unknownObject) {
      this.unknownChild = unknownObject;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.unknownChild != null) {
         children.add(this.unknownChild);
      }

      return Collections.unmodifiableList(children);
   }
}
