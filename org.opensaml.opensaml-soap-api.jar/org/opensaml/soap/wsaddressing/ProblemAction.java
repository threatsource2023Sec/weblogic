package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;

public interface ProblemAction extends AttributeExtensibleXMLObject, WSAddressingObject {
   String ELEMENT_LOCAL_NAME = "ProblemAction";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "ProblemAction", "wsa");
   String TYPE_LOCAL_NAME = "ProblemActionType";
   QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "ProblemActionType", "wsa");

   Action getAction();

   void setAction(Action var1);

   SoapAction getSoapAction();

   void setSoapAction(SoapAction var1);
}
