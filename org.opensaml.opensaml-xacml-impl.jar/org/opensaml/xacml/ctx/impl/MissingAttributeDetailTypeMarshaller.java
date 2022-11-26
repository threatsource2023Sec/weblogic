package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.ctx.MissingAttributeDetailType;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.w3c.dom.Element;

public class MissingAttributeDetailTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      MissingAttributeDetailType madt = (MissingAttributeDetailType)xmlObject;
      if (madt.getAttributeId() != null) {
         domElement.setAttributeNS((String)null, "AttributeId", madt.getAttributeId());
      }

      if (madt.getDataType() != null) {
         domElement.setAttributeNS((String)null, "DataType", madt.getDataType());
      }

      if (madt.getIssuer() != null) {
         domElement.setAttributeNS((String)null, "Issuer", madt.getIssuer());
      }

   }
}
