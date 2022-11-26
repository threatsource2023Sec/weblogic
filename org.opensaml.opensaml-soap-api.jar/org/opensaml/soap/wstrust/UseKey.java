package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface UseKey extends WSTrustObject {
   String ELEMENT_LOCAL_NAME = "UseKey";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "UseKey", "wst");
   String TYPE_LOCAL_NAME = "UseKeyType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "UseKeyType", "wst");
   String SIG_ATTRIB_NAME = "Sig";

   XMLObject getUnknownXMLObject();

   void setUnknownXMLObject(XMLObject var1);

   String getSig();

   void setSig(String var1);
}
