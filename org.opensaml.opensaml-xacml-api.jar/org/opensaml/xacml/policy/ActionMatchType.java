package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface ActionMatchType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ActionMatch";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ActionMatch", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "ActionMatchType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ActionMatchType", "xacml");
   String MATCH_ID_ATTRIB_NAME = "MatchId";

   AttributeValueType getAttributeValue();

   void setAttributeValue(AttributeValueType var1);

   AttributeDesignatorType getActionAttributeDesignator();

   void setActionAttributeDesignator(AttributeDesignatorType var1);

   AttributeSelectorType getAttributeSelector();

   void setAttributeSelector(AttributeSelectorType var1);

   String getMatchId();

   void setMatchId(String var1);
}
