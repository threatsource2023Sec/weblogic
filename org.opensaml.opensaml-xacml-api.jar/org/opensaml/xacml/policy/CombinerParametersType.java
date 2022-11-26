package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface CombinerParametersType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "CombinerParameters";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "CombinerParameters", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "CombinerParametersType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "CombinerParametersType", "xacml");

   List getCombinerParameters();
}
