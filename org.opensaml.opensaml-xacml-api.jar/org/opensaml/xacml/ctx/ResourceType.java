package org.opensaml.xacml.ctx;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface ResourceType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Resource";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Resource", "xacml-context");
   String TYPE_LOCAL_NAME = "ResourceType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "ResourceType", "xacml-context");

   ResourceContentType getResourceContent();

   void setResourceContent(ResourceContentType var1);

   List getAttributes();
}
