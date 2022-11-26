package org.opensaml.soap.wspolicy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface PolicyAttachment extends WSPolicyObject, ElementExtensibleXMLObject, AttributeExtensibleXMLObject {
   String ELEMENT_LOCAL_NAME = "PolicyAttachment";
   QName ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "PolicyAttachment", "wsp");

   AppliesTo getAppliesTo();

   void setAppliesTo(AppliesTo var1);

   List getPolicies();

   List getPolicyReferences();
}
