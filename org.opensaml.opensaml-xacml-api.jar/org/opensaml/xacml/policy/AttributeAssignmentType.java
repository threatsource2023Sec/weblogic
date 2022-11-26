package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;

public interface AttributeAssignmentType extends AttributeValueType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeAssignment";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeAssignment", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "AttributeAssignmentType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeAssignmentType", "xacml");
   String ATTR_ID_ATTRIB_NAME = "AttributeId";

   String getAttributeId();

   void setAttributeId(String var1);
}
