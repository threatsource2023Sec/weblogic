package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Action extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Action";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Action", "saml2");
   String TYPE_LOCAL_NAME = "ActionType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "ActionType", "saml2");
   String NAMEPSACE_ATTRIB_NAME = "Namespace";
   String RWEDC_NS_URI = "urn:oasis:names:tc:SAML:1.0:action:rwedc";
   String RWEDC_NEGATION_NS_URI = "urn:oasis:names:tc:SAML:1.0:action:rwedc-negation";
   String GHPP_NS_URI = "urn:oasis:names:tc:SAML:1.0:action:ghpp";
   String UNIX_NS_URI = "urn:oasis:names:tc:SAML:1.0:action:unix";
   String READ_ACTION = "Read";
   String WRITE_ACTION = "Write";
   String EXECUTE_ACTION = "Execute";
   String DELETE_ACTION = "Delete";
   String CONTROL_ACTION = "Control";
   String NEG_READ_ACTION = "~Read";
   String NEG_WRITE_ACTION = "~Write";
   String NEG_EXECUTE_ACTION = "~Execute";
   String NEG_DELETE_ACTION = "~Delete";
   String NEG_CONTROL_ACTION = "~Control";
   String HTTP_GET_ACTION = "GET";
   String HTTP_HEAD_ACTION = "HEAD";
   String HTTP_PUT_ACTION = "PUT";
   String HTTP_POST_ACTION = "POST";

   String getNamespace();

   void setNamespace(String var1);

   String getAction();

   void setAction(String var1);
}
