package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface XPathVersion extends XACMLObject {
   String ELEMENT_LOCAL_NAME = "XPathVersion";
   QName DEFAULTS_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "XPathVersion", "xacml");
}
