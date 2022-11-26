package org.opensaml.soap.wspolicy;

import java.util.List;
import javax.xml.namespace.QName;

public interface OperatorContentType extends WSPolicyObject {
   String TYPE_LOCAL_NAME = "OperatorContentType";
   QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "OperatorContentType", "wsp");

   List getPolicies();

   List getAlls();

   List getExactlyOnes();

   List getPolicyReferences();

   List getXMLObjects();

   List getXMLObjects(QName var1);
}
