package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;

public interface Status extends WSTrustObject {
   String ELEMENT_LOCAL_NAME = "Status";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Status", "wst");

   Code getCode();

   void setCode(Code var1);

   Reason getReason();

   void setReason(Reason var1);
}
