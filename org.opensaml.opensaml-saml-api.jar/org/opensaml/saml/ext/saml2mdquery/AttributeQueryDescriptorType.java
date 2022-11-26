package org.opensaml.saml.ext.saml2mdquery;

import java.util.List;
import javax.xml.namespace.QName;

public interface AttributeQueryDescriptorType extends QueryDescriptorType {
   String TYPE_LOCAL_NAME = "AttributeQueryDescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ext:query", "AttributeQueryDescriptorType", "query");

   List getAttributeConsumingServices();
}
