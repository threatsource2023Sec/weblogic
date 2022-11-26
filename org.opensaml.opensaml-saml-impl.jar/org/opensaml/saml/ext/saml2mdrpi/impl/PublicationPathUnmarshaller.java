package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2mdrpi.Publication;
import org.opensaml.saml.ext.saml2mdrpi.PublicationPath;

public class PublicationPathUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      PublicationPath pPath = (PublicationPath)parentObject;
      if (childObject instanceof Publication) {
         pPath.getPublications().add((Publication)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}
