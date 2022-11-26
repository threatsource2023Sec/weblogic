package org.opensaml.saml.saml2.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.NameIDType;
import org.w3c.dom.Element;

public abstract class AbstractNameIDTypeMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      NameIDType nameID = (NameIDType)samlObject;
      if (nameID.getNameQualifier() != null) {
         domElement.setAttributeNS((String)null, "NameQualifier", nameID.getNameQualifier());
      }

      if (nameID.getSPNameQualifier() != null) {
         domElement.setAttributeNS((String)null, "SPNameQualifier", nameID.getSPNameQualifier());
      }

      if (nameID.getFormat() != null) {
         domElement.setAttributeNS((String)null, "Format", nameID.getFormat());
      }

      if (nameID.getSPProvidedID() != null) {
         domElement.setAttributeNS((String)null, "SPProvidedID", nameID.getSPProvidedID());
      }

   }

   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      NameIDType nameID = (NameIDType)samlObject;
      ElementSupport.appendTextContent(domElement, nameID.getValue());
   }
}
