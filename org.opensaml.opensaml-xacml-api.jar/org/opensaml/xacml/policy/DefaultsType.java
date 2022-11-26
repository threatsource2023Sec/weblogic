package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.xacml.XACMLObject;

public interface DefaultsType extends XACMLObject {
   String POLICY_SET_DEFAULTS_ELEMENT_LOCAL_NAME = "PolicySetDefaults";
   QName POLICY_SET_DEFAULTS_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicySetDefaults", "xacml");
   String POLICY_DEFAULTS_ELEMENT_LOCAL_NAME = "PolicyDefaults";
   QName POLICY_DEFAULTS_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicyDefaults", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "DefaultsType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "DefaultsType", "xacml");

   XSString getXPathVersion();

   void setXPathVersion(XSString var1);
}
