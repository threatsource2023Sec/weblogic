package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface RequestedAuthnContext extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RequestedAuthnContext";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "RequestedAuthnContext", "saml2p");
   String TYPE_LOCAL_NAME = "RequestedAuthnContextType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "RequestedAuthnContextType", "saml2p");
   String COMPARISON_ATTRIB_NAME = "Comparison";

   AuthnContextComparisonTypeEnumeration getComparison();

   void setComparison(AuthnContextComparisonTypeEnumeration var1);

   List getAuthnContextClassRefs();

   List getAuthnContextDeclRefs();
}
