package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;

public interface AgreementMethod extends XMLObject, ElementExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AgreementMethod";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "AgreementMethod", "xenc");
   String TYPE_LOCAL_NAME = "AgreementMethodType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "AgreementMethodType", "xenc");
   String ALGORITHM_ATTRIBUTE_NAME = "Algorithm";

   @Nullable
   String getAlgorithm();

   void setAlgorithm(@Nullable String var1);

   @Nullable
   KANonce getKANonce();

   void setKANonce(@Nullable KANonce var1);

   @Nullable
   OriginatorKeyInfo getOriginatorKeyInfo();

   void setOriginatorKeyInfo(@Nullable OriginatorKeyInfo var1);

   @Nullable
   RecipientKeyInfo getRecipientKeyInfo();

   void setRecipientKeyInfo(@Nullable RecipientKeyInfo var1);
}
