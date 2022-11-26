package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.VariableDefinitionType;
import org.w3c.dom.Element;

public class VariableDefinitionTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      VariableDefinitionType variableDefinitionType = (VariableDefinitionType)xmlObject;
      if (!Strings.isNullOrEmpty(variableDefinitionType.getVariableId())) {
         domElement.setAttributeNS((String)null, "VariableId", variableDefinitionType.getVariableId());
      }

   }
}
