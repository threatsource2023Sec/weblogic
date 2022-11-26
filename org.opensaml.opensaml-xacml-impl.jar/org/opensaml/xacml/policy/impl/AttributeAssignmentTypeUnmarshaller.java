package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.policy.AttributeAssignmentType;
import org.w3c.dom.Attr;

public class AttributeAssignmentTypeUnmarshaller extends AttributeValueTypeUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      AttributeAssignmentType attrib = (AttributeAssignmentType)samlObject;
      if (attribute.getLocalName().equals("AttributeId")) {
         attrib.setAttributeId(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
