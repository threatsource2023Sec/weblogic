package org.opensaml.saml.saml2.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.GetComplete;
import org.w3c.dom.Element;

public class GetCompleteMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      GetComplete gc = (GetComplete)samlObject;
      if (gc.getGetComplete() != null) {
         ElementSupport.appendTextContent(domElement, gc.getGetComplete());
      }

   }
}
