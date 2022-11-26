package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.policy.SubjectAttributeDesignatorType;
import org.w3c.dom.Element;

public class SubjectAttributeDesignatorTypeMarshaller extends AttributeDesignatorTypeMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      SubjectAttributeDesignatorType subjectAttributeDesignatorType = (SubjectAttributeDesignatorType)xmlObject;
      if (!Strings.isNullOrEmpty(subjectAttributeDesignatorType.getSubjectCategory())) {
         domElement.setAttributeNS((String)null, "SubjectCategory", subjectAttributeDesignatorType.getSubjectCategory());
      }

      super.marshallAttributes(xmlObject, domElement);
   }
}
