package org.opensaml.xacml.ctx;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface ActionType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Action";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Action", "xacml-context");
   String TYPE_LOCAL_NAME = "ActionType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "ActionType", "xacml-context");

   List getAttributes();
}
