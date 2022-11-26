package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface EnvironmentMatchType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EnvironmentMatch";
   QName DEFAULT_ELEMENT_QNAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "EnvironmentMatch", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "EnvironmentMatchType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "EnvironmentMatchType", "xacml");
   String MATCH_ID_ATTRIB_NAME = "MatchId";

   AttributeValueType getAttributeValue();

   void setAttributeValue(AttributeValueType var1);

   AttributeDesignatorType getEnvironmentAttributeDesignator();

   void setEnvironmentAttributeDesignator(AttributeDesignatorType var1);

   AttributeSelectorType getAttributeSelector();

   void setAttributeSelector(AttributeSelectorType var1);

   String getMatchId();

   void setMatchId(String var1);
}
