package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface ObligationsType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Obligations";
   QName DEFAULT_ELEMENT_QNAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Obligations", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "ObligationsType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ObligationsType", "xacml");

   List getObligations();
}
