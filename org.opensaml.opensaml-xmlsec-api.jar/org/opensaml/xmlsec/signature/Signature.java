package org.opensaml.xmlsec.signature;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.security.credential.Credential;

public interface Signature extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Signature";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Signature", "ds");
   String TYPE_LOCAL_NAME = "SignatureType";
   QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureType", "ds");

   @Nullable
   String getCanonicalizationAlgorithm();

   void setCanonicalizationAlgorithm(@Nullable String var1);

   @Nullable
   String getSignatureAlgorithm();

   void setSignatureAlgorithm(@Nullable String var1);

   @Nullable
   Integer getHMACOutputLength();

   void setHMACOutputLength(@Nullable Integer var1);

   @Nullable
   Credential getSigningCredential();

   void setSigningCredential(@Nullable Credential var1);

   @Nullable
   KeyInfo getKeyInfo();

   void setKeyInfo(@Nullable KeyInfo var1);

   @Nonnull
   List getContentReferences();
}
