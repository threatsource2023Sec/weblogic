package org.opensaml.soap.wstrust.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wstrust.Participants;
import org.opensaml.soap.wstrust.Primary;

public class ParticipantsImpl extends AbstractWSTrustObject implements Participants {
   private Primary primary;
   private List participants = new ArrayList();
   private IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);

   public ParticipantsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Primary getPrimary() {
      return this.primary;
   }

   public void setPrimary(Primary newPrimary) {
      this.primary = (Primary)this.prepareForAssignment(this.primary, newPrimary);
   }

   public List getParticipants() {
      return this.participants;
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      List children = new ArrayList();
      if (this.primary != null) {
         children.add(this.primary);
      }

      children.addAll(this.participants);
      children.addAll(this.unknownChildren);
      return Collections.unmodifiableList(children);
   }
}
