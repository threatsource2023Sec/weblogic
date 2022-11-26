package org.opensaml.xacml.ctx;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface ResponseType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Response";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Response", "xacml-context");
   String TYPE_LOCAL_NAME = "ResponseType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "ResponseType", "xacml-context");

   List getResults();
}
