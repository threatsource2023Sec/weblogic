package org.opensaml.saml.saml2.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.NewID;
import org.w3c.dom.Element;

public class NewIDMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      NewID newID = (NewID)samlObject;
      if (newID.getNewID() != null) {
         ElementSupport.appendTextContent(domElement, newID.getNewID());
      }

   }
}
