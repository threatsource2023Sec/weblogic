package org.opensaml.saml.ext.saml2cb;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface ChannelBindings extends XSBase64Binary, MustUnderstandBearing, ActorBearing, SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ChannelBindings";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:protocol:ext:channel-binding", "ChannelBindings", "cb");
   String TYPE_LOCAL_NAME = "ChannelBindingsType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:protocol:ext:channel-binding", "ChannelBindingsType", "cb");
   String TYPE_ATTRIB_NAME = "Type";

   String getType();

   void setType(String var1);
}
