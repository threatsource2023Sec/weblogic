package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.ext.saml2mdquery.QueryDescriptorType;
import org.opensaml.saml.saml2.metadata.impl.RoleDescriptorMarshaller;
import org.w3c.dom.Element;

public abstract class QueryDescriptorTypeMarshaller extends RoleDescriptorMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      QueryDescriptorType descriptor = (QueryDescriptorType)xmlObject;
      if (descriptor.getWantAssertionsSignedXSBoolean() != null) {
         domElement.setAttributeNS((String)null, "WantAssertionsSigned", descriptor.getWantAssertionsSignedXSBoolean().toString());
      }

      super.marshallAttributes(xmlObject, domElement);
   }
}
