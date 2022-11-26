package org.opensaml.soap.wspolicy;

import java.util.List;
import javax.xml.namespace.QName;

public interface PolicyURIsBearing {
   String WSP_POLICY_URIS_ATTR_LOCAL_NAME = "PolicyURIs";
   QName WSP_POLICY_URIS_ATTR_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "PolicyURIs", "wsp");

   List getWSP12PolicyURIs();

   void setWSP12PolicyURIs(List var1);
}
