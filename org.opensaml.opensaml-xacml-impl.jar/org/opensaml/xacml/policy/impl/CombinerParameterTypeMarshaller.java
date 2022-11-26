package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.CombinerParameterType;
import org.w3c.dom.Element;

public class CombinerParameterTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      CombinerParameterType combinerParameterType = (CombinerParameterType)xmlObject;
      if (!Strings.isNullOrEmpty(combinerParameterType.getParameterName())) {
         domElement.setAttributeNS((String)null, "ParameterName", combinerParameterType.getParameterName());
      }

   }
}
