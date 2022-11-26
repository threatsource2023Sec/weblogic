package org.opensaml.soap.wspolicy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wspolicy.AppliesTo;
import org.opensaml.soap.wspolicy.Policy;
import org.opensaml.soap.wspolicy.PolicyAttachment;
import org.opensaml.soap.wspolicy.PolicyReference;
import org.w3c.dom.Attr;

public class PolicyAttachmentUnmarshaller extends AbstractWSPolicyObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      PolicyAttachment pa = (PolicyAttachment)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(pa.getUnknownAttributes(), attribute);
   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      PolicyAttachment pa = (PolicyAttachment)parentXMLObject;
      if (childXMLObject instanceof AppliesTo) {
         pa.setAppliesTo((AppliesTo)childXMLObject);
      } else if (childXMLObject instanceof Policy) {
         pa.getPolicies().add((Policy)childXMLObject);
      } else if (childXMLObject instanceof PolicyReference) {
         pa.getPolicyReferences().add((PolicyReference)childXMLObject);
      } else {
         pa.getUnknownXMLObjects().add(childXMLObject);
      }

   }
}
