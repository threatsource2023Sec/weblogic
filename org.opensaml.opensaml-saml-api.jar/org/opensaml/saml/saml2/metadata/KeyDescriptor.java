package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.security.credential.UsageType;
import org.opensaml.xmlsec.signature.KeyInfo;

public interface KeyDescriptor extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "KeyDescriptor";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "KeyDescriptor", "md");
   String TYPE_LOCAL_NAME = "KeyDescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "KeyDescriptorType", "md");
   String USE_ATTRIB_NAME = "use";

   UsageType getUse();

   void setUse(UsageType var1);

   KeyInfo getKeyInfo();

   void setKeyInfo(KeyInfo var1);

   List getEncryptionMethods();
}
