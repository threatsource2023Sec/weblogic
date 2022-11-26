package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface AttributeDesignatorType extends ExpressionType {
   String SUBJECT_ATTRIBUTE_DESIGNATOR_ELEMENT_LOCAL_NAME = "SubjectAttributeDesignator";
   QName SUBJECT_ATTRIBUTE_DESIGNATOR_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "SubjectAttributeDesignator", "xacml");
   String RESOURCE_ATTRIBUTE_DESIGNATOR_ELEMENT_LOCAL_NAME = "ResourceAttributeDesignator";
   QName RESOURCE_ATTRIBUTE_DESIGNATOR_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ResourceAttributeDesignator", "xacml");
   String ACTION_ATTRIBUTE_DESIGNATOR_ELEMENT_LOCAL_NAME = "ActionAttributeDesignator";
   QName ACTION_ATTRIBUTE_DESIGNATOR_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ActionAttributeDesignator", "xacml");
   String ENVIRONMENT_ATTRIBUTE_DESIGNATOR_ELEMENT_LOCAL_NAME = "EnvironmentAttributeDesignator";
   QName ENVIRONMENT_ATTRIBUTE_DESIGNATOR_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "EnvironmentAttributeDesignator", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "AttributeDesignatorType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeDesignatorType", "xacml");
   String ATTRIBUTE_ID_ATTRIB_NAME = "AttributeId";
   String DATA_TYPE_ATTRIB_NAME = "DataType";
   String ISSUER_ATTRIB_NAME = "Issuer";
   String MUST_BE_PRESENT_ATTRIB_NAME = "MustBePresent";

   String getAttributeId();

   void setAttributeId(String var1);

   String getDataType();

   void setDataType(String var1);

   String getIssuer();

   void setIssuer(String var1);

   XSBooleanValue getMustBePresentXSBoolean();

   void setMustBePresentXSBoolean(XSBooleanValue var1);

   void setMustBePresent(Boolean var1);

   Boolean getMustBePresent();
}
