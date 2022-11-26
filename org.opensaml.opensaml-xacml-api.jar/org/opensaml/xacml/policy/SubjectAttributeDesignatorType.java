package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;

public interface SubjectAttributeDesignatorType extends AttributeDesignatorType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectAttributeDesignator";
   QName DEFAULT_ELEMENT_QNAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "SubjectAttributeDesignator", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "SubjectAttributeDesignatorType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "SubjectAttributeDesignatorType", "xacml");
   String SUBJECT_CATEGORY_ATTRIB_NAME = "SubjectCategory";

   String getSubjectCategory();

   void setSubjectCategory(String var1);
}
