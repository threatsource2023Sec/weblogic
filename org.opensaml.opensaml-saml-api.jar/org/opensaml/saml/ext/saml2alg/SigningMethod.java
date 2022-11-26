package org.opensaml.saml.ext.saml2alg;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface SigningMethod extends SAMLObject, ElementExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SigningMethod";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:algsupport", "SigningMethod", "alg");
   String TYPE_LOCAL_NAME = "SigningMethodType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:algsupport", "SigningMethodType", "alg");
   String ALGORITHM_ATTRIB_NAME = "Algorithm";
   String MIN_KEY_SIZE_ATTRIB_NAME = "MinKeySize";
   String MAX_KEY_SIZE_ATTRIB_NAME = "MaxKeySize";

   @Nullable
   String getAlgorithm();

   void setAlgorithm(@Nullable String var1);

   @Nullable
   Integer getMinKeySize();

   void setMinKeySize(@Nullable Integer var1);

   @Nullable
   Integer getMaxKeySize();

   void setMaxKeySize(@Nullable Integer var1);
}
