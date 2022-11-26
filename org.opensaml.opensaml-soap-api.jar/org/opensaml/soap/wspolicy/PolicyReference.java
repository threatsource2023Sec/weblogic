package org.opensaml.soap.wspolicy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;

public interface PolicyReference extends AttributeExtensibleXMLObject, WSPolicyObject {
   String ELEMENT_LOCAL_NAME = "PolicyReference";
   QName ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "PolicyReference", "wsp");
   String URI_ATTRIB_NAME = "URI";
   String DIGEST_ATTRIB_NAME = "Digest";
   String DIGEST_ALGORITHM_ATTRIB_NAME = "DigestAlgorithm";
   String DIGEST_ALGORITHM_SHA1EXC = "http://schemas.xmlsoap.org/ws/2004/09/policy/Sha1Exc";

   String getURI();

   void setURI(String var1);

   String getDigest();

   void setDigest(String var1);

   String getDigestAlgorithm();

   void setDigestAlgorithm(String var1);
}
