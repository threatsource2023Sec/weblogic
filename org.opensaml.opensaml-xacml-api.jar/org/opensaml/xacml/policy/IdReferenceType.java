package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.xacml.XACMLObject;

public interface IdReferenceType extends XACMLObject, XSString {
   String POLICY_SET_ID_REFERENCE_ELEMENT_LOCAL_NAME = "PolicySetIdReference";
   QName POLICY_SET_ID_REFERENCE_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicySetIdReference", "xacml");
   String POLICY_ID_REFERENCE_ELEMENT_LOCAL_NAME = "PolicyIdReference";
   QName POLICY_ID_REFERENCE_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicyIdReference", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "IdReferenceType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "IdReferenceType", "xacml");
   String VERSION_ATTRIB_NAME = "Version";
   String EARLIEST_VERSION_ATTRIB_NAME = "EarliestVersion";
   String LATEST_VERSION_ATTRIB_NAME = "LatestVersion";

   String getVersion();

   void setVersion(String var1);

   String getEarliestVersion();

   void setEarliestVersion(String var1);

   String getLatestVersion();

   void setLatestVersion(String var1);
}
