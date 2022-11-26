package org.opensaml.xacml.ctx;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.xacml.XACMLObject;

public interface AttributeValueType extends XACMLObject, ElementExtensibleXMLObject, AttributeExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeValue";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "AttributeValue", "xacml-context");
   String TYPE_LOCAL_NAME = "AttributeValueType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "AttributeValueType", "xacml-context");

   String getValue();

   void setValue(String var1);
}
