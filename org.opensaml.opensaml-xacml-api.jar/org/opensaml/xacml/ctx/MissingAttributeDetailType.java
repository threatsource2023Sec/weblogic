package org.opensaml.xacml.ctx;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface MissingAttributeDetailType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "MissingAttributeDetail";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "MissingAttributeDetail", "xacml-context");
   String SCHEMA_TYPE_LOCAL_NAME = "MissingAttributeDetailType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "MissingAttributeDetailType", "xacml");
   String ATTRIBUTE_ID_ATTRIB_NAME = "AttributeId";
   String DATA_TYPE_ATTRIB_NAME = "DataType";
   String ISSUER_ATTRIB_NAME = "Issuer";

   List getAttributeValues();

   String getAttributeId();

   void setAttributeId(String var1);

   String getDataType();

   void setDataType(String var1);

   String getIssuer();

   void setIssuer(String var1);
}
