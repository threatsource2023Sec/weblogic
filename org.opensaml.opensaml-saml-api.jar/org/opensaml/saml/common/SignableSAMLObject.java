package org.opensaml.saml.common;

import javax.annotation.Nullable;
import org.opensaml.xmlsec.signature.SignableXMLObject;

public interface SignableSAMLObject extends SignableXMLObject, SAMLObject {
   @Nullable
   String getSignatureReferenceID();
}
