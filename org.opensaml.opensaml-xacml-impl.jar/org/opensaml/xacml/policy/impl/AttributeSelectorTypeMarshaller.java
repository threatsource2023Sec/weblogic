package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.AttributeSelectorType;
import org.w3c.dom.Element;

public class AttributeSelectorTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributeSelectorType attributeSelectorType = (AttributeSelectorType)xmlObject;
      if (!Strings.isNullOrEmpty(attributeSelectorType.getDataType())) {
         domElement.setAttributeNS((String)null, "DataType", attributeSelectorType.getDataType());
      }

      if (!Strings.isNullOrEmpty(attributeSelectorType.getRequestContextPath())) {
         domElement.setAttributeNS((String)null, "RequestContextPath", attributeSelectorType.getRequestContextPath());
      }

      if (attributeSelectorType.getMustBePresentXSBoolean() != null) {
         domElement.setAttributeNS((String)null, "MustBePresent", Boolean.toString(attributeSelectorType.getMustBePresentXSBoolean().getValue()));
      }

   }
}
