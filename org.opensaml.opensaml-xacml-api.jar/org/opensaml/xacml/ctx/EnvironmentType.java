package org.opensaml.xacml.ctx;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface EnvironmentType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Environment";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Environment", "xacml-context");
   String TYPE_LOCAL_NAME = "EnvironmentType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "EnvironmentType", "xacml-context");

   List getAttributes();
}
