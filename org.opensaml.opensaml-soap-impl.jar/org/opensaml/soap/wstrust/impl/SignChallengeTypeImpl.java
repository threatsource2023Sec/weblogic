package org.opensaml.soap.wstrust.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wstrust.Challenge;
import org.opensaml.soap.wstrust.SignChallengeType;

public class SignChallengeTypeImpl extends AbstractWSTrustObject implements SignChallengeType {
   private IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);
   private AttributeMap unknownAttributes = new AttributeMap(this);
   private Challenge challenge;

   public SignChallengeTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Challenge getChallenge() {
      return this.challenge;
   }

   public void setChallenge(Challenge newChallenge) {
      this.challenge = (Challenge)this.prepareForAssignment(this.challenge, newChallenge);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      List children = new ArrayList();
      if (this.challenge != null) {
         children.add(this.challenge);
      }

      children.addAll(this.unknownChildren);
      return Collections.unmodifiableList(children);
   }
}
