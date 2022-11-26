package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface SubjectsType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Subjects";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Subjects", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "SubjectsType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "SubjectsType", "xacml");

   List getSubjects();
}
