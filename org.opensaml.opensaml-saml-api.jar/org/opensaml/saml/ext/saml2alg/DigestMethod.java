package org.opensaml.saml.ext.saml2alg;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface DigestMethod extends SAMLObject, ElementExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "DigestMethod";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:algsupport", "DigestMethod", "alg");
   String TYPE_LOCAL_NAME = "DigestMethodType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:algsupport", "DigestMethodType", "alg");
   String ALGORITHM_ATTRIB_NAME = "Algorithm";

   @Nullable
   String getAlgorithm();

   void setAlgorithm(@Nullable String var1);
}
