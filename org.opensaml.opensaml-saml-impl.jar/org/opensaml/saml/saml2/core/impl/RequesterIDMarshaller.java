package org.opensaml.saml.saml2.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.RequesterID;
import org.w3c.dom.Element;

public class RequesterIDMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      RequesterID reqID = (RequesterID)samlObject;
      if (reqID.getRequesterID() != null) {
         ElementSupport.appendTextContent(domElement, reqID.getRequesterID());
      }

   }
}
