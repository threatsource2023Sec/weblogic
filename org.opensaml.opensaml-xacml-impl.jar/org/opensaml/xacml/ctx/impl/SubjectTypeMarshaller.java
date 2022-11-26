package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.ctx.SubjectType;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.w3c.dom.Element;

public class SubjectTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      SubjectType attribute = (SubjectType)samlElement;
      if (attribute.getSubjectCategory() != null) {
         domElement.setAttributeNS((String)null, "SubjectCategory", attribute.getSubjectCategory());
      }

   }
}
