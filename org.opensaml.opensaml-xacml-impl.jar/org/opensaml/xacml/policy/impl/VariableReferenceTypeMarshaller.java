package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.VariableReferenceType;
import org.w3c.dom.Element;

public class VariableReferenceTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      VariableReferenceType variableReferenceType = (VariableReferenceType)xmlObject;
      if (!Strings.isNullOrEmpty(variableReferenceType.getVariableId())) {
         domElement.setAttributeNS((String)null, "VariableId", variableReferenceType.getVariableId());
      }

   }
}
