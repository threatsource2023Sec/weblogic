package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface SubjectType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Subject";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Subject", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "SubjectType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "SubjectType", "xacml");

   List getSubjectMatches();
}
