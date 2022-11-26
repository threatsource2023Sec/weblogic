package org.opensaml.xacml.ctx;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.xacml.XACMLObject;

public interface StatusMessageType extends XSString, XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "StatusMessage";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "StatusMessage", "xacml-context");
}
