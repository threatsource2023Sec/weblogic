package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.policy.AttributeAssignmentType;
import org.w3c.dom.Element;

public class AttributeAssignmentTypeMarshaller extends AttributeValueTypeMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      AttributeAssignmentType attributeAssignment = (AttributeAssignmentType)samlElement;
      if (!Strings.isNullOrEmpty(attributeAssignment.getAttributeId())) {
         domElement.setAttributeNS((String)null, "AttributeId", attributeAssignment.getAttributeId());
      }

      if (!Strings.isNullOrEmpty(attributeAssignment.getDataType())) {
         super.marshallAttributes(samlElement, domElement);
      }

   }
}
