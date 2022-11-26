package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.AttributeDesignatorType;
import org.w3c.dom.Element;

public class AttributeDesignatorTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributeDesignatorType attributeDesignatorType = (AttributeDesignatorType)xmlObject;
      if (!Strings.isNullOrEmpty(attributeDesignatorType.getAttributeId())) {
         domElement.setAttributeNS((String)null, "AttributeId", attributeDesignatorType.getAttributeId());
      }

      if (!Strings.isNullOrEmpty(attributeDesignatorType.getDataType())) {
         domElement.setAttributeNS((String)null, "DataType", attributeDesignatorType.getDataType());
      }

      if (!Strings.isNullOrEmpty(attributeDesignatorType.getIssuer())) {
         domElement.setAttributeNS((String)null, "Issuer", attributeDesignatorType.getIssuer());
      }

      if (attributeDesignatorType.getMustBePresentXSBoolean() != null) {
         domElement.setAttributeNS((String)null, "MustBePresent", Boolean.toString(attributeDesignatorType.getMustBePresentXSBoolean().getValue()));
      }

   }
}
