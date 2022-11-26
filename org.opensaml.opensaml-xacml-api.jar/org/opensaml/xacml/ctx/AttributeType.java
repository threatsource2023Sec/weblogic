package org.opensaml.xacml.ctx;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface AttributeType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Attribute";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Attribute", "xacml-context");
   String TYPE_LOCAL_NAME = "AttributeType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "AttributeType", "xacml-context");
   String ATTRIBUTEID_ATTTRIB_NAME = "AttributeId";
   String DATATYPE_ATTRIB_NAME = "DataType";
   String ISSUER_ATTRIB_NAME = "Issuer";

   String getAttributeId();

   List getAttributeValues();

   String getDataType();

   String getIssuer();

   void setAttributeID(String var1);

   void setDataType(String var1);

   void setIssuer(String var1);
}
