package org.opensaml.soap.wspolicy.impl;

import org.opensaml.soap.wspolicy.PolicyAttachment;

public class PolicyAttachmentBuilder extends AbstractWSPolicyObjectBuilder {
   public PolicyAttachment buildObject() {
      return (PolicyAttachment)this.buildObject(PolicyAttachment.ELEMENT_NAME);
   }

   public PolicyAttachment buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PolicyAttachmentImpl(namespaceURI, localName, namespacePrefix);
   }
}
