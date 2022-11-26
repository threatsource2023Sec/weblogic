package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.IDPEntry;
import org.w3c.dom.Element;

public class IDPEntryMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      IDPEntry entry = (IDPEntry)samlObject;
      if (entry.getProviderID() != null) {
         domElement.setAttributeNS((String)null, "ProviderID", entry.getProviderID());
      }

      if (entry.getName() != null) {
         domElement.setAttributeNS((String)null, "Name", entry.getName());
      }

      if (entry.getLoc() != null) {
         domElement.setAttributeNS((String)null, "Loc", entry.getLoc());
      }

   }
}
