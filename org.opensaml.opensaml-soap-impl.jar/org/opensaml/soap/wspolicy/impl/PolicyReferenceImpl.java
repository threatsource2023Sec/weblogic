package org.opensaml.soap.wspolicy.impl;

import java.util.List;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wspolicy.PolicyReference;

public class PolicyReferenceImpl extends AbstractWSPolicyObject implements PolicyReference {
   private String uri;
   private String digest;
   private String digestAlgorithm;
   private AttributeMap unknownAttributes = new AttributeMap(this);

   public PolicyReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getDigest() {
      return this.digest;
   }

   public String getDigestAlgorithm() {
      return this.digestAlgorithm;
   }

   public String getURI() {
      return this.uri;
   }

   public void setDigest(String newDigest) {
      this.digest = this.prepareForAssignment(this.digest, newDigest);
   }

   public void setDigestAlgorithm(String newDigestAlgorithm) {
      this.digestAlgorithm = this.prepareForAssignment(this.digestAlgorithm, newDigestAlgorithm);
   }

   public void setURI(String newURI) {
      this.uri = this.prepareForAssignment(this.uri, newURI);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public List getOrderedChildren() {
      return null;
   }
}
