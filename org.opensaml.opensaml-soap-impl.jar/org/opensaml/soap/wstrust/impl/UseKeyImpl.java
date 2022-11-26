package org.opensaml.soap.wstrust.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.soap.wstrust.UseKey;

public class UseKeyImpl extends AbstractWSTrustObject implements UseKey {
   private XMLObject unknownChild;
   private String sig;

   public UseKeyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getSig() {
      return this.sig;
   }

   public void setSig(String newSig) {
      this.sig = this.prepareForAssignment(this.sig, newSig);
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
