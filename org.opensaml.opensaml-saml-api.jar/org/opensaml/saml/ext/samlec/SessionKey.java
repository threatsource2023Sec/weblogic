package org.opensaml.saml.ext.samlec;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;
import org.opensaml.xmlsec.signature.KeyInfo;

public interface SessionKey extends SAMLObject, MustUnderstandBearing, ActorBearing {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SessionKey";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:ietf:params:xml:ns:samlec", "SessionKey", "samlec");
   String TYPE_LOCAL_NAME = "SessionKeyType";
   QName TYPE_NAME = new QName("urn:ietf:params:xml:ns:samlec", "SessionKeyType", "samlec");
   String ALGORITHM_ATTRIB_NAME = "Algorithm";

   String getAlgorithm();

   void setAlgorithm(String var1);

   List getEncTypes();

   KeyInfo getKeyInfo();

   void setKeyInfo(KeyInfo var1);
}
