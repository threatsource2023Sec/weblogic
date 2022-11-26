package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface ResourceMatchType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ResourceMatch";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ResourceMatch", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "ResourceMatchType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ResourceMatchType", "xacml");
   String MATCH_ID_ATTRIB_NAME = "MatchId";

   AttributeValueType getAttributeValue();

   void setAttributeValue(AttributeValueType var1);

   AttributeDesignatorType getResourceAttributeDesignator();

   void setResourceAttributeDesignator(AttributeDesignatorType var1);

   AttributeSelectorType getAttributeSelector();

   void setAttributeSelector(AttributeSelectorType var1);

   String getMatchId();

   void setMatchId(String var1);
}
