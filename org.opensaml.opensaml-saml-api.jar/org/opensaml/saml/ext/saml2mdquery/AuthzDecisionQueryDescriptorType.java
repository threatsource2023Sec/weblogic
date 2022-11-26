package org.opensaml.saml.ext.saml2mdquery;

import java.util.List;
import javax.xml.namespace.QName;

public interface AuthzDecisionQueryDescriptorType extends QueryDescriptorType {
   String TYPE_LOCAL_NAME = "AuthzDecisionQueryDescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ext:query", "AuthzDecisionQueryDescriptorType", "query");

   List getActionNamespaces();
}
