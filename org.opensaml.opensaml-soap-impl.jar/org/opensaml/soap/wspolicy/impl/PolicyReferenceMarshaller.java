package org.opensaml.soap.wspolicy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wspolicy.PolicyReference;
import org.w3c.dom.Element;

public class PolicyReferenceMarshaller extends AbstractWSPolicyObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      PolicyReference pr = (PolicyReference)xmlObject;
      if (pr.getURI() != null) {
         domElement.setAttributeNS((String)null, "URI", pr.getURI());
      }

      if (pr.getDigest() != null) {
         domElement.setAttributeNS((String)null, "Digest", pr.getDigest());
      }

      if (pr.getDigestAlgorithm() != null) {
         domElement.setAttributeNS((String)null, "DigestAlgorithm", pr.getDigestAlgorithm());
      }

      XMLObjectSupport.marshallAttributeMap(pr.getUnknownAttributes(), domElement);
   }
}
