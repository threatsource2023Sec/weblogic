package org.opensaml.soap.wspolicy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wspolicy.PolicyAttachment;
import org.w3c.dom.Element;

public class PolicyAttachmentMarshaller extends AbstractWSPolicyObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      PolicyAttachment pa = (PolicyAttachment)xmlObject;
      XMLObjectSupport.marshallAttributeMap(pa.getUnknownAttributes(), domElement);
   }
}
