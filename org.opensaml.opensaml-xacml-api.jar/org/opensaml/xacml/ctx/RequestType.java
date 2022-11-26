package org.opensaml.xacml.ctx;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface RequestType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Request";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Request", "xacml-context");
   String TYPE_LOCAL_NAME = "RequestType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "RequestType", "xacml-context");

   List getSubjects();

   List getResources();

   ActionType getAction();

   void setAction(ActionType var1);

   EnvironmentType getEnvironment();

   void setEnvironment(EnvironmentType var1);
}
