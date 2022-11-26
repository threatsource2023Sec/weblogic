package org.opensaml.saml.ext.saml2mdattr;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface EntityAttributes extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EntityAttributes";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:attribute", "EntityAttributes", "mdattr");
   String TYPE_LOCAL_NAME = "EntityAttributesType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:attribute", "EntityAttributesType", "mdattr");

   List getAttributes();

   List getAssertions();
}
