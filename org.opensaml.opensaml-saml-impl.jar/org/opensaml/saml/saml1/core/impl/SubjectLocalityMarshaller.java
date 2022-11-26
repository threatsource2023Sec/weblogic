package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml1.core.SubjectLocality;
import org.w3c.dom.Element;

public class SubjectLocalityMarshaller extends AbstractSAMLObjectMarshaller {
   public void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      SubjectLocality subjectLocality = (SubjectLocality)samlElement;
      if (subjectLocality.getIPAddress() != null) {
         domElement.setAttributeNS((String)null, "IPAddress", subjectLocality.getIPAddress());
      }

      if (subjectLocality.getDNSAddress() != null) {
         domElement.setAttributeNS((String)null, "DNSAddress", subjectLocality.getDNSAddress());
      }

   }
}
