package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface EnvironmentsType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Environments";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Environments", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "EnvironmentsType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "EnvironmentsType", "xacml");

   List getEnvironments();
}
